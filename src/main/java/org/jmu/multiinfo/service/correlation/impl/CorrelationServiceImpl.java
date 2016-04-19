package org.jmu.multiinfo.service.correlation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.correlation.BiCoDataDTO;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrelationServiceImpl implements CorrelationService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BasicStatisticsService basicStatisticsService;

	@Override
	public Double pearsonRCoefficient(double[] dataArrX, double[] dataArrY) throws DataErrException {

		Double lxy = basicStatisticsService.averageMulSumDeviation(dataArrX, dataArrY);
		Double lxx = basicStatisticsService.averageSumDeviation(dataArrX);
		Double lyy = basicStatisticsService.averageSumDeviation(dataArrY);
		Double r = lxy / (Math.sqrt(lxx * lyy));
		return r;
	}

	@Override
	public Double pearsonTCoefficient(Double r, Integer n) {
		Double tc = r * Math.sqrt(n - 2) / Math.sqrt(1 - r * r);
		return tc;
	}

	@Override
	public Double spearmanRCoefficient(double[] dataArrX,double[] dataArrY) throws DataErrException {
		double[] rankArrX = basicStatisticsService.rankAve(dataArrX);
		double[] rankArrY = basicStatisticsService.rankAve(dataArrY);
		int N = basicStatisticsService.getN(rankArrX);
		if( N == 0 || N != rankArrY.length ) throw new DataErrException("cannot resolve for spearmanRCoefficient because diffrent size");
		double[] rankArr = new double[N];
		for (int i = 0 ; i < N ; i++ ) {
			rankArr[i] = rankArrX[i] - rankArrY[i];
		}
		Double diSum = basicStatisticsService.sumSquares(rankArr);
		Double r =1 - 6 * diSum /(N * (N * N -1));
		return r;
	}

	@Override
	public BivariateCorrelateDTO bivariate(BivariateCorrelateCondition condition) {
		BivariateCorrelateDTO biDTO = new BivariateCorrelateDTO();
		DataDTO[][] dataGrid = condition.getDataGrid();
		List<VarietyDTO> variableList	 = condition.getVariableList();
		 Map<String, ResultDescDTO> basicDataMap = new HashMap<>();
		 Map<String, List<Map<String, BiCoDataDTO>>> resDataMap = new HashMap<>();
		
		Map<String, List<Double>>  variableMap =	DataFormatUtil.converToDouble(dataGrid, variableList);
		 for (Map.Entry<String, List<Double>> entry : variableMap.entrySet()) {

			 List<Map<String, BiCoDataDTO>> resDataList = new ArrayList<>(); 
			 ResultDescDTO basicData = new ResultDescDTO();
			 String varityName = entry.getKey();
			 List<Double> dataList = entry.getValue();
			 double[] dataArrX = DataFormatUtil.converToDouble(dataList);
			 
			 //基本统计量
			 basicData.setCount(basicStatisticsService.getN(dataArrX));
			 basicData.setArithmeticMean(basicStatisticsService.arithmeticMean(dataArrX));
			 basicData.setStandardDeviation(basicStatisticsService.standardDeviation(dataArrX));
			basicDataMap.put(varityName, basicData );
			
			 Integer N = basicStatisticsService.getN(dataArrX);
			 for (Map.Entry<String, List<Double>> compEntry : variableMap.entrySet()) {
				 Map<String, BiCoDataDTO> resEMap = new HashMap<>();
				 BiCoDataDTO bidata = new BiCoDataDTO();
				 List<Double> compDataList = compEntry.getValue();
				 double[] dataArrY = DataFormatUtil.converToDouble(compDataList);
				 try {
				Double pearsonR =	pearsonRCoefficient(dataArrX, dataArrY);
				Double pearsonT =pearsonTCoefficient(pearsonR, N);
				Double spearmanR = spearmanRCoefficient(dataArrX, dataArrY);
				Double spearmanT =pearsonTCoefficient(spearmanR, N);
				bidata.setPearsonR(pearsonR);
				bidata.setPearsonT(pearsonT);
				bidata.setSpearmanR(spearmanR);
				bidata.setSpearmanT(spearmanT);
				bidata.setN(N);
				bidata.setCovariance(basicStatisticsService.covariance(dataArrX, dataArrY));
				bidata.setCroProSumSq(basicStatisticsService.averageMulSumDeviation(dataArrX, dataArrY));
				} catch (DataErrException e) {
					e.printStackTrace();
					logger.error(varityName +compEntry.getKey() +e.getMessage());
				}
			
				resEMap.put(compEntry.getKey(), bidata) ;
				resDataList.add(resEMap);
				
			}
			resDataMap.put(varityName, resDataList);


		}
			biDTO.setBasicDataMap(basicDataMap);
			 
			biDTO.setResDataMap(resDataMap);
		return biDTO;
	}

}
