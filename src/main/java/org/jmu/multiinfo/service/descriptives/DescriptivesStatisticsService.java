package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;

public interface DescriptivesStatisticsService {
	
	/***
	 * 描述功能
	 * @param condition
	 * @return
	 */
	 public CommonDTO calDesc(CommonCondition condition);
	 
	 
	 /***
	  * 频率功能
	  * @param condition
	  */
	 public CommonDTO calFrequency(CommonCondition condition);
}
