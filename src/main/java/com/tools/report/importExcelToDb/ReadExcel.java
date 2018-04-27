package com.tools.report.importExcelToDb;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	 // 读取单元格的值
    private String getValue(Cell cell) {
        String result = "";

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            result = cell.getBooleanCellValue() + "";
            break;
        case Cell.CELL_TYPE_STRING:
            result = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_FORMULA:
            result = cell.getCellFormula();
            break;
        case Cell.CELL_TYPE_NUMERIC:
        	
        	
        	// 格式化 number String 字符
SimpleDateFormat sdf = new SimpleDateFormat(
"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串



String d=cell.getCellStyle().getDataFormatString();


if(DateUtil.isCellDateFormatted(cell))
{ 
	result = sdf.format(HSSFDateUtil.getJavaDate(cell
			.getNumericCellValue()));
}
 else if ("@".equals(cell.getCellStyle().getDataFormatString())) {
	  DecimalFormat df = new DecimalFormat("0");
				result = df.format(cell.getNumericCellValue());
			} else if (d.contains("General")) {
				DecimalFormat Generalf = new DecimalFormat("0");
				result = Generalf.format(cell.getNumericCellValue());
			} else if (d.contains("0_")) {
				DecimalFormat Generalf = new DecimalFormat("0");
				result = Generalf.format(cell.getNumericCellValue());
			}
			 else if (d.contains("0.00")) {
					DecimalFormat Generalf = new DecimalFormat("0.00");
					result = Generalf.format(cell.getNumericCellValue());
				}
			 else if (d.contains("0.0")) {
					DecimalFormat Generalf = new DecimalFormat("0.0");
					result = Generalf.format(cell.getNumericCellValue());
				}
			else if(d.contains("0%"))
			{
				DecimalFormat bf = new DecimalFormat("#.##%");
				result = bf.format(cell.getNumericCellValue());
			}else {
				DecimalFormat Generalf = new DecimalFormat("0");
				result = Generalf.format(cell.getNumericCellValue());
			}

            break;
        }
        return result;
    }

    /***
     * 这种方法支持03，和07版本的excel读取
     * 但是对于合并的单元格，除了第一行第一列之外，其他部分读取的值为空
     * @param is
     */
    public List importXlsx(InputStream is) {
    	List rows=new ArrayList();
        try {
            Workbook wb = WorkbookFactory.create(is);
            // OPCPackage pkg = OPCPackage.open(is);
           //  XSSFWorkbook wb = new XSSFWorkbook(pkg);
            for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
                Sheet sheet = wb.getSheetAt(i);
                
              int rowcount=sheet.getLastRowNum();
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    if (sheet == null) {
                         return null;
                    }
                    Row row = sheet.getRow(j);
                    if(row==null){
                       continue;
                    }
                    
                    List columns=new ArrayList();
                    // 读取每一个单元格
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        if (cell == null) {
                        	columns.add("");
                            //return null;
                        }else {
                        	 String tmpString=getCellValue(sheet,cell);
                        	  columns.add(tmpString);
                              System.out.print(tmpString);
						}
                      
                       

                    }
                    
                    int enptycount=0;
                    for(int t=0;t<columns.size();t++)
                    {
                    	if("".equals(columns.get(t)))
                    	{
                    		enptycount++;
                    	}
                    	
                    }
                    if(enptycount<2)
                    {
                    	 rows.add(columns);
                    }else {
						
					}
                   
                    System.out.println();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rows;
    }
	
   
   /**
    * 判断是否是合并的单元格，如果是的话，返回合并区域，否则返回空（仅适用于）
    * 
    * @param sheet
    * @param cellRow
    * @param cellColumn
    * @return
    */
   private CellRangeAddress isMerged(Sheet sheet, Cell cell) {

       CellRangeAddress result = null;
       CellRangeAddress cra = null;
       int cellRow = cell.getRowIndex();
       int cellColumn = cell.getColumnIndex();
       int mergedNum = sheet.getNumMergedRegions();
       for (int i = 0; i < mergedNum; i++) {
           // 如果是xlsx的格式，怎么办？
           cra = sheet.getMergedRegion(i);
           if (cellRow >= cra.getFirstRow() && cellRow <= cra.getLastRow()
                   && cellColumn >= cra.getFirstColumn()
                   && cellColumn <= cra.getLastColumn()) {
               result = cra;
           }
       }
       return result;
   }

   private String getCellValue(Sheet sheet, Cell cell) {
       String result = "";
       // 判断是否是合并的单元格
       CellRangeAddress cra = null;
       if ((cra = isMerged(sheet, cell)) != null) {
           Cell fcell = sheet.getRow(cra.getFirstRow()).getCell(
                   cra.getFirstColumn());
           result = getValue(fcell);
       } else {
           result = getValue(cell);
       }
       return result;
   }

    
	
	/**
	 * 对外提供读取excel 的方法
	 * */
	public static List<List<Object>> readExcel(InputStream file,String name) throws IOException {
		String fileName = name;
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(file);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(file);
		} else {
			throw new IOException("不支持的文件类型");
		}
	}

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static List<List<Object>> read2003Excel(InputStream file)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(file);
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					System.out.println(i + "行" + j + " 列 is String type");
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					System.out.println(i + "行" + j
							+ " 列 is Number type ; DateFormt:"
							+ cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println(i + "行" + j + " 列 is Boolean type");
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					System.out.println(i + "行" + j + " 列 is Blank type");
					value = "";
					break;
				default:
					System.out.println(i + "行" + j + " 列 is default type");
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

	/**
	 * 读取Office 2007 excel
	 * */
	private static List<List<Object>> read2007Excel(InputStream file)
			throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(file);
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
															// 字符
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					System.out.println(i + "行" + j + " 列 is String type");
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					System.out.println(i + "行" + j
							+ " 列 is Number type ; DateFormt:"
							+ cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					System.out.println(i + "行" + j + " 列 is Boolean type");
					value = cell.getBooleanCellValue();
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					System.out.println(i + "行" + j + " 列 is Blank type");
					value = "";
					break;
				default:
					System.out.println(i + "行" + j + " 列 is default type");
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}

	public static void main(String[] args) {
//		try {
//		//	readExcel(new File("D:\\test.xlsx"));
//			// readExcel(new File("D:\\test.xls"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

