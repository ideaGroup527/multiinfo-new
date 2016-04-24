package org.jmu.multiinfo.service.basestatistics.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.MatrixUtil;
import org.jmu.multiinfo.service.basestatistics.MatrixStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class MatrixStatisticsServiceImpl implements MatrixStatisticsService{

	@Override
	public EigenvalueDTO eigenvector(double[][] dataArr) throws DataErrException {
		//如果不是对称方阵
		if(!MatrixUtil.isSymmetric(dataArr)) throw new DataErrException("data not a Symmetric in org.jmu.multiinfo.service.basestatistics.impl.MatrixStatisticsService#eigenvector") ;
		EigenvalueDTO egDTO =	MatrixUtil.eigenvalue(dataArr);
		double[][] V = egDTO.getV();
		double[] eigenvalues = egDTO.getRealEigenvalues();
		Map<Double,double[]> map = new HashMap<>();
		double[][] vt =	DataFormatUtil.transposition(V);
		List<Double> eigenvaluesList = new ArrayList<>();
		for (int i = 0; i < eigenvalues.length; i++) {
			Double eg = eigenvalues[i];
			map.put(eg,vt[i]);
			eigenvaluesList.add(eg);
		}
		Collections.sort(eigenvaluesList,new Comparator<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		});
	
		
		double[] sortEigenvalues = new double[eigenvaluesList.size()];
		double[][] eigenvectors = new double[eigenvaluesList.size()][vt[0].length];
		for (int i = 0; i < eigenvaluesList.size(); i++) {
			sortEigenvalues[i] = eigenvaluesList.get(i).doubleValue();
			double[] a = map.get(sortEigenvalues[i]);
			eigenvectors[i] = a;
		}
		egDTO.setSortEigenvalues(sortEigenvalues);
		
		egDTO.setEigenvectors(eigenvectors);
		return egDTO;
		
		
		
	}
	


}
