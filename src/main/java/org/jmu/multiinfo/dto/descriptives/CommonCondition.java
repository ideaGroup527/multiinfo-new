package org.jmu.multiinfo.dto.descriptives;

import java.util.List;

import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
public class CommonCondition {
	 //变量
	@JsonProperty("variablelist") 
	 private List<VarietyDTO> variableList;
	 
	 //数据
	@JsonProperty("datagrid") 
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
