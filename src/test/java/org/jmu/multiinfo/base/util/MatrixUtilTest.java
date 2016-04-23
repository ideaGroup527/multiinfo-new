package org.jmu.multiinfo.base.util;

import org.jmu.multiinfo.core.util.MatrixUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MyJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:conf/spring/applicationContext.xml"})
public class MatrixUtilTest {

	@Test
	public void eigenvalueForSymmetricTest(){
		double[][] dataArr = {{1,0.671,0.214,0.001},{0.671,1,-0.196,0.007},{0.214,-0.196,1,0.081},{0.001,0.007,0.081,1}};
//		double[][] dataArr ={ {1.000,0.517,-0.227,-0.662,-0.353},
//				  {0.517,1.000,-0.757,-0.826,-0.194},
//				{-0.227,-0.757,1.000,0.675,0.397},
//				{-0.662,-0.826,0.675,1.000,0.478},
//				{-0.353,-0.194,0.397,0.478,1.000}};
		
//		double[][] dataArr ={ {0.685694,-0.0392685, 1.27368,0.516904 },
//				 {-0.0392685, 0.188004,-0.321713,-0.117981 },
//				 {1.27368,-0.321713,  3.11318,1.29639    },
//				 {0.516904,-0.117981,  1.29639,0.582414  }};
		
//		double[][] dataArr ={ {1, -2 ,0},
//		{-2 ,5 ,0},
//		{0,0, 2}};
		
//		double[][] dataArr ={ {0.5653 ,   0.7883   , 0.1365  ,  0.9749},
//	    {0.2034 ,   0.5579  ,  0.3574 ,   0.6579},
//	    { 0.5070  ,  0.1541  ,  0.9648 ,   0.0833},
//	    {0.5373  ,  0.7229  ,  0.3223  ,  0.3344}};
		double[] D = MatrixUtil.eigenvalueForSymmetric(dataArr );
		for (int i = 0; i < dataArr.length; i++) {
	System.out.println(D[i]+",");
		
	}
		
		System.out.println();
	}
	
	@Test 
	public void eigenvectorForSymmetricTest(){
//		double[][] dataArr = {{1,0.671,0.214,0.001},{0.671,1,-0.196,0.007},{0.214,-0.196,1,0.081},{0.001,0.007,0.081,1}};
//		double[][] dataArr ={ {0.5653 ,   0.7883   , 0.1365  ,  0.9749},
//			    {0.2034 ,   0.5579  ,  0.3574 ,   0.6579},
//			    { 0.5070  ,  0.1541  ,  0.9648 ,   0.0833},
//			    {0.5373  ,  0.7229  ,  0.3223  ,  0.3344}};
		
//		double[][] dataArr ={ {1.000,0.517,-0.227,-0.662,-0.353},
//			  {0.517,1.000,-0.757,-0.826,-0.194},
//			{-0.227,-0.757,1.000,0.675,0.397},
//			{-0.662,-0.826,0.675,1.000,0.478},
//			{-0.353,-0.194,0.397,0.478,1.000}};
		double[][] dataArr ={ {0.685694,-0.0392685, 1.27368,0.516904 },
				 {-0.0392685, 0.188004,-0.321713,-0.117981 },
				 {1.27368,-0.321713,  3.11318,1.29639    },
				 {0.516904,-0.117981,  1.29639,0.582414  }};
		
		double[][] yy = MatrixUtil.eigenvectorForSymmetric(dataArr);
		for (int i = 0; i < yy.length; i++) {
			for (int j = 0; j < yy[0].length; j++) {
				System.out.print(yy[i][j] +" ");
			}
			System.out.println();
		}
		
	}
}
