package org.jmu.multiinfo.service.upload;

import java.io.File;
import java.io.IOException;

import org.jmu.multiinfo.dto.upload.ExcelDTO;
import org.jmu.multiinfo.dto.upload.TextDTO;

public interface UploadService {
	public ExcelDTO readExcel(File file,String name,int n,boolean isFirstRowVar) throws Exception;
	
public ExcelDTO readExcel(File file,String name,int n) throws Exception;

public ExcelDTO readExcel(File file,String name) throws Exception;

public TextDTO readText(File file,String name, boolean isFirstRowVar) throws IOException;
}
