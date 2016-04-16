package org.jmu.multiinfo.dto.regression;

import java.util.List;

import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonCondition {
	//自变量
	@JsonProperty("independentvariable") 
 private List<VarietyDTO> independentVariable;
 
 //因变量
	@JsonProperty("dependentvariable") 
 private VarietyDTO dependentVariable;
 
 //数据
	@JsonProperty("datagrid") 
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
