package org.jmu.multiinfo.dto.descriptives;

import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;
import org.jmu.multiinfo.dto.comparemean.ResultDataDTO;

public class CommonDTO  extends BaseDTO{
	private Map<String,ResultDataDTO> resDataMap;
	private String resultType;
	private String reportTitle;
	public Map<String, ResultDataDTO> getResDataMap() {
		return resDataMap;
	}
	public void setResDataMap(Map<String, ResultDataDTO> resDataMap) {
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
