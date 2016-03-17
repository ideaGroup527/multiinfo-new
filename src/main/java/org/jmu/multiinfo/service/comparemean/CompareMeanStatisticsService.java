package org.jmu.multiinfo.service.comparemean;

import java.util.Collection;

import org.jmu.multiinfo.dto.comparemean.*;

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
 public ResultDataDTO calOneWayAnova(AnovaCondition anovaCondition);

 /***
  * 单因素方差分析,只用于测试
  * @param anovaCondition
  * @return
  */
 @Deprecated
 public AnovaDTO anovaStatsForT(Collection<double[]> categoryData);

}
