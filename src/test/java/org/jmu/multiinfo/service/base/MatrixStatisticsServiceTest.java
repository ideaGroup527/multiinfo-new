package org.jmu.multiinfo.service.base;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.service.basestatistics.MatrixStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class MatrixStatisticsServiceTest {
	@Autowired
	private MatrixStatisticsService matrixStatisticsService;
	
	@Test
	public void eigenvector() throws DataErrException{
		double[][] dataArr = {{1,0.671,0.214,0.001},{0.671,1,-0.196,0.007},{0.214,-0.196,1,0.081},{0.001,0.007,0.081,1}};
		EigenvalueDTO egDTO  = matrixStatisticsService.eigenvector(dataArr);
		System.out.println();
	}
	
	@Test
	public void variation() throws DataErrException{
		double[][] dataArr={
				{1,0.857,0.514,0.2,0.028,0},
				{0.2,0,0.4,0.8,0.6,1}
		};
		DataFormatUtil.print(matrixStatisticsService.variation(dataArr));
	}

}
