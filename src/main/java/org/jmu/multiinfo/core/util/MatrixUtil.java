package org.jmu.multiinfo.core.util;

import org.jmu.multiinfo.core.dto.EigenvalueDTO;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

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
}
