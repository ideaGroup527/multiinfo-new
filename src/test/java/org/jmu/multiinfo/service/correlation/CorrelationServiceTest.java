package org.jmu.multiinfo.service.correlation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class CorrelationServiceTest {
	@Autowired
	private CorrelationService correlationService;
	@Test
	public void bivariateTest(){
		
//		correlationService.bivariate(condition);
	}
}
