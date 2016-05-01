package org.jmu.multiinfo.dto.regression;

import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class SlipStepwiseDTO extends BaseDTO{
	private StepwiseMultipleDTO preData;
	private StepwiseMultipleDTO  backData;
	private Map<String,Double> preForecast;
	private Map<String,Double> backForecast;
	public StepwiseMultipleDTO getPreData() {
		return preData;
	}
	public void setPreData(StepwiseMultipleDTO preData) {
		this.preData = preData;
	}
	public StepwiseMultipleDTO getBackData() {
		return backData;
	}
	public void setBackData(StepwiseMultipleDTO backData) {
		this.backData = backData;
	}
	public Map<String, Double> getPreForecast() {
		return preForecast;
	}
	public void setPreForecast(Map<String, Double> preForecast) {
		this.preForecast = preForecast;
	}
	public Map<String, Double> getBackForecast() {
		return backForecast;
	}
	public void setBackForecast(Map<String, Double> backForecast) {
		this.backForecast = backForecast;
	}
}
