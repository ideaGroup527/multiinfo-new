package org.jmu.multiinfo.dto.upload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VarietyDTO {
	private String position;
	@JsonProperty("typedes") 
	private String typeDes;
	private Integer type;
	@JsonProperty("typeformat") 
	private String typeFormat;
	@JsonProperty("varietyname") 
	private String  varietyName;
	@JsonProperty("varietydes") 
	private String varietyDes;
	//数据范围
	private String range;
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
	public String getTypeFormat() {
		return typeFormat;
	}
	public void setTypeFormat(String typeFormat) {
		this.typeFormat = typeFormat;
	}
	public String getVarietyName() {
		return varietyName;
	}
	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getVarietyDes() {
		return varietyDes;
	}
	public void setVarietyDes(String varietyDes) {
		this.varietyDes = varietyDes;
	}
}
