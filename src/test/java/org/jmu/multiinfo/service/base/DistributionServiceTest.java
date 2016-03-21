package org.jmu.multiinfo.service.base;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.dto.basestatistics.NormalDistributionDTO;
import org.jmu.multiinfo.service.basestatistics.DistributionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class DistributionServiceTest {
	@Autowired
	private DistributionService distributionService;
	@Test
	public void normalDistributionTest(){
		NormalDistributionDTO dto =	distributionService.normalDistribution(-2);
		System.out.println(dto);
//		for(int x=0;x<=300;x++){
//			NormalDistributionDTO dto =	distributionService.normalDistribution(x*0.01);
//			System.out.println(dto);
//			
//		}
		
		
	}
}
