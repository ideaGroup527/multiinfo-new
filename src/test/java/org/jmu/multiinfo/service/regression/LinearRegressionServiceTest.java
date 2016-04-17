package org.jmu.multiinfo.service.regression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.DataVariety;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class LinearRegressionServiceTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LinearRegressionService linearRegressionService;
	@Test
public void calOLSMultipleLinearRegression(){
	 double [] y = {5.92,4.3,3.3,6.23,10.97,9.14,5.77,6.45,7.6,11.47,13.46,10.24,5.99}; // mana cost is y intercept
	    double [][] x = new double[13][];

	    //atk,health,charge,divine
	    x[0] = new double []{4.9,4.78};
	    x[1] = new double []{5.9,3.84};
	    x[2] = new double []{5.6,3.31};
	    x[3] = new double []{4.9,3.44};
	    x[4] = new double []{5.6,6.84};
	    x[5] = new double []{8.5,9.47};
	    x[6] = new double []{7.7,6.51};
	    x[7] = new double []{7.1,5.92};
	    x[8] = new double []{6.1,6.08};
	    x[9] = new double []{5.8,8.09};
	    x[10] = new double []{7.1,10.01};
	    x[11] = new double []{7.6,10.81};
	    x[12] = new double []{9.7,8};
	    MultipleLinearDTO com = (MultipleLinearDTO)linearRegressionService.calOLSMultipleLinearRegression(y, x);
	    int i=0;
	    double[] regressionParameters = com.getRegressionParameters();
	    for (double d : regressionParameters) {
	    	logger.debug(d+"");
	    	logger.debug((d/com.getRegressionParametersStandardErrors()[i++])+"");
		}
	    logger.debug(com.toString());
//	    logger.debug((com.getRSquared()/(3-1))/((1-com.getRSquared())/(13-3))+"");
	   
	
}
	@Test
public void calSingleLinearRegressionTest(){
		double[][] data = new double[10][2];
		data[0][1] = 308;data[0][0] = 314;
		data[1][1] = 443;data[1][0] = 473;
		data[2][1] = 432;data[2][0] = 563;
		data[3][1] = 572;data[3][0] = 747;
		data[4][1] = 603;data[4][0] = 856;
		data[5][1] = 624;data[5][0] = 943;
		data[6][1] = 989;data[6][0] = 1308;
		data[7][1] = 1164;data[7][0] = 1871;
		data[8][1] = 1208;data[8][0] = 2265;
		data[9][1] = 1764;data[9][0] = 3038;
		

	
		SingleLinearDTO com = (SingleLinearDTO)	linearRegressionService.calSingleLinearRegression(data );
		   logger.debug(com.toString());
}
	@Test
	public void calLinearRegressionTest() throws JsonGenerationException, JsonMappingException, IOException{
		double[][] data = new double[10][2];
		data[0][1] = 308;data[0][0] = 314;
		data[1][1] = 443;data[1][0] = 473;
		data[2][1] = 432;data[2][0] = 563;
		data[3][1] = 572;data[3][0] = 747;
		data[4][1] = 603;data[4][0] = 856;
		data[5][1] = 624;data[5][0] = 943;
		data[6][1] = 989;data[6][0] = 1308;
		data[7][1] = 1164;data[7][0] = 1871;
		data[8][1] = 1208;data[8][0] = 2265;
		data[9][1] = 1764;data[9][0] = 3038;
		BiVarCondition condition = new BiVarCondition();
		DataDTO[][] dataGrid = new DataDTO[11][2];
		
		DataDTO i00 =new DataDTO();
		i00.setData("Yi");
		i00.setPosition("A1");
		i00.setPositionDes("A,1");
		i00.setTypeDes("字符串型");
		i00.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][0] = i00;
		DataDTO i01 =new DataDTO();
		i01.setData("Xi");
		i01.setPosition("B1");
		i01.setPositionDes("B,1");
		i01.setTypeDes("字符串型");
		i01.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][1] = i01;
		for (int i = 1; i < dataGrid.length; i++) {
			for (int j = 0; j < dataGrid[0].length; j++) {
				dataGrid[i][j] = new DataDTO();
				dataGrid[i][j].setData(data[i-1][j]);
				dataGrid[i][j].setType(DataVariety.DATA_TYPE_NUMERIC);
				dataGrid[i][j].setTypeDes("标准型数字");
				dataGrid[i][j].setPosition(ExcelUtil.getExcelColName(j+1)+(i+1)+"");
				dataGrid[i][j].setPositionDes(ExcelUtil.getExcelColName(j+1)+","+(i+1));
			}
		}


		condition.setDataGrid(dataGrid);
		VarietyDTO dependentVariable = new VarietyDTO();
		dependentVariable.setPosition("A1");
		dependentVariable.setRange("A2:A11");
		dependentVariable.setVarietyName("Yi");
		condition.setDependentVariable(dependentVariable);
		List<VarietyDTO> independentVariable = new ArrayList<VarietyDTO>();
		VarietyDTO e = new VarietyDTO();
		e.setPosition("B1");
		e.setRange("B2:B11");
		e.setVarietyName("Xi");
		independentVariable.add(e );
		condition.setIndependentVariable(independentVariable );
		   logger.debug(condition.toString());
		   SingleLinearDTO comt =(SingleLinearDTO)	linearRegressionService.calLinearRegression(condition );
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.writeValue(new File("E://a.json"),condition);
		System.out.println("");
		mapper.writeValue(new File("E://b.json"),comt);
	}
	
	
	@Test
	public void calLinearRegressionForMulTest() throws JsonGenerationException, JsonMappingException, IOException{
		BiVarCondition condition = new BiVarCondition();
		DataDTO[][] dataGrid = new DataDTO[14][3];
		DataDTO i00 =new DataDTO();
		i00.setData("实际通货膨胀率Y");
		i00.setPosition("A1");
		i00.setPositionDes("A,1");
		i00.setTypeDes("字符串型");
		i00.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][0] = i00;
		DataDTO i01 =new DataDTO();
		i01.setData("失业率X2");
		i01.setPosition("B1");
		i01.setPositionDes("B,1");
		i01.setTypeDes("字符串型");
		i01.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][1] = i01;
		DataDTO i02 =new DataDTO();
		i02.setData("预期通货膨胀率X3");
		i02.setPosition("C1");
		i02.setPositionDes("C,1");
		i02.setTypeDes("字符串型");
		i02.setType(DataVariety.DATA_TYPE_STRING);
		dataGrid[0][2] = i02;
		 double [] y = {5.92,4.3,3.3,6.23,10.97,9.14,5.77,6.45,7.6,11.47,13.46,10.24,5.99}; // mana cost is y intercept
		    double [][] x = new double[13][];

		    //atk,health,charge,divine
		    x[0] = new double []{4.9,4.78};
		    x[1] = new double []{5.9,3.84};
		    x[2] = new double []{5.6,3.31};
		    x[3] = new double []{4.9,3.44};
		    x[4] = new double []{5.6,6.84};
		    x[5] = new double []{8.5,9.47};
		    x[6] = new double []{7.7,6.51};
		    x[7] = new double []{7.1,5.92};
		    x[8] = new double []{6.1,6.08};
		    x[9] = new double []{5.8,8.09};
		    x[10] = new double []{7.1,10.01};
		    x[11] = new double []{7.6,10.81};
		    x[12] = new double []{9.7,8};
		for (int i = 1; i < dataGrid.length; i++) {
			for (int j = 0; j < dataGrid[0].length-1; j++) {
				dataGrid[i][j+1] = new DataDTO();
				dataGrid[i][j+1].setData(x[i-1][j]);
				dataGrid[i][j+1].setType(DataVariety.DATA_TYPE_NUMERIC);
				dataGrid[i][j+1].setTypeDes("标准型数字");
				dataGrid[i][j+1].setPosition(ExcelUtil.getExcelColName(j+2)+(i+1)+"");
				dataGrid[i][j+1].setPositionDes(ExcelUtil.getExcelColName(j+2)+","+(i+1));
			}
		}
		for (int i = 0; i < y.length; i++) {
			dataGrid[i+1][0] = new DataDTO();
			dataGrid[i+1][0].setData(y[i]);
			dataGrid[i+1][0].setType(DataVariety.DATA_TYPE_NUMERIC);
			dataGrid[i+1][0].setTypeDes("标准型数字");
			dataGrid[i+1][0].setPosition(ExcelUtil.getExcelColName(1)+(i+1)+"");
			dataGrid[i+1][0].setPositionDes(ExcelUtil.getExcelColName(1)+","+(i+1));
		}
		condition.setDataGrid(dataGrid);
		VarietyDTO dependentVariable = new VarietyDTO();
		dependentVariable.setPosition("A1");
		dependentVariable.setRange("A2:A14");
		dependentVariable.setVarietyName("Yi");
		condition.setDependentVariable(dependentVariable);
		List<VarietyDTO> independentVariable = new ArrayList<VarietyDTO>();
		VarietyDTO e = new VarietyDTO();
		e.setPosition("B1");
		e.setRange("B2:B14");
		e.setVarietyName("X2");
		independentVariable.add(e );
		VarietyDTO e1 = new VarietyDTO();
		e1.setPosition("C1");
		e1.setRange("C2:C14");
		e1.setVarietyName("X3");
		independentVariable.add(e1 );
		condition.setIndependentVariable(independentVariable );
		   logger.debug(condition.toString());
		MultipleLinearDTO comt =(MultipleLinearDTO)	linearRegressionService.calLinearRegression(condition );
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.writeValue(new File("E://a.json"),condition);
		System.out.println("");
		mapper.writeValue(new File("E://b.json"),comt);
	}
}
