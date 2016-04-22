package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 
 * 
 * @Title: DescriptivesStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description: <a href="http://www.seekbio.com/biotech/soft/SPSS/2012/j819365850.html">描述统计</a>
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月5日 下午4:55:56
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/descriptives.do")
public class DescriptivesStatisticsController extends BaseController{
	@Autowired
	private DescriptivesStatisticsService descriptivesStatisticsService;
	@RequestMapping(params = { "method=descriptives" })
	@ResponseBody
   public CommonDTO calDesc(@RequestBody PercentileCondition condition){
	
		if(condition.getPercentiles()==null || condition.getPercentiles().size() == 0){
			List<Double> percentiles = new ArrayList<Double>();
			percentiles.add(25.0);
			percentiles.add(50.0);
			percentiles.add(75.0);
			condition.setPercentiles(percentiles );
		}
		CommonDTO meanDTO =	descriptivesStatisticsService.calDesc(condition);
		return meanDTO;
	}
	
	@RequestMapping(params = { "method=frequency" })
	@ResponseBody
   public CommonDTO calFrequency(@RequestBody OneVarCondition condition){
		CommonDTO freDTO =	descriptivesStatisticsService.calFrequency(condition);
		return freDTO;
	}
	
}
