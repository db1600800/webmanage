package com.tools.report;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tools.PaginationUtil;
import com.tools.hibernate.ObjectDao;



@Controller
@RequestMapping("/ToExcelAction")
public class ToExcelController {

	
	
	private static Logger logger = LoggerFactory.getLogger(ToExcelController.class);
	@Resource
	private ObjectDao objectDao;


	List listcolumn = null;
	List listquerycolumn = null;
	List listresult = null;

	public ObjectDao getObjectDao() {
		return objectDao;
	}

	public void setObjectDao(ObjectDao objectDao) {
		this.objectDao = objectDao;
	}


	@RequestMapping("/index.do")
	public String index(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return "common/excel";
	}

	// 通用报表列表（开发者使用）
	@ResponseBody
	@RequestMapping("/list.do")
	public void list(HttpServletRequest request,HttpServletResponse response) {

		String select = request.getParameter("searchInput1");
		select = select.toLowerCase().replace("select", "");
		String from = request.getParameter("searchInput2");
		from = from.toLowerCase().replace("from", "");
		String where = request.getParameter("searchInput3");
		where = where.toLowerCase().replace("where", "");

		String sql = "";
		if (where == null || "".equals(where)) {
			sql = "select " + select + " from " + from;
		} else {
			sql = "select " + select + " from " + from + " where " + where;
		}

		query(sql);

		String pageString = "";
		pageString = pageString.replace(".html", "");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("listcolumn", listcolumn);
		jsonObject.put("listresult", listresult);
		jsonObject.put("pageString", pageString);

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 具体某个活动报表列表(业务人员使用)
	@RequestMapping("/activityReportIndex.do")
	public String activityReportIndex(HttpServletRequest request) {
		String path = request.getContextPath();
		Map paraMap = request.getParameterMap();
		JSONObject json = JSONObject.fromObject(paraMap);
		String jsonstring = json.toString();
		jsonstring = jsonstring.replace("[", "");
		jsonstring = jsonstring.replace("]", "");
		request.setAttribute("parameter", jsonstring);
		
		
		String excelId=String.valueOf(json.get("excelId"));
		excelId = excelId.replace("[", "");
		excelId = excelId.replace("]", "");
		excelId = excelId.replace("\"", "");
		 String excelTitle = KeyValue.readCache("excelTitle_" + excelId);

		 request.setAttribute("excelTitle", excelTitle);

		return "common/excelForActivity";
	}

	// 具体某个活动报表列表(业务人员使用)
	@RequestMapping("/activityReportList.do")
	public void activityReportList(HttpServletRequest request,HttpServletResponse response) {

		
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isBlank(pageNo)) {// 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
			pageNo = "1";
			request.setAttribute("pageNo", pageNo);
		}
		
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
			request.setAttribute("pageSize", pageSize);
		}
		
		
		String jsonString = (String) request.getParameter("parameter");

		String querycondition = (String) request.getParameter("query");
		System.out.println("querycondition1:"+querycondition);
		
		
		
		if(querycondition==null)
		{
			querycondition="";
		}
		
		JSONObject jsonobject = JSONObject.fromObject(jsonString);
		Iterator iterator = jsonobject.keys();
		
		
		String excelId="";
		String  sqlpara="";
		Map paraMap=new HashMap();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = jsonobject.getString(key);
			sqlpara+=key+"="+value+"&";
			if("excelId".equals(key))
			{
				excelId=value;	
				sqlpara+=key+"="+value+"&";
			}else
			{
				paraMap.put(key, value);
			}
		}

		if(sqlpara.lastIndexOf("&")!=-1)
		{
			sqlpara=sqlpara.substring(0,sqlpara.lastIndexOf("&"));
		}
		

	

		String excelTitle = KeyValue.readCache("excelTitle_" + excelId);
		String excelCnHead = KeyValue.readCache("excelCnHead_" + excelId);
		String excelSql = KeyValue.readCache("excelSql_" + excelId);
		String excelToWhichExcelId = KeyValue.readCache("excelToWhichExcelId_"
				+ excelId);

		String excelQueryColumn = KeyValue.readCache("excelQueryColumn_"
				+ excelId);

		JSONArray excelToWhichExcelIdJsonArray=null;
		if(excelToWhichExcelId!=null && !"".equals(excelToWhichExcelId))
		{
		 excelToWhichExcelIdJsonArray = JSONArray
				.fromObject(excelToWhichExcelId);
		}
		
		String orderby="";
		if(excelSql.toLowerCase().indexOf("order ")!=-1)
		{
		
		 orderby=excelSql.substring(excelSql.toLowerCase().indexOf("order "));
		 excelSql=excelSql.substring(0,excelSql.toLowerCase().indexOf("order "));
		}
			
		
		
		Set<String> keySet = paraMap.keySet();
		for (String key : keySet) {

			if (excelSql.contains(key + "='?'")) {

				excelSql = excelSql.replace(key + "='?'",
						key + "='" + paraMap.get(key)+"'");

			}

		}
		
		
		int sqlResultColumnCount=0;
		if(excelCnHead!=null && excelCnHead.indexOf(",")!=-1)
		{
			sqlResultColumnCount=(excelCnHead.split(",")).length;
		}
		else if(excelCnHead!=null && excelCnHead.indexOf("，")!=-1)
		{
			sqlResultColumnCount=(excelCnHead.split("，")).length;
		}
		
		
	
		
		if(excelSql.indexOf("='?'")!=-1)
		{
			String part1=excelSql.substring(0,excelSql.indexOf("='?'"));
			part1=part1.substring(0,part1.lastIndexOf("and"));
			
			String part2=excelSql.substring(excelSql.indexOf("='?'")+4);
			
		
			excelSql=part1+" "+part2;
			
		}
		
		if(excelSql.toLowerCase().indexOf("where")==-1 && querycondition.toLowerCase().indexOf("and")!=-1)
		{
			querycondition=" where "+querycondition.substring(querycondition.toLowerCase().indexOf("and")+3);
		}
		
		querycondition=querycondition.replace("big", ">");
		querycondition=querycondition.replace("small", "<");
		querycondition=querycondition.replace("#", "'");
		querycondition=querycondition.replace("lik", "like");
		querycondition=querycondition.replace("^", "%");
		int count=activityReportQuery(excelSql+querycondition+" "+orderby,pageNo,pageSize);
		logger.info(excelSql+querycondition+" "+orderby);
		// 报表头
		String heads[] = excelCnHead.split(",");

		listcolumn = new ArrayList();
		for (int i = 0; i < heads.length; i++) {
			listcolumn.add(heads[i]);
		}
		
		
		//清除分页返回页码
		{
		for (int i = 0; i < listresult.size(); i++) {

			Object column = listresult.get(i);
			if (column instanceof Object[]) {
				
				
				List list = new ArrayList();
				for (int j = 0; j < ((Object[]) column).length; j++) {
					String temp = String.valueOf(((Object[]) column)[j]);
					
					if(j<=sqlResultColumnCount-1)
					{
						list.add(temp);
					}
				}
				
				
				listresult.set(i, list.toArray());
			}
		
		}
	}
		
		
//有跳转到下页面情况
		if (excelToWhichExcelId != null && !"".equals(excelToWhichExcelId)) {
			// 报表体字段增加url
			listcolumn.add("操作");

			for (int i = 0; i < listresult.size(); i++) {

				Object column = listresult.get(i);
				if (column instanceof Object[]) {

					String addcolumn = "";
					List list = new ArrayList();

					for (int j = 0; j < ((Object[]) column).length; j++) {
						String temp = String.valueOf(((Object[]) column)[j]);
							list.add(temp);	
					}

					
					
					Map toWichs=new HashMap();
					Map urls=new HashMap();
					Map urlnames=new HashMap();
					
					for (int k = 0; k < excelToWhichExcelIdJsonArray.size(); k++) {
						Map o = (Map) excelToWhichExcelIdJsonArray.get(k);
						String toWhichExcelId = (String) o
								.get("toWhichExcelId");
						String relateFromColumn = (String) o
								.get("relateFromColumn");
						String relateToColumn = (String) o
								.get("relateToColumn");
						String relateFromColumnIndex = (String) o
								.get("relateFromColumnIndex");
						
						String url = (String) o
								.get("url");
						
						String urlname = (String) o
								.get("urlname");

						String value = "";

						for (int j = 0; j < ((Object[]) column).length; j++) {
							String temp = String
									.valueOf(((Object[]) column)[j]);

							if (relateFromColumnIndex.contains("" + j)) {
								value += relateToColumn + "=" + temp + "&";
							}
						}
						
						String oldvalue=String.valueOf(toWichs.get(toWhichExcelId));
						if(oldvalue==null||"null".equals(oldvalue))
						{
							toWichs.put(toWhichExcelId, value);
						}else
						{
							
							
							oldvalue+=value;
							toWichs.put(toWhichExcelId, oldvalue);
						}
						
						urls.put(toWhichExcelId, url);
						
						urlnames.put(toWhichExcelId, urlname);
					}
					
					for(Object key:toWichs.keySet())
					{
						

						String toWhichExcelId=String.valueOf(key);
						
						if(toWhichExcelId.contains("-"))
						{
							
							String value=String.valueOf(toWichs.get(key));
							
						
							if (value.lastIndexOf("&") != -1) {
								value = value.substring(0,
										value.lastIndexOf("&"));
							}
							addcolumn += "<a href=\""+urls.get(toWhichExcelId)+"?"
									+ value
									+ "\"> "
									+ "" + urlnames.get(toWhichExcelId) + "" + " </a>|";
						
						}else
						{
						String value=String.valueOf(toWichs.get(key));
						
						String cnHead = KeyValue.readCache("excelTitle_"
								+ Integer.valueOf(toWhichExcelId));
						addcolumn += "<a href=\"/gdmgr/ToExcelAction/activityReportIndex.do?"
								+ value
								+ "excelId="
								+ Integer.valueOf(toWhichExcelId)
								+ "\"> "
								+ "" + cnHead + "" + " </a>|";
						}

					}

					if (addcolumn.lastIndexOf("|") != -1) {
						addcolumn = addcolumn.substring(0,
								addcolumn.lastIndexOf("|"));
					}
					list.add(addcolumn);
					listresult.set(i, list.toArray());

				} else {

				}

			}
		}
		
		
		
		
		
		String pageString = "";
		
		 pageString = PaginationUtil.getPaginationHtml(Integer
				.valueOf(count), Integer.valueOf(pageSize), Integer
				.valueOf(pageNo), Integer.valueOf(2), Integer.valueOf(5),
				"javascript:getAll('/gdmgr/ToExcelAction/activityReportList.do?"
						+ sqlpara+ "&pageNo=", true);
		pageString = pageString.replace(".html", "");

		
		pageString = pageString.replace(".html", "");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("listcolumn", listcolumn);
		jsonObject.put("listresult", listresult);
		jsonObject.put("pageString", pageString);
		jsonObject.put("excelQueryColumn", excelQueryColumn);
		

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int activityReportQuery(String sql,String pageNo,String pageSize) {

		// 全部数据
		listresult = new ArrayList();
		
		

		if (sql == null || "".equals(sql)) {

		} else {

			if(pageNo==null||pageSize==null)
			{
				
				Map argsMap = new HashMap();
				listresult = (List) objectDao.findBySql(sql, argsMap);
				
				int count=0;
				if(listresult!=null)
					count=listresult.size();
				
				
				return count;
				
			}else
			{
				
				
			Map argsMap = new HashMap();
			listresult = (List) objectDao.findBySql(sql, argsMap,  (Integer.parseInt(pageNo) - 1)
							* Integer.parseInt(pageSize), Integer
							.parseInt(pageSize));
			
			
			
			//String tempsql=sql.substring(sql.indexOf("from"));
			
			List count=objectDao.findBySql("select count(*) from ("+sql+")", argsMap);
			
			return Integer.valueOf(String.valueOf(count.get(0)));
			}
			
			
		}
		
		return 0;

	}
	public void query(String sql) {
		// 数据表全部字段名
		listcolumn = new ArrayList();
		// 用户指定查询字段名
		listquerycolumn = new ArrayList();
		// 全部数据
		listresult = new ArrayList();

		if (sql == null || "".equals(sql)) {

		} else {

			String tableNames[] = sql.toLowerCase().split("from ");
			String tableName = "";
			if (tableNames != null && tableNames.length == 2) {

				// tableName 取得数据表名
				String part2s[] = tableNames[1].split("where");
				if (part2s != null && part2s.length > 0) {
					tableName = part2s[0];

				}

				// columns 取得查询字段
				String sqlcolumns = "";
				String columns[] = tableNames[0].toLowerCase().split("select");
				if (columns != null && columns.length == 2) {
					if ((columns[1]).trim().equals("*")) {// 没指定查询指定 ，全查*

					} else {// 用户指定查询字段
						String columnpart[] = columns[1].split(",");
						if (columnpart == null || columnpart.length == 0) {
							columnpart = columns[1].split("，");
						}
						for (int i = 0; i < columnpart.length; i++) {
							listquerycolumn.add(columnpart[i].trim());
						}
					}

				}

			}

			if (tableName == null || "".equals(tableName)) {

			} else {

				if (tableName.indexOf(",") != -1) {// 多表
					listcolumn = new ArrayList();

					String tablenames[] = tableName.split(",");
					for (int i = 0; i < tablenames.length; i++) {
						StringBuffer sb = new StringBuffer(
								" SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '"
										+ tablenames[i].toUpperCase()
										+ "' ORDER BY COLUMN_ID    ");

						Map argsMap = new HashMap();

						List temp = (List) objectDao.findBySql(sb.toString(),
								argsMap);

						for (int j = 0; j < temp.size(); j++) {
							listcolumn.add(temp.get(j));
						}
					}

				} else {

					StringBuffer sb = new StringBuffer(
							" SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '"
									+ tableName.toUpperCase().trim()
									+ "' ORDER BY COLUMN_ID    ");

					Map argsMap = new HashMap();

					listcolumn = (List) objectDao.findBySql(sb.toString(),
							argsMap);

				}

				if (listquerycolumn != null && listquerycolumn.size() > 0) {
					listcolumn = listquerycolumn;
				}

				Map argsMap = new HashMap();
				listresult = (List) objectDao.findBySql(sql, argsMap);

			}
		}

	}

	@ResponseBody
	@RequestMapping("/activityReportExportExcel.do")
	public void activityReportExportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {// 导出excel表

		Map m = request.getParameterMap();
		JSONObject json = JSONObject.fromObject(m);
		String jsonstring = json.toString();
		jsonstring = jsonstring.replace("[", "");
		jsonstring = jsonstring.replace("]", "");

		JSONObject jsonobject = JSONObject.fromObject(jsonstring);
		Iterator iterator = jsonobject.keys();
		
		
		String excelId="";
		Map paraMap=new HashMap();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = jsonobject.getString(key);
			if("excelId".equals(key))
			{
				excelId=value;	
			}else
			{
				paraMap.put(key, value);
			}
		}

		

	

		String excelTitle = KeyValue.readCache("excelTitle_" + excelId);
		String excelCnHead = KeyValue.readCache("excelCnHead_" + excelId);
		String excelSql = KeyValue.readCache("excelSql_" + excelId);
		String excelToWhichExcelId = KeyValue.readCache("excelToWhichExcelId_"
				+ excelId);

		String orderby="";
		if(excelSql.toLowerCase().indexOf("order ")!=-1)
		{
			 orderby=excelSql.substring(excelSql.toLowerCase().indexOf("order "));
		excelSql=excelSql.substring(0,excelSql.toLowerCase().indexOf("order "));
		
		}

		Set<String> keySet = paraMap.keySet();
		for (String key : keySet) {

			if (excelSql.contains(key + "='?'")) {

				excelSql = excelSql.replace(key + "='?'",
						key + "='" + paraMap.get(key)+"'");

			}

		}

	System.out.println("excelSql:"+excelSql);
	String querycondition = (String) request.getParameter("query");
	System.out.println("querycondition1:"+querycondition);
		if(querycondition==null)
		{
			querycondition="";
		}
	     
		
		if(excelSql.indexOf("='?'")!=-1)
		{
			String part1=excelSql.substring(0,excelSql.indexOf("='?'"));
			part1=part1.substring(0,part1.lastIndexOf("and"));
			
			String part2=excelSql.substring(excelSql.indexOf("='?'")+4);
			
			excelSql=part1+" "+part2;
			
		}
		if(excelSql.toLowerCase().indexOf("where")==-1 && querycondition.toLowerCase().indexOf("and")!=-1)
		{
			querycondition=" where "+querycondition.substring(querycondition.toLowerCase().indexOf("and")+3);
		}
		
		querycondition=querycondition.replace("big", ">");
		querycondition=querycondition.replace("small", "<");
		querycondition=querycondition.replace("#", "'");
		querycondition=querycondition.replace("lik", "like");
		querycondition=querycondition.replace("^", "%");
		System.out.println("querycondition:"+querycondition);
		activityReportQuery(excelSql+querycondition+" "+orderby,null,null);

		String heads[] = excelCnHead.split(",");

		listcolumn = new ArrayList();
		for (int i = 0; i < heads.length; i++) {
			listcolumn.add(heads[i]);
		}

		ExportExcel.exportXlsx(listcolumn, listresult, "",response);
	}

	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {// 导出excel表

		String select = request.getParameter("searchInput1");
		select = select.toLowerCase().replace("select", "");
		String from = request.getParameter("searchInput2");
		from = from.toLowerCase().replace("from", "");
		String where = request.getParameter("searchInput3");
		where = where.toLowerCase().replace("where", "");

		String sql = "";
		if (where == null || "".equals(where)) {
			sql = "select " + select + " from " + from;
		} else {
			sql = "select " + select + " from " + from + " where " + where;
		}

		query(sql);

		ExportExcel.exportXlsx(listcolumn, listresult, "",response);

	}
}
