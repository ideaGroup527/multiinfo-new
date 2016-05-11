package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.comparemean.AnovaCondition;
import org.jmu.multiinfo.dto.comparemean.AnovaDataDTO;
import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.service.comparemean.CompareMeanStatisticsService;
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
 * 均值统计
 * @Title: CompareMeanStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月3日 下午19:21:41
 * @version V1.0
 *
 */
@Api(value = "均值统计",tags="均值统计")  
@Controller
@RequestMapping("/statistics/comparemean")
public class CompareMeanStatisticsController  {
	
	@Autowired
	private CompareMeanStatisticsService compareMeanStatisticsService;
	
	
	/***
	 * 比较均值
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "比较均值", notes = "比较均值",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = MedianDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value =  "/mean.do" )
	@ResponseBody
   public MedianDTO calMean(@RequestBody MedianCondition condition){
		MedianDTO medianDTO =	compareMeanStatisticsService.calMean(condition);
		return medianDTO;
	}
	
	
	/***
	 * 单因素方差分析
	 * @param anovaCondition
	 * @return
	 */
	@ApiOperation(value = "单因素方差分析", notes = "单因素方差分析",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = AnovaDataDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value =  "/onewayanova.do" )
	@ResponseBody
	public AnovaDataDTO calOneWayAnova(@RequestBody AnovaCondition anovaCondition){
		AnovaDataDTO resDTO =compareMeanStatisticsService.calOneWayAnova(anovaCondition);
		return resDTO;
	}
}
