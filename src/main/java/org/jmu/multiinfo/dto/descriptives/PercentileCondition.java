package org.jmu.multiinfo.dto.descriptives;

import java.util.List;

public class PercentileCondition extends CommonCondition{
private List<Double> percentiles;

public List<Double> getPercentiles() {
	return percentiles;
}

public void setPercentiles(List<Double> percentiles) {
	this.percentiles = percentiles;
}

}
