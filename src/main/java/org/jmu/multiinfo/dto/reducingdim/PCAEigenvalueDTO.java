package org.jmu.multiinfo.dto.reducingdim;

public class PCAEigenvalueDTO {
	//特征值从大到小
	private double[] sortEigenvalues;
	//主成分系数表
	private double[][] eigenvectors;
	//解释总方差初始合计
	private double[] eigTotalInit;
	
	//解释总方差提取平方和载入合计
	private double[] eigTotalExtra;
	
	//初始方差贡献率
	private double[] varEigInit ;

	//初始累积贡献率
	private double[] accEigInit ;
	
	//提取平方和载入方差贡献率
	private double[] varEigExtra ;

	//提取平方和载入累积贡献率
	private double[] accEigExtra ;

	//成分矩阵
	private double[][] componentArr;
	
	//相关矩阵
	private double[][] correlationArr;
	
	//行列式
	private double determinant;
	
	
	//公因子方差表
	private double[][] communalityArr;

	public double[] getSortEigenvalues() {
		return sortEigenvalues;
	}

	public void setSortEigenvalues(double[] sortEigenvalues) {
		this.sortEigenvalues = sortEigenvalues;
	}

	public double[][] getEigenvectors() {
		return eigenvectors;
	}

	public void setEigenvectors(double[][] eigenvectors) {
		this.eigenvectors = eigenvectors;
	}



	public double[][] getComponentArr() {
		return componentArr;
	}

	public void setComponentArr(double[][] componentArr) {
		this.componentArr = componentArr;
	}

	public double[][] getCorrelationArr() {
		return correlationArr;
	}

	public void setCorrelationArr(double[][] correlationArr) {
		this.correlationArr = correlationArr;
	}

	public double getDeterminant() {
		return determinant;
	}

	public void setDeterminant(double determinant) {
		this.determinant = determinant;
	}

	public double[] getEigTotalInit() {
		return eigTotalInit;
	}

	public void setEigTotalInit(double[] eigTotalInit) {
		this.eigTotalInit = eigTotalInit;
	}

	public double[] getEigTotalExtra() {
		return eigTotalExtra;
	}

	public void setEigTotalExtra(double[] eigTotalExtra) {
		this.eigTotalExtra = eigTotalExtra;
	}

	public double[] getVarEigInit() {
		return varEigInit;
	}

	public void setVarEigInit(double[] varEigInit) {
		this.varEigInit = varEigInit;
	}

	public double[] getAccEigInit() {
		return accEigInit;
	}

	public void setAccEigInit(double[] accEigInit) {
		this.accEigInit = accEigInit;
	}

	public double[] getVarEigExtra() {
		return varEigExtra;
	}

	public void setVarEigExtra(double[] varEigExtra) {
		this.varEigExtra = varEigExtra;
	}

	public double[] getAccEigExtra() {
		return accEigExtra;
	}

	public void setAccEigExtra(double[] accEigExtra) {
		this.accEigExtra = accEigExtra;
	}

	public double[][] getCommunalityArr() {
		return communalityArr;
	}

	public void setCommunalityArr(double[][] communalityArr) {
		this.communalityArr = communalityArr;
	}
	
}
