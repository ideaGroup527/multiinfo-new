package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationCondition;
import org.jmu.multiinfo.dto.correlation.DistanceCorrelationDTO;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 相关分析
 * @Title: CorrelationStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月19日 上午10:55:40
 * @version V1.0
 *
 */
@Api(value = "相关分析",tags="相关分析")  
@Controller
@RequestMapping("/statistics/correlation")
public class CorrelationStatisticsController extends BaseController{
	@Autowired
	private CorrelationService correlationService;
	
	
	/***
	 * 双变量
	 * @param condition {@link org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition}
	 * @return
	 */
	@ApiOperation(value = "双变量", notes = "双变量",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = BivariateCorrelateDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/bivariate.do" )
	@ResponseBody
	public BivariateCorrelateDTO calBivariate(@RequestBody BivariateCorrelateCondition condition) {
		return correlationService.bivariate(condition);
	}
	
	
	/***
	 * 距离 
	 * @param condition {@link org.jmu.multiinfo.dto.correlation.DistanceCorrelationCondition}
	 * @return
	 */
	@ApiOperation(value = "双变量", notes = "双变量",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = BivariateCorrelateDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/distance.do" )
	@ResponseBody
	public DistanceCorrelationDTO calDistance(@RequestBody DistanceCorrelationCondition condition){
		return correlationService.distance(condition);
	}

}
