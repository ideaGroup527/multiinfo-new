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
					Map<String,Object> datamap = ExcelUtil.getCellFormatValue(row.getCell(j));
					String typeDes =(String) datamap.get("typeDes");
					String pjs = ExcelUtil.getExcelColName(j+1);
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
			String lastColIndex = ExcelUtil.getExcelColName(colcount);
			excelDto.setLastCellIndex(lastColIndex+rowcount+"");
			excelDto.setPhysicalCellNum(colcount);
		return excelDto;
	}
	
	

	


}
