package org.jmu.multiinfo.service.regression.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.BiVarCondition;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.regression.CommonDTO;
import org.jmu.multiinfo.dto.regression.GraphDTO;
import org.jmu.multiinfo.dto.regression.GraphDataDTO;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinearRegressionServiceImpl implements LinearRegressionService {
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	@Override
	public SingleLinearDTO calSingleLinearRegression(double[][] data) {
		SingleLinearDTO linearDTO = new SingleLinearDTO();
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);

		linearDTO.setIntercept(regression.getIntercept());
		linearDTO.setN(regression.getN());
		linearDTO.setSlope(regression.getSlope());
		linearDTO.setRSquare(regression.getRSquare());
		linearDTO.setRegressionSumSquares(regression.getRegressionSumSquares());
		linearDTO.setSumSquaredErrors(regression.getSumSquaredErrors());
		linearDTO.setXSumSquares(regression.getXSumSquares());
		linearDTO.setR(regression.getR());
		linearDTO.setMeanSquareError(regression.getMeanSquareError());
		linearDTO.setSlopeStdErr(regression.getSlopeStdErr());
		linearDTO.setSignificance(regression.getSignificance());
		linearDTO.setSlopeConfidenceInterval(regression.getSlopeConfidenceInterval());
		RegressionResults results = regression.regress();
		
		linearDTO.setAdjustedRSquared(results.getAdjustedRSquared());
		double[] regressionParameters = results.getParameterEstimates();
		double[] regressionParametersStandardErrors = results.getStdErrorOfEstimates();
		double[] ttests = new double[regressionParameters.length];
		for (int i = 0; i < ttests.length; i++) {
			ttests[i] = regressionParameters[i] / regressionParametersStandardErrors[i];
		}

		//		linearDTO.setRegressionStandardError(regressionStandardError);
		linearDTO.setTotalSumOfSquares(linearDTO.getRegressionSumSquares() + linearDTO.getSumSquaredErrors());
		linearDTO.setRegressionParameters(regressionParameters);
		linearDTO.setRegressionParametersStandardErrors(regressionParametersStandardErrors);
		double[] predict = new double[2];
		predict[0] = regression.predict(0);
		predict[1] = regression.predict(1);
		linearDTO.setPredict(predict);
		linearDTO.setTtests(ttests);
		
		
		linearDTO.setF((linearDTO.getRSquare() / (regressionParameters.length - 1))
				/ ((1 - linearDTO.getRSquare()) / (data.length - regressionParameters.length)));
		return linearDTO;
	}

	@Override
	public CommonDTO calOLSMultipleLinearRegression(double[] y, double[][] x) {
		MultipleLinearDTO linearDTO = new MultipleLinearDTO();
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		double[] regressionParameters = regression.estimateRegressionParameters();
		double[] regressionParametersStandardErrors = regression.estimateRegressionParametersStandardErrors();
		double[] residuals = regression.estimateResiduals();
		double[] ttests = new double[regressionParameters.length];
		for (int i = 0; i < ttests.length; i++) {
			ttests[i] = regressionParameters[i] / regressionParametersStandardErrors[i];
		}
		linearDTO.setAdjustedRSquared(regression.calculateAdjustedRSquared());
		linearDTO.setRSquared(regression.calculateRSquared());
		linearDTO.setTotalSumOfSquares(regression.calculateTotalSumOfSquares());
		linearDTO.setErrorVariance(regression.estimateErrorVariance());
		linearDTO.setRegressandVariance(regression.estimateRegressandVariance());
		linearDTO.setRegressionStandardError(regression.estimateRegressionStandardError());
		linearDTO.setResidualSumOfSquares(regression.calculateResidualSumOfSquares());

		linearDTO.setRegressionParameters(regressionParameters);
		linearDTO.setRegressionParametersStandardErrors(regressionParametersStandardErrors);
		linearDTO.setResiduals(residuals);
		linearDTO.setTtests(ttests);

		linearDTO.setF((linearDTO.getRSquared() / (regressionParameters.length - 1))
				/ ((1 - linearDTO.getRSquared()) / (y.length - regressionParameters.length)));
		return linearDTO;
	}

	@Override
	public CommonDTO calLinearRegression(BiVarCondition condition) {
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

		if (independVarDTOList.size() == 1) {
			double[][] data = new double[dependVarList.size()][2];

			for (int i = 0; i < data.length; i++) {
				data[i][1] = dependVarList.get(i);
			}
			List<Double> independVarList = new ArrayList<Double>();
			VarietyDTO independVarDTO = independVarDTOList.get(0);
			PositionBean indepvarRange = ExcelUtil.splitRange(independVarDTO.getRange());
			for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
				for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
					independVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
				}
			}
			for (int i = 0; i < data.length; i++)
				data[i][0] = independVarList.get(i);
			return calSingleLinearRegression(data);
		} else if (independVarDTOList.size() > 1) {
			double[] y = new double[dependVarList.size()];
			for (int i = 0; i < dependVarList.size(); i++) {
				y[i] = dependVarList.get(i);
			}

			// 每个自变量
			double[][] x = new double[dependVarList.size()][independVarDTOList.size()];
			
			for (int k = 0; k < independVarDTOList.size(); k++) {
				VarietyDTO independVarDTO = independVarDTOList.get(k);
				PositionBean indepvarRange = ExcelUtil.splitRange(independVarDTO.getRange());
				int row = 0;
				for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
					for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
						x[row++][k] = DataFormatUtil.converToDouble(dataGrid[i][j]);
					}
				}
				
			}
			return calOLSMultipleLinearRegression(y, x);
		} else
			return null;
	}

	@Override
	public GraphDTO convertForGraph(BiVarCondition condition) {
		GraphDTO g = new GraphDTO();
		VarietyDTO dependVarDTO = condition.getDependentVariable();
		List<VarietyDTO> independVarDTOList = condition.getIndependentVariable();
		DataDTO[][] dataGrid = condition.getDataGrid();
		Map<String, GraphDataDTO> resDataMap = new LinkedHashMap<String, GraphDataDTO>();
		// 因变量数据
		List<Double> dependVarList = new ArrayList<Double>();
		GraphDataDTO gdata = new GraphDataDTO();
		PositionBean depvarRange = ExcelUtil.splitRange(dependVarDTO.getRange());
		for (int i = depvarRange.getFirstRowId() - 1; i < depvarRange.getLastRowId(); i++) {
			for (int j = depvarRange.getFirstColId() - 1; j < depvarRange.getLastColId(); j++) {
				dependVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
			}
		}
		gdata.setData(dependVarList);
		double[] dataArr = new double[dependVarList.size()];
		for (int i = 0; i < dataArr.length; i++) {
			dataArr[i] = dependVarList.get(i);
		}
	
		gdata.setMin(basicStatisticsService.min(dataArr) );
		gdata.setMax(basicStatisticsService.max(dataArr) );
		gdata.setMean(basicStatisticsService.arithmeticMean(dataArr) );
		resDataMap.put(dependVarDTO.getVarietyName(), gdata);
		
		for (int k = 0; k < independVarDTOList.size(); k++) {
			List<Double> independVarList = new ArrayList<Double>();
			GraphDataDTO igdata = new GraphDataDTO();
			VarietyDTO independVarDTO = independVarDTOList.get(k);
			PositionBean indepvarRange = ExcelUtil.splitRange(independVarDTO.getRange());
			for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
				for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
					independVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
				}
			}
			dataArr = new double[independVarList.size()];
			for (int i = 0; i < dataArr.length; i++) {
				dataArr[i] = independVarList.get(i);
			}
			igdata.setData(independVarList);
			igdata.setMin(basicStatisticsService.min(dataArr) );
			igdata.setMax(basicStatisticsService.max(dataArr) );
			igdata.setMean(basicStatisticsService.arithmeticMean(dataArr) );
			resDataMap.put(independVarDTO.getVarietyName(), igdata);
		}
		
		
		g.setDependentVariable(dependVarDTO);
		g.setIndependentVariable(independVarDTOList);


		
		
		g.setResDataMap(resDataMap );
		return g;
		
	}

}
