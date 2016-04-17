package org.jmu.multiinfo.service.regression;

import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;
import org.jmu.multiinfo.dto.regression.CommonDTO;
import org.jmu.multiinfo.dto.regression.GraphDTO;


/***
 * *
 * 
 * 
 * @Title: LinearRegressionService.java
 * @Package org.jmu.multiinfo.service.regression
 * @Description:  线性回归分析
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月17日 下午2:19:43
 * @version V1.0
 *
 */
public interface LinearRegressionService {
	
public CommonDTO calSingleLinearRegression(double[][] data);

public CommonDTO calOLSMultipleLinearRegression(double[] y, double[][] x);

public CommonDTO calLinearRegression(BiVarCondition condition);

public GraphDTO convertForGraph(BiVarCondition condition);
}
