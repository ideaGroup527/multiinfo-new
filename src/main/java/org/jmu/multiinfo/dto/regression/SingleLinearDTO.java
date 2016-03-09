package org.jmu.multiinfo.dto.regression;

import java.util.Arrays;

public class SingleLinearDTO extends CommonDTO{
//the number of observations that have been added to the model.
private Long n;
//斜率
private Double slope;

private Double slopeStdErr;
//可决系数，0≤R^2≤1，若R^2→1，表明解释变量X_2 、X_3对被解释变量Y影响越大，模型的拟合程度越高。
private Double rSquare;

private Double regressionSumSquares;
//
private Double sumSquaredErrors;

private Double xSumSquares;

private Double r;

private Double meanSquareError;
//截距
private Double intercept;


private Double significance;

private Double slopeConfidenceInterval;

//依次β_1 、β_2 、
private double[] regressionParameters;
//se：standard error，标准误差。
private double[] regressionParametersStandardErrors;
//R2adj = 1 - [SSR (n - 1)] / [SSTO (n - p)] 修正的可决系数，R ̅^2=1-（1-R^2）(n-1)/(n-k)，同R^2类似，若R ̅^2→1，表明解释变量X_2 、X_3对被解释变量Y影响越大，模型的拟合程度越高。
private Double adjustedRSquared;

private double[] predict;

private double[] ttests;

public Long getN() {
	return n;
}

public void setN(Long n) {
	this.n = n;
}

public Double getSlope() {
	return slope;
}

public void setSlope(Double slope) {
	this.slope = slope;
}

public Double getSlopeStdErr() {
	return slopeStdErr;
}

public void setSlopeStdErr(Double slopeStdErr) {
	this.slopeStdErr = slopeStdErr;
}

public Double getRegressionSumSquares() {
	return regressionSumSquares;
}

public void setRegressionSumSquares(Double regressionSumSquares) {
	this.regressionSumSquares = regressionSumSquares;
}

public Double getR() {
	return r;
}

public void setR(Double r) {
	this.r = r;
}

public Double getMeanSquareError() {
	return meanSquareError;
}

public void setMeanSquareError(Double meanSquareError) {
	this.meanSquareError = meanSquareError;
}

public Double getIntercept() {
	return intercept;
}

public void setIntercept(Double intercept) {
	this.intercept = intercept;
}

public Double getRSquare() {
	return rSquare;
}

public void setRSquare(Double rSquare) {
	this.rSquare = rSquare;
}

public Double getXSumSquares() {
	return xSumSquares;
}

public void setXSumSquares(Double xSumSquares) {
	this.xSumSquares = xSumSquares;
}

public Double getSignificance() {
	return significance;
}

public void setSignificance(Double significance) {
	this.significance = significance;
}

public Double getSlopeConfidenceInterval() {
	return slopeConfidenceInterval;
}

public void setSlopeConfidenceInterval(Double slopeConfidenceInterval) {
	this.slopeConfidenceInterval = slopeConfidenceInterval;
}

public double[] getRegressionParameters() {
	return regressionParameters;
}

public void setRegressionParameters(double[] regressionParameters) {
	this.regressionParameters = regressionParameters;
}

public double[] getRegressionParametersStandardErrors() {
	return regressionParametersStandardErrors;
}

public void setRegressionParametersStandardErrors(double[] regressionParametersStandardErrors) {
	this.regressionParametersStandardErrors = regressionParametersStandardErrors;
}

public Double getAdjustedRSquared() {
	return adjustedRSquared;
}

public void setAdjustedRSquared(Double adjustedRSquared) {
	this.adjustedRSquared = adjustedRSquared;
}

public double[] getPredict() {
	return predict;
}

public void setPredict(double[] predict) {
	this.predict = predict;
}

public Double getSumSquaredErrors() {
	return sumSquaredErrors;
}

public void setSumSquaredErrors(Double sumSquaredErrors) {
	this.sumSquaredErrors = sumSquaredErrors;
}

public double[] getTtests() {
	return ttests;
}

public void setTtests(double[] ttests) {
	this.ttests = ttests;
}

@Override
public String toString() {
	return "SingleLinearDTO [n=" + n + ", slope=" + slope + ", slopeStdErr=" + slopeStdErr + ", rSquare=" + rSquare
			+ ", regressionSumSquares=" + regressionSumSquares + ", sumSquaredErrors=" + sumSquaredErrors
			+ ", xSumSquares=" + xSumSquares + ", r=" + r + ", meanSquareError=" + meanSquareError + ", intercept="
			+ intercept + ", significance=" + significance + ", slopeConfidenceInterval=" + slopeConfidenceInterval
			+ ", regressionParameters=" + Arrays.toString(regressionParameters)
			+ ", regressionParametersStandardErrors=" + Arrays.toString(regressionParametersStandardErrors)
			+ ", adjustedRSquared=" + adjustedRSquared + ", predict=" + Arrays.toString(predict) + ", ttests="
			+ Arrays.toString(ttests) + "]";
}


}
