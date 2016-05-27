package org.jmu.multiinfo.service.reducingdim;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.dto.reducingdim.CorrespAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.CorrespAnalysisDTO;

/**
 * 对应分析
 * @Title: CorrespondenceAnalysis.java
 * @Package org.jmu.multiinfo.service.reducingdim
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月25日 上午9:32:20
 * @version V1.0
 *
 */
public interface CorrespondenceAnalysisService {
	
	
	public CorrespAnalysisDTO correspondence(double[][] dataArr) throws DataErrException;
	
	
	
	public CorrespAnalysisDTO correspondence(CorrespAnalysisCondition condition);
	
	
	/***
	 * 概率矩阵
	 * @param dataArr
	 * @return
	 * @throws DataErrException
	 */
	public double[][] probability(double[][] dataArr) throws DataErrException;
	
	
	/**
	 * 根据概率矩阵求z矩阵
	 * @param proArr
	 * @return
	 * @throws DataErrException
	 */
	public double[][] calZMat(double[][] proArr) throws DataErrException;
	
	
	/**
	 * 根据z矩阵求ZT矩阵
	 * @param proArr
	 * @return
	 * @throws DataErrException
	 */
	public double[][] calZtMat(double[][] z) throws DataErrException;
	
	
	/***
	 * 求SQ矩阵
	 * @param z
	 * @param zt
	 * @return
	 * @throws DataErrException
	 */
	public double[][] calSQ(double[][]z ,double[][] zt) throws DataErrException;
	/***
	 * 求SR矩阵
	 * @param z
	 * @param zt
	 * @return
	 * @throws DataErrException
	 */
	public double[][] calSR(double[][]z ,double[][] zt) throws DataErrException;
}
