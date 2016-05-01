package org.jmu.multiinfo.service.regression.impl;

import java.util.ArrayList;
import java.util.List;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.exception.TestNotPassException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.jmu.multiinfo.service.regression.StepwiseRegressionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepwiseRegressionServiceImpl implements StepwiseRegressionService {
Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CorrelationService correlationService;
	@Override
	public double[][] initCorrelationMatrix(double[] dataArrY, List<double[]> dataArrXList) throws DataErrException {
	int N =	dataArrXList.size();
	N++;
	double[][] clMatrix = new double[N][N];
	for(int i=0;i<N;i++){
		for(int j=0;j<N;j++){
			double[] x = null;
			double[] y = null;
			if(i == N-1) x = dataArrY;
			else x = dataArrXList.get(i);
			if(j == N-1) y = dataArrY;
			else y = dataArrXList.get(j);
			

			Double r=	correlationService.pearsonRCoefficient(x, y);
			clMatrix[i][j]=r;	
		}
	}
		
		return clMatrix;
	}
	@Override
	public boolean optimizationVar(double[][] clMatrix,int N ,int l,int yp,double entryF,List<Integer> posList) {
		//1.确定引进变量，
		int size = clMatrix.length;
		int k = yp;
		double maxi = Double.MIN_VALUE;
		int pos =1;
				for (int j = 0; j < size - 1; j++) {
					if(posList.contains(j+1)) continue;
					double tem = clMatrix[j][k]* clMatrix[j][k] / clMatrix[j][j];
					if(tem > maxi) {maxi = tem; pos = j+1;}
				}
		//2.进行F引进检验
			double F = maxi / (clMatrix[k][k] - maxi ) * (N -(l- 1) -2);
				if(F <=entryF) return false;
				posList.add(pos);
		return true;
	}
	@Override
	public double[][] inverseCompactTransform(double[][] clMatrix, int l) {
		int row = clMatrix.length;
		int col = clMatrix[0].length;
		double[][] icMatrix = new double[row][col];
		int k = l;
		//Rkj = Rkj /Rkk
		for (int j = 0; j < col ; j++) {
			icMatrix[k-1][j] = clMatrix[k-1][j]  / clMatrix[k-1][k-1];
		}
		
		//Rij = Rij -Rik*Rkj/Rkk(i!=k,j!=k)
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(i != (k -1) && j !=(k-1))
				icMatrix[i][j] = clMatrix[i][j] - clMatrix[i][k-1] * clMatrix[k-1][j] / clMatrix[k-1][k-1];
			}
		}
		
		//Rkk = 1/Rkk
		icMatrix[k-1][k-1] = 1/clMatrix[k-1][k-1];
		
		
		//Rik = -Rik /Rkk(i!=k)
		for (int i = 0; i < row; i++) {
			if(i != (k -1))
			icMatrix[i][k-1] = -clMatrix[i][k-1]  / clMatrix[k-1][k-1];
		}
		
		return icMatrix;
	}
	@Override
	public void stepwise(double[] dataArrY, List<double[]> dataArrXList, double entryF, double delF) throws DataErrException {
		 double[][] initclMatArr =	initCorrelationMatrix(dataArrY,dataArrXList);
		 double[][] tmpMat =initclMatArr;
		 int N = initclMatArr.length;
		 int yp = N -1;
		 int l =1;
		 List<Integer> posList = new ArrayList<Integer>();
		 for(;;l++){
			 initclMatArr = tmpMat;
			boolean flag = optimizationVar(initclMatArr,dataArrY.length,l,yp,entryF,posList);
			if(!flag) break;
		int newPos =	posList.get(posList.size()-1);
			tmpMat =  inverseCompactTransform(initclMatArr,newPos);
			if( posList.size() >1 ){
				try {
					List<Integer> delList = new ArrayList<>();
					boolean flag2 = outlier(tmpMat,dataArrY.length,l,yp,delF,posList,delList );
					while(!flag2) {
						tmpMat =	inverseCompactTransform(tmpMat, delList.get(0));
						delList.clear();
						flag2=	outlier(tmpMat,dataArrY.length,l,yp,delF,posList,delList );
					}
				} catch (TestNotPassException e) {
					e.printStackTrace();
				}
			}
//		 DataFormatUtil.print(tmpMat);
		 }
		 for (int i = 0; i < posList.size(); i++) {
			 logger.debug(posList.get(i)+":"+tmpMat[posList.get(i) - 1][yp]);
		}
		 DataFormatUtil.print(tmpMat);
	}
	@Override
	public boolean outlier(double[][] clMatrix, int N, int l,int yp, double delF,List<Integer> posList,List<Integer> delList) throws TestNotPassException {
		int size  = posList.size();
		int k = yp;
		int depos = 1;
		if(size == 0) throw new TestNotPassException("无法进行剔除检验");
		double mini = Double.MAX_VALUE;
		for(int i=0;i< size-1;i++){
			int pos = posList.get(i);
			double temp =clMatrix[pos-1][k] *clMatrix[pos-1][k]/ clMatrix[pos-1][pos-1];
			if(temp < mini) {mini = temp;depos = i;}
		}
		double f2 = mini / clMatrix[k][k] * (N -(l- 1) -2);
		if( f2 > delF ) return true;
		delList.add(posList.get(depos));
		posList.remove(depos);
		return false;
	}

}
