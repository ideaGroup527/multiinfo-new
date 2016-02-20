package org.jmu.multiinfo.upload.dto;

public class SheetDto {
private String sheetName;
private int sheetNo;
private String[][] data;
public String getSheetName() {
	return sheetName;
}
public void setSheetName(String sheetName) {
	this.sheetName = sheetName;
}
public int getSheetNo() {
	return sheetNo;
}
public void setSheetNo(int sheetNo) {
	this.sheetNo = sheetNo;
}
public String[][] getData() {
	return data;
}
public void setData(String[][] data) {
	this.data = data;
}


}
