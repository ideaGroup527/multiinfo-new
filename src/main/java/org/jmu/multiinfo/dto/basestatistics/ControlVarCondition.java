package org.jmu.multiinfo.dto.basestatistics;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseBean;

public class ControlVarCondition extends BaseBean{
	//自变量
 private List<VarietyDTO> independentVariable;
 
 //控制变量
 private List<VarietyDTO> controlVariable;
 
 //数据
 private DataDTO[][] dataGrid;

public List<VarietyDTO> getIndependentVariable() {
	return independentVariable;
}

public void setIndependentVariable(List<VarietyDTO> independentVariable) {
	this.independentVariable = independentVariable;
}

public List<VarietyDTO> getControlVariable() {
	return controlVariable;
}

public void setControlVariable(List<VarietyDTO> controlVariable) {
	this.controlVariable = controlVariable;
}

public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
}
