package org.jmu.multiinfo.dto.cluster;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class PointGroupDTO extends BaseDTO{
private List<StepClusterDTO> stepList;
private List<Double> factorVarList;
public List<StepClusterDTO> getStepList() {
	return stepList;
}
public void setStepList(List<StepClusterDTO> stepList) {
	this.stepList = stepList;
}
public List<Double> getFactorVarList() {
	return factorVarList;
}
public void setFactorVarList(List<Double> factorVarList) {
	this.factorVarList = factorVarList;
}

}
