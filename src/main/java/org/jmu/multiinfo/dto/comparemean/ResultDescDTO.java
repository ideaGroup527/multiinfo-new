package org.jmu.multiinfo.dto.comparemean;

public class ResultDescDTO {
private Double max;
private Double min;
private Double arithmeticMean;
private Double variance;
private Double standardDeviation;
private Double total;
private Double median;
private Double skewness;
private Double kurtosis;
private Integer count;
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
@Override
public String toString() {
	return "ResultDescDTO [max=" + max + ", min=" + min + ", arithmeticMean=" + arithmeticMean + ", variance="
			+ variance + ", standardDeviation=" + standardDeviation + ", total=" + total + ", median=" + median
			+ ", skewness=" + skewness + ", kurtosis=" + kurtosis + ", count=" + count + "]";
}

}
