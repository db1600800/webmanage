package com.tools.report.importExcelToDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tools.jdbc.DataOperation;

public class ExcelDate2Db {

	public Integer dateToTB_POST_HERO_LIST(List rows, String filename,String type) {

		if (rows != null && rows.size() > 1) {
			
			//title
			List columnsone=(List) rows.get(0);
			String one=(String) columnsone.get(0);
			int p=one.indexOf("(");
			int p2=one.indexOf(")");
			if(p==-1)
			{
				 p=one.indexOf("（");
			}
			if(p2==-1)
			{
				 p2=one.indexOf("）");
			}
			String titleString="";
			String timeString="";
		  if(p!=-1)
		 {
			 titleString=one.substring(0,p);
			 timeString=one.substring(p+1,p2);
		 }else {
			 titleString=one;
		}
			
			
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
			String currenttimeString = df.format(new Date());// new
																// Date()Ϊ��ȡ��ǰϵͳʱ��

			DataOperation don = new DataOperation();

			// ����TB_POST_HERO_LIST��
			try {

				int HEROLISTNO = don.getMax("TB_POST_HERO_LIST", "HEROLISTNO") + 1;

				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("HEROLISTNO", HEROLISTNO);
				entity.put("HEROLISTNAME", titleString);
				entity.put("OPERATORID", "a");
				entity.put("OPERATIONDATE", currenttimeString);
				entity.put("FILENAME", filename);
				entity.put("HERONUM", rows.size() - 2);// title��ȥ��
				entity.put("HEROTYPE", type);
				entity.put("HERODATE", timeString);
				don.add("TB_POST_HERO_LIST", entity);
				return HEROLISTNO;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;

	}

	
	
	
	public boolean dateToTB_POST_HERO_LIST_DETAIL(List rows, Integer HEROLISTNO) {

		if (rows == null || rows.size() < 2) {
			return false;
		}
		if(HEROLISTNO==null)
		{
			return false;
		}
		DataOperation don = new DataOperation();
		try {
			for (int j = 1; j < rows.size(); j++) {
				List columnsList = (List) rows.get(j);
				if (columnsList == null || columnsList.size() != 9) {
					return false;
				}
				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("ID",
						don.getMax("TB_POST_HERO_LIST_DETAIL", "ID") + 1);
				entity.put("HEROLISTNO", HEROLISTNO);

				entity.put("HEROLISTNAME", columnsList.get(0));

				entity.put("DATETIME", columnsList.get(1));
				entity.put("HEADURL", columnsList.get(2));
				entity.put("CITY", columnsList.get(3));
				entity.put("COUNTY", columnsList.get(4));
				entity.put("POSTALNAME", columnsList.get(5));
				entity.put("HERONAME", columnsList.get(6));
				entity.put("ACHIEVEMENT", columnsList.get(7));
				entity.put("ORDERS", columnsList.get(8));
				don.add("TB_POST_HERO_LIST_DETAIL", entity);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	
	
	//�����ȷ����
	public boolean dateToTB_POST_HERO_CITY_LIST(List rows, Integer HEROLISTNO) {

		if (rows == null || rows.size() < 3) {
			return false;
		}
		if(HEROLISTNO==null)
		{
			return false;
		}
		DataOperation don = new DataOperation();
		try {
			
			
			//title
			List columnsone=(List) rows.get(0);
			String one=(String) columnsone.get(0);
			int p=one.indexOf("(");
			int p2=one.indexOf(")");
			if(p==-1)
			{
				 p=one.indexOf("（");
			}
			if(p2==-1)
			{
				 p2=one.indexOf("）");
			}
			String titleString="";
			String timeString="";
		  if(p!=-1)
		 {
			 titleString=one.substring(0,p);
			 timeString=one.substring(p+1,p2);
		 }
			
			
			for (int j = 2; j < rows.size(); j++) {
				List columnsList = (List) rows.get(j);
				if (columnsList == null || columnsList.size() != 5) {
					return false;
				}
				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("ID",
						don.getMax("TB_POST_HERO_CITY_LIST", "ID") + 1);
				entity.put("HEROLISTNO", HEROLISTNO);

				entity.put("HEROLISTNAME", titleString);

				entity.put("DATETIME", timeString);
				
				entity.put("GROUPS", columnsList.get(0));
				entity.put("CITY", columnsList.get(1));
				entity.put("ORGNUM", columnsList.get(2));
				entity.put("QUARTERINCREATE", columnsList.get(3));
				entity.put("ORDERS", columnsList.get(4));
				don.add("TB_POST_HERO_CITY_LIST", entity);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	//�����ȷ�����
	public boolean dateToTB_POST_HERO_COUNTY_LIST(List rows, Integer HEROLISTNO) {

		if (rows == null || rows.size() < 3) {
			return false;
		}
		if(HEROLISTNO==null)
		{
			return false;
		}
		DataOperation don = new DataOperation();
		try {
			
			
			//title
			List columnsone=(List) rows.get(0);
			String one=(String) columnsone.get(0);
			int p=one.indexOf("(");
			int p2=one.indexOf(")");
			if(p==-1)
			{
				 p=one.indexOf("（");
			}
			if(p2==-1)
			{
				 p2=one.indexOf("）");
			}
			String titleString="";
			String timeString="";
		  if(p!=-1)
		 {
			 titleString=one.substring(0,p);
			 timeString=one.substring(p+1,p2);
		 }else {
			 titleString=one;
		}
			
			
			for (int j = 2; j < rows.size(); j++) {
				List columnsList = (List) rows.get(j);
				if (columnsList == null || columnsList.size() != 6) {
					return false;
				}
				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("ID",
						don.getMax("TB_POST_HERO_COUNTY_LIST", "ID") + 1);
				entity.put("HEROLISTNO", HEROLISTNO);

				entity.put("HEROLISTNAME", titleString);

				entity.put("DATETIME", timeString);
				
				entity.put("GROUPS", columnsList.get(0));
				entity.put("CITY", columnsList.get(1));
				entity.put("COUNTY", columnsList.get(2));
				entity.put("ORGNUM", columnsList.get(3));
				entity.put("QUARTERINCREATE", columnsList.get(4));
				entity.put("ORDERSINGROUP", columnsList.get(5));
				don.add("TB_POST_HERO_COUNTY_LIST", entity);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	
	//ǧ��������
	public boolean dateToTB_POST_HERO_POSTAL_LIST(List rows, Integer HEROLISTNO) {

		if (rows == null || rows.size() < 3) {
			return false;
		}
		if(HEROLISTNO==null)
		{
			return false;
		}
		DataOperation don = new DataOperation();
		try {
			
			
			//title
			List columnsone=(List) rows.get(0);
			String one=(String) columnsone.get(0);
			int p=one.indexOf("(");
			int p2=one.indexOf(")");
			if(p==-1)
			{
				 p=one.indexOf("（");
			}
			if(p2==-1)
			{
				 p2=one.indexOf("）");
			}
			String titleString="";
			String timeString="";
		  if(p!=-1)
		 {
			 titleString=one.substring(0,p);
			 timeString=one.substring(p+1,p2);
		 }
			
			
			for (int j = 2; j < rows.size(); j++) {
				List columnsList = (List) rows.get(j);
				if (columnsList == null || columnsList.size() != 5) {
					return false;
				}
				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("ID",
						don.getMax("TB_POST_HERO_POSTAL_LIST", "ID") + 1);
				entity.put("HEROLISTNO", HEROLISTNO);

				entity.put("HEROLISTNAME", titleString);

				entity.put("DATETIME", timeString);
				
				entity.put("POSTALNAME", columnsList.get(0));
				entity.put("CITY", columnsList.get(1));
				entity.put("ORGNUM", columnsList.get(2));
				entity.put("YEARINCREATE", columnsList.get(3));
				entity.put("ORDERS", columnsList.get(4));
				don.add("TB_POST_HERO_POSTAL_LIST", entity);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	//�����Ӣ�۰�
	public boolean dateToTB_POST_HERO_PERSON_LIST(List rows, Integer HEROLISTNO) {

		if (rows == null || rows.size() < 3) {
			return false;
		}
		if(HEROLISTNO==null)
		{
			return false;
		}
		DataOperation don = new DataOperation();
		try {
			
			
			//title
			List columnsone=(List) rows.get(0);
			String one=(String) columnsone.get(0);
			int p=one.indexOf("(");
			int p2=one.indexOf(")");
			if(p==-1)
			{
				 p=one.indexOf("（");
			}
			if(p2==-1)
			{
				 p2=one.indexOf("）");
			}
			String titleString="";
			String timeString="";
		  if(p!=-1)
		 {
			 titleString=one.substring(0,p);
			 timeString=one.substring(p+1,p2);
		 }
			
			
			for (int j = 2; j < rows.size(); j++) {
				List columnsList = (List) rows.get(j);
				if (columnsList == null || columnsList.size() != 5) {
					return false;
				}
				Map<String, Object> entity = new LinkedHashMap<String, Object>();
				entity.put("ID",
						don.getMax("TB_POST_HERO_PERSON_LIST", "ID") + 1);
				entity.put("HEROLISTNO", HEROLISTNO);

				entity.put("HEROLISTNAME", titleString);

				entity.put("DATETIME", timeString);
				
				entity.put("POSTALNAME", columnsList.get(0));
				entity.put("CITY", columnsList.get(1));
				entity.put("ORGNUM", columnsList.get(2));
				entity.put("USERNAME", columnsList.get(3));
				entity.put("MONEY", columnsList.get(4));
				don.add("TB_POST_HERO_PERSON_LIST", entity);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}







