package org.jmu.multiinfo.dto.upload;

public class VarietyDTO {
	private String position;
	private String typeDes;
	private Integer type;
	private String typeFormat;
	private String  varietyName;
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
}
