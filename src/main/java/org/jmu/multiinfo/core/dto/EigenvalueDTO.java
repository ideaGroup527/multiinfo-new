package org.jmu.multiinfo.core.dto;


/***
 * 特征值
 * @Title: EigenvalueDTO.java
 * @Package org.jmu.multiinfo.core.dto
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月22日 下午11:28:58
 * @version V1.0
 *
 */
public class EigenvalueDTO {
private double[][] d;
private double[][] v;
private double[] imagEigenvalues;
private double[] realEigenvalues;
//特征值从大到小
private double[] sortEigenvalues;
//根据特征值顺序，每行对应特征值的特征向量（主成分系数表的转置）
private double[][] eigenvectors;

//方差贡献率
private double[] varEig ;

//累积贡献率
private double[] accEig ;

//成分矩阵的转置
private double[][] componentArr;

public double[][] getD() {
	return d;
}
public void setD(double[][] d) {
	this.d = d;
}
public double[][] getV() {
	return v;
}
public void setV(double[][] v) {
	this.v = v;
}
public double[] getImagEigenvalues() {
	return imagEigenvalues;
}
public void setImagEigenvalues(double[] imagEigenvalues) {
	this.imagEigenvalues = imagEigenvalues;
}
public double[] getRealEigenvalues() {
	return realEigenvalues;
}
public void setRealEigenvalues(double[] realEigenvalues) {
	this.realEigenvalues = realEigenvalues;
}
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
public double[] getVarEig() {
	return varEig;
}
public void setVarEig(double[] varEig) {
	this.varEig = varEig;
}
public double[] getAccEig() {
	return accEig;
}
public void setAccEig(double[] accEig) {
	this.accEig = accEig;
}
public double[][] getComponentArr() {
	return componentArr;
}
public void setComponentArr(double[][] componentArr) {
	this.componentArr = componentArr;
}


}
