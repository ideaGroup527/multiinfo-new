package org.jmu.multiinfo.service.basestatistics;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

/**
 * 
* @Title: BasicStatisticsService.java 
* @Package org.jmu.multiinfo.module.basicstatistics.service 
* @Description: 基本统计处理
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月4日 下午9:10:36 
* @version V1.0
 */
public interface BasicStatisticsService {
	/***
	 * 中位数
	 * @param dataArr
	 * @return
	 */
	public Double median(double[] dataArr);
	
	
	/***
	 * 最大值
	 * @param dataArr
	 * @return
	 */
	public Double max(double[] dataArr);
	
	/***
	 * 最小值
	 * @param dataArr
	 * @return
	 */
	public Double min(double[] dataArr);
	
	
	/***
	 * 方差
	 * @param dataArr
	 * @return
	 */
	public Double variance(double[] dataArr);
	
	/***
	 * 标准差
	 * @param dataArr
	 * @return
	 */
	public Double standardDeviation(double[] dataArr);
	
	/***
	 * 算术平均值
	 * @param dataArr
	 * @return
	 */
	public Double arithmeticMean(double[] dataArr);
	
	/***
	 * 平均数
	 * @param dataArr
	 * @return
	 */
	public Double average(double[] dataArr);

	/***
	 * 总和
	 * @param dataArr
	 * @return
	 */
	public Double sum(double[] dataArr);
	
	/***
	 * 偏度
	 * @param dataArr
	 * @return
	 */
	public Double skewness(double[] dataArr);
	
	
	/***
	 * 几何平均数,n个正数的连乘积的n次算术根叫做这n个数的几何平均数  
	 * @param dataArr
	 * @return
	 */
	public Double geometricMean(double[] dataArr);
	
	/***
	 * Kurtosis,峰度
	 * @param dataArr
	 * @return
	 */
	public Double kurtosis(double[] dataArr);


	/***
	 * percentile分位
	 * @param dataArr
	 * @return
     */
	public Percentile percentile(double[]dataArr);
	/***
	 * 频数
	 * @param dataArr
	 * @return
	 */
	public Frequency frequencyCount(Object[] dataArr);


	/***
	 * 频数
	 * @param dataArr
	 * @return
	 */
	public Frequency frequencyCount(double[] dataArr);
	
}
