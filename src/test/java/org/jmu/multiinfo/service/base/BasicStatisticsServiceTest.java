package org.jmu.multiinfo.service.base;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class BasicStatisticsServiceTest {
	@Autowired
	private BasicStatisticsService basicStatisticsService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void percentileTest(){
		double[] dataArr = new double[]{1,2,3,4,5};
		Percentile p =basicStatisticsService.percentile(dataArr );
		logger.warn(p.evaluate(25)+"");
	}
}
