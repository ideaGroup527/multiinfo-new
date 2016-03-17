package org.jmu.multiinfo.service.comparemean.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.MathUtils;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.ResultDescDTO;
import org.jmu.multiinfo.dto.comparemean.AnovaCondition;
import org.jmu.multiinfo.dto.comparemean.AnovaDTO;
import org.jmu.multiinfo.dto.comparemean.MedianCondition;
import org.jmu.multiinfo.dto.comparemean.MedianDTO;
import org.jmu.multiinfo.dto.comparemean.ResultDataDTO;
import org.jmu.multiinfo.dto.upload.DataDTO;
import org.jmu.multiinfo.dto.upload.VarietyDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.comparemean.CompareMeanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompareMeanStatisticsServiceImpl implements CompareMeanStatisticsService{

	
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	@Override
	public MedianDTO calMean(MedianCondition condition) {
		MedianDTO resDTO = new MedianDTO();
		Map<String, Map<String, ResultDataDTO>> resDataMap = new HashMap<String, Map<String, ResultDataDTO>>();
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
//			List<Map<String, ResultDataDTO>> value = new ArrayList<Map<String, ResultDataDTO>>();
			Map<String, ResultDataDTO> map = new HashMap<String, ResultDataDTO>();
			//遍历每个因变量
				for (VarietyDTO varietyDTO : dependVarList) {
					PositionBean depVarRange =ExcelUtil.splitRange(varietyDTO.getRange());
					List<Double> dataList = new ArrayList<Double>();
					for (int i = 0; i < si; i++) {
						for (int j = depVarRange.getFirstColId()-1; j < depVarRange.getLastColId(); j++) {
							if(i== data.length) break;
							dataList.add(Double.valueOf((String) data[tmpList.get(i)][j].getData()));
						}
					}
					double[] dataArr = new double[dataList.size()];
					for (int i = 0; i < dataList.size(); i++) {
						dataArr[i] = dataList.get(i);
					}
				ResultDataDTO reDataDTO = new ResultDataDTO();
					ResultDescDTO reDesDataDTO = new ResultDescDTO();
					reDataDTO.setResultData(reDesDataDTO);
					reDesDataDTO.setMedian(basicStatisticsService.median(dataArr));
					reDesDataDTO.setTotal(basicStatisticsService.sum(dataArr));
					reDesDataDTO.setMin(basicStatisticsService.min(dataArr));
					reDesDataDTO.setMax(basicStatisticsService.max(dataArr));
					reDesDataDTO.setVariance(basicStatisticsService.variance(dataArr));
					reDesDataDTO.setKurtosis(basicStatisticsService.kurtosis(dataArr));
					reDesDataDTO.setArithmeticMean(basicStatisticsService.geometricMean(dataArr));
				map.put(varietyDTO.getVarietyName(), reDataDTO );
				resDataMap.put(indep, map);
				}
				
		}

		resDTO.setResDataMap(resDataMap);
		return resDTO;
	}
	
	@Override
	public AnovaDTO anovaStatsForT(final Collection<double[]> categoryData) {
		return anovaStats(categoryData);
	}
	
	@Override
	public ResultDataDTO calOneWayAnova(AnovaCondition anovaCondition) {
		ResultDataDTO resDataDTO = new ResultDataDTO();
Map<String,AnovaDTO>  resMap = new HashMap<String,AnovaDTO>();
		//因变量
		List<VarietyDTO> dependVarList = anovaCondition.getDependentVariable();
		//因子
		VarietyDTO	factorVariable = anovaCondition.getFactorVariable();
		DataDTO[][] data =	anovaCondition.getDataGrid();
		PositionBean 	independentVarRange =ExcelUtil.splitRange( factorVariable.getRange());//自变量范围如A1:A8--
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
		//每个因变量
		for(VarietyDTO dependVar:dependVarList) {
			AnovaDTO resDTO = new AnovaDTO();
			Collection<double[]> categoryData = new ArrayList<double[]>();
			//遍历每个因子,
			for (String indep : indepList) {
				List<Integer> tmpList = indepIndexMap.get(indep);
				int si = tmpList.size();
				PositionBean depVarRange = ExcelUtil.splitRange(dependVar.getRange());
				List<Double> dataList = new ArrayList<Double>();
				for (int i = 0; i < si; i++) {
					for (int j = depVarRange.getFirstColId() - 1; j < depVarRange.getLastColId(); j++) {
						if (i == data.length) break;
						dataList.add(Double.valueOf((String) data[tmpList.get(i)][j].getData()));
					}
				}

				double[] dataArr = new double[dataList.size()];
				for (int i = 0; i < dataList.size(); i++)
					dataArr[i] = dataList.get(i);
				categoryData.add(dataArr);
			}
			resDTO = anovaStats(categoryData);
			resMap.put(dependVar.getVarietyName(),resDTO);
		}

		resDataDTO.setResultData(resMap);
		return resDataDTO;
	}
	
    private AnovaDTO anovaStats(final Collection<double[]> categoryData)
            throws NullArgumentException, DimensionMismatchException {

            MathUtils.checkNotNull(categoryData);

            final Collection<SummaryStatistics> categoryDataSummaryStatistics =
                    new ArrayList<SummaryStatistics>(categoryData.size());

            // convert arrays to SummaryStatistics
            for (final double[] data : categoryData) {
                final SummaryStatistics dataSummaryStatistics = new SummaryStatistics();
                categoryDataSummaryStatistics.add(dataSummaryStatistics);
                for (final double val : data) {
                    dataSummaryStatistics.addValue(val);
                }
            }

            return anovaStats(categoryDataSummaryStatistics, false);

        }
	private AnovaDTO anovaStats(final Collection<SummaryStatistics> categoryData, final boolean allowOneElementData)
			throws NullArgumentException, DimensionMismatchException {
		AnovaDTO anovaDTO = new AnovaDTO();
		MathUtils.checkNotNull(categoryData);

		if (!allowOneElementData) {
			// check if we have enough categories
			if (categoryData.size() < 2) {
				throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED,
						categoryData.size(), 2);
			}

			// check if each category has enough data
			for (final SummaryStatistics array : categoryData) {
				if (array.getN() <= 1) {
					throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED,
							(int) array.getN(), 2);
				}
			}
		}

		int dfwg = 0;
		double sswg = 0;
		double totsum = 0;
		double totsumsq = 0;
		int totnum = 0;

		for (final SummaryStatistics data : categoryData) {

			final double sum = data.getSum();
			final double sumsq = data.getSumsq();
			final int num = (int) data.getN();
			totnum += num;
			totsum += sum;
			totsumsq += sumsq;

			dfwg += num - 1;
			final double ss = sumsq - ((sum * sum) / num);
			sswg += ss;
		}

		final double sst = totsumsq - ((totsum * totsum) / totnum);
		final double ssbg = sst - sswg;
		final int dfbg = categoryData.size() - 1;
		final double msbg = ssbg / dfbg;
		final double mswg = sswg / dfwg;
		final double F = msbg / mswg;
		anovaDTO.setF(F);
		anovaDTO.setSsbg(ssbg);
		anovaDTO.setSst(sst);
		anovaDTO.setSswg(sswg);
		anovaDTO.setMsbg(msbg);
		anovaDTO.setMswg(mswg);
		return anovaDTO;

	}

}
