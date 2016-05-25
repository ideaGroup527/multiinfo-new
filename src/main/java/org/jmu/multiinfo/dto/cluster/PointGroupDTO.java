package org.jmu.multiinfo.dto.cluster;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.service.cluster.impl.StepBinaryTree;

public class PointGroupDTO extends BaseDTO{
private List<StepClusterDTO> stepList;
private List<Object> factorVarList;
private StepBinaryTree tree;
private List<Integer> orderStepList;
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
public StepBinaryTree getTree() {
	return tree;
}
public void setTree(StepBinaryTree tree) {
	this.tree = tree;
}
public List<Integer> getOrderStepList() {
	return orderStepList;
}
public void setOrderStepList(List<Integer> orderStepList) {
	this.orderStepList = orderStepList;
}


}
