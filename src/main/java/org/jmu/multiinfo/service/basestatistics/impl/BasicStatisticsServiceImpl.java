package org.jmu.multiinfo.service.basestatistics.impl;

import java.math.BigDecimal;

import org.apache.commons.math3.ml.distance.ChebyshevDistance;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.correlation.Covariance;
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
import org.apache.commons.math3.util.FastMath;
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
		if(size == 0 || size != dataArrY.length) throw new DataErrException("cannot resolve for averageMulSumDeviation because diffrent size");
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


	@Override
	public Double sumSquares(double[] dataArr) {
		int size = dataArr.length;
		Double sum = 0.0;
		for (int i = 0; i < size; i++) {
		sum += dataArr[i] * dataArr[i];
		}
		return sum;
	}


	@Override
	public Integer getN(double[] dataArr) {
		if(dataArr == null ) return  0;
		return dataArr.length;
	}


	@Override
	public Double round(Double data, Integer precision) {
		 BigDecimal bd = new BigDecimal(data);  
		return bd.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	@Override
	public Double covariance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		int size = getN(dataArrX);
		if(size == 0  || size != getN(dataArrY)) throw new DataErrException("cannot resolve for covariance because diffrent size");
		if(size < 2) throw new DataErrException("cannot resolve for covariance because to little sample ");
		Covariance co = new Covariance();
		return co.covariance(dataArrX, dataArrY);
	}


	@Override
	public Double euclideanDistance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		int size = getN(dataArrX);
		if(size == 0  || size != getN(dataArrY)) throw new DataErrException("cannot resolve for euclideanDistance because diffrent size");
		EuclideanDistance ed = new EuclideanDistance();
		return ed.compute(dataArrX, dataArrY);
	}


	@Override
	public Double squareEuclideanDistance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		Double e = euclideanDistance(dataArrX, dataArrY);
		return e * e;
	}


	@Override
	public Double chebyshevDistance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		int size = getN(dataArrX);
		if(size == 0  || size != getN(dataArrY)) throw new DataErrException("cannot resolve for chebyshevDistance because diffrent size");
		ChebyshevDistance cd = new ChebyshevDistance();
		return cd.compute(dataArrX, dataArrY);
	}


	@Override
	public Double cityBlockDistance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		return manhattanDistance(dataArrX, dataArrY);
	}


	@Override
	public Double manhattanDistance(double[] dataArrX, double[] dataArrY) throws DataErrException {
		int size = getN(dataArrX);
		if(size == 0  || size != getN(dataArrY)) throw new DataErrException("cannot resolve for manhattanDistance because diffrent size");
		ManhattanDistance md = new ManhattanDistance();
		return md.compute(dataArrX, dataArrY);
	}


	@Override
	public Double minkowskiDistace(double[] dataArrX, double[] dataArrY, Integer p, Integer q) throws DataErrException {
		int size = getN(dataArrX);
		if(size == 0  || size != getN(dataArrY)) throw new DataErrException("cannot resolve for manhattanDistance because diffrent size");
		 Double qqsq = 1.0/q;
		double sum = 0;
	        for (int i = 0; i < size; i++) {
	            sum +=FastMath.pow(FastMath.abs(dataArrX[i] - dataArrY[i]), p) ;
	        }
		return   FastMath.pow(sum,qqsq);
	}


	@Override
	public double[] stepwiseRatio(double[] dataArr) throws DataErrException {
		int size = getN(dataArr)-1;
		if(size <= 0 ) throw new DataErrException("cannot resolve for stepwiseRatio because too little size");
		double[] resDataArr = new double[size];
		for (int i = 0; i < size; i++) {
			resDataArr[i] = dataArr[i] / dataArr[i+1] ;  
		}
		return resDataArr;
	}


	@Override
	public double[] cumulativeSum(double[] dataArr) throws DataErrException {
		int size = getN(dataArr);
		if(size <= 0 ) throw new DataErrException("cannot resolve for cumulativeSum because too little size");
		double[] resDataArr = new double[size];
		double sum = 0.0;
		for (int i = 0; i < size; i++) {
			sum += dataArr[i];
			resDataArr[i] = sum ;  
		}
		return resDataArr;
	}


	@Override
	public double[] averageGeneration(double[] dataArr, double formCoefficient) throws DataErrException {
		int size = getN(dataArr)-1;
		if(size <= 0 ) throw new DataErrException("cannot resolve for averageGeneration because too little size");
		double[] resDataArr = new double[size];
		for (int i = 0; i < size; i++) {
			resDataArr[i] = dataArr[i] * (1-formCoefficient) + dataArr[i+1] * formCoefficient;  
		}
		return resDataArr;
}


	@Override
	public double[] regularization(double[] dataArr) throws DataErrException {
		int size = getN(dataArr);
		if(size < 1 ) throw new DataErrException("cannot resolve for regularization because too little size");
		double[] resData = new double[size];
		for (int i = 0; i < size; i++) {
			Double amin =  min(dataArr);
			Double amax = max(dataArr);
			resData[i] = ( dataArr[i] - amin) / ( amax - amin );
		}
		
		return resData;
	}


	@Override
	public Double max(double[][] dataArr) {
		int size = dataArr.length;
		double[] rowArr = new double[size];
		for (int i = 0; i < dataArr.length; i++) {
			rowArr[i] =	max(dataArr[i]);
		}
		
		return max(rowArr);
	}


	@Override
	public Double min(double[][] dataArr) {
		int size = dataArr.length;
		double[] rowArr = new double[size];
		for (int i = 0; i < dataArr.length; i++) {
			rowArr[i] =	min(dataArr[i]);
		}
		
		return min(rowArr);
	}


	@Override
	public double[][] regularization(double[][] dataArr) throws DataErrException {
		Double amin =  min(dataArr);
		Double amax = max(dataArr);
		
		int rows = dataArr.length;
		int cols = dataArr[0].length;
		if(rows < 1 || cols < 1) throw new DataErrException("cannot resolve for regularization because too little size");
		double[][] resData = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++){
			resData[i][j] = ( dataArr[i][j] - amin) / ( amax - amin );
			}
		}
		
		return resData;
	}

	
}
