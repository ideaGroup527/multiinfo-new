package org.jmu.multiinfo.dto.dingchart;

import java.util.List;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class DingChartCondition {
 
 //数据
 private DataDTO[][] dataGrid;
 
 //计算方法
 private Integer calculateMethod;
 
	//行变量
private List<VarietyDTO> rowVarList;

//列变量
private List<VarietyDTO> colVarList;
 
	/****
	 * 按行计算
	 */
	public final static int CALCULATE_METHOD_ROW = 0;
	
	
	/****
	 * 按列计算
	 */
	public final static int CALCULATE_METHOD_COL = 1;
	
	/****
	 * 全表计算
	 */
	public final static int CALCULATE_METHOD_ALL = 2;


public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}

public Integer getCalculateMethod() {
	return calculateMethod;
}

public void setCalculateMethod(Integer calculateMethod) {
	this.calculateMethod = calculateMethod;
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
