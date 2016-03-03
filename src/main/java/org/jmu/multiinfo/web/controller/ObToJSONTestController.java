package org.jmu.multiinfo.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.DataVariety;
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
		independentVariable.setType(0);
		independentVariable.setTypeDes("字符串型");
		condition.setIndependentVariable(independentVariable );
		List<VarietyDTO> dependentVariableList = new ArrayList<VarietyDTO>();
		
		VarietyDTO deVar = new VarietyDTO();
		deVar.setVarietyName("降水");
		deVar.setPosition("B");
		deVar.setType(1);
		deVar.setTypeDes("标准型数字");
		dependentVariableList.add(deVar );
		condition.setDependentVariable(dependentVariableList );
		DataDTO[][] dataGrid = new DataDTO[4][2];
		DataDTO i00 =new DataDTO();
		i00.setData("地点");
		i00.setPosition("A1");
		i00.setPositionDes("A,1");
		i00.setTypeDes("字符串型");
		i00.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][0] = i00;
		DataDTO i01 =new DataDTO();
		i01.setData("降水");
		i01.setPosition("B1");
		i01.setPositionDes("B,1");
		i01.setTypeDes("字符串型");
		i01.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][1] = i01;
		DataDTO i10 =new DataDTO();
		i10.setData("北京");
		i10.setPosition("A2");
		i10.setPositionDes("A,2");
		i10.setType(DataVariety.DATA_TYPE_STRING);
		i10.setTypeDes("字符串型");
		dataGrid[1][0] = i10;
		DataDTO i20 =new DataDTO();
		i20.setData("上海");
		i20.setPosition("A3");
		i20.setPositionDes("A,3");
		i20.setType(DataVariety.DATA_TYPE_STRING);
		i20.setTypeDes("字符串型");
		dataGrid[2][0] = i20;
		DataDTO i30 =new DataDTO();
		i30.setData("上海");
		i30.setPosition("A4");
		i30.setPositionDes("A,4");
		i30.setType(DataVariety.DATA_TYPE_STRING);
		i30.setTypeDes("字符串型");
		dataGrid[3][0] = i30;
		DataDTO i11 =new DataDTO();
		i11.setData("100");
		i11.setPosition("B2");
		i11.setPositionDes("B,2");
		i11.setType(DataVariety.DATA_TYPE_NUMERIC);
		i11.setTypeDes("标准型数字");
		dataGrid[1][1] = i11;
		DataDTO i21 =new DataDTO();
		i21.setData("200");
		i21.setPosition("B3");
		i21.setPositionDes("B,3");
		i21.setType(DataVariety.DATA_TYPE_NUMERIC);
		i21.setTypeDes("标准型数字");
		dataGrid[2][1] = i21;
		DataDTO i31 =new DataDTO();
		i31.setData("150");
		i31.setPosition("B4");
		i31.setPositionDes("B,4");
		i31.setType(DataVariety.DATA_TYPE_NUMERIC);
		i31.setTypeDes("标准型数字");
		dataGrid[3][1] = i31;
		condition.setDataGrid(dataGrid);
		
		return condition;
	}
}
