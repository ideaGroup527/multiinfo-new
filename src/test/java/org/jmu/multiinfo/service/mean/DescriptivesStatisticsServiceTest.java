package org.jmu.multiinfo.service.mean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.dto.descriptives.MeanCondition;
import org.jmu.multiinfo.dto.descriptives.MeanDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.DataVariety;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class DescriptivesStatisticsServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private DescriptivesStatisticsService descriptivesStatisticsService;

@Test
public void calMeanTest() throws JsonGenerationException, JsonMappingException, IOException{
	MeanCondition condition = new MeanCondition();
	List<VarietyDTO> dependentVariableList = new ArrayList<VarietyDTO>();
	
	VarietyDTO deVar = new VarietyDTO();
	deVar.setVarietyName("降水");
	deVar.setPosition("B");
	deVar.setType(1);
	deVar.setTypeDes("标准型数字");
	deVar.setRange("B2:B5");
	dependentVariableList.add(deVar );
	
	VarietyDTO deVar2 = new VarietyDTO();
	deVar2.setVarietyName("湿度");
	deVar2.setPosition("C");
	deVar2.setType(1);
	deVar2.setTypeDes("标准型数字");
	deVar2.setRange("C2:C5");
	dependentVariableList.add(deVar2);
	
	condition.setVariableList(dependentVariableList );
	DataDTO[][] dataGrid = new DataDTO[5][3];
	DataDTO i00 =new DataDTO();
	i00.setData("地点");
	i00.setPosition("A1");
	i00.setPositionDes("A,1");
	i00.setTypeDes("字符串型");
	i00.setType(DataVariety.DATA_TYPE_STRING);
	dataGrid[0][0] = i00;

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
	DataDTO i40 =new DataDTO();
	i40.setData("上海");
	i40.setPosition("A5");
	i40.setPositionDes("A,5");
	i40.setType(DataVariety.DATA_TYPE_STRING);
	i40.setTypeDes("字符串型");
	dataGrid[4][0] = i40;
	
	
	DataDTO i01 =new DataDTO();
	i01.setData("降水");
	i01.setPosition("B1");
	i01.setPositionDes("B,1");
	i01.setTypeDes("字符串型");
	i01.setType(DataVariety.DATA_TYPE_STRING);
	dataGrid[0][1] = i01;
	
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
	DataDTO i41 =new DataDTO();
	i41.setData("150");
	i41.setPosition("B5");
	i41.setPositionDes("B,5");
	i41.setType(DataVariety.DATA_TYPE_NUMERIC);
	i41.setTypeDes("标准型数字");
	dataGrid[4][1] = i41;
	
	
	DataDTO i02 =new DataDTO();
	i01.setData("湿度");
	i01.setPosition("C1");
	i01.setPositionDes("C,1");
	i01.setTypeDes("字符串型");
	i01.setType(DataVariety.DATA_TYPE_STRING);
	dataGrid[0][2] = i02;
	
	DataDTO i12 =new DataDTO();
	i12.setData("20.4");
	i12.setPosition("C2");
	i12.setPositionDes("C,2");
	i12.setType(DataVariety.DATA_TYPE_NUMERIC);
	i12.setTypeDes("标准型数字");
	dataGrid[1][2] = i12;
	DataDTO i22 =new DataDTO();
	i22.setData("17.3");
	i22.setPosition("C3");
	i22.setPositionDes("C,3");
	i22.setType(DataVariety.DATA_TYPE_NUMERIC);
	i22.setTypeDes("标准型数字");
	dataGrid[2][2] = i22;
	DataDTO i32 =new DataDTO();
	i32.setData("33.3");
	i32.setPosition("C4");
	i32.setPositionDes("C,4");
	i32.setType(DataVariety.DATA_TYPE_NUMERIC);
	i32.setTypeDes("标准型数字");
	dataGrid[3][2] = i32;
	DataDTO i42 =new DataDTO();
	i42.setData("6.4");
	i42.setPosition("C5");
	i42.setPositionDes("C,5");
	i42.setType(DataVariety.DATA_TYPE_NUMERIC);
	i42.setTypeDes("标准型数字");
	dataGrid[4][2] = i42;
	
	condition.setDataGrid(dataGrid);
	MeanDTO meanDTO =	descriptivesStatisticsService.calMean(condition );
	System.out.println(meanDTO.toString());
	ObjectMapper mapper = new ObjectMapper(); 
	mapper.writeValue(new File("G://a.json"),condition);
	System.out.println("");
	mapper.writeValue(new File("G://b.json"),meanDTO);
}
}
