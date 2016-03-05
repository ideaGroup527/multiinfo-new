package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.descriptives.MeanCondition;
import org.jmu.multiinfo.dto.descriptives.MeanDTO;

public interface DescriptivesStatisticsService {
	
	/***
	 * 均值
	 * @param condition
	 * @return
	 */
	 public MeanDTO calMean(MeanCondition condition);
}
