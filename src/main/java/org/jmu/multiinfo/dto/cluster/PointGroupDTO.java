package org.jmu.multiinfo.dto.cluster;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class PointGroupDTO extends BaseDTO{
private List<StepClusterDTO> stepList;
private List<Object> factorVarList;
public List<StepClusterDTO> getStepList() {
	return stepList;
}
public void setStepList(List<StepClusterDTO> stepList) {
	this.stepList = stepList;
}
public List<Object> getFactorVarList() {
	return factorVarList;
}
public void setFactorVarList(List<Object> factorVarList) {
	this.factorVarList = factorVarList;
}

}
