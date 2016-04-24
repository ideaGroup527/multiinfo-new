package org.jmu.multiinfo.dto.reducingdim;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;

public class PrincipalComponentAnalysisCondition extends OneVarCondition{
	/***
	 * 基于特征值
	 */
	public final static int EXTRACT_METHOD_EIGENVALUE = 0;
	/***
	 * 因子
	 */
	public final static int EXTRACT_METHOD_FACTOR = 1;
	
	private Integer extractMethod;
	
	/***
	 * 基于特征值
	 */
	private Double eigExtraNum;
	
	/***
	 * 因子的固定数量
	 */
	private Integer factorExtraNum;

	public Integer getExtractMethod() {
		return extractMethod;
	}

	public void setExtractMethod(Integer extractMethod) {
		this.extractMethod = extractMethod;
	}

	public Double getEigExtraNum() {
		return eigExtraNum;
	}

	public void setEigExtraNum(Double eigExtraNum) {
		this.eigExtraNum = eigExtraNum;
	}

	public Integer getFactorExtraNum() {
		return factorExtraNum;
	}

	public void setFactorExtraNum(Integer factorExtraNum) {
		this.factorExtraNum = factorExtraNum;
	}
	
	

}
