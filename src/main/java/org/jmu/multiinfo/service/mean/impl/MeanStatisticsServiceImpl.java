package org.jmu.multiinfo.service.mean.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.mean.MedianCondition;
import org.jmu.multiinfo.dto.mean.MedianDTO;
import org.jmu.multiinfo.dto.mean.ResultDataDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.mean.MeanStatisticsService;
import org.jmu.multiinfo.service.upload.BasicStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeanStatisticsServiceImpl implements MeanStatisticsService{

	
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	@Override
	public MedianDTO calMedian(MedianCondition condition) {
		MedianDTO resDTO = new MedianDTO();
		Map<String, List<Map<String, ResultDataDTO>>> resDataMap = new HashMap<String, List<Map<String, ResultDataDTO>>>();
		//因变量
		List<VarietyDTO> dependVarList = condition.getDependentVariable();
		VarietyDTO	independentVar = condition.getIndependentVariable();
		DataDTO[][] data =	condition.getDataGrid();
		PositionBean 	independentVarRange =ExcelUtil.splitRange( independentVar.getRange());//自变量范围如A1:A8--
		Set<String> indepSet = new HashSet<String>();
		//自变量坐标
		Map<String,List<Integer>> indepIndexMap = new HashMap<String,List<Integer>>();
		for (int i = independentVarRange.getFirstRowId()-1; i < independentVarRange.getLastRowId(); i++) {
			for (int j = independentVarRange.getFirstColId()-1; j < independentVarRange.getLastColId(); j++) {
				indepSet.add(data[i][j].getData().toString());
				List<Integer> tmpList =	indepIndexMap.get(data[i][j].getData().toString());
				if(tmpList==null ||tmpList.isEmpty()) tmpList = new ArrayList<Integer>();
				tmpList.add(i);
				indepIndexMap.put(data[i][j].getData().toString(), tmpList);
			}
		}
		List<String> indepList = new ArrayList<String>(indepSet); 
		
		//遍历每个自变量,
		for (String indep : indepList) {
			List<Integer> tmpList =	indepIndexMap.get(indep);
			int si = tmpList.size();
			List<Map<String, ResultDataDTO>> value = new ArrayList<Map<String, ResultDataDTO>>();
			
			//遍历每个因变量
				for (VarietyDTO varietyDTO : dependVarList) {
					PositionBean depVarRange =ExcelUtil.splitRange(varietyDTO.getRange());
					List<Double> dataList = new ArrayList<Double>();
					for (int i = 0; i < si; i++) {
						for (int j = depVarRange.getFirstColId()-1; j < depVarRange.getLastColId(); j++) {
							if(i== data.length) break;
							dataList.add((Double)data[tmpList.get(i)][j].getData());
						}
					}
				Map<String, ResultDataDTO> map = new HashMap<String, ResultDataDTO>();
				ResultDataDTO reDataDTO = new ResultDataDTO();
				reDataDTO.setResultData(basicStatisticsService.median(dataList));
				map.put(varietyDTO.getVarietyName(), reDataDTO );
					value.add(map);
				}
				resDataMap.put(indep, value);
		}

		resDTO.setResDataMap(resDataMap);
		return resDTO;
	}

}
