package org.jmu.multiinfo.dto.reducingdim;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class PrincipalComponentAnalysisDTO extends BaseDTO{
private List<VarietyDTO> variableList;
private PCAEigenvalueDTO data;
public List<VarietyDTO> getVariableList() {
	return variableList;
}
public void setVariableList(List<VarietyDTO> variableList) {
	this.variableList = variableList;
}
public PCAEigenvalueDTO getData() {
	return data;
}
public void setData(PCAEigenvalueDTO data) {
	this.data = data;
}
}
