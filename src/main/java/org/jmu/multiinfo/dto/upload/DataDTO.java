package org.jmu.multiinfo.dto.upload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDTO {
private String position;
@JsonProperty("positiondes") 
private String positionDes;
@JsonProperty("positiondes") 
private String typeDes;
private Integer type;
@JsonProperty("typeformat") 
private String typeFormat;
private Object data;
@JsonProperty("mergedrange") 
private String mergedRange = "";
@JsonProperty("mergedrangedes") 
	private String mergedRangeDes = "";
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
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
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
public String getTypeFormat() {
	return typeFormat;
}
public void setTypeFormat(String typeFormat) {
	this.typeFormat = typeFormat;
}
public String getMergedRange() {
	return mergedRange;
}
public void setMergedRange(String mergedRange) {
	this.mergedRange = mergedRange;
}
public String getPositionDes() {
	return positionDes;
}
public void setPositionDes(String positionDes) {
	this.positionDes = positionDes;
}


	public String getMergedRangeDes() {
		return mergedRangeDes;
	}

	public void setMergedRangeDes(String mergedRangeDes) {
		this.mergedRangeDes = mergedRangeDes;
	}
}