package org.jmu.multiinfo.core.util;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import no.uib.cipr.matrix.DenseMatrix;

/***
 * 
 * 矩阵算法
 * @Title: MatrixUtil.java
 * @Package org.jmu.multiinfo.core.util
 * @author  <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2016年4月22日 下午5:04:44
 * @version V1.0
 *
 */
public class MatrixUtil {


	/***
	 * 
	 * @param dataArr
	 * @return
	 */
	public static EigenvalueDTO eigenvalue(double[][] dataArr){
		EigenvalueDTO eigenvalueDTO = new EigenvalueDTO();
		Matrix matrix = new Matrix(dataArr);
		EigenvalueDecomposition eigValue =	matrix.eig();
		eigenvalueDTO.setD(eigValue.getD().getArray());
		eigenvalueDTO.setV(eigValue.getV().getArray());
		eigenvalueDTO.setRealEigenvalues(eigValue.getRealEigenvalues());
		eigenvalueDTO.setImagEigenvalues(eigValue.getImagEigenvalues());
		return eigenvalueDTO;
	}
	
	
	/**
	 * 判断是不是对称方阵
	 * @param dataArr
	 * @return
	 */
	public static boolean isSymmetric(double[][] dataArr){
		int row = dataArr.length;
		int col  = dataArr.length;
		if((row == col) && row >0 ) return true;
		else
		return true;
		
	}
	/**
	 * 对称方阵 特征值 
	 * @param dataArr
	 * @return
	 */
	public static double[] eigenvalueForSymmetric(double[][] dataArr){
		if(isSymmetric(dataArr)){
			Matrix matrix = new Matrix(dataArr);
			EigenvalueDecomposition eigValue =	matrix.eig();
			double[] D = eigValue.getRealEigenvalues();
			return D;
		}else
			return null;
	}

	/**
	 * 对称方阵求V （每列是特征向量）
	 * @param dataArr
	 * @return
	 */
	public static double[][] eigenvectorForSymmetric(double[][] dataArr){
		if(isSymmetric(dataArr)){
			Matrix matrix = new Matrix(dataArr);
			EigenvalueDecomposition eigValue =	matrix.eig();
			Matrix V = eigValue.getV();
			return V.getArray();
		}else
		return null;
	}
	
	
	/***
	 * 行列式
	 * @param dataArr
	 * @return
	 */
	public static double determinant(double[][] dataArr){
		Matrix matrix = new Matrix(dataArr);
		return matrix.det();
	}

	
	
	/***
	 * 
	 * @param dataArr
	 * @param excuseRow
	 * @param excuseCol
	 * @return
	 */
	public static double[][] createSubMatrix(double[][] dataArr,int excuseRow,int excuseCol){
		excuseRow--;excuseCol--;
		double[][] subArr = new double[dataArr.length-1][dataArr[0].length-1];
		    int r = -1;
		    for (int i=0;i<dataArr.length;i++) {
		        if (i==excuseRow)
		            continue;
		            r++;
		            int c = -1;
		        for (int j=0;j<dataArr[0].length;j++) {
		            if (j==excuseCol)
		                continue;
		            subArr[r][++c] = dataArr[i][j];
		        }
		    }
		    return subArr;
	}
	
	
	
	/***
	 * 代数余子式
	 * @param dataArr
	 * @return
	 */
	public static double[][] cofactor(double[][] dataArr){
		int row = dataArr.length;
		int col = dataArr[0].length;
		double[][] focArr = new double[row][col];
		for (int i=0;i<row;i++) {
	        for (int j=0; j<col;j++) {
	    		Matrix subMatrix = new Matrix(createSubMatrix(dataArr, i+1, j+1));
	    		double det = subMatrix.det();
	    		focArr[i][j] = changeSign(i+1,j+1) * det;
	        }
	    }
		return focArr;
	}
	
	/***
	 * 偏相关矩阵
	 * -Cij/(sqrt(Cii*Cjj))
	 * @param dataArr
	 * @return
	 */
	public static double[][] partialCorrelation(double[][] dataArr){
		int row = dataArr.length;
		int col = dataArr[0].length;
		double[][] pcArr = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				pcArr[i][j] = - dataArr[i][j]/(FastMath.sqrt(dataArr[i][i]*dataArr[j][j]));
			}
		}
		return pcArr;
	}
	
	
	public static double[][] product(double[][] dataArr,double l){
		Matrix mt = new Matrix(dataArr);
		return mt.times(l).getArray();
	}
	
	/***
	 * 增广矩阵
	 * @param dataArr
	 * @return
	 */
	public static double[][] augmentedMatrix(double[][] dataArr){
		int row = dataArr.length;
		int col = dataArr[0].length;
		int col2 =  col * 2;
		double[][] resArr = new double[row][col2];
		for (int i = 0; i < row ; i++) {
			for (int j = 0; j < col; j++) {
				resArr[i][j] = dataArr[i][j];
			}
			for (int j = col; j < col2; j++) {
				int k = j - col;
				if( k == i )
				resArr[i][j] = 1;
				else 
					resArr[i][j] = 0 ;
			}
		}
		
		return resArr;
	}

	//假如i是偶数返回1
	private static int changeSign(int i , int j) {
		if((i+j)%2 == 0) return 1;
		return -1;
	}
}
