package org.jmu.multiinfo.service.descriptives.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;
import org.jmu.multiinfo.dto.descriptives.PercentileDTO;
import org.jmu.multiinfo.dto.descriptives.ResultDataDTO;
import org.jmu.multiinfo.dto.descriptives.ResultDescDTO;
import org.jmu.multiinfo.dto.descriptives.ResultFrequencyDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.DataVariety;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptivesStatisticsServiceImpl implements DescriptivesStatisticsService {
	@Autowired
	private BasicStatisticsService basicStatisticsService;

	@Override
	public CommonDTO calDesc(PercentileCondition condition) {
		CommonDTO meanDTO = new CommonDTO();
		Map<String, ResultDataDTO> resDataMap = new HashMap<String, ResultDataDTO>();
		List<VarietyDTO> variableList = condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		List<Double> percentileList = condition.getPercentiles();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();

			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(Double.valueOf(dataDTO.getData().toString()));
				}
			}
			double[] dataArr = new double[dataList.size()];
			for (int i = 0; i < dataList.size(); i++) {
				dataArr[i] = dataList.get(i);
			}
			ResultDescDTO retDto = new ResultDescDTO();
			retDto.setMax(basicStatisticsService.max(dataArr));
			retDto.setArithmeticMean(basicStatisticsService.arithmeticMean(dataArr));
			retDto.setMin(basicStatisticsService.min(dataArr));
			retDto.setTotal(basicStatisticsService.sum(dataArr));
			retDto.setKurtosis(basicStatisticsService.kurtosis(dataArr));
			retDto.setVariance(basicStatisticsService.variance(dataArr));
			retDto.setSkewness(basicStatisticsService.skewness(dataArr));
			retDto.setStandardDeviation(basicStatisticsService.standardDeviation(dataArr));
			retDto.setCount(dataArr.length);
			Percentile pt = basicStatisticsService.percentile(dataArr);
			if (percentileList!=null && percentileList.size() > 0) {
				List<PercentileDTO> percentiles = new ArrayList<PercentileDTO>();
				for (int i = 0; i < percentileList.size(); i++) {
					PercentileDTO e = new PercentileDTO();
					Double param = percentileList.get(i);
					e.setParam(param);
					e.setData(pt.evaluate(param));
					percentiles.add(e);
				}
				retDto.setPercentiles(percentiles);
			}
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retDto);
			retDataDTO.setDataArr(dataArr);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		meanDTO.setResDataMap(resDataMap);
		return meanDTO;
	}

	@Override
	public CommonDTO calFrequency(CommonCondition condition) {
		CommonDTO meanDTO = new CommonDTO();
		Map<String, ResultDataDTO> resDataMap = new HashMap<String, ResultDataDTO>();
		List<VarietyDTO> variableList = condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Object> dataList = new ArrayList<Object>();

			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(dataDTO.getData());
				}
			}
			Object[] dataArr = dataList.toArray();
			ResultFrequencyDTO retDto = new ResultFrequencyDTO();
			Map<String, Double> frequencyMap = new HashMap<String, Double>();
			Map<String, Double> percentage = new HashMap<String, Double>();
			Map<String, Double> accumulationPercentage = new HashMap<String, Double>();
			Frequency frequency = basicStatisticsService.frequencyCount(dataArr);

			// 去重
			Set<Object> uniqSet = new HashSet<Object>(dataList);
			List<Object> uniqList = new ArrayList<Object>();
			uniqList.addAll(uniqSet);
			if (varietyDTO.getType() == DataVariety.DATA_TYPE_NUMERIC) {
				Collections.sort(uniqList, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						Double a1;
						Double a2;
						try {
							a1 = Double.valueOf(o1.toString());
							a2 = Double.valueOf(o2.toString());
						} catch (NumberFormatException e) {
							return o1.toString().compareTo(o2.toString());
						}
						return a1.compareTo(a2);
					}
				});
			}

			retDto.setUniqueData(uniqList);
			for (int i = 0; i < uniqList.size(); i++) {
				String key = uniqList.get(i).toString();
				frequencyMap.put(key, (double) frequency.getCount(key));
				percentage.put(key, frequency.getPct(key));
				accumulationPercentage.put(key, frequency.getCumPct(key));
			}

			retDto.setFrequencyMap(frequencyMap);

			retDto.setAccumulationPercentage(accumulationPercentage);
			retDto.setPercentage(percentage);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retDto);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		meanDTO.setResDataMap(resDataMap);
		return meanDTO;
	}

}
