package org.jmu.multiinfo.dto.upload;

import java.util.List;

public class ExcelDTO {
private String fileName;
private int sheetNum;
private SheetDTO sheet;
private int currenSheetNo;
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

}
