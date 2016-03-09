package org.jmu.multiinfo.service.regression;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

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
}
