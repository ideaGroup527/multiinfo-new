package org.jmu.multiinfo.service.upload.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.SheetDTO;
import org.jmu.multiinfo.service.upload.UploadService;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService{
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
			String[][] str = new String[rowcount][colcount];
			HSSFRow row = null;
			for (int i = 0; i < rowcount; i++) {
				row = sheet.getRow(i); // 获得第i行
				for (int j = 0; j < colcount; j++) {
					str[i][j] = getCellFormatValue(row.getCell(j)).trim();
				}
			}
			sheetDto.setData(str);
			sheetDto.setSheetName(sheet.getSheetName());
			excelDto.setSheet(sheetDto);
		return excelDto;
	}
	
	
	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

}
