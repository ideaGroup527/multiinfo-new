package org.jmu.multiinfo.dto.descriptives;

import java.util.List;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;

public class PercentileCondition extends OneVarCondition{
private List<Double> percentiles;

public List<Double> getPercentiles() {
	return percentiles;
}

public void setPercentiles(List<Double> percentiles) {
	this.percentiles = percentiles;
}

}
