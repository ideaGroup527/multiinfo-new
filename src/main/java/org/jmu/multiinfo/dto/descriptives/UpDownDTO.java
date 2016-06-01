package org.jmu.multiinfo.dto.descriptives;

public class UpDownDTO {
private Double up;
private Double down;
private Long frequency;
private Double percentage;


public Double getUp() {
	return up;
}
public void setUp(Double up) {
	this.up = up;
}
public Double getDown() {
	return down;
}
public void setDown(Double down) {
	this.down = down;
}
public Long getFrequency() {
	return frequency;
}
public void setFrequency(Long frequency) {
	this.frequency = frequency;
}
public Double getPercentage() {
	return percentage;
}
public void setPercentage(Double percentage) {
	this.percentage = percentage;
}


}
