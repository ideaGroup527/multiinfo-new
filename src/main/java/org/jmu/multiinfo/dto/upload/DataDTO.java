package org.jmu.multiinfo.dto.upload;

public class DataDTO {
private String position;
private String typeDes;
private Integer type;
private String typeFormat;
private Object data;

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

}