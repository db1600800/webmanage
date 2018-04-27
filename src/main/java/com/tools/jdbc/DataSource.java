package com.tools.jdbc;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DataSource {
	private static Connection conn;
	private DataSource() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/oracle.properties"));
			String DRIVER = properties.getProperty("driver");
			String URL = properties.getProperty("url");
			String USER = decode(properties.getProperty("user"));
			String PASSWORD = decode(properties.getProperty("password"));
		
			try {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (conn == null) {
			new DataSource();
		}
		return conn;
	}


	 private static final String key0 = "ECOIL";  
	    private static final Charset charset = Charset.forName("UTF-8");  
	    private static byte[] keyBytes = key0.getBytes(charset);  
	      
	    public static String encode(String enc){  
	        byte[] b = enc.getBytes(charset);  
	        for(int i=0,size=b.length;i<size;i++){  
	            for(byte keyBytes0:keyBytes){  
	                b[i] = (byte) (b[i]^keyBytes0);  
	            }  
	        }  
	        return new String(b);  
	    }  
	      
	    public static String decode(String dec){  
	        byte[] e = dec.getBytes(charset);  
	        byte[] dee = e;  
	        for(int i=0,size=e.length;i<size;i++){  
	            for(byte keyBytes0:keyBytes){  
	                e[i] = (byte) (dee[i]^keyBytes0);  
	            }  
	        }  
	        return new String(e);  
	    }  
	   /* public static void main(String[] args) {  
	        String s="jyqymobile";  
	        String enc = encode(s);  
	        String dec = decode(enc);  
	        System.out.println(enc);  
	        System.out.println(dec);  
	    } */ 
}






