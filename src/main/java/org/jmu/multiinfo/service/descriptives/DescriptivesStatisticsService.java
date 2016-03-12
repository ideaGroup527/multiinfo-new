package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;

public interface DescriptivesStatisticsService {
	
	/***
	 * 描述功能
	 * @param condition
	 * @return
	 */
	 public CommonDTO calDesc(PercentileCondition condition);
	 
	 
	 /***
	  * 频率功能
	  * @param condition
	  */
	 public CommonDTO calFrequency(CommonCondition condition);
}
