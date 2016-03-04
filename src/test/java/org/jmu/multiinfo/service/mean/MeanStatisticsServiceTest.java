package org.jmu.multiinfo.service.mean;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MeanStatisticsServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private MeanStatisticsService meanStatisticsService;
public void calMedianTest(){
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
	meanStatisticsService.calMedian(condition );
}
}
