package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;
import org.jmu.multiinfo.dto.regression.GraphDTO;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.dto.regression.SlipStepwiseCondition;
import org.jmu.multiinfo.dto.regression.SlipStepwiseDTO;
import org.jmu.multiinfo.dto.regression.StepwiseCondition;
import org.jmu.multiinfo.dto.regression.StepwiseMultipleDTO;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.jmu.multiinfo.service.regression.StepwiseRegressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *  回归分析
 * @Title: RegressionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月8日 下午2:22:39
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/regression.do")
public class RegressionController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LinearRegressionService linearRegressionService;
	
	@Autowired
	private StepwiseRegressionService stpRegressionService;
	
	/***
	 * 一元线性回归
	 * 路径/statistics/regression.do?method=singleLinear
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=singleLinear" })
	@ResponseBody
	public SingleLinearDTO calSingleLinearRegression(@RequestBody BiVarCondition condition){
		SingleLinearDTO linearDTO = 	(SingleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
	}
	
	/***
	 * 多元线性回归
	 * 路径/statistics/regression.do?method=multipleLinear
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=multipleLinear" })
	@ResponseBody
	public MultipleLinearDTO calOLSMultipleLinearRegression(@RequestBody BiVarCondition condition){
		MultipleLinearDTO linearDTO = 	(MultipleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
		
	}
	
	
	/***
	 * 一般逐步回归
	 * 路径/statistics/regression.do?method=stepwise
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=stepwise" })
	@ResponseBody
	public StepwiseMultipleDTO calStepwiseMultipleLinearRegression(@RequestBody StepwiseCondition condition){
		return stpRegressionService.stepwise(condition);
		
	}
	/***
	 * 滑移逐步回归
	 * 路径/statistics/regression.do?method=stepwise
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=slipstepwise" })
	@ResponseBody
	public SlipStepwiseDTO calSlipStepwiseLinearRegression(@RequestBody SlipStepwiseCondition condition){
		return stpRegressionService.slipStepwise(condition);
		
	}
	
	/***
	 * 散点图数据转换
	 * 路径/statistics/regression.do?method=convertForGraph
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=convertForGraph" })
	@ResponseBody
	public GraphDTO convertForGraph(@RequestBody BiVarCondition condition){
	return 	linearRegressionService.convertForGraph(condition);
		
	}
}
