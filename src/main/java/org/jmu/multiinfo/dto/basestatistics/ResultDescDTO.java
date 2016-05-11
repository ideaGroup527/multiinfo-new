package org.jmu.multiinfo.dto.basestatistics;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseBean;
import org.jmu.multiinfo.dto.descriptives.PercentileDTO;

public class ResultDescDTO extends BaseBean{
private Double max;
private Double min;
private Double arithmeticMean;
private Double variance;
private Double standardDeviation;
private Double total;
private Double median;
private Double skewness;
private Double kurtosis;
private List<PercentileDTO> percentiles;
private Integer count;
private List<Double> errPercentiles;
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
public Double getArithmeticMean() {
	return arithmeticMean;
}
public void setArithmeticMean(Double arithmeticMean) {
	this.arithmeticMean = arithmeticMean;
}
public Double getVariance() {
	return variance;
}
public void setVariance(Double variance) {
	this.variance = variance;
}
public Double getStandardDeviation() {
	return standardDeviation;
}
public void setStandardDeviation(Double standardDeviation) {
	this.standardDeviation = standardDeviation;
}
public Double getTotal() {
	return total;
}
public void setTotal(Double total) {
	this.total = total;
}
public Double getSkewness() {
	return skewness;
}
public void setSkewness(Double skewness) {
	this.skewness = skewness;
}
public Double getKurtosis() {
	return kurtosis;
}
public void setKurtosis(Double kurtosis) {
	this.kurtosis = kurtosis;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public Double getMedian() {
		return median;
	}
public void setMedian(Double median) {
		this.median = median;
	}
public List<PercentileDTO> getPercentiles() {
	return percentiles;
}
public void setPercentiles(List<PercentileDTO> percentiles) {
	this.percentiles = percentiles;
}
public List<Double> getErrPercentiles() {
	return errPercentiles;
}
public void setErrPercentiles(List<Double> errPercentiles) {
	this.errPercentiles = errPercentiles;
}
@Override
public String toString() {
	return "ResultDescDTO [max=" + max + ", min=" + min + ", arithmeticMean=" + arithmeticMean + ", variance="
			+ variance + ", standardDeviation=" + standardDeviation + ", total=" + total + ", median=" + median
			+ ", skewness=" + skewness + ", kurtosis=" + kurtosis + ", percentiles=" + percentiles + ", count=" + count
			+ ", errPercentiles=" + errPercentiles + "]";
}

}
