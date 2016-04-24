package org.jmu.multiinfo.dto.prediction;

import org.jmu.multiinfo.dto.basestatistics.FactorVarCondition;

public class GreyPredictionCondition extends FactorVarCondition{
	//生成系数
private Double formCoefficient;

/**
 * 独立
 */
public static final int ASSOCIATION_INDEPENDENT = 0;

/***
 * 连续
 */
public static final int ASSOCIATION_CONTINUOUS = 1;


//关联类型-变量间独立还是关联
private Integer associationType;


public Double getFormCoefficient() {
	return formCoefficient;
}

public void setFormCoefficient(Double formCoefficient) {
	this.formCoefficient = formCoefficient;
}

public Integer getAssociationType() {
	return associationType;
}

public void setAssociationType(Integer associationType) {
	this.associationType = associationType;
}


}
