package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;

public interface DescriptivesStatisticsService {
	
	/***
	 * 均值
	 * @param condition
	 * @return
	 */
	 public CommonDTO calMean(CommonCondition condition);
}
