package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;
import org.jmu.multiinfo.service.prediction.GreyPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 预测
 * @Title: PredictionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月20日 下午4:43:20
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/prediction.do")
public class PredictionController extends BaseController{
	@Autowired
	private GreyPredictionService greyPredictionService;
	
	
	/***
	 * <a href="https://github.com/ideaGroup527/multiinfo-data/tree/master/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA.doc">灰色预测</a>
	 * 路径/statistics/prediction.do?method=grey
	 * @param condition {@link org.jmu.multiinfo.dto.prediction.GreyPredictionCondition}
	 * @return
	 */
	@RequestMapping(params = { "method=grey" })
	@ResponseBody
	public GreyPredictionDTO calGrey(@RequestBody GreyPredictionCondition condition) {
		return greyPredictionService.grey(condition);

	}
}
