package org.jmu.multiinfo.dto.regression;

import java.util.Arrays;
/**
 * 
 * 
 * @Title: MultipleLinearDTO.java
 * @Package org.jmu.multiinfo.dto.regression
 * @Description: 普通最小二乘法多元线性回归模型
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月8日 上午10:32:46
 * @version V1.0
 *
 */
public class MultipleLinearDTO extends CommonDTO{
private Double residualSumOfSquares;
//R2adj = 1 - [SSR (n - 1)] / [SSTO (n - p)] 修正的可决系数，R ̅^2=1-（1-R^2）(n-1)/(n-k)，同R^2类似，若R ̅^2→1，表明解释变量X_2 、X_3对被解释变量Y影响越大，模型的拟合程度越高。
private Double adjustedRSquared;
// R2 = 1 - SSR / SSTO 可决系数，0≤R^2≤1，若R^2→1，表明解释变量X_2 、X_3对被解释变量Y影响越大，模型的拟合程度越高。
private Double rSquared;
//the sum of squared deviations of Y from its mean.
private Double totalSumOfSquares;

private Double errorVariance;
//the variance of the regressand, ie Var(y).
private Double regressandVariance;

private Double regressionStandardError;
//依次β_1 、β_2 、β_3。。。 
private double[] regressionParameters;
//se：standard error，标准误差。
private double[] regressionParametersStandardErrors;

//
private double[] residuals;
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
public double[] getRegressionParametersStandardErrors() {
	return regressionParametersStandardErrors;
}
public void setRegressionParametersStandardErrors(double[] regressionParametersStandardErrors) {
	this.regressionParametersStandardErrors = regressionParametersStandardErrors;
}
public double[] getResiduals() {
	return residuals;
}
public void setResiduals(double[] residuals) {
	this.residuals = residuals;
}
@Override
public String toString() {
	return "MultipleLinearDTO [residualSumOfSquares=" + residualSumOfSquares + ", adjustedRSquared=" + adjustedRSquared
			+ ", rSquared=" + rSquared + ", totalSumOfSquares=" + totalSumOfSquares + ", errorVariance=" + errorVariance
			+ ", regressandVariance=" + regressandVariance + ", regressionStandardError=" + regressionStandardError
			+ ", regressionParameters=" + Arrays.toString(regressionParameters)
			+ ", regressionParametersStandardErrors=" + Arrays.toString(regressionParametersStandardErrors)
			+ ", residuals=" + Arrays.toString(residuals) + "]";
}

}