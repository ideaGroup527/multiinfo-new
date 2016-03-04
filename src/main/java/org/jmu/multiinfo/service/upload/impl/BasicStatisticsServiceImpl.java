package org.jmu.multiinfo.service.upload.impl;

import java.util.Collections;
import java.util.List;

import org.jmu.multiinfo.service.upload.BasicStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class BasicStatisticsServiceImpl implements BasicStatisticsService{

	@Override
	public Double median(List<Double> dataList) {
		Collections.sort(dataList);
		if(dataList.size()%2==0){
			return	(dataList.get(dataList.size()/2-1)+dataList.get(dataList.size()/2))/2;
		}else{
			return dataList.get(dataList.size()/2);
		}
	}

	@Override
	public Double mean(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double max(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double min(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double covariance(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double variance(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double standardDeviation(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double arithmeticMean(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double average(List<Double> dataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
