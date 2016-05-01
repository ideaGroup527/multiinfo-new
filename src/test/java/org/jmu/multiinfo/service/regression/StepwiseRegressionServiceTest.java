package org.jmu.multiinfo.service.regression;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.base.util.MyJUnit4ClassRunner;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.MatrixUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class StepwiseRegressionServiceTest {
@Autowired
private StepwiseRegressionService stpService;
double[] dataArrY=null;
List<double[]> dataArrXList = null;
//@Before
public void init(){
	double[]  dataArrY_c={900,354,566,521,333,
			359,589,416,289,254,
209,428,673,395,327,
829,266,653,187,178,
160,280,234,264,216,
294,268,185,246
};
	dataArrY = dataArrY_c.clone();
	double[] dataArrX1=new double[]{14.51,7.57,1.94,3.04,8.07,
			4.64,3.02,6.2,2.69,2.85,
			1.02,1.62,7.02,2.09,0.83,
			4.56,5.43,4.05,3.78,1.11,
			7.17,5,3.88,0.74,3.05,
			0.3,3.44,5.94,3.12};
	
	double[] dataArrX2=	new double[]{27,27.7,28.3,27.3,28.5,
			28.5,27.4,28.2,29,27.5,
			27,27.5,27.3,27.3,28.7,
			27,	29,	26.9,	28,	29,
			27,	26,	27,	26.5,	27.8,
			28,	28,	25,	27.2};
	double[] dataArrX3=	new double[]{8.8,10.8,13.6,12.1,5.7,
	15.8,5.4,12,12.7,5,
	20.7,7,5.8,14.5,11.8,
	7,	7.2,	4.2,11.6,13.6,
	11,33.6,16,-1.2,13.4,
	11,8,10,9.1};
	
	double[] dataArrX4=	new double[]{2,7,	13,	13,	-2,
	14,	0,	12,	6,	12,
	1,	4,	-17,	-11,	-13,
	-4,	-4,	-1,	8,	-3,
	2,	-27,	-7,6,		-7,	-7,
	-4,	1,	6};
	
	double[] dataArrX5=	new double[]{-0.5,	0.8,	-0.2,	0.2,	-0.6,
	1.4,	0.6,	0,	1.3,	0,
	1,	1.5,	1.8,	0,	2.3,
	-0.3,	-1.5,	-0.3,	-1,	-0.5,
	-1,	2.7,	1,	-2,	-1.7,
	-0.7,	-0.2,	-2.7,	1};
	double[] dataArrX6=	new double[]{8,
	5,
	1.7,
	1.5,
	2.7,
	2,
	4.6,
	2.5,
	15.7,
	6.8,
	10,
	6,
	10,
	8.5,
	4,
	4,
	4,
	2.8,
	12.2,
	14,
	10.6,
	23.3,
	9.5,
	9,
	2.7,
	8,
	11.7,
	5.2,
	17.3};
	double[] dataArrX7=	new double[]{248,
	81,
	124.8,
	314.6,
	110.4,
	109.6,
	110,
	378,
	87.8,
	152.2,
	148.5,
	48,
	230,
	110.5,
	125,
	240,
	157.2,
	80,
	97,
	144,
	157.3,
	206.4,
	134,
	368,
	165.2,
	144.2,
	256,
	201.6,
	173};

	 dataArrXList = new ArrayList<>();
	dataArrXList.add(dataArrX1);
	dataArrXList.add(dataArrX2);
	dataArrXList.add(dataArrX3);
	dataArrXList.add(dataArrX4);
	dataArrXList.add(dataArrX5);
	dataArrXList.add(dataArrX6);
	dataArrXList.add(dataArrX7);
}

@Before
public void init2(){
	double[]  dataArrY_c={78.5,
	74.3,
	104.2,
	87.6,
	95.9,
	109.2,
	102.7,
	72.5,
	93.1,
	115.9,
	83.8,
	113.3,
	109.4};
	double[] dataArrX1=	new double[]{
			7,1
,11
,11
,7
,11
,3
,1
,2
,21
,1
,11
,10};
			double[] dataArrX2=	new double[]{26,29
,56
,31
,52
,55
,71
,31
,54
,47
,40
,66
,68
	};
			double[] dataArrX3=	new double[]{6
,15
,8
,8
,6
,9
,17
,22
,18
,4
,23
,9
,8

			};
			
			
			double[] dataArrX4=	new double[]{60
,52
,20
,47
,33
,22
,6
,44
,22
,26
,34
,12
,12
			};
	dataArrY = dataArrY_c.clone();
	
	 dataArrXList = new ArrayList<>();
	dataArrXList.add(dataArrX1);
	dataArrXList.add(dataArrX2);
	dataArrXList.add(dataArrX3);
	dataArrXList.add(dataArrX4);
}

@Test
public void initCorrelationMatrixTest() throws DataErrException{

	DataFormatUtil.print(stpService.initCorrelationMatrix(dataArrY, dataArrXList));
	
}

@Test
public void stepwiseRegression() throws DataErrException{
	DataFormatUtil.print(MatrixUtil.augmentedMatrix(stpService.initCorrelationMatrix(dataArrY, dataArrXList)));
//	System.out.println(stpService.optimizationVar(stpService.initCorrelationMatrix(dataArrY, dataArrXList)));
}

@Test
public void inverseCompactTransform() throws DataErrException{
	double[][] calMatrix = {{10,7,4,4},
			{7,7,3,4},
			{4,3,4,3}};
	
	int[] a = {1,6,5,3,2};
//	double[][] calMatrix = {{1   ,    0.2286 , -0.8241 , -0.2454  , 0.7307},
//	{0.2286 , 1   ,    -0.1392 ,  -0.9730 , 0.8163},
//	{-0.8241, -0.1392,  1    ,     0.0295  ,-0.5347},
//	{-0.2454, -0.9730 , 0.0295 ,    1   ,   -0.8213},
//	{0.7307 , 0.8163 ,  -0.5347,    -0.8213,  1}};
//	DataFormatUtil.print(stpService.inverseCompactTransform(stpService.initCorrelationMatrix(dataArrY, dataArrXList) , 1));
	calMatrix=	stpService.initCorrelationMatrix(dataArrY, dataArrXList);
	for(int l=0;l<a.length;l++){
		calMatrix = stpService.inverseCompactTransform(calMatrix, a[l]);
		DataFormatUtil.print(calMatrix);
	}
		
}

@Test
public void stepwise() throws DataErrException{
	List<Double> bList=	stpService.stepwise(dataArrY, dataArrXList, 4,4);
	for (int i = 0; i < bList.size(); i++) {
		System.out.println(bList.get(i));
	}
}
}
