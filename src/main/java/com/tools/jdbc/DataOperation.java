package com.tools.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DataOperation {
	public Logger log = LoggerFactory.getLogger(DataOperation.class);
	
	public Connection con = DataSource.getConnection();
	
	public Map<String,Object> getById(String table, String key, String id) throws Exception {
		String sql = "select * from "+table+" where "+key+"=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		Map<String,Object> map = new HashMap<String,Object>();
		while(rs.next()){
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int i=1;i<=count;i++){
				String columnName = rsmd.getColumnName(i);
				String fchar = columnName.charAt(0) + "";
				if(fchar.matches("\\d")){
					columnName  = "_" + columnName;
				}
				map.put(columnName, rs.getObject(i));
			}
		}
		
		rs.close();
		ps.close();
		return map;
	}
	
	public List get(String table, String key,String pageHead,String pageEnd) throws Exception {
		String sql = "select * from "+table+"  "+key+" ";
		log.info("-----              -----   "+pageHead+sql+pageEnd);
		Statement ps = con.createStatement();
		//ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery(pageHead+sql+pageEnd);
	
		List re=new ArrayList();
		while(rs.next()){
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			Map<String,Object> map = new HashMap<String,Object>();
			for(int i=1;i<=count;i++){
				String columnName = rsmd.getColumnName(i);
				String fchar = columnName.charAt(0) + "";
				if(fchar.matches("\\d")){
					columnName  = "_" + columnName;
				}
				map.put(columnName, rs.getObject(i));
			}
			re.add(map);
		}
		
		rs.close();
		ps.close();
		return re;
	}
	
	
	public List get(String sql) throws Exception {
		
		log.info("-----              -----   "+sql);
		Statement ps = con.createStatement();
		//ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery(sql);
	
		List re=new ArrayList();
		while(rs.next()){
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			Map<String,Object> map = new HashMap<String,Object>();
			for(int i=1;i<=count;i++){
				String columnName = rsmd.getColumnName(i);
				String fchar = columnName.charAt(0) + "";
				if(fchar.matches("\\d")){
					columnName  = "_" + columnName;
				}
				map.put(columnName, rs.getObject(i));
			}
			re.add(map);
		}
		
		rs.close();
		ps.close();
		return re;
	}
	
	public int getCount(String table, String key,String pageHead,String pageEnd) throws Exception {
		String sql = "select count(*) from "+table+"  "+key+" ";
		log.info("-----              -----   "+sql);
		Statement ps = con.createStatement();
		//ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery(pageHead+sql+pageEnd);
		Map<String,Object> map = new HashMap<String,Object>();
		int count=0;
		while(rs.next()){
			count++;
			
		}
		
		rs.close();
		ps.close();
		
		return count;
	}
	
	
	public int getMax(String table, String key) throws Exception {
		String sql = "select max(to_number("+key+")) as count from "+table;
		log.info("-----              -----   "+sql);
		Statement ps = con.createStatement();
		
		
		ResultSet rs = ps.executeQuery(sql);
		int max=0;
		while(rs.next()){
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			//for(int i=1;i<=count;i++){
			//Object tmpString=rs.getObject(i);
			String c=rs.getString("count");
			if(c==null||"".equals(c))
			{
				max=0;
			}else {
				max=Integer.valueOf(rs.getString("count"));
			}
				//Integer.valueOf( tmpString);
			//}
		}
		rs.close();
		ps.close();
		return max;
	}
	public List<Map<String,Object>> getByFilter(String table,String filter,String orderBy,String orderType,
			String page,String mainTable) throws Exception {
		StringBuffer sb = new StringBuffer("");
		if(mainTable != null){
			sb.append("select " + mainTable + ".* from " + table);
		} else {
			sb.append("select * from " + table);
		}
		
		if(filter != null){
			sb.append(" where " + filter);
		}
		if(orderBy != null){
			sb.append(" order by " + orderBy + " " + orderType);
		}
		if(page != null){
			sb.append(" limit " + page + ",10");
		}
		String sql = sb.toString();
		System.out.println(sql);
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		while(rs.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int i=1;i<=count;i++){
				String columnName = rsmd.getColumnName(i);
				String fchar = columnName.charAt(0) + "";
				if(fchar.matches("\\d")){
					columnName  = "_" + columnName;
				}
				if(rs.getObject(i) == null){
					map.put(columnName, "");
				} else {
					map.put(columnName, rs.getObject(i));
				}
			}
			list.add(map);
		}
		rs.close();
		ps.close();
		return list;
	}
	
	public boolean add(String table,Map<String,Object> entity) throws Exception {
		String columns = "",columnsVal = "";

		for(String key : entity.keySet()) {
			columns += (key+",");
			Object obj = entity.get(key);
			if(obj == null){
				columnsVal += "null,";
			} else {
				String dataType = obj.getClass().getName();
				if(dataType == "java.lang.String"){
					if(obj.toString().startsWith("\"")){
						columnsVal += obj+",";
					}
					else{
						columnsVal += "'"+obj+"',";
					}
				} else {
					columnsVal += obj+",";
				}
			}
		}
		columns = columns.substring(0, columns.length()-1);
		columnsVal = columnsVal.substring(0, columnsVal.length()-1);
		String sql = "insert into "+table+"("+columns+") values("+columnsVal+")";
		log.info("addsql:"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		boolean istrue= ps.execute();
		ps.close();
		return istrue;
	}
	public void update(String table, String where, String column) throws Exception {
		String sql = "update " + table + " set " + column + " where " + where;
		log.info("updatesql:"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.executeUpdate();
	}
	
	public List<String> getColumnName(String table) throws Exception {
		List<String> list = new ArrayList<String>();
		String sql = "select * from " + table + " where 1=2";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for(int i=1;i<=count;i++){
			String columnName = rsmd.getColumnName(i);
			System.out.print(columnName + ",");
			list.add(columnName);
		}
		return list;
	}
	public boolean delete(String table, String filter) throws Exception {
		String sql = "delete from " + table;
		if(filter != null)
			sql += (" where "+filter);
		System.out.println("delsql:"+sql);
		PreparedStatement ps = con.prepareStatement(sql);
		return ps.execute();
	}

	public static void main(String[] args) {
		String sql = "select t.* from TB_POST_HERO_LIST_DETAIL t order by id desc";
		
		System.out.println(sql.substring(sql.indexOf("from")));
	}
}

