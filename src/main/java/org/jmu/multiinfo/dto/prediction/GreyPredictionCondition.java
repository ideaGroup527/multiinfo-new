package org.jmu.multiinfo.dto.prediction;

import org.jmu.multiinfo.dto.basestatistics.FactorVarCondition;

public class GreyPredictionCondition extends FactorVarCondition{
	//生成系数
private Double formCoefficient;

public Double getFormCoefficient() {
	return formCoefficient;
}

public void setFormCoefficient(Double formCoefficient) {
	this.formCoefficient = formCoefficient;
}
}
