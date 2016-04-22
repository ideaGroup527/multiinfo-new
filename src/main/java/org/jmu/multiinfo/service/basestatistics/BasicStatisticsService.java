package org.jmu.multiinfo.service.basestatistics;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.jmu.multiinfo.core.exception.DataErrException;

/**
 * 基本统计处理
 * @Title: BasicStatisticsService.java 
 * @Package org.jmu.multiinfo.module.basicstatistics.service 
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
	 * 最大值
	 * @param dataArr
	 * @return
	 */
	public Double max(double[][] dataArr);
	
	/***
	 * 最小值
	 * @param dataArr
	 * @return
	 */
	public Double min(double[] dataArr);
	
	/***
	 * 最小值
	 * @param dataArr
	 * @return
	 */
	public Double min(double[][] dataArr);
	
	
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
	 * 求箱线图下边缘
	 * @param pt
	 * @return
	 */
	public Double zeroPercentile(Percentile pt);
	
	/***
	 * 求箱线图上边缘
	 * @param pt
	 * @return
	 */
	public Double fullPercentile(Percentile pt);
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
	
	/***
	 * 平方和
	 * @param dataArr
	 * @return
	 */
	public Double sumSquares(double[] dataArr);
	/***
	 * 离均差平方和
	 * @param dataArr
	 * @throws DataErrException 
	 */
	public  Double averageSumDeviation(double[] dataArr) throws DataErrException;
	
	
	/***
	 * 离均差积和
	 * @param dataArr
	 * @throws DataErrException 
	 */
	public  Double averageMulSumDeviation(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	
	/***
	 * 秩次
	 * @param dataArr
	 * @return
	 */
	public double[] rank(double[] dataArr);
	
	
	/***
	 * 秩次（同名次加权）
	 * @param dataArr
	 * @return
	 */
	public double[] rankAve(double[] dataArr);
	
	/***
	 * 返回样例数
	 * @param dataArr
	 * @return
	 */
	public Integer getN(double[] dataArr);
	

	/***
	 * 四舍五入
	 * @param data
	 * @param precision 小数位数
	 * @return
	 */
	public Double round(Double data,Integer precision);
	
	/***
	 * 协方差
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException 
	 */
	public Double covariance(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	/***
	 * 欧氏距离公式
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException 
	 */
	public Double euclideanDistance(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	/***
	 * 平方欧式举例公式
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException
	 */
	public Double squareEuclideanDistance(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	
	/***
	 * 切比雪夫距离
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException
	 */
	public Double chebyshevDistance(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	/***
	 * 城市街区距离（曼哈顿距离）
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException
	 */
	public Double cityBlockDistance(double[] dataArrX,double[] dataArrY) throws DataErrException;
	
	/***
	 * 曼哈顿距离
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException
	 */
	public Double manhattanDistance(double[] dataArrX,double[] dataArrY) throws DataErrException;

	/***
	 * 闵可夫斯基距离
	 * @param dataArrX
	 * @param dataArrY
	 * @param p 幂
	 * @param q 根
	 * @return
	 * @throws DataErrException
	 */
	public Double minkowskiDistace(double[] dataArrX,double[] dataArrY,Integer p,Integer q) throws DataErrException;
	
	/***
	 * 级比
	 * 第一个数除以后面每个数得到的
	 * @param dataArr
	 * @return
	 * @throws DataErrException 
	 */
	public double[] stepwiseRatio(double[] dataArr) throws DataErrException;
	
	
	/***
	 * 累计和
	 * @param dataArr
	 * @return
	 * @throws DataErrException
	 */
	public double[]  cumulativeSum(double[] dataArr) throws DataErrException;
	
	
	/***
	 * 均值生成
	 * @param dataArr
	 * @param formCoefficient
	 * @return
	 */
	public double[] averageGeneration(double[] dataArr,double formCoefficient) throws DataErrException;
	
	
	/**
	 * 正规化
	 * @param dataArr
	 * @return
	 * @throws DataErrException
	 */
	public double[] regularization(double[] dataArr) throws DataErrException;
	
	
	/**
	 * 正规化
	 * @param dataArr
	 * @return
	 * @throws DataErrException
	 */
	public double[][] regularization(double[][] dataArr) throws DataErrException;
}
