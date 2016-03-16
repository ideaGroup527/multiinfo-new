package org.jmu.multiinfo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.dto.comparemean.AnovaCondition;
import org.jmu.multiinfo.dto.comparemean.AnovaDTO;
import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;
import org.jmu.multiinfo.service.comparemean.CompareMeanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * 
 * 
 * @Title: CompareMeanStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description: 均值统计
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月3日 下午19:21:41
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/comparemean.do")
public class CompareMeanStatisticsController {
	
	@Autowired
	private CompareMeanStatisticsService compareMeanStatisticsService;
	
	@RequestMapping(params = { "method=mean" })
	@ResponseBody
   public MedianDTO calMean(@RequestBody MedianCondition condition){
		MedianDTO medianDTO =	compareMeanStatisticsService.calMean(condition);
		return medianDTO;
	}
	@RequestMapping(params = { "method=onewayanova" })
	@ResponseBody
	public Map<String,AnovaDTO> calOneWayAnova(List<AnovaCondition> anovaConditionList){
		Map<String,AnovaDTO> anovaMap = new HashMap<String,AnovaDTO>();
		for (AnovaCondition anova : anovaConditionList) {
			 String varietyName = anova.getDependentVariable().getVarietyName();
			 AnovaDTO e =compareMeanStatisticsService.calOneWayAnova(anova);
			anovaMap.put(varietyName, e);
		}
		return anovaMap;
	}
}
