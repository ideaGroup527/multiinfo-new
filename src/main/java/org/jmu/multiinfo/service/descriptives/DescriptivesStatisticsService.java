package org.jmu.multiinfo.service.descriptives;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.KSTestDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;


/**
 * 描述统计
 * @Title: DescriptivesStatisticsService.java
 * @Package org.jmu.multiinfo.service.descriptives
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月17日 下午2:19:25
 * @version V1.0
 *
 */
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
	 public CommonDTO calFrequency(OneVarCondition condition);


	 /***
	  * kolmogorov-smirnov检验
	  * @param data
	  * @return
	  */
	public KSTestDTO calNormalDistribution(double[] data);
}
