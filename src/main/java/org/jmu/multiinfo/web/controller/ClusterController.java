package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.service.cluster.ClusterService;
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
 * 聚类分析
 * @Title: ClusterController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年5月3日 上午11:44:50
 * @version V1.0
 *
 */
@Api(value = "聚类分析",tags="聚类分析")
@Controller
@RequestMapping("/statistics/cluster")
public class ClusterController {
	@Autowired
	private ClusterService clusterService;
	
	
	/***
	 * 点群分析
	 * @param condition
	 * @return
	 */
	@ApiOperation(value = "点群分析", notes = "点群分析",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = PointGroupDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value= "/pointgroup.do" )
	@ResponseBody
public PointGroupDTO pointGroup(@RequestBody PointGroupCondition condition){
	return 	clusterService.pointGroup(condition);
}
}
