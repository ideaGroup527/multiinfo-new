package org.jmu.multiinfo.service.comparemean;

import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;

public interface CompareMeanStatisticsService {
	/**
	 * 中位数
	 * @param condition
	 * @return
	 */
 public MedianDTO calMedian(MedianCondition condition);
}
