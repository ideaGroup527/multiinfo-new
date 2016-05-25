package org.jmu.multiinfo.dto.opseg;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class OptSegDTO extends BaseDTO{
private double[][] D;
private List<OptSegResDTO> optRes;
public double[][] getD() {
	return D;
}
public void setD(double[][] d) {
	D = d;
}
public List<OptSegResDTO> getOptRes() {
	return optRes;
}
public void setOptRes(List<OptSegResDTO> optRes) {
	this.optRes = optRes;
}

}
