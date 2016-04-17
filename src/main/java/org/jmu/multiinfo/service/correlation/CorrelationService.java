package org.jmu.multiinfo.service.correlation;

import org.jmu.multiinfo.core.exception.DataErrException;

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
//public  bivariate();

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
public Double pearsonTCoefficient(Double r,Double n);


/***
 * spearman系数R计算双变量
 * @param dataArr
 * @return
 * @throws DataErrException 
 */
public Double spearmanRCoefficient(double[] dataArrX,double[] dataArrY) throws DataErrException;
}
