package org.jmu.multiinfo.dto.regression;


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



}
