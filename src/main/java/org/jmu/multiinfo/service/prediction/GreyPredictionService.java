package org.jmu.multiinfo.service.prediction;

import java.util.Collection;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeCondition;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeDTO;
import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;

/**
 * 灰色预测
 * @Title: GreyPredictionService.java
 * @Package org.jmu.multiinfo.service.prediction
 * @Description:  
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月20日 上午10:54:21
 * @version V1.0
 *
 */
public interface GreyPredictionService {
	
	
	
	/***
	 * 连续性 灰色预测
	 * @param condition
	 * @return
	 */
public GreyPredictionDTO grey(GreyPredictionCondition condition);


/***
 * 独立型 灰色预测
 * @param condition
 * @return
 */
public GreyPredictionDTO inpGrey(GreyPredictionCondition condition);

public Double gm(Double formCoefficient, double[] dataArr) throws DataErrException;


/***
 * 灰色关联度
 * @param condition
 * @return
 */
public GreyCorrelationDegreeDTO greyCorrelationDegree(GreyCorrelationDegreeCondition condition);

/**
 * 灰色关联系数ξ（Xi）
 * <a href="http://baike.baidu.com/link?url=9QFEGsAmTvYrBIVV6pnTsM7gTeVn15vv26G2dukLSmPSZ8NHmxyDsyGa47EXLXY-32zGKjJEw4RiQhqxdkLS-a">公式</a>
 * @param motherSeqArr 母序列
 * @param sonSeqArr 子序列
 * @param resolutionRatio 分辨系数 0 - 1
 * @return
 * @throws DataErrException 
 */
public double[][] greyRelationalCoefficient(double[] motherSeqArr,Collection<double[]> sonSeqArr,double resolutionRatio) throws DataErrException;

/***
 * 灰色关联系数ξ（Xi）
 * @param absArr 母子序列相减绝对值
 * @param max 最大
 * @param min 最小
 * @param resolutionRatio 分辨系数 0 - 1
 * @return
 */
public double[] greyRelationalCoefficient(double[] absArr , double max, double min, double resolutionRatio);

/***
 * 关联度
 * @param grcArr
 * @return
 */
public double[] associationDegree(double[][] grcArr);

}
