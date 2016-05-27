package org.jmu.multiinfo.dto.opseg;

public class SegDataDTO {
private Integer from;
private Integer to;
//组内离差平方和
private Double sswg;
public Integer getFrom() {
	return from;
}
public void setFrom(Integer from) {
	this.from = from;
}
public Integer getTo() {
	return to;
}
public void setTo(Integer to) {
	this.to = to;
}
public Double getSswg() {
	return sswg;
}
public void setSswg(Double sswg) {
	this.sswg = sswg;
}



}
