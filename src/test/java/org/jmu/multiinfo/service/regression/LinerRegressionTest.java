package org.jmu.multiinfo.service.regression;

import org.apache.commons.math3.stat.inference.TestUtils;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class LinerRegressionTest {
	@Test
	public void testSimpleOLS(){
	    OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
	    //double [] manaCost = {4,6,6,3,1,0,4,1};
	    //double [] [] x = new double[8][];

	    double [] manaCost = {5.92,4.3,3.3,6.23,10.97,9.14,5.77,6.45,7.6,11.47,13.46,10.24,5.99}; // mana cost is y intercept
	    double [] [] x = new double[13][];

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
double[][] fortx= new double[2][13];
for (int i = 0; i < fortx.length; i++) {
	fortx[0][i]=x[i][0];
	fortx[1][i]=x[i][1];
}
	    ols.newSampleData(manaCost, x);

	    double[] regressionParameters = ols.estimateRegressionParameters();

	    for (int i = 0; i < regressionParameters.length; i++) {
	        double regressionParameter = regressionParameters[i];
	        System.out.println(i + " " + regressionParameter);
	    }
//	    System.out.println(ols.calculateAdjustedRSquared()+"");
//	    System.out.println(ols.calculateResidualSumOfSquares());
//	    System.out.println(ols.estimateErrorVariance());
//	    System.out.println(ols.estimateRegressionStandardError());
//	    System.out.println(TestUtils.t);
	    System.out.println(TestUtils.tTest( 3.30,regressionParameters));
	}
}
