package org.jmu.multiinfo.service.regression.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.regression.CommonCondition;
import org.jmu.multiinfo.dto.regression.CommonDTO;
import org.jmu.multiinfo.dto.regression.MultipleLinearDTO;
import org.jmu.multiinfo.dto.regression.SingleLinearDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.regression.LinearRegressionService;
import org.springframework.stereotype.Service;
@Service
public class LinearRegressionServiceImpl implements LinearRegressionService {

	@Override
	public CommonDTO calSingleLinearRegression(double[][] data) {
		SingleLinearDTO linearDTO = new SingleLinearDTO();
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		RegressionResults  results =	regression.regress(); 
		linearDTO.setIntercept(regression.getIntercept());
		linearDTO.setN(regression.getN());
		linearDTO.setSlope(regression.getSlope());
		linearDTO.setRSquare(regression.getRSquare());
		linearDTO.setRegressionSumSquares(regression.getRegressionSumSquares());
		linearDTO.setSumSquaredErrors(regression.getSumSquaredErrors());
		linearDTO.setXSumSquares(regression.getXSumSquares());
		linearDTO.setR(regression.getR());
		linearDTO.setMeanSquareError(regression.getMeanSquareError());
		linearDTO.setSlopeStdErr(	regression.getSlopeStdErr());
		linearDTO.setSignificance(regression.getSignificance());
		linearDTO.setSlopeConfidenceInterval(regression.getSlopeConfidenceInterval());
		linearDTO.setAdjustedRSquared(	results.getAdjustedRSquared());
		linearDTO.setRegressionParameters(results.getParameterEstimates());
		linearDTO.setRegressionParametersStandardErrors(results.getStdErrorOfEstimates());
		double[] predict = new double[2];
		predict[0] = regression.predict(0);
		predict[1] = regression.predict(1);
		linearDTO.setPredict(predict);
		
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
		return linearDTO;
	}

	@Override
	public CommonDTO calLinearRegression(CommonCondition condition) {
		VarietyDTO dependVarDTO = condition.getDependentVariable();
		List<VarietyDTO> independVarDTOList = condition.getIndependentVariable();
		DataDTO[][] dataGrid = condition.getDataGrid();

		//因变量数据
		List<Double> dependVarList = new ArrayList<Double>();
		PositionBean depvarRange = ExcelUtil.splitRange(dependVarDTO.getRange());
		for (int i = depvarRange.getFirstRowId() - 1; i < depvarRange.getLastRowId(); i++) {
			for (int j = depvarRange.getFirstColId() - 1; j < depvarRange.getLastColId(); j++) {
				dependVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
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
					independVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
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
			
			//每个自变量
			double[][] x = new double[independVarDTOList.size()][dependVarList.size()];
			for (int k = 0; k < independVarDTOList.size(); k++) {
				List<Double> independVarList = new ArrayList<Double>();
				VarietyDTO independVarDTO = independVarDTOList.get(k);
				PositionBean indepvarRange = ExcelUtil.splitRange(independVarDTO.getRange());
				for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
					for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
						independVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
					}
				}

				for (int i = 0; i < independVarList.size(); i++)
					x[k][i] = independVarList.get(i);
			}
			return calOLSMultipleLinearRegression(y, x);
		} else
			return null;
	}

}
