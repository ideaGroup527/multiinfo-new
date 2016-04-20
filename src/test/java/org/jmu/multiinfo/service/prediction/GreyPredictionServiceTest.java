package org.jmu.multiinfo.service.prediction;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class GreyPredictionServiceTest {
@Autowired
private GreyPredictionService greyPredictionService;
@Test
public void gmTest() throws DataErrException{
	double[] dataArr = {87.6167,98.5000,108.4750,118.4167,132.8083,145.4083};
	greyPredictionService.gm(0.4, dataArr );
}
}
