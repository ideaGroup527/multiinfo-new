package org.jmu.multiinfo.dto.basestatistics;

import java.util.List;

public class FactorVarCondition {
	//自变量
 private List<VarietyDTO> independentVariable;
 
 //因子变量
 private VarietyDTO factorVarVariable;
 
 //数据
 private DataDTO[][] dataGrid;

public List<VarietyDTO> getIndependentVariable() {
	return independentVariable;
}

public void setIndependentVariable(List<VarietyDTO> independentVariable) {
	this.independentVariable = independentVariable;
}


public VarietyDTO getFactorVarVariable() {
	return factorVarVariable;
}

public void setFactorVarVariable(VarietyDTO factorVarVariable) {
	this.factorVarVariable = factorVarVariable;
}

public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
}
