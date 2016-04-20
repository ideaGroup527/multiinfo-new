package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;
import org.jmu.multiinfo.service.prediction.GreyPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * *
 * 
 * 
 * @Title: PredictionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @Description: 预测
 * @author <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月20日 下午4:43:20
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/statistics/prediction.do")
public class PredictionController {
	@Autowired
	private GreyPredictionService greyPredictionService;
	@RequestMapping(params = { "method=grey" })
	@ResponseBody
	public GreyPredictionDTO calGrey(@RequestBody GreyPredictionCondition condition) {
		return greyPredictionService.grey(condition);

	}
}
