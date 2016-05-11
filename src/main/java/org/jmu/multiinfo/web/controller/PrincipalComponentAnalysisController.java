package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.service.reducingdim.PrincipalComponentAnalysisService;
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

/***
 * 
 * 主成分分析
 * @Title: PrincipalComponentAnalysisController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月23日 下午4:06:13
 * @version V1.0
 *
 */
@Api(value = "分析算法",tags="分析算法")  
@Controller
@RequestMapping("/statistics/analysis")
public class PrincipalComponentAnalysisController {
	@Autowired
	private PrincipalComponentAnalysisService pcaService;
	/***
	 * 主成分分析
	 * @param condition {@link org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition}
	 * @return
	 */
	@ApiOperation(value = "主成分分析", notes = "返回计算结果对象",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = PrincipalComponentAnalysisDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/principalComponent.do")
	@ResponseBody
	public PrincipalComponentAnalysisDTO calPrincipalComponentAnalysis(@ApiParam(required = true, name = "condition", value = "主成分分析入参") @RequestBody PrincipalComponentAnalysisCondition condition) {
		return pcaService.principalComponentAnalysis(condition);
	
	}

}
