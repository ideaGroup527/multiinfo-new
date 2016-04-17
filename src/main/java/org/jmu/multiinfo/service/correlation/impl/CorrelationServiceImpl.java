package org.jmu.multiinfo.service.correlation.impl;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrelationServiceImpl implements CorrelationService {
	@Autowired
private BasicStatisticsService basicStatisticsService;
	@Override
	public Double pearsonRCoefficient(double[] dataArrX, double[] dataArrY) throws DataErrException {

		Double lxy = basicStatisticsService.averageMulSumDeviation(dataArrX,dataArrY);
		Double lxx = basicStatisticsService.averageSumDeviation(dataArrX);
		Double lyy = basicStatisticsService.averageSumDeviation(dataArrY);
		Double r = lxy / ( Math.sqrt( lxx * lyy) );
		return r;
	}
	@Override
	public Double pearsonTCoefficient(Double r, Double n) {
		Double tc = r * Math.sqrt(n-2) / Math.sqrt(1 - r * r);
		return tc;
	}

}
