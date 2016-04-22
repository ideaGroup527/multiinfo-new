package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.comparemean.*;
import org.jmu.multiinfo.service.comparemean.CompareMeanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 均值统计
 * @Title: CompareMeanStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月3日 下午19:21:41
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/comparemean.do")
public class CompareMeanStatisticsController extends BaseController{
	
	@Autowired
	private CompareMeanStatisticsService compareMeanStatisticsService;
	
	
	/***
	 * 比较均值
	 * 路径/statistics/comparemean.do?method=mean
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=mean" })
	@ResponseBody
   public MedianDTO calMean(@RequestBody MedianCondition condition){
		MedianDTO medianDTO =	compareMeanStatisticsService.calMean(condition);
		return medianDTO;
	}
	
	
	/***
	 * 单因素方差分析
	 * 路径/statistics/comparemean.do?method=onewayanova
	 * @param anovaCondition
	 * @return
	 */
	@RequestMapping(params = { "method=onewayanova" })
	@ResponseBody
	public AnovaDataDTO calOneWayAnova(AnovaCondition anovaCondition){
		AnovaDataDTO resDTO =compareMeanStatisticsService.calOneWayAnova(anovaCondition);
		return resDTO;
	}
}
