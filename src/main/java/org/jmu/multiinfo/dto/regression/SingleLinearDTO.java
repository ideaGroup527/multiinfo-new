package org.jmu.multiinfo.dto.regression;

import java.util.Arrays;

public class SingleLinearDTO extends CommonDTO{
//the number of observations that have been added to the model.
private Long n;
//斜率
private Double slope;

private Double slopeStdErr;
//
private Double rSquare;

private Double regressionSumSquares;

private Double xSumSquares;

private Double r;

private Double meanSquareError;

private Double intercept;


private Double significance;

private Double slopeConfidenceInterval;

//依次β_1 、β_2 、
private double[] regressionParameters;
//se：standard error，标准误差。
private double[] regressionParametersStandardErrors;

private Double adjustedRSquared;

private double[] predict;

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

@Override
public String toString() {
	return "SingleLinearDTO [n=" + n + ", slope=" + slope + ", slopeStdErr=" + slopeStdErr + ", rSquare=" + rSquare
			+ ", regressionSumSquares=" + regressionSumSquares + ", xSumSquares=" + xSumSquares + ", r=" + r
			+ ", meanSquareError=" + meanSquareError + ", intercept=" + intercept + ", significance=" + significance
			+ ", slopeConfidenceInterval=" + slopeConfidenceInterval + ", regressionParameters="
			+ Arrays.toString(regressionParameters) + ", regressionParametersStandardErrors="
			+ Arrays.toString(regressionParametersStandardErrors) + ", adjustedRSquared=" + adjustedRSquared
			+ ", predict=" + Arrays.toString(predict) + "]";
}





}
