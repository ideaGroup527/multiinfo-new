package org.jmu.multiinfo.service.correlation;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationCondition;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationDTO;

/***
 * *
 * 
 * 
 * @Title: CorrelationService.java
 * @Package org.jmu.multiinfo.service.correlation
 * @Description:  相关分析
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月17日 下午2:22:24
 * @version V1.0
 *
 */
public interface CorrelationService {
	
	/***
	 * 相关分析-双变量
	 * @param condition
	 * @return
	 */
public BivariateCorrelateDTO bivariate(BivariateCorrelateCondition condition);

	/***
	 * person系数R计算双变量
	 * @param dataArrX
	 * @param dataArrY
	 * @return
	 * @throws DataErrException 
	 */
public Double pearsonRCoefficient(double[] dataArrX,double[] dataArrY) throws DataErrException;

/***
 * person系数t计算双变量
 * @param r 系数R
 * @param n 案例数
 * @return
 */
public Double pearsonTCoefficient(Double r,Integer n);


/***
 * spearman系数R计算双变量
 * @param dataArr
 * @return
 * @throws DataErrException 
 */
public Double spearmanRCoefficient(double[] dataArrX,double[] dataArrY) throws DataErrException;


/***
 * 距离相关
 * @param condition
 * @return
 */
public DistanceCorrelationDTO distance(DistanceCorrelationCondition condition);
}
