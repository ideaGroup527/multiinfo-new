package org.jmu.multiinfo.dto.reducingdim;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;

public class CorrespAnalysisDTO extends BaseDTO{
private double[][] proArr;
private double[][] z;
private double[][] zt;
private double[][] sr ;
private double[][] sq ;
private PCAEigenvalueDTO sqPcaDTO;
private PCAEigenvalueDTO srPcaDTO;


public double[][] getProArr() {
	return proArr;
}
public void setProArr(double[][] proArr) {
	this.proArr = proArr;
}
public double[][] getZ() {
	return z;
}
public void setZ(double[][] z) {
	this.z = z;
}
public double[][] getZt() {
	return zt;
}
public void setZt(double[][] zt) {
	this.zt = zt;
}
public double[][] getSr() {
	return sr;
}
public void setSr(double[][] sr) {
	this.sr = sr;
}
public double[][] getSq() {
	return sq;
}
public void setSq(double[][] sq) {
	this.sq = sq;
}
public PCAEigenvalueDTO getSqPcaDTO() {
	return sqPcaDTO;
}
public void setSqPcaDTO(PCAEigenvalueDTO sqPcaDTO) {
	this.sqPcaDTO = sqPcaDTO;
}
public PCAEigenvalueDTO getSrPcaDTO() {
	return srPcaDTO;
}
public void setSrPcaDTO(PCAEigenvalueDTO srPcaDTO) {
	this.srPcaDTO = srPcaDTO;
}

}
