package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.service.cluster.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
@Controller
@RequestMapping("/statistics/cluster.do")
public class ClusterController {
	@Autowired
	private ClusterService clusterService;
	
	
	/***
	 * 点群分析
	 * 路径/statistics/cluster.do?method=pointgroup
	 * @param condition
	 * @return
	 */
	@RequestMapping(params = { "method=pointgroup" })
	@ResponseBody
public PointGroupDTO pointGroup(@RequestBody PointGroupCondition condition){
	return 	clusterService.pointGroup(condition);
}
}
