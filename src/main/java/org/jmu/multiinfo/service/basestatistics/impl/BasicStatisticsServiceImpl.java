package org.jmu.multiinfo.service.basestatistics.impl;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Min;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.stat.ranking.TiesStrategy;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class BasicStatisticsServiceImpl implements BasicStatisticsService{

	@Override
	public Double median(double[] dataArr) {
		Median median = new Median();
		return median.evaluate(dataArr);
	}


	@Override
	public Double max(double[] dataArr) {
		Max max = new Max();
		return max.evaluate(dataArr);
	}

	@Override
	public Double min(double[] dataArr) {
		Min min = new Min();
		return min.evaluate(dataArr);
	}


	@Override
	public Double variance(double[] dataArr) {
		Variance variance = new Variance();
		return variance.evaluate(dataArr);
	}

	@Override
	public Double standardDeviation(double[] dataArr) {
		StandardDeviation StandardDeviation = new StandardDeviation();//标准差  
		return StandardDeviation.evaluate(dataArr);
	}

	@Override
	public Double arithmeticMean(double[] dataArr) {
		Mean mean = new Mean();
		return mean.evaluate(dataArr);
	}

	@Override
	public Double average(double[] dataArr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double sum(double[] dataArr) {
		Sum sum = new Sum();
		return sum.evaluate(dataArr);
	}

	@Override
	public Double skewness(double[] dataArr) {
		 Skewness skewness = new Skewness();
		return skewness.evaluate(dataArr);
	}


	@Override
	public Double geometricMean(double[] dataArr) {
		GeometricMean geoMean = new GeometricMean();
		return geoMean.evaluate(dataArr);
	}


	@Override
	public Double kurtosis(double[] dataArr) {
		  Kurtosis kurtosis = new Kurtosis(); // Kurtosis,峰度  
		return kurtosis.evaluate(dataArr);
	}

	@Override
	public Percentile percentile(double[] dataArr) {
		Percentile p = new Percentile();
		p.setData(dataArr);
		return p;
	}

	@Override
	public Frequency frequencyCount(Object[] dataArr) {
		Frequency freq = new Frequency();  
		for (int i = 0; i < dataArr.length; i++) {
			freq.addValue(dataArr[i].toString());
		}
		return freq;
	}
	
	@Override
	public Frequency frequencyCount(double[] dataArr) {
		Frequency freq = new Frequency();  
		for (int i = 0; i < dataArr.length; i++) {
			freq.addValue(dataArr[i]);
		}
		return freq;
	}


	@Override
	public Double zeroPercentile(Percentile pt) {
		Double q3 = pt.evaluate(25.0);
		Double q1 = pt.evaluate(75.0);
		return q3+(q3-q1)*1.5;
	}


	@Override
	public Double fullPercentile(Percentile pt) {
		Double q3 = pt.evaluate(25.0);
		Double q1 = pt.evaluate(75.0);
		return q1-(q3-q1)*1.5;
	}


	@Override
	public Double averageSumDeviation(double[] dataArr) throws DataErrException {
		return averageMulSumDeviation(dataArr,dataArr);
	}


	@Override
	public Double averageMulSumDeviation(double[] dataArrX, double[] dataArrY) throws DataErrException {
		int size = dataArrX.length;
		if(size == 0 || size != dataArrY.length) throw new DataErrException("cannot resolve for averageMulSumDeviation");
		Double xmean = arithmeticMean(dataArrX);		
		Double ymean = arithmeticMean(dataArrY);	
		Double sumDeviation= 0.0;
		for( int i = 0 ; i < size ; i++ ){
			Double deviationMul = (dataArrX[i]-xmean) * (dataArrY[i] - ymean);
			sumDeviation += deviationMul;
		}
		return sumDeviation;
	}


	@Override
	public double[] rank(double[] dataArr) {
		NaturalRanking nr = new NaturalRanking(NaNStrategy.MINIMAL,TiesStrategy.MAXIMUM);
		return nr.rank(dataArr);
	}


	@Override
	public double[] rankAve(double[] dataArr) {
		NaturalRanking nr = new NaturalRanking(NaNStrategy.MAXIMAL,TiesStrategy.AVERAGE);
		return nr.rank(dataArr);
	}

}
