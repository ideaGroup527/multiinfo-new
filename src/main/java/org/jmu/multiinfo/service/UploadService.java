package org.jmu.multiinfo.service;

import java.io.File;

import org.jmu.multiinfo.upload.dto.ExcelDto;

public interface UploadService {
public ExcelDto readExcel(File file,int n);
}
