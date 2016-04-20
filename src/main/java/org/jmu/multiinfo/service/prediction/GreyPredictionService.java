package org.jmu.multiinfo.service.prediction;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;

/***
 * *
 * 
 * 
 * @Title: GreyPredictionService.java
 * @Package org.jmu.multiinfo.service.prediction
 * @Description:  灰色预测
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月20日 上午10:54:21
 * @version V1.0
 *
 */
public interface GreyPredictionService {
	
	
public GreyPredictionDTO grey(GreyPredictionCondition condition);


public Double gm(Double formCoefficient, double[] dataArr) throws DataErrException;
}
