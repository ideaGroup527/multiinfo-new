package org.jmu.multiinfo.service.upload;

import java.io.File;
import org.jmu.multiinfo.dto.upload.ExcelDTO;

public interface UploadService {
	public ExcelDTO readExcel(File file,int n,boolean isFirstRowVar) throws Exception;
	
public ExcelDTO readExcel(File file,int n) throws Exception;

public ExcelDTO readExcel(File file) throws Exception;
}
