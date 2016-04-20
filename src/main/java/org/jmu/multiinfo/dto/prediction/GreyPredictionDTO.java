package org.jmu.multiinfo.dto.prediction;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class GreyPredictionDTO extends BaseDTO{
private boolean examineSuccess;
private double[] resData;
private String pridictName;
private List<VarietyDTO> independVarList;
public boolean getExamineSuccess() {
	return examineSuccess;
}

public void setExamineSuccess(boolean examineSuccess) {
	this.examineSuccess = examineSuccess;
}

public double[] getResData() {
	return resData;
}

public void setResData(double[] resData) {
	this.resData = resData;
}

public String getPridictName() {
	return pridictName;
}

public void setPridictName(String pridictName) {
	this.pridictName = pridictName;
}

public List<VarietyDTO> getIndependVarList() {
	return independVarList;
}

public void setIndependVarList(List<VarietyDTO> independVarList) {
	this.independVarList = independVarList;
}


}
