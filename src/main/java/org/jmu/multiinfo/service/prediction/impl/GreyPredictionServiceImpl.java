package org.jmu.multiinfo.service.prediction.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeCondition;
import org.jmu.multiinfo.dto.prediction.GreyCorrelationDegreeDTO;
import org.jmu.multiinfo.dto.prediction.GreyPredictionCondition;
import org.jmu.multiinfo.dto.prediction.GreyPredictionDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.prediction.GreyPredictionService;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.fabric.xmlrpc.base.Data;

@Service
public class GreyPredictionServiceImpl implements GreyPredictionService{
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	
	@Autowired
	private LinearRegressionService linearRegressionService;

	@Override
	public GreyPredictionDTO grey(GreyPredictionCondition condition) {
		GreyPredictionDTO gpDTO = new GreyPredictionDTO();
		//因子
		VarietyDTO	factorVariable =condition.getFactorVarVariable();
		DataDTO[][] dataGrid =	condition.getDataGrid();
		List<VarietyDTO> independVarList  = condition.getIndependentVariable();
		Double formCoefficient = condition.getFormCoefficient();
		gpDTO.setIndependVarList(independVarList);
		Double xlast = 0.0;
		Map<String, List<Double>> resDataMap =	DataFormatUtil.converToDouble(dataGrid, independVarList, factorVariable);
		
		int pos = 0;
		List<String> factNameList = new ArrayList<>();
		PositionBean factorVarRange = ExcelUtil.splitRange(factorVariable.getRange());
		for (int i = factorVarRange.getFirstRowId() - 1; i < factorVarRange.getLastRowId(); i++) {
			for (int j = factorVarRange.getFirstColId() - 1; j < factorVarRange.getLastColId(); j++) {
				factNameList.add(dataGrid[i][j].getData().toString());
				}
			}
		gpDTO.setPredictName((DataFormatUtil.converToDouble(factNameList.get(factNameList.size()-1))+1) +"");
		double[] dataArr = new double[factNameList.size()];
		for (String facName : factNameList) {
			    List<Double> dataList = resDataMap.get(facName);
			    double[] dataTmp =    DataFormatUtil.converToDouble(dataList);
			    dataArr[pos++] =  basicStatisticsService.arithmeticMean(dataTmp);
		}
    	try {
			gpDTO.setExamineSuccess(examineGrey(dataArr));
			if(!gpDTO.getExamineSuccess()) return gpDTO;
		} catch (DataErrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gpDTO.setRet_err(e.getMessage());
			gpDTO.setRet_code("-1");
			gpDTO.setRet_msg("数据检验失败");
		}
    	
	    
	   
	    try {
	    	 xlast =  gm(formCoefficient, dataArr);
	    	xlast = xlast * independVarList.size();
		} catch (DataErrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gpDTO.setRet_err(e.getMessage());
			gpDTO.setRet_code("-1");
		}
	    
	    
	    Map<String, List<Double>> resVarMap =  DataFormatUtil.converToDouble(dataGrid, independVarList);
	    double[] sumArr = new double[independVarList.size()];
	    pos=0;
	    for (VarietyDTO varDt : independVarList) {
	    	List<Double>  dataList=resVarMap.get(varDt.getVarietyName());
		    double[] dataGridArr =	DataFormatUtil.converToDouble(dataList);
			   Double eSum = basicStatisticsService.sum(dataGridArr);
			   sumArr[pos++] = eSum;
		}
	   Double sum = basicStatisticsService.sum(sumArr);
	   for (int i = 0; i < sumArr.length; i++) {
		   sumArr[i] = sumArr[i] / sum * xlast;
	}
	   gpDTO.setResData(sumArr);
	   gpDTO.setRet_code("0");
		return gpDTO;
	}

	public Double gm(Double formCoefficient, double[] dataArr) throws DataErrException {
		Integer N = basicStatisticsService.getN(dataArr);
		double[] x1 = basicStatisticsService.cumulativeSum(dataArr);
		double[] z1 = basicStatisticsService.averageGeneration(x1, formCoefficient);
		double[][] linearList = new double[N-1][2];
		for(int i=0 ; i<N-1 ; i++){
			linearList[i][0] = z1[i];
			linearList[i][1] = dataArr[i+1];
		}
		SingleLinearDTO singleDto = 	linearRegressionService.calSingleLinearRegression(linearList);
		double[] linearParam  = singleDto.getRegressionParameters();
		Double b = linearParam[0];
		Double a = -linearParam[1];
		//白微分方程
		Double xt1 =	( dataArr[0] - b / a ) * FastMath.exp( (-a) * N ) + (b/a);
		Double xt =	( dataArr[0] - b / a ) * FastMath.exp( (-a) * (N-1) ) + (b/a);
		
		Double x0 = xt1 - xt;
		return x0;
	}
	
	public boolean examineGrey(double[] dataArr) throws DataErrException{
		
    	Integer N = basicStatisticsService.getN(dataArr);
    	Double xmin = FastMath.exp((-2.0) / (N+1));
    	Double xmax = FastMath.exp((2.0) / (N+1));
    	double[] stepArr = basicStatisticsService.stepwiseRatio(dataArr);
		for (double d : stepArr) {
			if(d > xmax || d < xmin) return false;
		}
		return true;
	}

	@Override
	public GreyPredictionDTO inpGrey(GreyPredictionCondition condition) {
		GreyPredictionDTO gpDTO = new GreyPredictionDTO();
		//因子
		VarietyDTO	factorVariable =condition.getFactorVarVariable();
		DataDTO[][] dataGrid =	condition.getDataGrid();
		List<VarietyDTO> independVarList  = condition.getIndependentVariable();
		Double formCoefficient = condition.getFormCoefficient();
		gpDTO.setIndependVarList(independVarList);
		Double xlast = 0.0;
		
		List<String> factNameList = new ArrayList<>();
		PositionBean factorVarRange = ExcelUtil.splitRange(factorVariable.getRange());
		for (int i = factorVarRange.getFirstRowId() - 1; i < factorVarRange.getLastRowId(); i++) {
			for (int j = factorVarRange.getFirstColId() - 1; j < factorVarRange.getLastColId(); j++) {
				factNameList.add(dataGrid[i][j].getData().toString());
				}
			}
		gpDTO.setPredictName((DataFormatUtil.converToDouble(factNameList.get(factNameList.size()-1))+1) +"");
		Map<String, List<Double>> dataMap = DataFormatUtil.converToDouble(dataGrid, independVarList);
		double[] sumArr = new double[independVarList.size()];
	    int pos=0;
		for (VarietyDTO indepDto : independVarList) {
			double[] dataArrTmp = 	DataFormatUtil.converToDouble(dataMap.get(indepDto.getVarietyName()));
			double[] dataArr = new double[factNameList.size()];
			for(int i=0;i<factNameList.size();i++){
				dataArr[i] = dataArrTmp[i];
			}
			
	    	try {
				gpDTO.setExamineSuccess(examineGrey(dataArr));
				if(!gpDTO.getExamineSuccess()) return gpDTO;
			} catch (DataErrException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				gpDTO.setRet_err(e.getMessage());
				gpDTO.setRet_code("-1");
				gpDTO.setRet_msg("数据检验失败");
			}
			
		    try {
		    	 xlast =  gm(formCoefficient, dataArr);
		    	 sumArr[pos++] = xlast;
			} catch (DataErrException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				gpDTO.setRet_err(e.getMessage());
				gpDTO.setRet_code("-1");
			}
		}
	   gpDTO.setResData(sumArr);
	   gpDTO.setRet_code("0");
		return gpDTO;
	}

	@Override
	public GreyCorrelationDegreeDTO greyCorrelationDegree(GreyCorrelationDegreeCondition condition) {
		GreyCorrelationDegreeDTO gcdDTO = new GreyCorrelationDegreeDTO();
		VarietyDTO montherVar =	condition.getMotherSeq();
		DataDTO[][]  dataGrid = condition.getDataGrid();
		List<VarietyDTO> sonSeqArrVar =	condition.getSonSeqArr();
		List<VarietyDTO> monthList = new ArrayList<VarietyDTO>();
		monthList.add(montherVar);
		 Map<String, List<Double>> mtmap =	DataFormatUtil.converToDouble(dataGrid, monthList);
		 double[] monthArr = DataFormatUtil.converToDouble(mtmap.get(montherVar.getVarietyName())) ;
		 Map<String, List<Double>> map =	DataFormatUtil.converToDouble(dataGrid, sonSeqArrVar);
		 Collection<double[]> sonSeqArr = new ArrayList<>();
		for (VarietyDTO var : sonSeqArrVar) {
			sonSeqArr.add(DataFormatUtil.converToDouble(map.get(var.getVarietyName())));
		}
		double[][] rcArr =null;
		 double[] acdArr = null;
		 double[] sortArr = null;
		try {
			rcArr=	greyRelationalCoefficient(monthArr,sonSeqArr,condition.getResolutionRatio());
			acdArr = 	associationDegree(rcArr);
			gcdDTO.setMaxCorrelation(basicStatisticsService.max(acdArr));
			List<Double> temp = new ArrayList<Double>(acdArr.length);
			for (int i = 0; i < acdArr.length; i++) {
				temp.add(acdArr[i]);
			}
			Collections.sort(temp, Collections.reverseOrder());
			sortArr = DataFormatUtil.converToDouble(temp);
		} catch (DataErrException e) {
			gcdDTO.setRet_code("-1");
			gcdDTO.setRet_err(e.getMessage());
			gcdDTO.setRet_code(e.getMessage());
			e.printStackTrace();
		}
		gcdDTO.setSortCorrelationArr(sortArr);
		gcdDTO.setCorrelationArr(acdArr);
		return gcdDTO;
	}

	@Override
	public double[][] greyRelationalCoefficient(double[] motherSeqArr, Collection<double[]> sonSeqArr,double resolutionRatio) throws DataErrException {
		//1.无量纲化
		motherSeqArr =	basicStatisticsService.nondimensionalize(motherSeqArr);
		double[] maxi = new double[sonSeqArr.size()];
		double[] mini = new double[sonSeqArr.size()];
		double[][] absValueArr = new double[sonSeqArr.size()][motherSeqArr.length];
		
		double[][] resArr = new double[sonSeqArr.size()][motherSeqArr.length];
		 Iterator<double[]> it = sonSeqArr.iterator();  
		 int pos=0;
	        while (it.hasNext()) {  
	        	double[] value = it.next();
	        	value= basicStatisticsService.nondimensionalize(value);
	        	//相减差的绝对值
	        	double[] absValue= basicStatisticsService.minusAbs(value, motherSeqArr);
	        	absValueArr[pos] = absValue.clone();
	        	maxi[pos] = basicStatisticsService.max(absValue);
	        	mini[pos++] = basicStatisticsService.min(absValue);
	        }  
	        
	       double Max = basicStatisticsService.max(maxi);
	       double Min = basicStatisticsService.min(mini);
	       
	       for (int i = 0; i < absValueArr.length; i++) {
	    	   resArr[i]  = greyRelationalCoefficient(absValueArr[i],Max,Min,resolutionRatio);
	       }
		 
		return resArr;
	}

	@Override
	public double[] greyRelationalCoefficient(double[] absArr, double max, double min, double resolutionRatio) {
		double[] resArr = new double[absArr.length];
		for (int i = 0; i < absArr.length; i++) {
			resArr[i] = (min + resolutionRatio * max) / ( absArr[i] + resolutionRatio * max);
		}
		return resArr;
	}

	@Override
	public double[] associationDegree(double[][] grcArr) {
		int size = grcArr.length;
		double[] resArr = new double[size];
		for (int i = 0; i < grcArr.length; i++) {
			resArr[i] = basicStatisticsService.arithmeticMean(grcArr[i]);
		}
		
		return resArr;
	}


	
}
