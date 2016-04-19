package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * *
 * 
 * 
 * @Title: CorrelationStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description:  相关分析
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月19日 上午10:55:40
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/correlation.do")
public class CorrelationStatisticsController {
	@Autowired
	private CorrelationService correlationService;
	
	@RequestMapping(params = { "method=bivariate" })
	@ResponseBody
	public BivariateCorrelateDTO calBivariate(BivariateCorrelateCondition condition) {
		return correlationService.bivariate(condition);
	}

}
