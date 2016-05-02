package org.jmu.multiinfo.service.regression.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.exception.TestNotPassException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.regression.SlipStepwiseCondition;
import org.jmu.multiinfo.dto.regression.SlipStepwiseDTO;
import org.jmu.multiinfo.dto.regression.StepwiseCondition;
import org.jmu.multiinfo.dto.regression.StepwiseMultipleDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
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
	
	@Autowired
	private BasicStatisticsService basicStatisticsService;
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
	public StepwiseMultipleDTO stepwise(double[] dataArrY, List<double[]> dataArrXList, double entryF, double delF) throws DataErrException {
		StepwiseMultipleDTO smDTO = new StepwiseMultipleDTO();
		 List<Double> meanList = new ArrayList<Double>();
		 List<Double> sdList = new ArrayList<Double>();
		for (int i = 0; i < dataArrXList.size(); i++) {
			meanList.add(basicStatisticsService.arithmeticMean(dataArrXList.get(i)));
			sdList.add(FastMath.sqrt(basicStatisticsService.averageSumDeviation(dataArrXList.get(i))));
		}

		meanList.add(basicStatisticsService.arithmeticMean(dataArrY));
		sdList.add(FastMath.sqrt(basicStatisticsService.averageSumDeviation(dataArrY)));
		
		 List<Double> bList = new ArrayList<Double>(dataArrXList.size()+1);
		for (int i = 0; i <dataArrXList.size()+1; i++) {
			bList.add(0.0);
		}
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
		 double b0 = 0.0;
		 for (int i = 0; i < posList.size(); i++) {
			 logger.debug(posList.get(i)+":"+tmpMat[posList.get(i) - 1][yp]);
			 
			 
			 double bi = sdList.get(sdList.size() - 1) / sdList.get(posList.get(i)- 1) * tmpMat[posList.get(i) - 1][yp];
			 bList.set(posList.get(i), bi);
			 b0 = b0 - bi * meanList.get(posList.get(i)- 1);
		}
		 
			double meanY =basicStatisticsService.arithmeticMean(dataArrY);
		 bList.set(0, meanY + b0 );
//		 DataFormatUtil.print(tmpMat);
		 double Rkk = tmpMat[yp][yp];
		 smDTO.setResidualSumOfSquares(Rkk * sdList.get(sdList.size() - 1)* sdList.get(sdList.size() - 1));
		 smDTO.setRegressionStandardError( FastMath.sqrt(smDTO.getResidualSumOfSquares()/(dataArrY.length - (posList.size()) -1.0)));
		 smDTO.setRSquared(1 - Rkk);
		 double Sk = sdList.get(sdList.size() - 1) ;
		 double U = Sk * (1 - Rkk);
		 double Q = Sk -  U;
		 smDTO.setF((U / (posList.size()))*(Q /(dataArrY.length - (posList.size()) -1.0)) );
		 smDTO.setRegressionParameters(DataFormatUtil.converToDouble(bList));
		 return smDTO;
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
	@Override
	public StepwiseMultipleDTO stepwise(StepwiseCondition condition) {
		StepwiseMultipleDTO stpDTO = new StepwiseMultipleDTO();
		VarietyDTO dependVarDTO = condition.getDependentVariable();
		List<VarietyDTO> independVarDTOList = condition.getIndependentVariable();
		DataDTO[][] dataGrid = condition.getDataGrid();
		// 因变量数据
		List<Double> dependVarList = new ArrayList<Double>();
		PositionBean depvarRange = ExcelUtil.splitRange(dependVarDTO.getRange());
		for (int i = depvarRange.getFirstRowId() - 1; i < depvarRange.getLastRowId(); i++) {
			for (int j = depvarRange.getFirstColId() - 1; j < depvarRange.getLastColId(); j++) {
				dependVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
			}
		}
		List<double[]> dataArrXList = new ArrayList<>();
		Map<String, List<Double>> xMap =	DataFormatUtil.converToDouble(dataGrid, independVarDTOList);
		for (VarietyDTO inpDto:independVarDTOList) {
			dataArrXList.add(DataFormatUtil.converToDouble(xMap.get(inpDto.getVarietyName())));
		}
		
		double[] dataArrY = DataFormatUtil.converToDouble(dependVarList);
		try {
			stpDTO= stepwise(dataArrY , dataArrXList, condition.getEntryF(), condition.getDelF());
		} catch (DataErrException e) {
			stpDTO.setRet_code("-1");
			stpDTO.setRet_msg(e.getMessage());
			e.printStackTrace();
		}
		return stpDTO;
	}
	@Override
	public SlipStepwiseDTO slipStepwise(SlipStepwiseCondition condition) {
		SlipStepwiseDTO slDTO = new SlipStepwiseDTO();
		VarietyDTO dependVarDTO = condition.getDependentVariable();
		List<VarietyDTO> independVarDTOList = condition.getIndependentVariable();
		VarietyDTO timeVarDTO =	condition.getTimeVariable();
		DataDTO[][] dataGrid = condition.getDataGrid();
		//自变量
		List<double[]> dataArrXList = new ArrayList<>();

		//时间变量
		List<Double> timeVarList = new ArrayList<>();
		PositionBean timevarRange = ExcelUtil.splitRange(timeVarDTO.getRange());
		for (int i = timevarRange.getFirstRowId() - 1; i < timevarRange.getLastRowId(); i++) {
			for (int j = timevarRange.getFirstColId() - 1; j < timevarRange.getLastColId(); j++) {
				timeVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
			}
		}
		double[] timeArr = DataFormatUtil.converToDouble(timeVarList);
		
		// 因变量数据
		List<Double> oraDependVarList = new ArrayList<Double>();
		PositionBean depvarRange = ExcelUtil.splitRange(dependVarDTO.getRange());
		for (int i = depvarRange.getFirstRowId() - 1; i < depvarRange.getLastRowId(); i++) {
			for (int j = depvarRange.getFirstColId() - 1; j < depvarRange.getLastColId(); j++) {
				oraDependVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
			}
		}
		StepwiseMultipleDTO preDTO = new StepwiseMultipleDTO();
		switch (condition.getPreviousMethod()) {
		
		case SlipStepwiseCondition.SLIP_PREVIOUS_ASC:{
			double nextYear = timeArr[timeArr.length-1] +1;
			List<Double> dependVarList = new ArrayList<>(); 
			for (int i = 0; i < oraDependVarList.size(); i++) 
				dependVarList.add(oraDependVarList.get(i));
			
			dependVarList.remove(0);
			dataArrXList = new ArrayList<>();
			double[] dataArrY = DataFormatUtil.converToDouble(dependVarList);
			Map<String, List<Double>> xMap =	DataFormatUtil.converToDouble(dataGrid, independVarDTOList);
			List<Double> fuInpList= new ArrayList<>();
			for (int i = 0; i < independVarDTOList.size(); i++) {
				List<Double> tempList =	xMap.get(independVarDTOList.get(i).getVarietyName());
				Double removeData = tempList.remove(tempList.size() - 1);
				fuInpList.add(removeData);
				dataArrXList.add(DataFormatUtil.converToDouble(tempList));
			}
			
			try {
				preDTO= stepwise(dataArrY , dataArrXList, condition.getEntryF(), condition.getDelF());
				Map<String, Double> preForecast = new HashMap<>();
				
				double[] parameters = preDTO.getRegressionParameters();
				double value = parameters[0];
				for (int i = 1; i < parameters.length; i++) {
					value+=fuInpList.get(i-1) * parameters[i];
				}
				preForecast.put(nextYear+"", value);
				slDTO.setPreForecast(preForecast );
				
			} catch (DataErrException e) {
				preDTO.setRet_code("-1");
				preDTO.setRet_msg(e.getMessage());
				e.printStackTrace();
			}
			break;
		}
		case SlipStepwiseCondition.SLIP_PREVIOUS_DESC:{
			double nextYear = timeArr[0] +1;
			List<Double> dependVarList = new ArrayList<>(); 
			for (int i = 0; i < oraDependVarList.size(); i++) 
				dependVarList.add(oraDependVarList.get(i));
			dependVarList.remove(dependVarList.size()-1);
			dataArrXList = new ArrayList<>();
			double[] dataArrY = DataFormatUtil.converToDouble(dependVarList);
			Map<String, List<Double>> xMap =	DataFormatUtil.converToDouble(dataGrid, independVarDTOList);
			List<Double> fuInpList= new ArrayList<>();
			for (int i = 0; i < independVarDTOList.size(); i++) {
				List<Double> tempList =	xMap.get(independVarDTOList.get(i).getVarietyName());
				Double removeData = tempList.remove(0);
				fuInpList.add(removeData);
				dataArrXList.add(DataFormatUtil.converToDouble(tempList));
			}
			
			try {
				preDTO= stepwise(dataArrY , dataArrXList, condition.getEntryF(), condition.getDelF());
				Map<String, Double> preForecast = new HashMap<>();
				
				double[] parameters = preDTO.getRegressionParameters();
				double value = parameters[0];
				for (int i = 1; i < parameters.length; i++) {
					value+=fuInpList.get(i-1) * parameters[i];
				}
				preForecast.put(nextYear+"", value);
				slDTO.setPreForecast(preForecast );
				
			} catch (DataErrException e) {
				preDTO.setRet_code("-1");
				preDTO.setRet_msg(e.getMessage());
				e.printStackTrace();
			}
			
		}
		default:
			break;
		}
		StepwiseMultipleDTO backDTO = new StepwiseMultipleDTO();
		switch(condition.getBackwardMethod()){
		
		case SlipStepwiseCondition.SLIP_BACKWARD_ASC:{
			double preYear = timeArr[0] -1;
			List<Double> dependVarList = new ArrayList<>(); 
			for (int i = 0; i < oraDependVarList.size(); i++) 
				dependVarList.add(oraDependVarList.get(i));
			dependVarList.remove(dependVarList.size()-1);
			dataArrXList = new ArrayList<>();
			double[] dataArrY = DataFormatUtil.converToDouble(dependVarList);
			Map<String, List<Double>> xMap =	DataFormatUtil.converToDouble(dataGrid, independVarDTOList);
			List<Double> fuInpList= new ArrayList<>();
			for (int i = 0; i < independVarDTOList.size(); i++) {
				List<Double> tempList =	xMap.get(independVarDTOList.get(i).getVarietyName());
				Double removeData = tempList.remove(0);
				fuInpList.add(removeData);
				dataArrXList.add(DataFormatUtil.converToDouble(tempList));
			}
			
			try {
				backDTO= stepwise(dataArrY , dataArrXList, condition.getEntryF(), condition.getDelF());
				double[] parameters = backDTO.getRegressionParameters();
				double value = parameters[0];
				for (int i = 1; i < parameters.length; i++) {
					value+=fuInpList.get(i-1) * parameters[i];
				}
				Map<String, Double> backForecast = new HashMap<>();
				backForecast.put(preYear+"", value);
				slDTO.setBackForecast(backForecast);
			} catch (DataErrException e) {
				preDTO.setRet_code("-1");
				preDTO.setRet_msg(e.getMessage());
				e.printStackTrace();
			}
		
			break;
			}
			
		case SlipStepwiseCondition.SLIP_BACKWARD_DESC:{
			double preYear = timeArr[timeArr.length-1] -1;
			List<Double> dependVarList = new ArrayList<>(); 
			for (int i = 0; i < oraDependVarList.size(); i++) 
				dependVarList.add(oraDependVarList.get(i));
			dependVarList.remove(dependVarList.size()-1);
			dataArrXList = new ArrayList<>();
			double[] dataArrY = DataFormatUtil.converToDouble(dependVarList);
			Map<String, List<Double>> xMap =	DataFormatUtil.converToDouble(dataGrid, independVarDTOList);
			List<Double> fuInpList= new ArrayList<>();
			for (int i = 0; i < independVarDTOList.size(); i++) {
				List<Double> tempList =	xMap.get(independVarDTOList.get(i).getVarietyName());
				Double removeData = tempList.remove(tempList.size() - 1);
				fuInpList.add(removeData);
				dataArrXList.add(DataFormatUtil.converToDouble(tempList));
			}
			
			try {
				backDTO= stepwise(dataArrY , dataArrXList, condition.getEntryF(), condition.getDelF());
				double[] parameters = backDTO.getRegressionParameters();
				double value = parameters[0];
				for (int i = 1; i < parameters.length; i++) {
					value+=fuInpList.get(i-1) * parameters[i];
				}
				Map<String, Double> backForecast = new HashMap<>();
				backForecast.put(preYear+"", value);
				slDTO.setBackForecast(backForecast);
			} catch (DataErrException e) {
				preDTO.setRet_code("-1");
				preDTO.setRet_msg(e.getMessage());
				e.printStackTrace();
			}
			break;
			}
		default:
			break;
		
		}
		slDTO.setPreData(preDTO);
		slDTO.setBackData(backDTO);
		return slDTO;
	}

}
