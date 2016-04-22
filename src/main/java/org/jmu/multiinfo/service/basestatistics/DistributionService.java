package org.jmu.multiinfo.service.basestatistics;

import org.jmu.multiinfo.dto.basestatistics.NormalDistributionDTO;

/**
 * 分布
 * @Title: DistributionService.java
 * @Package org.jmu.multiinfo.service.basestatistics
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月14日 下午8:24:46
 * @version V1.0
 *
 */
public interface DistributionService {
	
	
	
	
/***
 * 
 * @param mean 平均数μ
 * @param sd 标准差 σ
 * @param inverseCumAccuracy 逆累积概率精度
 * @param x
 * @return
 */
public NormalDistributionDTO normalDistribution(double mean, double sd, double inverseCumAccuracy,double x);


/***
 * 
 * @param mean
 * @param sd
 * @param x
 * @return
 */
public NormalDistributionDTO normalDistribution(double mean, double sd,double x);

/***
 * 标准正态分布(钟形分布)
 * @param x
 * @return
 */
public NormalDistributionDTO normalDistribution(double x);
}
