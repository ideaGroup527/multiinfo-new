package org.jmu.multiinfo.dto.basestatistics;

import java.util.List;

import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;

public class OneVarCondition {
	 //变量
	 private List<VarietyDTO> variableList;
	 
	 //数据
	 private DataDTO[][] dataGrid;

	public List<VarietyDTO> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<VarietyDTO> variableList) {
		this.variableList = variableList;
	}

	public DataDTO[][] getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataDTO[][] dataGrid) {
		this.dataGrid = dataGrid;
	}
	 
	 
}
