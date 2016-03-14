package org.jmu.multiinfo.dto.descriptives;
/**
 * *
 * 
 * 
 * @Title: KSTestDTO.java
 * @Package org.jmu.multiinfo.dto.descriptives
 * @Description:  kolmogorov-smirnov检验
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月14日 下午11:35:10
 * @version V1.0
 *
 */
public class KSTestDTO {
private Long n;
private Double mean;
private Double sd;
private Double absolute;
private Double positive;
private Double negative;
private Double ksz;
private Double sig;
public Long getN() {
	return n;
}
public void setN(Long n) {
	this.n = n;
}
public Double getMean() {
	return mean;
}
public void setMean(Double mean) {
	this.mean = mean;
}
public Double getSd() {
	return sd;
}
public void setSd(Double sd) {
	this.sd = sd;
}
public Double getAbsolute() {
	return absolute;
}
public void setAbsolute(Double absolute) {
	this.absolute = absolute;
}
public Double getPositive() {
	return positive;
}
public void setPositive(Double positive) {
	this.positive = positive;
}
public Double getNegative() {
	return negative;
}
public void setNegative(Double negative) {
	this.negative = negative;
}
public Double getKsz() {
	return ksz;
}
public void setKsz(Double ksz) {
	this.ksz = ksz;
}
public Double getSig() {
	return sig;
}
public void setSig(Double sig) {
	this.sig = sig;
}
@Override
public String toString() {
	return "KSTestDTO [n=" + n + ", mean=" + mean + ", sd=" + sd + ", absolute=" + absolute + ", positive=" + positive
			+ ", negative=" + negative + ", ksz=" + ksz + ", sig=" + sig + "]";
}


}
