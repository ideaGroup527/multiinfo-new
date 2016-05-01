package org.jmu.multiinfo.dto.regression;

import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;

public class StepwiseCondition extends BiVarCondition{
private Double entryF;
private Double delF;
public Double getEntryF() {
	return entryF;
}
public void setEntryF(Double entryF) {
	this.entryF = entryF;
}
public Double getDelF() {
	return delF;
}
public void setDelF(Double delF) {
	this.delF = delF;
}

}
