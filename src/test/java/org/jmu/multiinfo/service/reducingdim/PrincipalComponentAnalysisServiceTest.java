package org.jmu.multiinfo.service.reducingdim;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class PrincipalComponentAnalysisServiceTest {
@Autowired
private PrincipalComponentAnalysisService pcaService;
@Test
public  void kmo(){
	double[][] correlationArr = {{1,0.816,0.749},{0.816,1,0.624},{0.749,0.624,1}};
	double k = pcaService.kmo(correlationArr);
	System.out.println(k);
}

@Test
public void chiSquareBartlett(){
//	double[][] correlationArr = {{1,0.816,0.749},{0.816,1,0.624},{0.749,0.624,1}};
//	double[][] correlationArr = {
//			{1.000,0.816,0.749,0.519},
//			{0.816,1.000,0.624,0.571},
//			{0.749,0.624,1.000,0.531},
//			{0.519,0.571,0.531,1.000}
//	};
//	double[][] correlationArr = {{1.000,0.816,0.749,0.519,0.320},
//	{0.816,1.000,0.624,0.571,0.286},
//	{0.749, 0.624 ,1.000 ,0.531 ,0.654},
//	{0.519 ,0.571 ,0.531 ,1.000 ,0.420},
//	{0.320 ,0.286, 0.654 ,0.420 ,1.000}
//	};
	
	double[][] correlationArr = {{1.000,0.816,0.749,0.519,0.320,-0.038},
	{0.816,1.000,0.624,0.571,0.286,0.088},
	{0.749,0.624,1.000,0.531,0.654,-0.081},
	{0.519,0.571,0.531,1.000,0.420,0.068},
	{0.320,0.286,0.654,0.420,1.000,-0.037},
	{-0.038,0.088,-0.081,0.068,-0.037,1.000}
	};

	
	 double csn = pcaService.chiSquareBartlett(correlationArr);
	 System.out.println(csn); 
}
}
