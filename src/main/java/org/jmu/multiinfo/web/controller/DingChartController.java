package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.dingchart.DingChartCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;
import org.jmu.multiinfo.service.dingchart.DingChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * *
 * 
 * 
 * @Title: DingChartController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description:  图表
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月21日 下午4:17:34
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/chart.do")
public class DingChartController {
	
	@Autowired
	private DingChartService dingChartService;
	
	@RequestMapping(params = { "method=ding" })
	@ResponseBody
	public DingChartDTO calDing(DingChartCondition condition){
	return dingChartService.ding(condition);	
	}

}
