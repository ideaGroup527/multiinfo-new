package org.jmu.multiinfo.dto.regression;

import java.util.List;

public class TrendStepwiseCondition extends SlipStepwiseCondition{
	private List<Integer> weight;
	private Integer moveNum;
	public List<Integer> getWeight() {
		return weight;
	}

	public void setWeight(List<Integer> weight) {
		this.weight = weight;
	}

	public Integer getMoveNum() {
		return moveNum;
	}

	public void setMoveNum(Integer moveNum) {
		this.moveNum = moveNum;
	}
	

}
