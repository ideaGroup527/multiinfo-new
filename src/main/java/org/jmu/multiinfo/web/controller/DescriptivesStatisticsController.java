package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.MeanDTO;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 
 * 
 * @Title: DescriptivesStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description:  描述统计  需求连接http://www.seekbio.com/biotech/soft/SPSS/2012/j819365850.html
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月5日 下午4:55:56
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/descriptives.do")
public class DescriptivesStatisticsController {
	@Autowired
	private DescriptivesStatisticsService descriptivesStatisticsService;
	@RequestMapping(params = { "method=mean" })
	@ResponseBody
   public MeanDTO calMean(CommonCondition condition){
		MeanDTO meanDTO =	descriptivesStatisticsService.calMean(condition);
		return meanDTO;
	}
	
	
	@RequestMapping(params = { "method=max" })
	@ResponseBody
   public CommonDTO calMax(CommonCondition condition){
		CommonDTO maxDTO =	descriptivesStatisticsService.calMax(condition);
		return maxDTO;
	}
	
	@RequestMapping(params = { "method=min" })
	@ResponseBody
   public CommonDTO calMin(CommonCondition condition){
		CommonDTO maxDTO =	descriptivesStatisticsService.calMin(condition);
		return maxDTO;
	}
	
	
	@RequestMapping(params = { "method=sum" })
	@ResponseBody
   public CommonDTO calSum(CommonCondition condition){
		CommonDTO maxDTO =	descriptivesStatisticsService.calSum(condition);
		return maxDTO;
	}
}
