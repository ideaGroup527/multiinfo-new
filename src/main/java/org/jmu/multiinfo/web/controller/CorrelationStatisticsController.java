package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationCondition;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationDTO;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 相关分析
 * @Title: CorrelationStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月19日 上午10:55:40
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/correlation.do")
public class CorrelationStatisticsController extends BaseController{
	@Autowired
	private CorrelationService correlationService;
	
	
	/***
	 * 双变量
	 * 路径/statistics/correlation.do?method=bivariate
	 * @param condition {@link org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition}
	 * @return
	 */
	@RequestMapping(params = { "method=bivariate" })
	@ResponseBody
	public BivariateCorrelateDTO calBivariate(@RequestBody BivariateCorrelateCondition condition) {
		return correlationService.bivariate(condition);
	}
	
	
	/***
	 * 距离 
	 * 路径/statistics/correlation.do?method=distance
	 * @param condition {@link org.jmu.multiinfo.dto.correlation.DistanceCorrelationCondition}
	 * @return
	 */
	@RequestMapping(params = { "method=distance" })
	@ResponseBody
	public DistanceCorrelationDTO calDistance(@RequestBody DistanceCorrelationCondition condition){
		return correlationService.distance(condition);
	}

}
