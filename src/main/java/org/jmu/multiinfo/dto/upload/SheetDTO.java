package org.jmu.multiinfo.dto.upload;

import java.util.List;

public class SheetDTO {
private String sheetName;
private int sheetNo;
private DataDTO[][] dataGrid;
private List<VarietyDTO> variety;
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
public DataDTO[][] getDataGrid() {
	return dataGrid;
}
public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
public List<VarietyDTO> getVariety() {
	return variety;
}
public void setVariety(List<VarietyDTO> variety) {
	this.variety = variety;
}


}
