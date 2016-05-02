package org.jmu.multiinfo.dto.cluster;

import org.jmu.multiinfo.dto.basestatistics.FactorVarCondition;


/***
 * 
 * 点群
 * @Title: PointGroupCondition.java
 * @Package org.jmu.multiinfo.dto.cluster
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月2日 下午4:59:00
 * @version V1.0
 *
 */
public class PointGroupCondition extends FactorVarCondition {
	/**
	 * 极差正规化
	 */
	public final static int NORMALIZATION_RANGE = 0;

	/***
	 * 极差标准化
	 */
	public final static int NORMALIZATION_RANGE_SD = 1;

	/***
	 * 标准差标准化
	 */
	public final static int NORMALIZATION_STANDARD_SD = 2;

	/**
	 * Q型聚类
	 */
	public final static int TYPE_Q = 0;

	/***
	 * R型聚类
	 */
	public final static int TYPE_R = 1;

	/**
	 * 距离系数
	 */
	public final static int STATISTICS_DISTANCE = 0;

	/***
	 * 夹角余弦
	 */
	public final static int STATISTICS_ANGLE_COSINE = 1;

	/***
	 * 相关系数
	 */
	public final static int STATISTICS_CORRELATION = 2;

	private Integer normalizationMethod;

	private Integer clusterMethod;

	private Integer statisticsMethod;

	public Integer getNormalizationMethod() {
		return normalizationMethod;
	}

	public void setNormalizationMethod(Integer normalizationMethod) {
		this.normalizationMethod = normalizationMethod;
	}

	public Integer getClusterMethod() {
		return clusterMethod;
	}

	public void setClusterMethod(Integer clusterMethod) {
		this.clusterMethod = clusterMethod;
	}

	public Integer getStatisticsMethod() {
		return statisticsMethod;
	}

	public void setStatisticsMethod(Integer statisticsMethod) {
		this.statisticsMethod = statisticsMethod;
	}

}
