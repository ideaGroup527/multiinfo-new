package org.jmu.multiinfo.service.reducingdim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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
	double[][] correlationArr = {{1,0.816,0.749},{0.816,1,0.624},{0.749,0.624,1}};
	 double csn = pcaService.chiSquareBartlett(correlationArr);
	 System.out.println(csn); 
}
}
