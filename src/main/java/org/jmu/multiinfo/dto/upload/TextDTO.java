package org.jmu.multiinfo.dto.upload;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class TextDTO extends BaseDTO{
private DataDTO[][]  dataGrid;
private Boolean isFirstRowVar;
private List<VarietyDTO> variety;
private String fileName;
private Integer physicalRowNum;
private Integer physicalCellNum;
private String lastCellIndex;
public DataDTO[][] getDataGrid() {
	return dataGrid;
}
public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
public Boolean getIsFirstRowVar() {
	return isFirstRowVar;
}
public void setIsFirstRowVar(Boolean isFirstRowVar) {
	this.isFirstRowVar = isFirstRowVar;
}
public List<VarietyDTO> getVariety() {
	return variety;
}
public void setVariety(List<VarietyDTO> variety) {
	this.variety = variety;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public Integer getPhysicalRowNum() {
	return physicalRowNum;
}
public void setPhysicalRowNum(Integer physicalRowNum) {
	this.physicalRowNum = physicalRowNum;
}
public Integer getPhysicalCellNum() {
	return physicalCellNum;
}
public void setPhysicalCellNum(Integer physicalCellNum) {
	this.physicalCellNum = physicalCellNum;
}
public String getLastCellIndex() {
	return lastCellIndex;
}
public void setLastCellIndex(String lastCellIndex) {
	this.lastCellIndex = lastCellIndex;
}

}
