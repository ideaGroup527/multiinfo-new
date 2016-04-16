package org.jmu.multiinfo.dto.basestatistics;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NormalDistributionDTO {
private double density;
@JsonProperty("cumulativeprobability") 
private double cumulativeProbability;
private double mean;
private double sd;
private double probability;
public double getDensity() {
	return density;
}
public void setDensity(double density) {
	this.density = density;
}
public double getCumulativeProbability() {
	return cumulativeProbability;
}
public void setCumulativeProbability(double cumulativeProbability) {
	this.cumulativeProbability = cumulativeProbability;
}
public double getMean() {
	return mean;
}
public void setMean(double mean) {
	this.mean = mean;
}
public double getSd() {
	return sd;
}
public void setSd(double sd) {
	this.sd = sd;
}
public double getProbability() {
	return probability;
}
public void setProbability(double probability) {
	this.probability = probability;
}
@Override
public String toString() {
	return "NormalDistributionDTO [density=" + density + ", cumulativeProbability=" + cumulativeProbability + ", mean="
			+ mean + ", sd=" + sd + ", probability=" + probability + "]";
}
}
