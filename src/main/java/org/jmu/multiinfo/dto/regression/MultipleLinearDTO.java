package org.jmu.multiinfo.dto.regression;


public class MultipleLinearDTO extends CommonDTO{
private Double residualSumOfSquares;
//R2adj = 1 - [SSR (n - 1)] / [SSTO (n - p)]
private Double adjustedRSquared;
// R2 = 1 - SSR / SSTO
private Double rSquared;
//the sum of squared deviations of Y from its mean.
private Double totalSumOfSquares;

private Double errorVariance;
//the variance of the regressand, ie Var(y).
private Double regressandVariance;

private Double regressionStandardError;
//the regression parameters b.
private double[] regressionParameters;
public Double getResidualSumOfSquares() {
	return residualSumOfSquares;
}
public void setResidualSumOfSquares(Double residualSumOfSquares) {
	this.residualSumOfSquares = residualSumOfSquares;
}
public Double getAdjustedRSquared() {
	return adjustedRSquared;
}
public void setAdjustedRSquared(Double adjustedRSquared) {
	this.adjustedRSquared = adjustedRSquared;
}
public Double getRSquared() {
	return rSquared;
}
public void setRSquared(Double rSquared) {
	this.rSquared = rSquared;
}
public Double getTotalSumOfSquares() {
	return totalSumOfSquares;
}
public void setTotalSumOfSquares(Double totalSumOfSquares) {
	this.totalSumOfSquares = totalSumOfSquares;
}
public Double getErrorVariance() {
	return errorVariance;
}
public void setErrorVariance(Double errorVariance) {
	this.errorVariance = errorVariance;
}
public Double getRegressandVariance() {
	return regressandVariance;
}
public void setRegressandVariance(Double regressandVariance) {
	this.regressandVariance = regressandVariance;
}
public Double getRegressionStandardError() {
	return regressionStandardError;
}
public void setRegressionStandardError(Double regressionStandardError) {
	this.regressionStandardError = regressionStandardError;
}
public double[] getRegressionParameters() {
	return regressionParameters;
}
public void setRegressionParameters(double[] regressionParameters) {
	this.regressionParameters = regressionParameters;
}


}
