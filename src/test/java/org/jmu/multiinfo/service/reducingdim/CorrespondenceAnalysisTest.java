package org.jmu.multiinfo.service.reducingdim;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class CorrespondenceAnalysisTest {
	@Autowired
	private CorrespondenceAnalysisService correspondenceAnalysisService;
	
	@Test
	public void probability() throws DataErrException{
		double[][] dataArr={{1,1,3},
				{2,1,2},
				{2,1,4},
				{3,1,2}};
		correspondenceAnalysisService.probability(dataArr);
		
	}
}
