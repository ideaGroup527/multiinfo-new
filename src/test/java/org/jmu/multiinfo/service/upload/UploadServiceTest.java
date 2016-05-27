package org.jmu.multiinfo.service.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EmptyFileException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class UploadServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UploadService uploadService;
	
	private File file;
	
	private FileInputStream fileInp;
	@Before
	public void init() throws IOException{
		file = FileUtils.getFile("J:\\校园卡消费项目\\test.xlsx");
		fileInp = FileUtils.openInputStream(file);
		
	}
	
	
	@Test
	public void tt() throws EmptyFileException, IOException, InvalidFormatException{
		Map<String,Object> condition = new HashMap<String,Object>();
		UploadServiceTest.create(fileInp, condition);
	}
	@Test
	public void jd(){
		uploadService.jdeExcelNum(file,"test.xlsx");
	}
	@Test
	public void jdtest() throws EncryptedDocumentException, InvalidFormatException, IOException{
		Map<String,Object> condition = new HashMap<String,Object>();
		ExcelUtil.create(fileInp, condition);
	} 
	
	public static ExcelDTO jdeExcelNum(File file, String name)   {
		ExcelDTO excelDto = new ExcelDTO();
		Workbook wb = null;
		Map<String,Object> condition = new HashMap<String,Object>();
			try {
				wb = ExcelUtil.create(FileUtils.openInputStream(file),condition);
				excelDto.setVersion(condition.get("version").toString());
			} catch (EncryptedDocumentException e) {
				excelDto.setRet_msg("excel已被加密");
				excelDto.setRet_code("-1");
				excelDto.setRet_err(e.getMessage());
				return excelDto;
			} catch (InvalidFormatException e) {
				excelDto.setRet_msg("格式不合法");
				excelDto.setRet_code("-1");
				excelDto.setRet_err(e.getMessage());
				return excelDto;
			} catch (FileNotFoundException e) {
				excelDto.setRet_msg("文件不存在");
				excelDto.setRet_code("-1");
				excelDto.setRet_err(e.getMessage());
				return excelDto;
			} catch (IOException e) {
				excelDto.setRet_msg("无法读取文件");
				excelDto.setRet_code("-1");
				excelDto.setRet_err(e.getMessage());
				return excelDto;
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
				excelDto.setRet_msg(e.getMessage());
				excelDto.setRet_code("-1");
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
		excelDto.setFileName(name);
		return excelDto;
	}
	
	
	
	public static Workbook create(InputStream inp, Map<String, Object> condition) throws EmptyFileException, IOException, InvalidFormatException  {
	    if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
	     // Ensure that there is at least some data there
        byte[] header8 = IOUtils.peekFirst8Bytes(inp);
	    if (POIFSFileSystem.hasPOIFSHeader(header8)) {
	    	NPOIFSFileSystem fs = new NPOIFSFileSystem(inp);
	    	condition.put("version", "2003");
	        return WorkbookFactory.create(fs);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	    	condition.put("version", "2007");
	        return new XSSFWorkbook(new BufferedInputStream(inp));
	    }
//	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
		return null;
	}
}
