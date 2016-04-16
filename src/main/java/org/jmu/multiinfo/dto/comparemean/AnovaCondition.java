package org.jmu.multiinfo.dto.comparemean;

import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 
 * 
 * 
 * @Title: AnovaCondition.java
 * @Package org.jmu.multiinfo.dto.comparemean
 * @Description:  方差分析入参
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月16日 下午10:21:49
 * @version V1.0
 *
 */
public class AnovaCondition {
	//因子变量
	@JsonProperty("factorvariable") 
 private VarietyDTO factorVariable;
 
 //因变量
	@JsonProperty("dependentvariable") 
 private List<VarietyDTO> dependentVariable;
 
 //数据
	@JsonProperty("dataGrid") 
 private DataDTO[][] dataGrid;

public VarietyDTO getFactorVariable() {
	return factorVariable;
}

public void setFactorVariable(VarietyDTO factorVariable) {
	this.factorVariable = factorVariable;
}

public List<VarietyDTO> getDependentVariable() {
	return dependentVariable;
}

public void setDependentVariable(List<VarietyDTO> dependentVariable) {
	this.dependentVariable = dependentVariable;
}

public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}
}
