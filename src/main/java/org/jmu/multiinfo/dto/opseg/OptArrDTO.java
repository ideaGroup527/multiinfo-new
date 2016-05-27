package org.jmu.multiinfo.dto.opseg;

import java.util.Arrays;
import java.util.List;

import org.jmu.multiinfo.core.dto.BaseBean;

public class OptArrDTO extends BaseBean{
	private int a[][] ;
	private int resA[][] ;
	private double w[][] ;
	private double resW[][] ;
	private List<OptSegResDTO> optRes;
 	public int[][] getA() {
		return a;
	}
	public void setA(int[][] a) {
		this.a = a;
	}
	public int[][] getResA() {
		return resA;
	}
	public void setResA(int[][] resA) {
		this.resA = resA;
	}
	public double[][] getW() {
		return w;
	}
	public void setW(double[][] w) {
		this.w = w;
	}
	public double[][] getResW() {
		return resW;
	}
	public void setResW(double[][] resW) {
		this.resW = resW;
	}
	public List<OptSegResDTO> getOptRes() {
		return optRes;
	}
	public void setOptRes(List<OptSegResDTO> optRes) {
		this.optRes = optRes;
	}
	
	
}
