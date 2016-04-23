package org.jmu.multiinfo.service.reducingdim;

import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;

/**
 * 主成分分析
 * @Title: PrincipalComponentAnalysisService.java
 * @Package org.jmu.multiinfo.service.reducingdim
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月22日 上午10:32:20
 * @version V1.0
 *
 */
public interface PrincipalComponentAnalysisService {
	
	/***
	 * 主成分系数 sqrt(lambda) * eigenvector
	 * @param eigenvector
	 * @param lambda
	 * @return
	 */
	public double[] principalComponentCoefficient(double[] eigenvector,double lambda);
	
	
	
	/***
	 * PCA 主成分分析
	 * @param dataArr
	 * @return
	 * @throws DataErrException 
	 */
	public EigenvalueDTO principalComponentAnalysis(double[][] dataArr) throws DataErrException;
	
	
	/***
	 * PCA 主成分分析
	 * @param dataArr
	 * @return
	 */
	public PrincipalComponentAnalysisDTO principalComponentAnalysis(PrincipalComponentAnalysisCondition condition);
	
	/***
	 * 方差贡献率 lambda/sum(lambda) * 100
	 * @param eigenvector
	 * @return
	 */
	public double[] varianceContribution(double[] eigenvectors);
	
	/***
	 * 累积贡献率
	 * @param eigenvectors
	 * @return
	 */
	public double[] accumulationContribution(double[] eigenvectors);
	
	/***
	 * 公因子方差
	 * @param componentArr
	 * @return
	 */
	public double[] communality(double[][] componentArr);
}
