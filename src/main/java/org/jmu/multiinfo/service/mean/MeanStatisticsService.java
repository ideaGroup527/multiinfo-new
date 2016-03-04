package org.jmu.multiinfo.service.mean;

import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.mean.MedianDTO;

public interface MeanStatisticsService {
	/**
	 * 中位数
	 * @param condition
	 * @return
	 */
 public MedianDTO calMedian(MedianCondition condition);
}
