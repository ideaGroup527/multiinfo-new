package org.jmu.multiinfo.service.upload;

import java.util.List;

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
	 * @param dataList
	 * @return
	 */
	public Double median(List<Double> dataList);
	
	/***
	 * 均值
	 * @param dataList
	 * @return
	 */
	public Double mean(List<Double> dataList);
	
	
	/***
	 * 最大值
	 * @param dataList
	 * @return
	 */
	public Double max(List<Double> dataList);
	
	/***
	 * 最小值
	 * @param dataList
	 * @return
	 */
	public Double min(List<Double> dataList);
	
	/***
	 * 协方差
	 * @param dataList
	 * @return
	 */
	public Double covariance(List<Double> dataList);
	
	/***
	 * 方差
	 * @param dataList
	 * @return
	 */
	public Double variance(List<Double> dataList);
	
	/***
	 * 标准差
	 * @param dataList
	 * @return
	 */
	public Double standardDeviation(List<Double> dataList);
	
	/***
	 * 算术平均值
	 * @param dataList
	 * @return
	 */
	public Double arithmeticMean(List<Double> dataList);
	
	/***
	 * 平均数
	 * @param dataList
	 * @return
	 */
	public Double average(List<Double> dataList);
	
}
