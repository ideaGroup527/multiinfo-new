package org.jmu.multiinfo.dto.prediction;

import java.util.List;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class GreyCorrelationDegreeCondition {
	//子序列
 private List<VarietyDTO> sonSeqArr;
 
 //母序列
 private VarietyDTO motherSeq;
 
 //数据
 private DataDTO[][] dataGrid;
 
 //分辨系数
 private double resolutionRatio;

public List<VarietyDTO> getSonSeqArr() {
	return sonSeqArr;
}

public void setSonSeqArr(List<VarietyDTO> sonSeqArr) {
	this.sonSeqArr = sonSeqArr;
}

public VarietyDTO getMotherSeq() {
	return motherSeq;
}

public void setMotherSeq(VarietyDTO motherSeq) {
	this.motherSeq = motherSeq;
}

public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}

public double getResolutionRatio() {
	return resolutionRatio;
}

public void setResolutionRatio(double resolutionRatio) {
	this.resolutionRatio = resolutionRatio;
}
 
}
