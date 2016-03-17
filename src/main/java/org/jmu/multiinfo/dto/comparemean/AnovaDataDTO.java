package org.jmu.multiinfo.dto.comparemean;

import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class AnovaDataDTO extends BaseDTO{
	private Map<String,AnovaDTO> resDataMap;
	private String resultType;
	private String reportTitle;
	public Map<String, AnovaDTO> getResDataMap() {
		return resDataMap;
	}
	public void setResDataMap(Map<String, AnovaDTO> resDataMap) {
		this.resDataMap = resDataMap;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	
	
}
