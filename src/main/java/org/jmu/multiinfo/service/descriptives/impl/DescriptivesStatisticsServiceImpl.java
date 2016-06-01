package org.jmu.multiinfo.service.descriptives.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.OneVarCondition;
import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.descriptives.CommonDTO;
import org.jmu.multiinfo.dto.descriptives.KSTestDTO;
import org.jmu.multiinfo.dto.descriptives.PercentileCondition;
import org.jmu.multiinfo.dto.descriptives.PercentileDTO;
import org.jmu.multiinfo.dto.descriptives.ResultDataDTO;
import org.jmu.multiinfo.dto.descriptives.ResultFrequencyDTO;
import org.jmu.multiinfo.dto.descriptives.UpDownDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.basestatistics.DistributionService;
import org.jmu.multiinfo.service.descriptives.DescriptivesStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptivesStatisticsServiceImpl implements DescriptivesStatisticsService {
	@Autowired
	private BasicStatisticsService basicStatisticsService;

	@Autowired
	private DistributionService distributionService;
	
	@Override
	public CommonDTO calDesc(PercentileCondition condition) {
		CommonDTO meanDTO = new CommonDTO();
		Map<String, ResultDataDTO> resDataMap = new LinkedHashMap<String, ResultDataDTO>();
		List<VarietyDTO> variableList = condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		List<Double> percentileList = condition.getPercentiles();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Double> dataList = new ArrayList<Double>();
			List<Double> errPercentiles  = new ArrayList<Double>();
			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(DataFormatUtil.converToDouble(dataDTO));
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
			retDto.setMedian(basicStatisticsService.median(dataArr));
			Percentile pt = basicStatisticsService.percentile(dataArr);
			
			if (percentileList!=null && percentileList.size() > 0) {
				List<PercentileDTO> percentiles = new ArrayList<PercentileDTO>();
				
				PercentileDTO minp = new PercentileDTO();
				minp.setParam(0.0);
				minp.setData(basicStatisticsService.zeroPercentile(pt));
				percentiles.add(minp);
				for (int i = 0; i < percentileList.size(); i++) {
					PercentileDTO e = new PercentileDTO();
					Double param = percentileList.get(i);
					e.setParam(param);
					e.setData(pt.evaluate(param));
					percentiles.add(e);
				}

				PercentileDTO maxp = new PercentileDTO();
				maxp.setParam(100.0);
				maxp.setData(basicStatisticsService.fullPercentile(pt));
				percentiles.add(maxp);

				retDto.setPercentiles(percentiles);
				
				for (int i = 0; i < dataList.size(); i++) {
					Double each = dataList.get(i);
					if(each >maxp.getData() || each < minp.getData()){
						errPercentiles.add(each);
					}
				}
				retDto.setErrPercentiles(errPercentiles);
				
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
	public CommonDTO calFrequency(OneVarCondition condition) {
		CommonDTO meanDTO = new CommonDTO();
		Map<String, ResultDataDTO> resDataMap = new LinkedHashMap<String, ResultDataDTO>();
		List<VarietyDTO> variableList = condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
			List<Object> dataList = new ArrayList<Object>();

			VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
			PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
			for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
				for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
					DataDTO dataDTO = dataGrid[i][j];
					dataList.add(dataDTO);
				}
			}
			
			Frequency frequency = null;
			ResultFrequencyDTO retDto = new ResultFrequencyDTO();
			Map<String, Long> frequencyMap = new HashMap<String, Long>();
			Map<String, Double> percentage = new HashMap<String, Double>();
			Map<String, Double> validatePercentage = new HashMap<String, Double>();
			Map<String, Double> accumulationPercentage = new HashMap<String, Double>();
			Double sumPercentage = 0.0;
			Double sumValidatePercentage = 0.0;
			try {
				double[] dataArr  = new double[dataList.size()];
				for (int i = 0; i < dataArr.length; i++) {
					dataArr[i] =DataFormatUtil.converToDouble((DataDTO)dataList.get(i));
				}
				frequency = basicStatisticsService.frequencyCount(dataArr);
				retDto.setArithmeticMean(basicStatisticsService.arithmeticMean(dataArr));
				retDto.setStandardDeviation(basicStatisticsService.standardDeviation(dataArr));
				retDto.setMin(basicStatisticsService.min(dataArr));
				retDto.setMax(basicStatisticsService.max(dataArr));
				retDto.setCount(dataArr.length);
				retDto.setTotal(basicStatisticsService.sum(dataArr));
				retDto.setKsTest(calNormalDistribution(dataArr));
			} catch (NumberFormatException e) {
				Object[] dataArr = new Object[dataList.size()];
				for (int i = 0; i < dataArr.length; i++) {
					dataArr[i] =DataFormatUtil.converToObject((DataDTO)dataList.get(i));
				}
				frequency = basicStatisticsService.frequencyCount(dataArr );
				retDto.setCount(dataArr.length);
			}

			List<Object> uniqList = new ArrayList<Object>();
			Iterator<Entry<Comparable<?>, Long>>  it =	frequency.entrySetIterator();
			boolean isnum =true;
			while (it.hasNext()) {
				Map.Entry<java.lang.Comparable<?>, java.lang.Long> entry = (Map.Entry<java.lang.Comparable<?>, java.lang.Long>) it
						.next();
				uniqList.add(entry.getKey().toString());
				try {
					Double.parseDouble(entry.getKey().toString());
				} catch (NumberFormatException e) {
					isnum=false;
				}
				frequencyMap.put(entry.getKey().toString(), entry.getValue() );
				Double pct = frequency.getPct(entry.getKey())  * 100 ;
				sumPercentage += pct;
				percentage.put(entry.getKey().toString(), pct);
				
				sumValidatePercentage += pct;
				validatePercentage.put(entry.getKey().toString(), pct);
				
				Double cumpct = frequency.getCumPct(entry.getKey())  * 100;
				accumulationPercentage.put(entry.getKey().toString(), cumpct);
			}
		List<UpDownDTO> interList = new ArrayList<>();
		//如果为数组计算组距
		if(isnum){
			int uniqSize = uniqList.size();
		double[] uniqArr = new double[uniqSize];
		for (int i = 0; i < uniqArr.length; i++) {
			uniqArr[i] = DataFormatUtil.converToDouble(uniqList.get(i).toString());
		}
		double max = basicStatisticsService.max(uniqArr);
		double min=	basicStatisticsService.min(uniqArr);
		double range = max-min;
		int groupCount = 6;
		double interval=	FastMath.ceil(range / groupCount );
		Double down = uniqArr[0] - interval / 2;
		int uniqPos= 0;
		for (int i = 0; i <groupCount; i++) {
			UpDownDTO e = new UpDownDTO();
			Double up = down+interval;
			e.setUp(up);
			e.setDown(down);
			long eFrequency = 0;
while(uniqPos<uniqArr.length && uniqArr[uniqPos]<up) {
	eFrequency+=Long.valueOf(frequencyMap.get(String.valueOf(uniqArr[uniqPos]) ).toString());
	uniqPos++;
}
e.setFrequency(eFrequency);	
			
			down=up;
			interList.add(e );
		}
		}
		
		
						
/*			if (varietyDTO.getType() == DataVariety.DATA_TYPE_NUMERIC) {
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
			}*/
			retDto.setSumPercentage(basicStatisticsService.round(sumPercentage, 3));
			retDto.setSumValidatePercentage(basicStatisticsService.round(sumValidatePercentage, 3));
			retDto.setSumFreq(frequency.getSumFreq());
			retDto.setUniqueData(uniqList);

			retDto.setFrequencyMap(frequencyMap);
			retDto.setValidatePercentage(validatePercentage);
			retDto.setAccumulationPercentage(accumulationPercentage);
			retDto.setPercentage(percentage);
			retDto.setIsNum(isnum);
			retDto.setUniqueInterval(interList);
			ResultDataDTO retDataDTO = new ResultDataDTO();
			retDataDTO.setResultData(retDto);
			resDataMap.put(varietyDTO.getVarietyName(), retDataDTO);
		}
		meanDTO.setResDataMap(resDataMap);
		return meanDTO;
	}
	
	@Override
	public KSTestDTO calNormalDistribution(double[] data){
		KSTestDTO ksdto = new KSTestDTO();
	Double mean =	basicStatisticsService.arithmeticMean(data);
	ksdto.setMean(mean);
	Double sd = basicStatisticsService.standardDeviation(data);
	ksdto.setSd(sd);
	Frequency f =  basicStatisticsService.frequencyCount(data);
	int n = data.length;
	ksdto.setN(Long.valueOf(n));
	double fcount = 0.0;
	List<Double> DList = new ArrayList<Double>();
	Iterator<Entry<Comparable<?>, Long>>  it =	f.entrySetIterator();
	while (it.hasNext()) {
		Map.Entry<java.lang.Comparable<?>, java.lang.Long> entry = (Map.Entry<java.lang.Comparable<?>, java.lang.Long>) it
				.next();
		double x = DataFormatUtil.converToDouble(entry.getKey().toString());
		//标准化值
		double z = (x-mean)/sd;
		//理论分布值
		double f0 = distributionService.normalDistribution(z).getCumulativeProbability();
		fcount = fcount + DataFormatUtil.converToDouble(entry.getValue().toString());
		double fn = fcount / n;
		double D = fn - f0;
		DList.add(D);
		
	}
	
	ksdto.setPositive(Collections.max(DList));
	ksdto.setNegative(Collections.min(DList));
	ksdto.setAbsolute((FastMath.abs(ksdto.getPositive()) >FastMath.abs(ksdto.getNegative())?FastMath.abs(ksdto.getPositive()):Math.abs(ksdto.getNegative())));
	ksdto.setKsz(FastMath.sqrt(n)*ksdto.getAbsolute());
	return ksdto;
	}

}
