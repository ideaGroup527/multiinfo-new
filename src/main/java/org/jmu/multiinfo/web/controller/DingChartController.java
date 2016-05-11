package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeDTO;
import org.jmu.multiinfo.dto.regression.GraphDTO;
import org.jmu.multiinfo.service.dingchart.DingChartService;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
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
 * 图表
 * @Title: DingChartController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月21日 下午4:17:34
 * @version V1.0
 *
 */
@Api(value = "图表",tags="图表")  
@Controller
@RequestMapping("/statistics/chart")
public class DingChartController {
	@Autowired
	private LinearRegressionService linearRegressionService;
	@Autowired
	private DingChartService dingChartService;
	
	/***
	 * 丁氏图
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "丁氏图", notes = "丁氏图",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = DingChartDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value = "/ding.do")
	@ResponseBody
	public DingChartDTO calDing(@RequestBody DingChartCondition condition){
	return dingChartService.ding(condition);	
	}
	/***
	 * 散点图数据转换
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "散点图数据转换", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = GraphDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(params = { "/convertForGraph.do" })
	@ResponseBody
	public GraphDTO convertForGraph(@RequestBody BiVarCondition condition){
	return 	linearRegressionService.convertForGraph(condition);
		
	}
}
