package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;
import org.jmu.multiinfo.dto.dingchart.DingChartDTO;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/***
 *  <a href="http://www.seekbio.com/biotech/soft/SPSS/2012/j819365850.html">描述统计</a>
 * @Title: DescriptivesStatisticsController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年3月5日 下午4:55:56
 * @version V1.0
 *
 */
@Api(value = "描述统计",tags="描述统计")  
@Controller
@RequestMapping("/statistics/descriptives")
public class DescriptivesStatisticsController extends BaseController{
	@Autowired
	private DescriptivesStatisticsService descriptivesStatisticsService;
	
	/***
	 * 描述统计
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = " 描述统计", notes = " 描述统计",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = CommonDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value =  "/descriptives.do")
	@ResponseBody
   public CommonDTO calDesc(@RequestBody PercentileCondition condition){
	
		if(condition.getPercentiles()==null || condition.getPercentiles().size() == 0){
			List<Double> percentiles = new ArrayList<Double>();
			percentiles.add(25.0);
			percentiles.add(50.0);
			percentiles.add(75.0);
			condition.setPercentiles(percentiles );
		}
		CommonDTO meanDTO =	descriptivesStatisticsService.calDesc(condition);
		return meanDTO;
	}
	
	/***
	 * 频率分析
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = " 频率分析", notes = "频率分析",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = CommonDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/frequency.do")
	@ResponseBody
   public CommonDTO calFrequency(@RequestBody OneVarCondition condition){
		CommonDTO freDTO =	descriptivesStatisticsService.calFrequency(condition);
		return freDTO;
	}
	
}
