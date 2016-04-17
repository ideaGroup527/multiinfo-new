package org.jmu.multiinfo.service.comparemean;

import java.util.Collection;

import org.jmu.multiinfo.dto.comparemean.AnovaCondition;
import org.jmu.multiinfo.dto.comparemean.AnovaDTO;
import org.jmu.multiinfo.dto.comparemean.AnovaDataDTO;
import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;

/***
 * *
 * 
 * 
 * @Title: CompareMeanStatisticsService.java
 * @Package org.jmu.multiinfo.service.comparemean
 * @Description:  均值统计
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月17日 下午2:19:05
 * @version V1.0
 *
 */
public interface CompareMeanStatisticsService {
	/**
	 * 中位数
	 * @param condition
	 * @return
	 */
 public MedianDTO calMean(MedianCondition condition);
 
 /***
  * 单因素方差分析
  * @param anovaCondition
  * @return
  */
 public AnovaDataDTO calOneWayAnova(AnovaCondition anovaCondition);

 /***
  * 单因素方差分析,只用于测试
  * @param anovaCondition
  * @return
  */
 @Deprecated
 public AnovaDTO anovaStatsForT(Collection<double[]> categoryData);

}
