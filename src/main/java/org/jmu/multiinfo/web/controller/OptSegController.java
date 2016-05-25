package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.dto.opseg.OptSegCondition;
import org.jmu.multiinfo.dto.opseg.OptSegDTO;
import org.jmu.multiinfo.service.opseg.OptSegService;
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
 * 
 * 最优分割
 * @Title: OptSegController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月21日 上午11:44:50
 * @version V1.0
 *
 */
@Api(value = "最优分割",tags="最优分割")  
@Controller
@RequestMapping("/statistics/optseg")
public class OptSegController {
	@Autowired
	private OptSegService optSegService;
	
	/***
	 * 点群分析
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "最优K分割", notes = "最优分割",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = OptSegDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/optk.do" )
	@ResponseBody
public OptSegDTO optSeg(@RequestBody OptSegCondition condition){
	return 	optSegService.optimalKSegmentation(condition);
}
}
