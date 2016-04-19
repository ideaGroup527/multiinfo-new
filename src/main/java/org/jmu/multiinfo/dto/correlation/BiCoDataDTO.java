package org.jmu.multiinfo.dto.correlation;

public class BiCoDataDTO {
private Double pearsonR;
private Double pearsonT;
private Double spearmanR;
private Double spearmanT;
private Integer n;
//叉积平方和lxy
private Double croProSumSq;
//协方差
private Double covariance;
private Double sigTwo;
private Double sigOne;

public Double getPearsonR() {
	return pearsonR;
}
public void setPearsonR(Double pearsonR) {
	this.pearsonR = pearsonR;
}
public Double getPearsonT() {
	return pearsonT;
}
public void setPearsonT(Double pearsonT) {
	this.pearsonT = pearsonT;
}
public Double getSpearmanR() {
	return spearmanR;
}
public void setSpearmanR(Double spearmanR) {
	this.spearmanR = spearmanR;
}
public Double getSpearmanT() {
	return spearmanT;
}
public void setSpearmanT(Double spearmanT) {
	this.spearmanT = spearmanT;
}
public Integer getN() {
	return n;
}
public void setN(Integer n) {
	this.n = n;
}
public Double getCroProSumSq() {
	return croProSumSq;
}
public void setCroProSumSq(Double croProSumSq) {
	this.croProSumSq = croProSumSq;
}
public Double getCovariance() {
	return covariance;
}
public void setCovariance(Double covariance) {
	this.covariance = covariance;
}
public Double getSigTwo() {
	return sigTwo;
}
public void setSigTwo(Double sigTwo) {
	this.sigTwo = sigTwo;
}
public Double getSigOne() {
	return sigOne;
}
public void setSigOne(Double sigOne) {
	this.sigOne = sigOne;
}

}
