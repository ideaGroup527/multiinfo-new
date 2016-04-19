package org.jmu.multiinfo.dto.correlation;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;

public class BivariateCorrelateDTO extends BaseDTO{
private Map<String,List<Map<String,BiCoDataDTO>>> resDataMap;
private Map<String,ResultDescDTO> basicDataMap;
public Map<String, List<Map<String, BiCoDataDTO>>> getResDataMap() {
	return resDataMap;
}

public void setResDataMap(Map<String, List<Map<String, BiCoDataDTO>>> resDataMap) {
	this.resDataMap = resDataMap;
}

public Map<String, ResultDescDTO> getBasicDataMap() {
	return basicDataMap;
}

public void setBasicDataMap(Map<String, ResultDescDTO> basicDataMap) {
	this.basicDataMap = basicDataMap;
}


}
