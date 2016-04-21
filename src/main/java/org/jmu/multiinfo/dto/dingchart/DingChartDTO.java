package org.jmu.multiinfo.dto.dingchart;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class DingChartDTO extends BaseDTO{
 
 private double[][] resData;
	//行变量
private List<VarietyDTO> rowVarList;

//列变量
private List<VarietyDTO> colVarList;

public double[][] getResData() {
	return resData;
}

public void setResData(double[][] resData) {
	this.resData = resData;
}

public List<VarietyDTO> getRowVarList() {
	return rowVarList;
}

public void setRowVarList(List<VarietyDTO> rowVarList) {
	this.rowVarList = rowVarList;
}

public List<VarietyDTO> getColVarList() {
	return colVarList;
}

public void setColVarList(List<VarietyDTO> colVarList) {
	this.colVarList = colVarList;
}
 
}
