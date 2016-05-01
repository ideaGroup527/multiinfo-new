package org.jmu.multiinfo.service.correlation;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class CorrelationServiceTest {
	@Autowired
	private CorrelationService correlationService;
	@Test
	public void bivariateTest(){
		
//		correlationService.bivariate(condition);
	}
	
	@Test
	public void personTest() throws DataErrException{
		double[] dataArrX = {314,473,563,747,856,943,1308,1871,2265,3038};
		double[] dataArrY = {
			308,
			443,
			432,
			572,
			603,
			624,
			989,
			1164,
			1208,
			1764};
		correlationService.PearsonsCorrelationMatrix(dataArrX, dataArrY);
		
	}
}
