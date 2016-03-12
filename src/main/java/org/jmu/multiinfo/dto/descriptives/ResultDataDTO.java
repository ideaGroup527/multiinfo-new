package org.jmu.multiinfo.dto.descriptives;

import java.util.Arrays;

public class ResultDataDTO {
private Object resultData;
private double[] dataArr;
private String varietyName;
private String typeDes;
private Integer type;
private String typeFormat;
public Object getResultData() {
	return resultData;
}
public void setResultData(Object resultData) {
	this.resultData = resultData;
}
public String getVarietyName() {
	return varietyName;
}
public void setVarietyName(String varietyName) {
	this.varietyName = varietyName;
}
public String getTypeDes() {
	return typeDes;
}
public void setTypeDes(String typeDes) {
	this.typeDes = typeDes;
}
public Integer getType() {
	return type;
}
public void setType(Integer type) {
	this.type = type;
}
public String getTypeFormat() {
	return typeFormat;
}
public void setTypeFormat(String typeFormat) {
	this.typeFormat = typeFormat;
}
public double[] getDataArr() {
	return dataArr;
}
public void setDataArr(double[] dataArr) {
	this.dataArr = dataArr;
}
@Override
public String toString() {
	return "ResultDataDTO [resultData=" + resultData + ", dataArr=" + Arrays.toString(dataArr) + ", varietyName="
			+ varietyName + ", typeDes=" + typeDes + ", type=" + type + ", typeFormat=" + typeFormat + "]";
}


}
