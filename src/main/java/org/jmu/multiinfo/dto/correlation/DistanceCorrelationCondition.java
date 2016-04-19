package org.jmu.multiinfo.dto.correlation;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;

/***
 * 
 * 
 * 
 * @Title: DistanceCorrelationCondition.java
 * @Package org.jmu.multiinfo.dto.correlation
 * @Description:  距离相关
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月19日 下午4:16:08
 * @version V1.0
 *
 */
public class DistanceCorrelationCondition extends OneVarCondition{
	private Integer minkowskiP;
	private Integer minkowskiQ;
	public Integer getMinkowskiP() {
		return minkowskiP;
	}
	public void setMinkowskiP(Integer minkowskiP) {
		this.minkowskiP = minkowskiP;
	}
	public Integer getMinkowskiQ() {
		return minkowskiQ;
	}
	public void setMinkowskiQ(Integer minkowskiQ) {
		this.minkowskiQ = minkowskiQ;
	}
	
}
