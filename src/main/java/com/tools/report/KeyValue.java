package com.tools.report;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class KeyValue {
	public static String readCache(String key)
	{
	
		try {
		
		String path = "";
		
		return readValue(path,key);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return "";
	}
	

	
	//根据key读取value
	 public static String readValue(String filePath,String key) {
	  Properties props = new Properties();
	        try {
	         InputStream in = KeyValue.class.getResourceAsStream("/toExcel.properties");
	        // InputStream in= KeyValue.class.getResourceAsStream(filePath);//读取jar包内部文件( src内的)
	         props.load(in);
	         String value = props.getProperty (key);
	         if(value==null)
	        	 value="";
	            System.out.println(key+value);
	            return value;
	        } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	        }
	 }
	 
	 //读取properties的全部信息
	    public static void readProperties(String filePath) {
	     Properties props = new Properties();
	        try {
	        InputStream in =  KeyValue.class.getResourceAsStream("/toExcel.properties");
	         //InputStream in= KeyValue.class.getResourceAsStream(filePath);
	         props.load(in);
	            Enumeration en = props.propertyNames();
	             while (en.hasMoreElements()) {
	              String key = (String) en.nextElement();
	                    String Property = props.getProperty (key);
	                    System.out.println(key+Property);
	                }
	        } catch (Exception e) {
	         e.printStackTrace();
	        }
	    }

	 
	    /*
	    public static void main(String[] args) {
	     readValue("info.properties","url");
	       
	        readProperties("info.properties" );
	        System.out.println("OK");
	    }*/
	
}
