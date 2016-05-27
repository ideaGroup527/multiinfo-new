package org.jmu.multiinfo.dto.opseg;

import java.util.List;

public class OptSegResDTO {
private Integer segNum;
private List<SegDataDTO> segDataList;
//总离差平方和
private Double sst;
public Integer getSegNum() {
	return segNum;
}
public void setSegNum(Integer segNum) {
	this.segNum = segNum;
}
public List<SegDataDTO> getSegDataList() {
	return segDataList;
}
public void setSegDataList(List<SegDataDTO> segDataList) {
	this.segDataList = segDataList;
}
public Double getSst() {
	return sst;
}
public void setSst(Double sst) {
	this.sst = sst;
}


}
