package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.mean.MedianDTO;
import org.jmu.multiinfo.service.mean.MeanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * 
 * 
 * @Title: MeanStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description: 均值统计
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月3日 下午19:21:41
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/mean.do")
public class MeanStatisticsController {
	
	@Autowired
	private MeanStatisticsService meanStatisticsService;
	
	@RequestMapping(params = { "method=median" })
	@ResponseBody
   public MedianDTO calMedian(MedianCondition condition){
		MedianDTO medianDTO =	meanStatisticsService.calMedian(condition);
		return medianDTO;
	}
}
