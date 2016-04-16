package org.jmu.multiinfo.service.upload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.SheetDTO;
import org.jmu.multiinfo.dto.upload.TextDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public ExcelDTO readExcel(File file,String name) throws Exception{
	return	readExcel(file,name,0);
	}
	
	public ExcelDTO readExcel(File file,String name,int n) throws Exception{
	return	readExcel(file,name,n,true);
	}
	
	
	public ExcelDTO readExcel(File file,String name,int n,boolean isFirstRowVar) throws Exception{
		ExcelDTO excelDto = new ExcelDTO();
		SheetDTO sheetDto = new SheetDTO();
		excelDto.setFileName(name);
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
				Sheet sheet = (Sheet) wb.getSheetAt(i);
				sheetNameList.add(sheet.getSheetName());
			}
			excelDto.setSheetNum(sheetNum);
			excelDto.setSheetNameList(sheetNameList);
			Sheet sheet = (Sheet) wb.getSheetAt(n);
			int rowcount = sheet.getLastRowNum();// 取得有效的行数
			int colcount = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
			DataDTO[][] dataGrid = new DataDTO[rowcount][colcount];
			List<VarietyDTO> varietyList = new ArrayList<VarietyDTO>();
			Row row = null;
			for (int i = 0; i < rowcount; i++) {
				row = sheet.getRow(i); // 获得第i行
				for (int j = 0; j < colcount; j++) {
					Map<String,Object> datamap =null;
				if(	ExcelUtil.isMergedRegion(sheet, i, j)){
					datamap=	ExcelUtil.getMergedRegionValue(sheet, i, j);
				}else{
					
					datamap= ExcelUtil.getCellFormatValue(row.getCell(j));
				}
					String typeDes =(String) datamap.get("typeDes");
					String pjs = ExcelUtil.getExcelColName(j+1);
					String mergedRange = (String) datamap.get("mergedRange");
					String mergedRangeDes = (String) datamap.get("mergedRangeDes");
//					logger.debug(pjs);
					if(isFirstRowVar&&i==0){
						VarietyDTO variety =  new VarietyDTO();
						
						variety.setPosition(pjs);
						variety.setVarietyName(datamap.get("value").toString());
						variety.setType((Integer) datamap.get("type"));
						variety.setTypeDes(typeDes);
						varietyList.add(variety);
					}
					if(!isFirstRowVar&&i==0){
							VarietyDTO variety =  new VarietyDTO();
						
						variety.setPosition(pjs);
						variety.setVarietyName("V"+(j+1));
						variety.setType((Integer) datamap.get("type"));
						variety.setTypeDes(typeDes);
						varietyList.add(variety);
						
					}
					DataDTO data = new DataDTO();
					dataGrid[i][j] = data;
					data.setPosition(pjs + (i+1) +"");
					data.setPositionDes(pjs +","+ (i+1) );
					data.setData(datamap.get("value"));
					data.setType((Integer) datamap.get("type"));
					data.setMergedRange(mergedRange);
					data.setMergedRangeDes(mergedRangeDes);
					data.setTypeDes(typeDes );
					
				}
			}
			sheetDto.setDataGrid(dataGrid);
			
			sheetDto.setVariety(varietyList);
			sheetDto.setSheetName(sheet.getSheetName());
			sheetDto.setFirstRowVar(isFirstRowVar);
			excelDto.setSheet(sheetDto);
			excelDto.setPhysicalRowNum(sheet.getPhysicalNumberOfRows());
			String lastColIndex = ExcelUtil.getExcelColName(colcount);
			excelDto.setLastCellIndex(lastColIndex+rowcount+"");
			excelDto.setPhysicalCellNum(colcount);
		return excelDto;
	}

	@Override
	public TextDTO readText(File file,String name, boolean isFirstRowVar) throws IOException {
		TextDTO textDto = new TextDTO();
		textDto.setFileName(name);
		textDto.setIsFirstRowVar(isFirstRowVar);
		String lastCellIndex ="";
		List<String>  lines = FileUtils.readLines(file,Charset.forName("UTF-8"));
		Integer physicalRowNum = lines.size();
		textDto.setPhysicalRowNum(physicalRowNum);
		Integer physicalCellNum = lines.get(0).split("\t|\\s+").length;
		textDto.setPhysicalCellNum(physicalCellNum);
		DataDTO[][] dataGrid = new DataDTO[physicalRowNum][physicalCellNum];
		List<VarietyDTO> varietyList = new ArrayList<VarietyDTO>();
		for (int i = 0; i < physicalRowNum; i++) {
			String row = lines.get(i);
			String[] eachDatas = row.split("\t|\\s+");
			for (int j = 0; j < eachDatas.length; j++) {
				Map<String,Object> datamap =null;
				datamap =	ExcelUtil.getCellFormatValue(eachDatas[j]);
				String typeDes =(String) datamap.get("typeDes");
				String pjs = ExcelUtil.getExcelColName(j+1);
				String mergedRange = (String) datamap.get("mergedRange");
				if(isFirstRowVar&&i==0){
					VarietyDTO variety =  new VarietyDTO();
					
					variety.setPosition(pjs);
					variety.setVarietyName(datamap.get("value").toString());
					variety.setType((Integer) datamap.get("type"));
					variety.setTypeDes(typeDes);
					varietyList.add(variety);
				}
				if(!isFirstRowVar&&i==0){
						VarietyDTO variety =  new VarietyDTO();
					variety.setPosition(pjs);
					variety.setVarietyName("V"+(j+1));
					variety.setType((Integer) datamap.get("type"));
					variety.setTypeDes(typeDes);
					varietyList.add(variety);
					
				}
				
				DataDTO data = new DataDTO();
				dataGrid[i][j] = data;
				data.setPosition(pjs + (i+1) +"");
				lastCellIndex = pjs + (i+1) +"";
				data.setPositionDes(pjs +","+ (i+1) );
				data.setData(datamap.get("value"));
				data.setType((Integer) datamap.get("type"));
				data.setMergedRange(mergedRange);
				data.setTypeDes(typeDes );
			}
			
		}
		textDto.setDataGrid(dataGrid);
		textDto.setVariety(varietyList);

		textDto.setLastCellIndex(lastCellIndex);
		return textDto;
	}

	@Override
	public ExcelDTO jdeExcelNum(File file, String name)   {
		ExcelDTO excelDto = new ExcelDTO();
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
			Sheet sheet = (Sheet) wb.getSheetAt(i);
			sheetNameList.add(sheet.getSheetName());
		}
		excelDto.setSheetNum(sheetNum);
		excelDto.setSheetNameList(sheetNameList);
		return excelDto;
	}
	

}
