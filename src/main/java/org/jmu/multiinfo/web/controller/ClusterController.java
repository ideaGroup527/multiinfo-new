package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.service.cluster.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics/cluster.do")
public class ClusterController {
	@Autowired
	private ClusterService clusterService;
	@RequestMapping(params = { "method=pointgroup" })
	@ResponseBody
public PointGroupDTO pointGroup(@RequestBody PointGroupCondition condition){
	return 	clusterService.pointGroup(condition);
}
}
