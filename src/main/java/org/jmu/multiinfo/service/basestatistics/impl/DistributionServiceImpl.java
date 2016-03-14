package org.jmu.multiinfo.service.basestatistics.impl;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.jmu.multiinfo.dto.basestatistics.NormalDistributionDTO;
import org.jmu.multiinfo.service.basestatistics.DistributionService;
import org.springframework.stereotype.Service;
@Service
public class DistributionServiceImpl implements DistributionService{
	/***
	 * 
	 */
    public static final double DEFAULT_INVERSE_ABSOLUTE_ACCURACY = 1e-9;
	@Override
	public NormalDistributionDTO normalDistribution(double mean, double sd, double inverseCumAccuracy,double x) {
		NormalDistributionDTO normalDto = new NormalDistributionDTO();
		NormalDistribution normal = new NormalDistribution(mean,sd,inverseCumAccuracy);
		double density = normal.density(x);
		double cumulativeProbability =	normal.cumulativeProbability(x);
		double probability = 	normal.probability(x);
		normalDto.setCumulativeProbability(cumulativeProbability);
		normalDto.setMean(mean);
		normalDto.setDensity(density);
		normalDto.setSd(sd);
		normalDto.setProbability(probability);
		return normalDto;
	}

	@Override
	public NormalDistributionDTO normalDistribution(double mean, double sd,double x) {
		return normalDistribution(mean,sd,DEFAULT_INVERSE_ABSOLUTE_ACCURACY,x);
	}
	
	@Override
	public NormalDistributionDTO normalDistribution(double x) {
		return normalDistribution(0,1,DEFAULT_INVERSE_ABSOLUTE_ACCURACY,x);
	}

}
