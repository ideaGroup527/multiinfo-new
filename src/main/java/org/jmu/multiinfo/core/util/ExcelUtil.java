package org.jmu.multiinfo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmu.multiinfo.dto.upload.DataVariety;
import org.jmu.multiinfo.web.utils.CommonUtil;

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
	
	

	/**
	 * 1、字母转数字
	 * 
	 * 思想： 从字符串的最后一位到第一位,乘以26的幂,依次相加.
	 * 
	 * 算法: 26^0 * (最后一位) + 26 ^ 1 * (前一位 ) + ... + 26 ^ n * (第一位).
	 * 
	 * @param value
	 * @return
	 */

	public static int getExcelColIndex(String value) {
		int rtn = 0;
		int powIndex = 0;

		for (int i = value.length() - 1; i >= 0; i--) {
			int tmpInt = value.charAt(i);
			tmpInt -= 64;
			rtn += (int) Math.pow(26, powIndex) * tmpInt;
			powIndex++;
		}
		return rtn;
	}

	/***
	 * 如A1:B8
	 * @param range
	 * @return
	 */
	public static PositionBean splitRange(String range){
		PositionBean positionBean = new PositionBean();
		String[] rangeArr =	range.split(":");
		if(rangeArr == null ||rangeArr.length<2) return null;
		String first = rangeArr[0];
		String last = rangeArr[1];
		Pattern pattern = Pattern.compile("[0-9]");
		 Matcher matcher = pattern.matcher(first);
		 if (matcher.find()) {
			  int beginIndex =  first.indexOf(matcher.group());
			  positionBean.setFirstRowId(Integer.valueOf(first.substring(beginIndex)));
			  positionBean.setFirstColId(Integer.valueOf(getExcelColIndex(first.substring(0,beginIndex))));
			 }else{
				 return null;
			 }
		 Matcher matcher2 = pattern.matcher(last);
		 if (matcher2.find()) {
			  int beginIndex =  last.indexOf(matcher2.group());
			  positionBean.setLastRowId(Integer.valueOf(last.substring(beginIndex)));
			  positionBean.setLastColId(Integer.valueOf(getExcelColIndex(last.substring(0,beginIndex))));
			 }else{
				 return null;
			 }
		 return positionBean;
	}
	
	/***
	 * 列号转化为标题，如AA--27
	 * @param index
	 * @return
	 */
	public static String getExcelColName(int value) {
		StringBuffer rtn = new StringBuffer();
		List<Integer> iList = new ArrayList<Integer>();

		// To single Int
		while (value / 26 != 0 || value % 26 != 0) {
			iList.add(value % 26);
			value /= 26;
		}

		// Change 0 To 26
		for (int j = 0; j < iList.size(); j++) {
			if (iList.get(j) == 0) {
				iList.set(j + 1, iList.get(j + 1) - 1);
				iList.set(j, 26);
			}
		}

		// Remove 0 at last
		if (iList.get(iList.size() - 1) == 0) {
			iList.remove(iList.size() - 1);
		}

		// To String
		for (int j = (iList.size() - 1); j >= 0; j--) {
			char c = (char) (iList.get(j) + 64);
			rtn.append(c);
		}
		return rtn.toString();
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
	public static Map<String,Object> getCellFormatValue(Cell cell) {
		Map<String,Object> map = new HashMap<String,Object>();

		int type = 0;
		Object cellvalue =null;
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_NUMERIC:
			    if (HSSFDateUtil.isCellDateFormatted(cell)) {   
			        //  如果是date类型则 ，获取该cell的date值   
			    	cellvalue = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();   
			    } else { // 纯数字   
			    	cellvalue = String.valueOf(cell.getNumericCellValue()); 
			    	
			    }
			    type = DataVariety.DATA_TYPE_NUMERIC;
			    break;
			case Cell.CELL_TYPE_FORMULA: {
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
			case Cell.CELL_TYPE_BLANK:
				cellvalue = "";
				type = DataVariety.DATA_TYPE_STRING;
				break;
			// 如果当前Cell的Type为STRIN
			case Cell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				type = DataVariety.DATA_TYPE_STRING;
				break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean     
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
	
	
	
	/**    
     * 获取合并单元格的值    
     * @param sheet    
     * @param row    
     * @param column    
     * @return    map:"value"-->值      ;"type"--->类型 ;"typeDes"-->类型描述 ;"mergedRange" --->合并范围 如A1:B3
     */     
     public  static Map<String, Object> getMergedRegionValue(Sheet sheet ,int row , int column){     
         int sheetMergeCount = sheet.getNumMergedRegions();     
              
         for(int i = 0 ; i < sheetMergeCount ; i++){     
             CellRangeAddress ca = sheet.getMergedRegion(i);     
             int firstColumn = ca.getFirstColumn();     
             int lastColumn = ca.getLastColumn();     
             int firstRow = ca.getFirstRow();     
             int lastRow = ca.getLastRow();     
             if(row >= firstRow && row <= lastRow){     
                  if(column >= firstColumn && column <= lastColumn){     
                     Row fRow = sheet.getRow(firstRow); 
                     Cell fCell = fRow.getCell(firstColumn); 
                     Map<String, Object> map =    getCellFormatValue(fCell);
                     String firstIndex = getExcelColName(firstColumn+1)+(firstRow+1);
                     String lastIndex = getExcelColName(lastColumn+1)+(lastRow+1);
                     map.put("mergedRange", firstIndex+":"+lastIndex);
					  map.put("mergedRangeDes", getExcelColName(firstColumn+1)+","+(firstRow+1)+":"+getExcelColName(lastColumn+1)+","+(lastRow+1));
                     return   map;
                 }     
             }     
         }     
              
         return null ;     
     }   
     
     /**   
      * 判断指定的单元格是否是合并单元格   
      * @param sheet    
      * @param row 行下标   
      * @param column 列下标   
      * @return   
      */   
      public static boolean isMergedRegion(Sheet sheet,int row ,int column) {   
        int sheetMergeCount = sheet.getNumMergedRegions();   
        for (int i = 0; i < sheetMergeCount; i++) {   
              CellRangeAddress range = sheet.getMergedRegion(i);   
              int firstColumn = range.getFirstColumn(); 
              int lastColumn = range.getLastColumn();   
              int firstRow = range.getFirstRow();   
              int lastRow = range.getLastRow();   
              if(row >= firstRow && row <= lastRow){ 
                      if(column >= firstColumn && column <= lastColumn){ 
                              return true;   
                          } 
              }   
        } 
        return false;   
      }



	public static Map<String,Object> getCellFormatValue(String ora) {
		if(ora == null )
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int type = 0;
		Object cellvalue =ora;
		if("".equals(ora)){
			type =DataVariety.DATA_TYPE_STRING;
			cellvalue = " ";
		}else if(CommonUtil.isRealNumber(ora)){
		    type = DataVariety.DATA_TYPE_NUMERIC;
		}else if(CommonUtil.isScienceNumber(ora)){
			type =DataVariety.DATA_TYPE_NUMERIC_SCIENCE;
			
		}else if(CommonUtil.isVirgNumber(ora)){
			type =DataVariety.DATA_TYPE_NUMERIC_VIRG;
		}else {
			type = DataVariety.DATA_TYPE_STRING;
		}
		map.put("value", cellvalue);
		map.put("type", type);
		String typeDes =jdType(type);
		map.put("typeDes", typeDes);
		return map;
		
	} 
}
