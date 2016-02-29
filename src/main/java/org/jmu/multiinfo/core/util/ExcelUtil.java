package org.jmu.multiinfo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
}
