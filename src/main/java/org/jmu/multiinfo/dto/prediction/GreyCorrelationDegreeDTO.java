package org.jmu.multiinfo.dto.prediction;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class GreyCorrelationDegreeDTO extends BaseDTO{
private double[] correlationArr;
private double[] sortCorrelationArr;
private double maxCorrelation;
public double[] getCorrelationArr() {
	return correlationArr;
}
public void setCorrelationArr(double[] correlationArr) {
	this.correlationArr = correlationArr;
}
public double[] getSortCorrelationArr() {
	return sortCorrelationArr;
}
public void setSortCorrelationArr(double[] sortCorrelationArr) {
	this.sortCorrelationArr = sortCorrelationArr;
}
public double getMaxCorrelation() {
	return maxCorrelation;
}
public void setMaxCorrelation(double maxCorrelation) {
	this.maxCorrelation = maxCorrelation;
}
}
