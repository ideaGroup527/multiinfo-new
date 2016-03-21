package org.jmu.multiinfo.dto.comparemean;

public class AnovaDTO {
private Double F;
private Double sst;
private Double ssbg;
private Double sswg;
private Double msbg;
private Double mswg;
public Double getF() {
	return F;
}
public void setF(Double f) {
	F = f;
}
public Double getSst() {
	return sst;
}
public void setSst(Double sst) {
	this.sst = sst;
}
public Double getSsbg() {
	return ssbg;
}
public void setSsbg(Double ssbg) {
	this.ssbg = ssbg;
}
public Double getSswg() {
	return sswg;
}
public void setSswg(Double sswg) {
	this.sswg = sswg;
}
public Double getMsbg() {
	return msbg;
}
public void setMsbg(Double msbg) {
	this.msbg = msbg;
}
public Double getMswg() {
	return mswg;
}
public void setMswg(Double mswg) {
	this.mswg = mswg;
}
@Override
public String toString() {
	return "AnovaDTO [F=" + F + ", sst=" + sst + ", ssbg=" + ssbg + ", sswg=" + sswg + ", msbg=" + msbg + ", mswg="
			+ mswg + "]";
}
}
