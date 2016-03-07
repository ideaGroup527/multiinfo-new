package org.jmu.multiinfo.service.regression;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
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
	 double [] y = {5.92,4.3,3.3,6.23,10.97,9.14,5.77,6.45,7.6,11.46,13.46,10.24,5.99}; // mana cost is y intercept
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
	    double[] regressionParameters = com.getRegressionParameters();
	    for (double d : regressionParameters) {
	    	logger.debug(d+"");
		}
	    
	
}
}
