package org.jmu.multiinfo.service.descriptives.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.comparemean.ResultDataDTO;
import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.MeanDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.jmu.multiinfo.service.upload.BasicStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptivesStatisticsServiceImpl implements DescriptivesStatisticsService{
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	@Override
	public MeanDTO calMean(CommonCondition condition) {
		MeanDTO meanDTO = new MeanDTO();
		Map<String,ResultDataDTO> resDataMap = new HashMap<String,ResultDataDTO>();
		List<VarietyDTO> variableList =	condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();
			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange =ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(Double.valueOf(dataDTO.getData().toString()));
				}
			}
			Double retMean = basicStatisticsService.arithmeticMean(dataList);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retMean);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		meanDTO.setResDataMap(resDataMap);
		return meanDTO;
	}
	@Override
	public CommonDTO calMax(CommonCondition condition) {
		CommonDTO maxDTO = new CommonDTO();
		Map<String,ResultDataDTO> resDataMap = new HashMap<String,ResultDataDTO>();
		List<VarietyDTO> variableList =	condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();
			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange =ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(Double.valueOf(dataDTO.getData().toString()));
				}
			}
			Double retMean = basicStatisticsService.max(dataList);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retMean);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		maxDTO.setResDataMap(resDataMap);
		return maxDTO;
	
	}
	@Override
	public CommonDTO calMin(CommonCondition condition) {
		CommonDTO minDTO = new CommonDTO();
		Map<String,ResultDataDTO> resDataMap = new HashMap<String,ResultDataDTO>();
		List<VarietyDTO> variableList =	condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();
			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange =ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(Double.valueOf(dataDTO.getData().toString()));
				}
			}
			Double retMean = basicStatisticsService.min(dataList);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retMean);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		minDTO.setResDataMap(resDataMap);
		return minDTO;
	}
	@Override
	public CommonDTO calSum(CommonCondition condition) {
		CommonDTO sumDTO = new CommonDTO();
		Map<String,ResultDataDTO> resDataMap = new HashMap<String,ResultDataDTO>();
		List<VarietyDTO> variableList =	condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();
			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange =ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(Double.valueOf(dataDTO.getData().toString()));
				}
			}
			Double retMean = basicStatisticsService.sum(dataList);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retMean);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		sumDTO.setResDataMap(resDataMap);
		return sumDTO;
	}

}
