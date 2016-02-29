package org.jmu.multiinfo.dto.upload;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class ExcelDTO extends BaseDTO{
private String fileName;
private int sheetNum;
private SheetDTO sheet;
private int currenSheetNo;
private String version;
private List<String> sheetNameList;
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public int getSheetNum() {
	return sheetNum;
}
public void setSheetNum(int sheetNum) {
	this.sheetNum = sheetNum;
}

public List<String> getSheetNameList() {
	return sheetNameList;
}
public void setSheetNameList(List<String> sheetNameList) {
	this.sheetNameList = sheetNameList;
}
public SheetDTO getSheet() {
	return sheet;
}
public void setSheet(SheetDTO sheet) {
	this.sheet = sheet;
}
public int getCurrenSheetNo() {
	return currenSheetNo;
}
public void setCurrenSheetNo(int currenSheetNo) {
	this.currenSheetNo = currenSheetNo;
}
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}

}
