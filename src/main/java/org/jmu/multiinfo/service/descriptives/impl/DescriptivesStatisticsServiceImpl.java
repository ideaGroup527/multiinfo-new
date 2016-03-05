package org.jmu.multiinfo.service.descriptives.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.descriptives.ResultDataDTO;
import org.jmu.multiinfo.dto.descriptives.CommonCondition;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.ResultDescDTO;
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
	public CommonDTO calMean(CommonCondition condition) {
		CommonDTO meanDTO = new CommonDTO();
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
			ResultDescDTO retDto = new ResultDescDTO();
			retDto.setMax(basicStatisticsService.max(dataList));
			retDto.setArithmeticMean(basicStatisticsService.arithmeticMean(dataList));
			retDto.setMin(basicStatisticsService.min(dataList));
			retDto.setTotal(basicStatisticsService.sum(dataList));
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retDto);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		meanDTO.setResDataMap(resDataMap);
		return meanDTO;
	}

}
