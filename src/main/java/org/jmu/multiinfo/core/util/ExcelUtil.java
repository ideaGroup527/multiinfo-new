package org.jmu.multiinfo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmu.multiinfo.dto.upload.DataVariety;

public class ExcelUtil {
	public static Workbook create(InputStream inp, Map<String, Object> condition) throws IOException,InvalidFormatException,EncryptedDocumentException {
	    if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
	    	condition.put("version", "2003");
	        return new HSSFWorkbook(inp);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	    	condition.put("version", "2007");
	        return new XSSFWorkbook(OPCPackage.open(inp));
	    }
	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
	
	/***
	 * 列号转化为标题，如AA--27
	 * @param index
	 * @return
	 */
	public static String getExcelColName(int index) {
		String returnStr = "";
		index--;
		char[] a = "A".toCharArray();
		int c = a[0];
		if (index < 26) {
			returnStr += (char) (c + index);
		} else {
			returnStr += (char) (c + (int) (index / 26) - 1);
			returnStr += (char) (c + index % 26);
		}
		return returnStr;
	}
	
	
	private static String jdType(Integer object) {
		if(object==null)
		return null;
		int type = object;
		String des = null;
		switch(type){
			case DataVariety.DATA_TYPE_STRING:
				des = "字符串型";
				break;		
			case DataVariety.DATA_TYPE_NUMERIC:
				des = "标准型数字";
				break;		
			case DataVariety.DATA_TYPE_NUMERIC_VIRG:
				des = "逗号型数值";
					break;		
			case DataVariety.DATA_TYPE_NUMERIC_DOT:
				des = "圆点型数值";
						break;		
			case DataVariety.DATA_TYPE_NUMERIC_SCIENCE:
				des = "科学型数值";
							break;		
			case DataVariety.DATA_TYPE_NUMERIC_DOLLAL:
				des = "美元型数值";
								break;		
			case DataVariety.DATA_TYPE_DATE:
				des = "日期型";
									break;
			case DataVariety.DATA_TYPE_CUSTOM:
				des = "用户自定义型";
									break;
			
			default:
				des=null;
		
		}
		return des;
	}


	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return map:"value"-->值      ;"type"--->类型 ;"typeDes"-->类型描述
	 */
	public static Map<String,Object> getCellFormatValue(HSSFCell cell) {
		Map<String,Object> map = new HashMap<String,Object>();

		int type = 0;
		Object cellvalue =null;
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			    if (HSSFDateUtil.isCellDateFormatted(cell)) {   
			        //  如果是date类型则 ，获取该cell的date值   
			    	cellvalue = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();   
			    } else { // 纯数字   
			    	cellvalue = String.valueOf(cell.getNumericCellValue()); 
			    	
			    }
			    type = DataVariety.DATA_TYPE_NUMERIC;
			    break;
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
//					Date date = cell.getDateCellValue();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					cellvalue = sdf.format(date);
					cellvalue = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString(); 

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = cell.getCellFormula();
				}
				type = DataVariety.DATA_TYPE_NUMERIC;
				break;
			}
			case HSSFCell.CELL_TYPE_BLANK:
				cellvalue = "";
				type = DataVariety.DATA_TYPE_STRING;
				break;
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				type = DataVariety.DATA_TYPE_STRING;
				break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean     
            	cellvalue =cell.getBooleanCellValue();     
            	type = DataVariety.DATA_TYPE_NUMERIC;
                break;   
			// 默认的Cell值
			default:
				cellvalue = " ";
				type = DataVariety.DATA_TYPE_STRING;
			}
		} else {
			cellvalue = "";
		}
		map.put("value", cellvalue);
		map.put("type", type);
		String typeDes =jdType(type);
		map.put("typeDes", typeDes);
		return map;

	}
}
