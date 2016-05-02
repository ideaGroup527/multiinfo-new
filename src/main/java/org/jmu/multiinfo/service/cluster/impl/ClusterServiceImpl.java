package org.jmu.multiinfo.service.cluster.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.dto.cluster.StepClusterDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.cluster.ClusterService;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterServiceImpl implements ClusterService{
	
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	
	@Autowired
	private CorrelationService correlationService;

	@Override
	public PointGroupDTO pointGroup(PointGroupCondition condition) {
		PointGroupDTO pointDTO = new PointGroupDTO();
		DataDTO[][] dataGrid =	condition.getDataGrid();
		VarietyDTO factorVar =	condition.getFactorVarVariable();
		List<VarietyDTO>  independVarList=	condition.getIndependentVariable();
		Map<String, List<Double>> dataMap =	DataFormatUtil.converToDouble(dataGrid, independVarList);
		List<List<Double>> dataGridList = new ArrayList<>();
		for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
			 List<Double> dataList = entry.getValue();
			 dataGridList.add(dataList);
		}
		
		// 样本号数据
		List<Double> factorVarList = new ArrayList<Double>();
		// 样本号
		PositionBean factorRange = ExcelUtil.splitRange(factorVar.getRange());
		for (int i = factorRange.getFirstRowId() - 1; i < factorRange.getLastRowId(); i++) {
			for (int j = factorRange.getFirstColId() - 1; j < factorRange.getLastColId(); j++) {
				factorVarList.add(DataFormatUtil.converToDouble(dataGrid[i][j]));
			}
		}
		
		
		double[][] dataArr=null;
		switch (condition.getClusterMethod()) {
		case PointGroupCondition.TYPE_Q:{
			//个体间
			 dataArr = DataFormatUtil.transposition(dataGridList);
			break;
		}
		case PointGroupCondition.TYPE_R:{

			
			dataArr = new double[dataGridList.size()][dataGridList.get(0).size()];
			for (int i = 0; i < dataGridList.size(); i++) {
				dataArr[i] =	DataFormatUtil.converToDouble(dataGridList.get(i));
			}
			break;
			}
		default:
			break;
		}
		
		int row = dataArr.length;
		int col = dataArr[0].length;
		
		switch (condition.getNormalizationMethod()) {
		case PointGroupCondition.NORMALIZATION_RANGE:
			for (int i = 0; i < row; i++) {
				try {
					dataArr[i] = basicStatisticsService.regularizationRange(dataArr[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					return pointDTO;
				}
			}
			break;
		case PointGroupCondition.NORMALIZATION_RANGE_SD:
			for (int i = 0; i < row; i++) {
				try {
					dataArr[i] = basicStatisticsService.RangeNormalization(dataArr[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					pointDTO.setRet_err(e.getMessage());
					return pointDTO;
				}
			}
			break;
		case PointGroupCondition.NORMALIZATION_STANDARD_SD:
			for (int i = 0; i < row; i++) {
				try {
					dataArr[i] = basicStatisticsService.StandardDeviationNormalization(dataArr[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					pointDTO.setRet_err(e.getMessage());
					return pointDTO;
				}
			}
			break;
		default:
			break;
		}
		List<StepClusterDTO> stepList = new ArrayList<>();
		switch (condition.getStatisticsMethod()) {
		case PointGroupCondition.STATISTICS_CORRELATION:{
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < row; j++) {
					try {
						if(j == i+1){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = correlationService.pearsonRCoefficient(dataArr[i], dataArr[j]);
						e.setData(data);
						e.setRowIndex(i+1);
						e.setColIndex(j+1);
						stepList.add(e);
						}
					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			break;}
		case PointGroupCondition.STATISTICS_ANGLE_COSINE:{
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < row; j++) {
					try {
						if(j == i+1){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = basicStatisticsService.cos(dataArr[i], dataArr[j]);
						e.setData(data);
						e.setRowIndex(i+1);
						e.setColIndex(j+1);
						stepList.add(e);
						}
					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			break;}
		case PointGroupCondition.STATISTICS_DISTANCE:{
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < row; j++) {
					try {
						if(j == i+1){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = basicStatisticsService.euclideanDistance(dataArr[i], dataArr[j]);
						e.setData(data);
						e.setRowIndex(i+1);
						e.setColIndex(j+1);
						stepList.add(e);
						}
					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			break;}
		default:
			break;
		}
		
		Collections.sort(stepList,new Comparator<StepClusterDTO>() {
			@Override
			public int compare(StepClusterDTO o1, StepClusterDTO o2) {
				return o1.getData().compareTo(o2.getData());
			}
		});
		pointDTO.setFactorVarList(factorVarList);
		pointDTO.setStepList(stepList);
		
		return pointDTO;
	}

}
