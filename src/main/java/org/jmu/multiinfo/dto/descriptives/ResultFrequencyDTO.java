package org.jmu.multiinfo.dto.descriptives;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;


public class ResultFrequencyDTO extends ResultDescDTO{
//有效基本数据
private List<Object> uniqueData;
private Map<String,Long> frequencyMap;
private Map<String,Double> percentage;
//有效百分比
private Map<String,Double> validatePercentage;

//累积百分比
private Map<String,Double> accumulationPercentage;

//频数总计
private Long sumFreq;


private Object sumPercentage;
//有效百分比总计
private Object sumValidatePercentage;

public List<Object> getUniqueData() {
	return uniqueData;
}

public void setUniqueData(List<Object> uniqueData) {
	this.uniqueData = uniqueData;
}

public Map<String, Long> getFrequencyMap() {
	return frequencyMap;
}

public void setFrequencyMap(Map<String, Long> frequencyMap) {
	this.frequencyMap = frequencyMap;
}

public Map<String, Double> getPercentage() {
	return percentage;
}

public void setPercentage(Map<String, Double> percentage) {
	this.percentage = percentage;
}

public Map<String, Double> getValidatePercentage() {
	return validatePercentage;
}

public void setValidatePercentage(Map<String, Double> validatePercentage) {
	this.validatePercentage = validatePercentage;
}

public Map<String, Double> getAccumulationPercentage() {
	return accumulationPercentage;
}

public void setAccumulationPercentage(Map<String, Double> accumulationPercentage) {
	this.accumulationPercentage = accumulationPercentage;
}

public Long getSumFreq() {
	return sumFreq;
}

public void setSumFreq(Long sumFreq) {
	this.sumFreq = sumFreq;
}

public Object getSumPercentage() {
	return sumPercentage;
}

public void setSumPercentage(Object sumPercentage) {
	this.sumPercentage = sumPercentage;
}

public Object getSumValidatePercentage() {
	return sumValidatePercentage;
}

public void setSumValidatePercentage(Object sumValidatePercentage) {
	this.sumValidatePercentage = sumValidatePercentage;
}

}
