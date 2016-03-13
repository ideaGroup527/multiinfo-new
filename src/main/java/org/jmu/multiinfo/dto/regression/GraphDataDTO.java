package org.jmu.multiinfo.dto.regression;

import java.util.List;

public class GraphDataDTO {
private List<Double> data;
private Double max;
private Double min;
private Double mean;
public List<Double> getData() {
	return data;
}
public void setData(List<Double> data) {
	this.data = data;
}
public Double getMax() {
	return max;
}
public void setMax(Double max) {
	this.max = max;
}
public Double getMin() {
	return min;
}
public void setMin(Double min) {
	this.min = min;
}
public Double getMean() {
	return mean;
}
public void setMean(Double mean) {
	this.mean = mean;
}

}
