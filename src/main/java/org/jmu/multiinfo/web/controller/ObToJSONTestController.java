package org.jmu.multiinfo.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/json.do")
public class ObToJSONTestController {
	@RequestMapping(params = { "method=median" })
	@ResponseBody
   public MedianCondition calMedian(){
		MedianCondition condition = new MedianCondition();
		VarietyDTO independentVariable = new VarietyDTO();
		independentVariable.setVarietyName("地点");
		independentVariable.setPosition("A");
		condition.setIndependentVariable(independentVariable );
		List<VarietyDTO> dependentVariableList = new ArrayList<VarietyDTO>();
		
		VarietyDTO deVar = new VarietyDTO();
		deVar.setVarietyName("降水");
		deVar.setPosition("B");
		dependentVariableList.add(deVar );
		condition.setDependentVariable(dependentVariableList );
		DataDTO[][] dataGrid = new DataDTO[10][10];
		DataDTO i00 =new DataDTO();
		dataGrid[0][0] = i00;
		condition.setDataGrid(dataGrid);
		
		return condition;
	}
}
