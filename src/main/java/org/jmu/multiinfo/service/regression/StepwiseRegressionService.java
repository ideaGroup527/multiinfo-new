package org.jmu.multiinfo.service.regression;

import java.util.Collection;
import java.util.List;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.exception.TestNotPassException;
import org.jmu.multiinfo.dto.regression.StepwiseMultipleDTO;

/***
 * 
 * 逐步回归
 * @Title: StepwiseRegressionService.java
 * @Package org.jmu.multiinfo.service.regression
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月29日 上午10:15:03
 * @version V1.0
 *
 */
public interface StepwiseRegressionService {
	/***
	 * 求初始相关系数矩阵
	 * @param dataArrY
	 * @param dataArrXList
	 * @return
	 * @throws DataErrException
	 */
public double[][] initCorrelationMatrix(double[] dataArrY,List<double[]> dataArrXList) throws DataErrException;


/***
 * 引进变量并检验
 * @param clMatrix
 * @param N 个案数
 * @param l 引进第几个变量
 * @param yp 因变量位置
 * @param entryF 引入的F
 * @param posList 
 * @return
 * @throws TestNotPassException 
 */
public boolean optimizationVar(double[][] clMatrix,int N,int l,int yp,double entryF,List<Integer> posList) ;

/***
 * 求逆紧奏变换法
 * @param clMatrix
 * @param l 新引进变量位置
 * @return
 */
public double[][]  inverseCompactTransform(double[][] clMatrix ,int l);


/***
 * 剔除
 * @param clMatrix
 * @param N
 * @param l
 * @param yp 因变量位置
 * @param delF
 * @param posList
 * @return true继续引进 false 应该剔除
 * @throws TestNotPassException 
 */
public boolean outlier(double[][] clMatrix,int N,int l,int yp,double delF,List<Integer> posList,List<Integer> delList) throws TestNotPassException;


public StepwiseMultipleDTO stepwise(double[] dataArrY,List<double[]> dataArrXList,double entryF,double delF) throws DataErrException;
}
