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
import org.jmu.multiinfo.dto.upload.DataToken;
import org.jmu.multiinfo.dto.upload.TokenDTO;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.jmu.multiinfo.service.regression.StepwiseRegressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 *  回归分析
 * @Title: RegressionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月8日 下午2:22:39
 * @version V1.0
 *
 */
@Api(value = "回归分析",tags="回归分析")  
@Controller
@RequestMapping("/statistics/regression")
public class RegressionController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LinearRegressionService linearRegressionService;
	
	@Autowired
	private StepwiseRegressionService stpRegressionService;
	
	/***
	 * 一元线性回归
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "一元线性回归", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = SingleLinearDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value="/singleLinear.do")
	@ResponseBody
	public SingleLinearDTO calSingleLinearRegression(@ApiParam(required = true, name = "condition", value = "一元线性回归入参") @RequestBody BiVarCondition condition){
		SingleLinearDTO linearDTO = 	(SingleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
	}
	
	/***
	 * 多元线性回归
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "多元线性回归", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = MultipleLinearDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value="/multipleLinear.do")
	@ResponseBody
	public MultipleLinearDTO calOLSMultipleLinearRegression(@ApiParam(required = true, name = "condition", value = "多元线性回归入参")@RequestBody BiVarCondition condition){
		MultipleLinearDTO linearDTO = 	(MultipleLinearDTO)linearRegressionService.calLinearRegression(condition);
		return linearDTO;
		
	}
	
	
	/***
	 * 一般逐步回归
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "一般逐步回归", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = StepwiseMultipleDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping( value="/stepwise.do")
	@ResponseBody
	public StepwiseMultipleDTO calStepwiseMultipleLinearRegression(@RequestBody StepwiseCondition condition){
		return stpRegressionService.stepwise(condition);
		
	}
	
	/***
	 * 滑移逐步回归
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "滑移逐步回归", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = SlipStepwiseDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/slipstepwise.do" )
	@ResponseBody
	public SlipStepwiseDTO calSlipStepwiseLinearRegression(@RequestBody SlipStepwiseCondition condition){
		return stpRegressionService.slipStepwise(condition);
		
	}
	
}
