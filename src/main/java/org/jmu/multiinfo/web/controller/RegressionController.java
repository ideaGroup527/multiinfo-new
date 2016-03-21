package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.regression.CommonCondition;
import org.jmu.multiinfo.dto.regression.GraphDTO;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * 
 * 
 * @Title: RegressionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description: 回归分析
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月8日 下午2:22:39
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/regression.do")
public class RegressionController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LinearRegressionService linearRegressionService;
	
	@RequestMapping(params = { "method=singleLinear" })
	@ResponseBody
	public SingleLinearDTO calSingleLinearRegression(@RequestBody CommonCondition condition){
		SingleLinearDTO linearDTO = 	(SingleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
	}
	
	@RequestMapping(params = { "method=multipleLinear" })
	@ResponseBody
	public MultipleLinearDTO calOLSMultipleLinearRegression(@RequestBody CommonCondition condition){
		MultipleLinearDTO linearDTO = 	(MultipleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
		
	}
	
	
	@RequestMapping(params = { "method=convertForGraph" })
	@ResponseBody
	public GraphDTO convertForGraph(@RequestBody CommonCondition condition){
	return 	linearRegressionService.convertForGraph(condition);
		
	}
}
