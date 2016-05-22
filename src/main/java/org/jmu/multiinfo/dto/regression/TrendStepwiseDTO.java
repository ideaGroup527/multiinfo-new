package org.jmu.multiinfo.dto.regression;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class TrendStepwiseDTO  extends BaseDTO{
private List<SlipStepwiseDTO> trendWiseDTO;
private Map<String,Double> preForecast;
private Map<String,Double> backForecast;
public List<SlipStepwiseDTO> getTrendWiseDTO() {
	return trendWiseDTO;
}

public void setTrendWiseDTO(List<SlipStepwiseDTO> trendWiseDTO) {
	this.trendWiseDTO = trendWiseDTO;
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
