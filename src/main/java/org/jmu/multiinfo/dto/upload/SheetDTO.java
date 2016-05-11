package org.jmu.multiinfo.dto.upload;

import java.io.Serializable;
import java.util.List;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class SheetDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1342941453706539761L;
private String sheetName;
private int sheetNo;
private DataDTO[][] dataGrid;
private List<VarietyDTO> variety;
private boolean isFirstRowVar;
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
public boolean getIsFirstRowVar() {
	return isFirstRowVar;
}
public void setFirstRowVar(boolean isFirstRowVar) {
	this.isFirstRowVar = isFirstRowVar;
}

}
