package org.jmu.multiinfo.dto.mean;

import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class MedianDTO extends BaseDTO{
private Map<String,List<Map<String,ResultDataDTO>>> resDataMap;
private String resultType;
private String reportTitle;
public Map<String, List<Map<String, ResultDataDTO>>> getResDataMap() {
	return resDataMap;
}
public void setResDataMap(Map<String, List<Map<String, ResultDataDTO>>> resDataMap) {
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
