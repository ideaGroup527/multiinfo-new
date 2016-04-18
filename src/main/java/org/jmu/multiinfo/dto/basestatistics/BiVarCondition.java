package org.jmu.multiinfo.dto.basestatistics;

import java.util.List;

public class BiVarCondition {
	//自变量
 private List<VarietyDTO> independentVariable;
 
 //因变量
 private VarietyDTO dependentVariable;
 
 //数据
 private DataDTO[][] dataGrid;

public List<VarietyDTO> getIndependentVariable() {
	return independentVariable;
}

public void setIndependentVariable(List<VarietyDTO> independentVariable) {
	this.independentVariable = independentVariable;
}

public VarietyDTO getDependentVariable() {
	return dependentVariable;
}

public void setDependentVariable(VarietyDTO dependentVariable) {
	this.dependentVariable = dependentVariable;
}

public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
 
 
}