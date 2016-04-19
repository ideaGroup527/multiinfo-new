package org.jmu.multiinfo.dto.correlation;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;

public class DistanceCorrelationDTO extends BaseDTO{
	//变量间
	private Map<String,List<Map<String,DisCoDataDTO>>> resDataMap;
	//基本量
	private Map<String,ResultDescDTO> basicDataMap;
	
	//个体间
	private DisCoDataDTO[][] unitDataArr;

	public Map<String, List<Map<String, DisCoDataDTO>>> getResDataMap() {
		return resDataMap;
	}

	public void setResDataMap(Map<String, List<Map<String, DisCoDataDTO>>> resDataMap) {
		this.resDataMap = resDataMap;
	}

	public Map<String, ResultDescDTO> getBasicDataMap() {
		return basicDataMap;
	}

	public void setBasicDataMap(Map<String, ResultDescDTO> basicDataMap) {
		this.basicDataMap = basicDataMap;
	}

	public DisCoDataDTO[][] getUnitDataArr() {
		return unitDataArr;
	}

	public void setUnitDataArr(DisCoDataDTO[][] unitDataArr) {
		this.unitDataArr = unitDataArr;
	}
	
}
