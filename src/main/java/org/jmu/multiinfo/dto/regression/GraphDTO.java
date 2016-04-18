package org.jmu.multiinfo.dto.regression;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class GraphDTO extends BaseDTO {
private Map<String,GraphDataDTO> resDataMap;
//自变量
private List<VarietyDTO> independentVariable;

//因变量
private VarietyDTO dependentVariable;



public Map<String, GraphDataDTO> getResDataMap() {
	return resDataMap;
}

public void setResDataMap(Map<String, GraphDataDTO> resDataMap) {
	this.resDataMap = resDataMap;
}

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



}
