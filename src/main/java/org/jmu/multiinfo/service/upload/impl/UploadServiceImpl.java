package org.jmu.multiinfo.service.upload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.DataVariety;
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.SheetDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ExcelDTO readExcel(File file,int n) throws Exception{
		ExcelDTO excelDto = new ExcelDTO();
		SheetDTO sheetDto = new SheetDTO();
		excelDto.setFileName(file.getName());
		excelDto.setCurrenSheetNo(n);

			Workbook wb = null;
			Map<String,Object> condition = new HashMap<String,Object>();
				try {
					wb = ExcelUtil.create(new FileInputStream(file),condition);
					excelDto.setVersion(condition.get("version").toString());
				} catch (EncryptedDocumentException e) {
					excelDto.setRet_msg("excel已被加密");
					excelDto.setRet_err(e.getMessage());
					return excelDto;
				} catch (InvalidFormatException e) {
					excelDto.setRet_msg("格式不合法");
					excelDto.setRet_err(e.getMessage());
					return excelDto;
				} catch (FileNotFoundException e) {
					excelDto.setRet_msg("文件不存在");
					excelDto.setRet_err(e.getMessage());
					return excelDto;
				} catch (IOException e) {
					excelDto.setRet_msg("无法读取文件");
					excelDto.setRet_err(e.getMessage());
					return excelDto;
				}
			int sheetNum = wb.getNumberOfSheets();
			List<String> sheetNameList = new ArrayList<String>();
			for (int i = 0; i < sheetNum; i++) {
				HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(i);
				sheetNameList.add(sheet.getSheetName());
			}
			excelDto.setSheetNum(sheetNum);
			excelDto.setSheetNameList(sheetNameList);
			HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(n);
			int rowcount = sheet.getLastRowNum();// 取得有效的行数
			int colcount = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
			DataDTO[][] dataGrid = new DataDTO[rowcount][colcount];
			List<VarietyDTO> varietyList = new ArrayList<VarietyDTO>();
			HSSFRow row = null;
			for (int i = 0; i < rowcount; i++) {
				row = sheet.getRow(i); // 获得第i行
				for (int j = 0; j < colcount; j++) {
					Map<String,Object> datamap = getCellFormatValue(row.getCell(j));
					String typeDes = jdType((Integer) datamap.get("type"));
					char pj = (char) ('A'+j);
					String pjs = pj+"";
					logger.debug(pjs);
					if(i==0){
						VarietyDTO variety =  new VarietyDTO();
						
						variety.setPosition(pjs);
						variety.setVarietyName(datamap.get("value").toString());
						variety.setType((Integer) datamap.get("type"));
						variety.setTypeDes(typeDes);
						varietyList.add(variety);
					}
					DataDTO data = new DataDTO();
					dataGrid[i][j] = data;
					data.setPosition(pjs + (i+1) +"");
					
					data.setData(datamap.get("value"));
					data.setType((Integer) datamap.get("type"));
					
					data.setTypeDes(typeDes );
					
				}
			}
			sheetDto.setDataGrid(dataGrid);
			
			sheetDto.setVariety(varietyList);
			sheetDto.setSheetName(sheet.getSheetName());
			excelDto.setSheet(sheetDto);
			excelDto.setPhysicalRowNum(sheet.getPhysicalNumberOfRows());
			char lastColIndex = (char) ('A'+colcount-1);
			excelDto.setLastCellIndex(lastColIndex+rowcount+"");
			excelDto.setPhysicalCellNum(colcount);
		return excelDto;
	}
	
	
	private String jdType(Integer object) {
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
	 * @return
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
		return map;

	}

}
