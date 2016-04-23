package org.jmu.multiinfo.service.basestatistics;

import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;

/***
 * 
 * 矩阵运算
 * @Title: MatrixStatisticsService.java
 * @Package org.jmu.multiinfo.service.basestatistics
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月22日 下午11:36:19
 * @version V1.0
 *
 */
public interface MatrixStatisticsService {
	
public EigenvalueDTO eigenvector(double[][] dataArr) throws DataErrException;
}
