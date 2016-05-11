package org.jmu.multiinfo.web.controller;

import org.jmu.multiinfo.core.controller.BaseController;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeCondition;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeDTO;
import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;
import org.jmu.multiinfo.service.prediction.GreyPredictionService;
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
 * 预测
 * @Title: PredictionController.java
 * @Package org.jmu.multiinfo.web.controller
 * @author <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月20日 下午4:43:20
 * @version V1.0
 *
 */
@Api(value = "预测算法",tags="预测算法")  
@Controller
@RequestMapping("/statistics/prediction")
public class PredictionController extends BaseController{
	@Autowired
	private GreyPredictionService greyPredictionService;
	
	
	/***
	 * 
	 * <a href="https://github.com/ideaGroup527/multiinfo-data/tree/master/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA.doc">灰色预测</a>
	 * @param condition {@link org.jmu.multiinfo.dto.prediction.GreyPredictionCondition}
	 * @return
	 */
	@ApiOperation(value = "灰色预测", notes = "<a href='https://github.com/ideaGroup527/multiinfo-data/tree/master/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA/%E7%81%B0%E8%89%B2%E7%90%86%E8%AE%BA.doc'>灰色预测</a>",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = GreyPredictionDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value = "/grey.do")
	@ResponseBody
	public GreyPredictionDTO calGrey(@RequestBody GreyPredictionCondition condition) {
		GreyPredictionDTO  gpDTO = new GreyPredictionDTO();
		Integer type = condition.getAssociationType();
		switch (type) {
		case GreyPredictionCondition.ASSOCIATION_INDEPENDENT:
			gpDTO= greyPredictionService.inpGrey(condition);
			break;
		case GreyPredictionCondition.ASSOCIATION_CONTINUOUS:
			gpDTO=  greyPredictionService.grey(condition);
			break;
		default:
			gpDTO.setRet_msg("类型选择错误");
			gpDTO.setRet_code("-1");
			break;
		}
		return gpDTO;
		

	}
	
	/***
	 * 灰色关联度
	 * @param condition {@link org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeCondition}
	 * @return 
	 */
	@ApiOperation(value = "灰色关联度", notes = "灰色关联度",httpMethod="POST")  
	  @ApiResponses(value = {  
	            @ApiResponse(code = 200, message = "计算成功", response = GreyCorrelationDegreeDTO.class),  
	            @ApiResponse(code = 400, message = "入参有误"),
	            @ApiResponse(code = 500, message = "内部报错")}  
	  )
	@RequestMapping(value="/greydegree.do")
	@ResponseBody
	public GreyCorrelationDegreeDTO greyCorrelationDegree(@RequestBody GreyCorrelationDegreeCondition condition){
		return greyPredictionService.greyCorrelationDegree(condition);
	}
}
