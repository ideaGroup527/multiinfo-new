package org.jmu.multiinfo.dto.comparemean;

public class AnovaDTO {
private Double F;
//总偏差平方和
private Double sst;
//组间离均差平方和
private Double ssbg;
//组内离均差平方和
private Double sswg;
//组间均差
private Double msbg;
//组内均差
private Double mswg;
//组间df
private Integer dfbg;
//组内df
private Integer dfwg;
//总df
private Integer dft;

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
public Integer getDfbg() {
	return dfbg;
}
public void setDfbg(Integer dfbg) {
	this.dfbg = dfbg;
}
public Integer getDfwg() {
	return dfwg;
}
public void setDfwg(Integer dfwg) {
	this.dfwg = dfwg;
}
public Integer getDft() {
	return dft;
}
public void setDft(Integer dft) {
	this.dft = dft;
}

}
