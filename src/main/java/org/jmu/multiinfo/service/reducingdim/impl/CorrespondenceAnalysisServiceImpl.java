package org.jmu.multiinfo.service.reducingdim.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.MatrixUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.reducingdim.CorrespAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.CorrespAnalysisDTO;
import org.jmu.multiinfo.dto.reducingdim.PCAEigenvalueDTO;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.reducingdim.CorrespondenceAnalysisService;
import org.jmu.multiinfo.service.reducingdim.PrincipalComponentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrespondenceAnalysisServiceImpl implements CorrespondenceAnalysisService {
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	
	@Autowired
	private PrincipalComponentAnalysisService principalComponentAnalysisService;
	
	@Override
	public double[][] probability(final double[][] dataArr) throws DataErrException {
		int row = dataArr.length;
		int col = dataArr[0].length;
		double[][] proArr = new double[row+1][col+1];
		double sum =0.0;
		for (double[] ds : dataArr) {
			sum+=basicStatisticsService.sum(ds);
		}
		
		for(int i=0;i< row;i++){
			for(int j=0;j< col;j++){
				proArr[i][j] = dataArr[i][j] / sum;
				proArr[row][j] +=  proArr[i][j];
				proArr[i][col] +=  proArr[i][j];
				proArr[row][col] +=  proArr[i][j];
			}
		}
		
		return proArr;
		

//		double[] eigenvalue=	MatrixUtil.eigenvalueForSymmetric(sr);
//		EigenvalueDTO egDTO =	principalComponentAnalysisService.principalComponentAnalysis(sr);
//		egDTO.getSortEigenvalues();
//		return sr;
	}

	@Override
	public double[][] calZMat(final double[][] proArr) throws DataErrException {
		int row = proArr.length - 1;
		int col = proArr[0].length - 1;
		double[][] z = new double[row][col];
		
		for(int i=0;i< row;i++){
			for(int j=0;j< col;j++){
				double up = proArr[i][j] - proArr[i][col] * proArr[row][j];
				double down = FastMath.sqrt(proArr[i][col] * proArr[row][j]);
				z[i][j] = up /down;
			}
		}
		return z;
	}

	@Override
	public double[][] calSR(double[][] z, double[][] zt) throws DataErrException {
		double[][] sr = MatrixUtil.product(zt, z);
		return sr;
	}

	@Override
	public double[][] calZtMat(double[][] z) throws DataErrException {
		double[][] zt = 	MatrixUtil.transpose(MatrixUtil.clone(z));
		return zt;
	}

	@Override
	public double[][] calSQ(double[][] z, double[][] zt) throws DataErrException {
		double[][] sq = MatrixUtil.product(z, zt);
		return sq;
	}

	@Override
	public CorrespAnalysisDTO correspondence(double[][] dataArr) throws DataErrException {
		CorrespAnalysisDTO caDTO = new CorrespAnalysisDTO();
		double[][] proArr = probability(dataArr);
		double[][] z = calZMat(proArr);
		double[][] zt = calZtMat(z);
		double[][] sr = calSR(z, zt);
		double[][] sq = calSQ(z, zt);
		caDTO.setProArr(proArr);
		caDTO.setSq(sq);
		caDTO.setSr(sr);
		caDTO.setZ(z);
		caDTO.setZt(zt);
		return caDTO;
	}

	@Override
	public CorrespAnalysisDTO correspondence(CorrespAnalysisCondition condition)  {
		List<VarietyDTO> independVarList = 	condition.getVariableList();
		DataDTO[][] dataGrid = condition.getDataGrid();
		Map<String, List<Double>> dataMap =	DataFormatUtil.converToDouble(dataGrid, independVarList);
		int indVarSize = independVarList.size();
		List<List<Double>> dataGridList = new ArrayList<>();
		for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
			 List<Double> dataList = entry.getValue();
			 dataGridList.add(dataList);
		}
		double[][] oraArr = DataFormatUtil.transposition(dataGridList);
		CorrespAnalysisDTO caDTO = new CorrespAnalysisDTO();
		EigenvalueDTO	srEdDTO = null;
		EigenvalueDTO	sqEdDTO = null;
		try {
			caDTO = correspondence(oraArr);
			srEdDTO=	principalComponentAnalysisService.principalComponentAnalysis(caDTO.getSr());
			sqEdDTO =	principalComponentAnalysisService.principalComponentAnalysis(caDTO.getSq());
		} catch (DataErrException e) {
			caDTO.setRet_code("-1");
			caDTO.setRet_msg(e.getMessage());
			caDTO.setRet_err(e.getMessage());
		}

		PCAEigenvalueDTO srPcaDTO = 	totalVarExp(condition, indVarSize, srEdDTO);
		srPcaDTO.setSortEigenvalues(srEdDTO.getSortEigenvalues());
		srPcaDTO.setEigTotalInit(srEdDTO.getSortEigenvalues());
		srPcaDTO.setAccEigInit(srEdDTO.getAccEig());
		srPcaDTO.setVarEigInit(srEdDTO.getVarEig());
		srPcaDTO.setEigenvectors(srEdDTO.getEigenvectors());
		
		
		PCAEigenvalueDTO sqPcaDTO = 	totalVarExp(condition, indVarSize,sqEdDTO );
		sqPcaDTO.setSortEigenvalues(sqEdDTO.getSortEigenvalues());
		sqPcaDTO.setEigTotalInit(sqEdDTO.getSortEigenvalues());
		sqPcaDTO.setAccEigInit(sqEdDTO.getAccEig());
		sqPcaDTO.setVarEigInit(sqEdDTO.getVarEig());
		sqPcaDTO.setEigenvectors(sqEdDTO.getEigenvectors());
		

		caDTO.setSrPcaDTO(srPcaDTO);
		caDTO.setSqPcaDTO(sqPcaDTO);
		return caDTO;
	}

	private PCAEigenvalueDTO totalVarExp(CorrespAnalysisCondition condition, int indVarSize, EigenvalueDTO egDTO) {
		double[]  sortEigvalues = egDTO.getSortEigenvalues();
	double[] vareigs =	egDTO.getVarEig();
	double[] acceigs =egDTO.getAccEig();
double[][] oraComArr =	egDTO.getComponentArr();
PCAEigenvalueDTO pcaDTO = new PCAEigenvalueDTO();
		switch (condition.getExtractMethod()) {
		
		case CorrespAnalysisCondition.EXTRACT_METHOD_EIGENVALUE:{
			int tmpSize = 0;
			for (int i = 0; i < sortEigvalues.length; i++) {
				if(sortEigvalues[i] > condition.getEigExtraNum()){
					tmpSize++;
				}else{
					break;
				}
			}
			double[] eigTotalExtra = new double[tmpSize];
			double[] accEigExtra = new double[tmpSize];
			double[] varEigExtra = new double[tmpSize];
			double[][] componentArr = new double[tmpSize][indVarSize];
			for (int i = 0; i < tmpSize; i++) {
				if(sortEigvalues[i] > condition.getEigExtraNum()){
					eigTotalExtra[i] = sortEigvalues[i];
					accEigExtra[i] = acceigs[i];
					varEigExtra[i] = vareigs[i];
					componentArr[i] = oraComArr[i];
				}
			}
			
			pcaDTO.setEigTotalExtra(eigTotalExtra);
			pcaDTO.setAccEigExtra(accEigExtra);
			pcaDTO.setVarEigExtra(varEigExtra);
			pcaDTO.setComponentArr(DataFormatUtil.transposition(componentArr));
			break;
		}
		case CorrespAnalysisCondition.EXTRACT_METHOD_FACTOR:{
			int tmpSize = condition.getFactorExtraNum();
			double[] eigTotalExtra = new double[tmpSize];
			double[] accEigExtra = new double[tmpSize];
			double[] varEigExtra = new double[tmpSize];
			double[][] componentArr = new double[tmpSize][indVarSize];
			for (int i = 0; i < tmpSize; i++) {
					eigTotalExtra[i] = sortEigvalues[i];
					accEigExtra[i] = acceigs[i];
					varEigExtra[i] = vareigs[i];
					componentArr[i] = oraComArr[i];
			}
			
			pcaDTO.setEigTotalExtra(eigTotalExtra);
			pcaDTO.setAccEigExtra(accEigExtra);
			pcaDTO.setVarEigExtra(varEigExtra);
			pcaDTO.setComponentArr(DataFormatUtil.transposition(componentArr));
			break;
		}
		default:
			break;
		}
		return pcaDTO;
	}
}
