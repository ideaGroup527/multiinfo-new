package org.jmu.multiinfo.dto.mean;

import java.util.List;

import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;


/****
 * 
 * 
 * @Title: MedianCondition.java
 * @Package org.jmu.multiinfo.dto.mean
 * @Description: 中位数分析条件
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月3日 下午6:55:41
 * @version V1.0
 *
 */
public class MedianCondition {
	//自变量
 private VarietyDTO independentVariable;
 
 //因变量
 private List<VarietyDTO> dependentVariable;
 
 //数据
 private DataDTO[][] dataGrid;

public VarietyDTO getIndependentVariable() {
	return independentVariable;
}

public void setIndependentVariable(VarietyDTO independentVariable) {
	this.independentVariable = independentVariable;
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
