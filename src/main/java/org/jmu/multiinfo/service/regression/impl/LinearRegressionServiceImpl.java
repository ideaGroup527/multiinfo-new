package org.jmu.multiinfo.service.regression.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.regression.CommonCondition;
import org.jmu.multiinfo.dto.regression.CommonDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.regression.LinearRegressionService;

public class LinearRegressionServiceImpl implements LinearRegressionService{

	@Override
	public CommonDTO calSingleLinearRegression(double[][] data) {
		SimpleRegression regression =new SimpleRegression();
		regression.addData(data);
		return null;
	}

	@Override
	public CommonDTO calOLSMultipleLinearRegression(double[] y, double[][] x) {
		OLSMultipleLinearRegression regression =new  OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		return null;
	}

	@Override
	public CommonDTO calLinearRegression(CommonCondition condition) {
		 VarietyDTO dependVarDTO =	condition.getDependentVariable();
		 List<VarietyDTO> independVarDTOList =	condition.getIndependentVariable();
			DataDTO[][] dataGrid =	 condition.getDataGrid();
			
			
			List<Double> dependVarList = new ArrayList<Double>();
		 PositionBean depvarRange =ExcelUtil.splitRange( dependVarDTO.getRange());
			for (int i = depvarRange.getFirstRowId() - 1; i < depvarRange.getLastRowId(); i++) {
				for (int j = depvarRange.getFirstColId() - 1; j < depvarRange.getLastColId(); j++) {
					dependVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
				}
			}
			 double[][] data = new double[dependVarList.size()][2];
			 double[] y =new double[dependVarList.size()];
for (int i = 0; i < data.length; i++){
	data[i][0] = dependVarList.get(i);
	 y[i] = dependVarList.get(i);
}

		 if(independVarDTOList.size() == 1){
			 List<Double> independVarList = new ArrayList<Double>();
			 VarietyDTO independVarDTO =	 independVarDTOList.get(0);
			 PositionBean indepvarRange =ExcelUtil.splitRange( independVarDTO.getRange());
				for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
					for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
						independVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
					}
				}
				for (int i = 0; i < data.length; i++) 
					data[i][1] = independVarList.get(i);
		return	calSingleLinearRegression(data);
		 }else if(independVarDTOList.size() >1){
				double[][] x = new double[independVarDTOList.size()][dependVarList.size()];
			 for (int k = 0; k < independVarDTOList.size(); k++) {
				 List<Double> independVarList = new ArrayList<Double>();
				 VarietyDTO independVarDTO =	 independVarDTOList.get(k);
				 PositionBean indepvarRange =ExcelUtil.splitRange( independVarDTO.getRange());
					for (int i = indepvarRange.getFirstRowId() - 1; i < indepvarRange.getLastRowId(); i++) {
						for (int j = indepvarRange.getFirstColId() - 1; j < indepvarRange.getLastColId(); j++) {
							independVarList.add(Double.valueOf(dataGrid[i][j].getData().toString()));
						}
					}

					for (int i = 0; i < data.length; i++) 
						x [k][i] = independVarList.get(i);
			}
			 calOLSMultipleLinearRegression(y,x);
			 return null;
		 }else
		return null;
	}

}
