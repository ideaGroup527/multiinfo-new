package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.MeanDTO;

public interface DescriptivesStatisticsService {
	
	/***
	 * 均值
	 * @param condition
	 * @return
	 */
	 public MeanDTO calMean(CommonCondition condition);

	 /***
	  * 最大值
	  * @param condition
	  * @return
	  */
	 public CommonDTO calMax(CommonCondition condition);

	/***
	 * 最小值
	 * @param condition
	 * @return
	 */
	public CommonDTO calMin(CommonCondition condition);

	/***
	 * 合计
	 * @param condition
	 * @return
	 */
	public CommonDTO calSum(CommonCondition condition);
}
