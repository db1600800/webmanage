package com.tools.report.importExcelToDb;

import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;


public class ImportTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger log = LoggerFactory.getLogger(ImportTableServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("come to importexcel servlet ");

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(request.getCharacterEncoding());

	
		List rows = null;

		String errmsg="excel文件内容有误";

		try {

			List<FileItem> fileItems = upload.parseRequest(request);
			String whichTableString = "";
			for (int i = 0; i < fileItems.size(); i++) {
				FileItem item = fileItems.get(i);
				if (i == 0) {

					whichTableString = new String(item.getString().getBytes(
							"ISO-8859-1"), "utf-8");

				}

				if ((item.getName() != null && item.getName().endsWith(".xls"))
						|| (item.getName() != null && item.getName().endsWith(
								".xlsx"))) {
					log.info("come to importexcel  read excel");
					ReadExcel readExcel = new ReadExcel();
					rows = readExcel.importXlsx(item.getInputStream());

					int point = item.getName().indexOf(".xl");
					String filename = item.getName().substring(0, point);

					log.info("come to importexcel todb ");

					if (rows != null && rows.size() > 1) {
						boolean isuloadfiletrue=uploadfileIsTrue(whichTableString,rows);
						if(isuloadfiletrue)
						{
						boolean todbtrue=toDb( whichTableString,  rows,  filename); 
						   if(todbtrue)
						   {
							
									JSONArray jsonArray = JSONArray.fromObject(rows);
									String t = jsonArray.toString();
									out.write("{\"status\":\"true\",\"info\":\"导入成功。\",\"rows\":" + jsonArray.toString()
											+ "}");
							
								out.flush();
								out.close();
								return ;
						   }
						}else
						{
							errmsg="上传文件与选择的表不一致";
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			errmsg="excel文件内容有误";
		}


	
	   out.write("{\"status\":\"false\",\"info\":\"" + errmsg
					+ "\"}");
		
		out.flush();
		out.close();

	}
	
	public boolean uploadfileIsTrue(String whichTableString,List rows)
	{
		boolean issucess=false;
		List columnsone = (List) rows.get(0);
		String one = (String) columnsone.get(0);
		int p = one.indexOf("(");
		int p2 = one.indexOf(")");
		if (p == -1) {
			p = one.indexOf("（");
		}
		if (p2 == -1) {
			p2 = one.indexOf("）");
		}
		String titleString = "";

		if (p != -1) {
			titleString = one.substring(0, p);
		} else {
			titleString = one;
		}
		log.info("come to importexcel title  "+titleString);
		
		if ("保险先锋地市".equals(titleString)&&whichTableString.equals("city")) {
			issucess = true;
		}else if ("保险先锋县域".equals(titleString)&&whichTableString.equals("county")) {
			issucess = true;
		}else if ("千万标杆网点".equals(titleString)&&whichTableString.equals("postal")) {
			issucess = true;
		}else if ("百万大单英雄榜".equals(titleString)&&whichTableString.equals("person")) {
			issucess = true;
		}
		return issucess;
	}

	public boolean toDb(String whichTableString, List rows, String filename) {
		log.info("come to importexcel toDb ");
		boolean issucess = false;
		
		try {

			ExcelDate2Db excelDate2Db = new ExcelDate2Db();

			
			if (whichTableString.equals("city")) {
			
					// 插入TB_POST_HERO_LIST表
					Integer herono = excelDate2Db.dateToTB_POST_HERO_LIST(rows,
							filename, whichTableString);

					// 插入TB_POST_HERO_LIST_DETAIL表
					issucess = excelDate2Db.dateToTB_POST_HERO_CITY_LIST(rows,
							herono);
				
			} else if (whichTableString.equals("county")) {
			
					// 插入TB_POST_HERO_LIST表
					Integer herono = excelDate2Db.dateToTB_POST_HERO_LIST(rows,
							filename, whichTableString);

					// 插入TB_POST_HERO_LIST_DETAIL表
					issucess = excelDate2Db.dateToTB_POST_HERO_COUNTY_LIST(
							rows, herono);
					
			} else if (whichTableString.equals("postal")) {
			
					// 插入TB_POST_HERO_LIST表
					Integer herono = excelDate2Db.dateToTB_POST_HERO_LIST(rows,
							filename, whichTableString);

					// 插入TB_POST_HERO_LIST_DETAIL表
					issucess = excelDate2Db.dateToTB_POST_HERO_POSTAL_LIST(
							rows, herono);
				

			} else if (whichTableString.equals("person")) {
				
					// 插入TB_POST_HERO_LIST表
					Integer herono = excelDate2Db.dateToTB_POST_HERO_LIST(rows,
							filename, whichTableString);

					// 插入TB_POST_HERO_LIST_DETAIL表
					issucess = excelDate2Db.dateToTB_POST_HERO_PERSON_LIST(
							rows, herono);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return issucess;
		}
		return issucess;
	}

}



