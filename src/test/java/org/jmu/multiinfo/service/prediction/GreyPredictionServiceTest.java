package org.jmu.multiinfo.service.prediction;

import java.util.ArrayList;
import java.util.Collection;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
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

@Test
public void greyRelationalCoefficient() throws DataErrException{
	
	
	double[] motherSeqArr = {1998,2061,2335,2750,3356,3806};
	Collection<double[]> sonSeqArr=new ArrayList<>();
	sonSeqArr.add(new double[]{386,408,422,482,511,561});
	sonSeqArr.add(new double[]{839,846,960,1258,1577,1893});
	sonSeqArr.add(new double[]{763,808,953,1010,1268,1352});
	
	double resolutionRatio=0.5;
	double[]  resArr = greyPredictionService.associationDegree(greyPredictionService.greyRelationalCoefficient(motherSeqArr, sonSeqArr, resolutionRatio));
	DataFormatUtil.print(resArr);
}
}
