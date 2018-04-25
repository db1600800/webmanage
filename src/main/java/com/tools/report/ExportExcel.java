package com.tools.report;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportExcel {
	public static void export(List heads, List list,String fileName,HttpServletResponse response) {
		Object[] excelHeader = (Object[]) heads.toArray();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Student");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);

		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(String.valueOf(excelHeader[i]));
			cell.setCellStyle(style);
			sheet.autoSizeColumn((short) i);
			sheet.setColumnWidth(i, 10000);
		}

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);

			if (list.get(i) instanceof ArrayList
					|| list.get(i) instanceof Object[]) {
				Object[] object = (Object[]) list.get(i);

				for (int j = 0; j < object.length; j++) {
					String theme = String.valueOf(object[j]);
					row.createCell(j).setCellValue(theme);
				}
			} else {
				Object object = (Object) list.get(i);
				String theme = String.valueOf(object);
				row.createCell(0).setCellValue(theme);
			}

		}
		
		
		try {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(fileName+"报表.xls", "UTF-8"));
		
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	public static void exportXlsx(List heads, List list,String fileName,HttpServletResponse response) {
		Object[] excelHeader = (Object[]) heads.toArray();
		
		try{
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Student");
		XSSFRow row = sheet.createRow((int) 0);
		XSSFCellStyle style =(XSSFCellStyle) wb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);

		for (int i = 0; i < excelHeader.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(String.valueOf(excelHeader[i]));
			cell.setCellStyle(style);
			sheet.autoSizeColumn((short) i);
			sheet.setColumnWidth(i, 10000);
		}

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);

			if (list.get(i) instanceof ArrayList
					|| list.get(i) instanceof Object[]) {
				Object[] object = (Object[]) list.get(i);

				for (int j = 0; j < object.length; j++) {
					String theme = String.valueOf(object[j]);
					row.createCell(j).setCellValue(theme);
				}
			} else {
				Object object = (Object) list.get(i);
				String theme = String.valueOf(object);
				row.createCell(0).setCellValue(theme);
			}

		}
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ URLEncoder.encode(fileName+"报表.xlsx", "UTF-8"));
		
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
}

