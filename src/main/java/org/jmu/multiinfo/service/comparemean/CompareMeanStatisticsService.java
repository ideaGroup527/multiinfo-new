package org.jmu.multiinfo.service.comparemean;

import java.util.Collection;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.jmu.multiinfo.dto.comparemean.AnovaCondition;
import org.jmu.multiinfo.dto.comparemean.AnovaDTO;
import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;

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
 public AnovaDTO calOneWayAnova(AnovaCondition anovaCondition);

 /***
  * 单因素方差分析,只用于测试
  * @param anovaCondition
  * @return
  */
 @Deprecated
 public AnovaDTO anovaStatsForT(Collection<double[]> categoryData);

}
