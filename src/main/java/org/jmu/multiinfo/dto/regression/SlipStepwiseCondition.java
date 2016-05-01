package org.jmu.multiinfo.dto.regression;

import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class SlipStepwiseCondition extends StepwiseCondition{
	/***
	 * 前移降序
	 */
public final static  int SLIP_PREVIOUS_DESC = 1;
/***
 * 前移升序
 */
public final static  int SLIP_PREVIOUS_ASC = 0;


/***
 * 后移降序
 */
public final static  int SLIP_BACKWARD_DESC = 1;
/***
* 后移升序
*/
public final static  int SLIP_BACKWARD_ASC = 0;

//时间变量
private VarietyDTO timeVariable;


private Integer previousMethod;
private Integer backwardMethod;
public Integer getPreviousMethod() {
	return previousMethod;
}
public void setPreviousMethod(Integer previousMethod) {
	this.previousMethod = previousMethod;
}
public Integer getBackwardMethod() {
	return backwardMethod;
}
public void setBackwardMethod(Integer backwardMethod) {
	this.backwardMethod = backwardMethod;
}
public VarietyDTO getTimeVariable() {
	return timeVariable;
}
public void setTimeVariable(VarietyDTO timeVariable) {
	this.timeVariable = timeVariable;
}

}
