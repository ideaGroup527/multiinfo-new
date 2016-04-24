package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;
import org.jmu.multiinfo.service.reducingdim.PrincipalComponentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/statistics/analysis.do")
public class PrincipalComponentAnalysisController {
	@Autowired
	private PrincipalComponentAnalysisService pcaService;
	/***
	 * 主成分分析
	 * 路径/statistics/analysis.do?method=principalComponent
	 * @param condition {@link org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition}
	 * @return
	 */
	@RequestMapping(params = { "method=principalComponent" })
	@ResponseBody
	public PrincipalComponentAnalysisDTO calPrincipalComponentAnalysis(@RequestBody PrincipalComponentAnalysisCondition condition) {
		return pcaService.principalComponentAnalysis(condition);
	
	}

}
