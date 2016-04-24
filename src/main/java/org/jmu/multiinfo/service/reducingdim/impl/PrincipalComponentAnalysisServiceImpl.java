package org.jmu.multiinfo.service.reducingdim.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.dto.EigenvalueDTO;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.MatrixUtil;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.correlation.BiCoDataDTO;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateCondition;
import org.jmu.multiinfo.dto.correlation.BivariateCorrelateDTO;
import org.jmu.multiinfo.dto.reducingdim.PCAEigenvalueDTO;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.basestatistics.MatrixStatisticsService;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.jmu.multiinfo.service.reducingdim.PrincipalComponentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrincipalComponentAnalysisServiceImpl implements PrincipalComponentAnalysisService {
@Autowired
private MatrixStatisticsService matrixStatisticsService;

@Autowired
private BasicStatisticsService basicStatisticsService;

@Autowired
private CorrelationService correlationService;
	@Override
	public double[] principalComponentCoefficient(double[] eigenvector, double lambda) {
		double[] eigvecCoef = new double[eigenvector.length];
		for (int i = 0; i < eigvecCoef.length; i++) {
			eigvecCoef[i] = FastMath.sqrt(lambda) * eigenvector[i];
		}
		
		return eigvecCoef;
	}
	@Override
	public EigenvalueDTO principalComponentAnalysis(double[][] dataArr) throws DataErrException {
		EigenvalueDTO eigDTO =new EigenvalueDTO();
			eigDTO =	matrixStatisticsService.eigenvector(dataArr);
			double[] sortEigenvalues = eigDTO.getSortEigenvalues();
			
			//方差贡献率
			double[] vareig = varianceContribution(sortEigenvalues);
			eigDTO.setVarEig(vareig);
			//累积贡献率
			double[] accEig = accumulationContribution(sortEigenvalues);
			eigDTO.setAccEig(accEig);
			double[][] eigenvectors = eigDTO.getEigenvectors();
			for (int i = 0; i < sortEigenvalues.length; i++) {
				eigenvectors[i] =	principalComponentCoefficient(eigenvectors[i],sortEigenvalues[i]);
				
			}
			eigDTO.setComponentArr(eigenvectors.clone());
			eigDTO.setEigenvectors(eigenvectors);

		return eigDTO;
		
	}
	@Override
	public double[] varianceContribution(double[] eigenvectors) {
	double sum =	basicStatisticsService.sum(eigenvectors);
	double[] vareig = new double[eigenvectors.length];
	for (int i = 0; i < eigenvectors.length; i++) {
		vareig[i] = eigenvectors[i]/sum * 100;
		
	}
		return vareig;
	}
	@Override
	public double[] accumulationContribution(double[] eigenvectors) {
		double[] vareig =	varianceContribution(eigenvectors);
		double acc = 0.0;
		double[] accEig = new double[vareig.length]; 
		for (int i = 0; i < vareig.length; i++) {
			acc += vareig[i];
			accEig[i] = acc;
		}
		return accEig;
	}
	
	@Override
	public double[] communality(double[][] componentArr){
		double[] communality = new double[componentArr.length];
		for (int i = 0; i < componentArr.length; i++) {
			communality[i] = basicStatisticsService.sumSquares(componentArr[i]);
		}
		return communality;
	}
	@Override
	public PrincipalComponentAnalysisDTO principalComponentAnalysis(PrincipalComponentAnalysisCondition condition) {
		PrincipalComponentAnalysisDTO rePcaDTO = new PrincipalComponentAnalysisDTO();
		List<VarietyDTO> varDTOList = 	condition.getVariableList();
		rePcaDTO.setVariableList(varDTOList);
		PCAEigenvalueDTO pcaDTO = new PCAEigenvalueDTO();
		
		
		//计算相关矩阵表person
		BivariateCorrelateCondition bicondition = new BivariateCorrelateCondition();
		bicondition.setDataGrid(condition.getDataGrid());
		bicondition.setVariableList(varDTOList);
		BivariateCorrelateDTO biDTO =	correlationService.bivariate(bicondition);
		Map<String, List<Map<String, BiCoDataDTO>>> map = biDTO.getResDataMap();
		double[][] pearsonArr = new double[varDTOList.size()][varDTOList.size()];
		for (int i=0 ;i < varDTOList.size() ; i++) {
			//双变量bug。多嵌套了一个数组，前端做完了。无影响不改动
			List<Map<String, BiCoDataDTO>> innerList =	map.get(varDTOList.get(i).getVarietyName());
			for (int j=0 ;j < varDTOList.size() ; j++) {
				Double pearsonr =	innerList.get(j).get(varDTOList.get(j).getVarietyName()).getPearsonR();
				pearsonArr[i][j] = pearsonr;
			}
		}
		pcaDTO.setCorrelationArr(pearsonArr);
		pcaDTO.setKmo(kmo(pearsonArr));
		pcaDTO.setKmoDesc("0.9以上表示非常适合；0.8表示适合；0.7表示一般；0.6表示不太适合；0.5以下表示极不适合");
		pcaDTO.setDeterminant(MatrixUtil.determinant(pearsonArr));
		
		EigenvalueDTO eigDTO = new EigenvalueDTO();
		try {
			eigDTO = principalComponentAnalysis(pearsonArr);
		} catch (DataErrException e) {
			rePcaDTO.setRet_code("-1");
			rePcaDTO.setRet_err(e.getMessage());
			rePcaDTO.setRet_msg(e.getMessage());
			e.printStackTrace();
		}
		double[]  sortEigvalues = eigDTO.getSortEigenvalues();
	double[] vareigs =	eigDTO.getVarEig();
	double[] acceigs =eigDTO.getAccEig();
double[][] oraComArr =	eigDTO.getComponentArr();
		pcaDTO.setEigTotalInit(sortEigvalues);
		pcaDTO.setAccEigInit(acceigs);
		pcaDTO.setVarEigInit(vareigs);
		switch (condition.getExtractMethod()) {
		case PrincipalComponentAnalysisCondition.EXTRACT_METHOD_EIGENVALUE:{
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
			double[][] componentArr = new double[tmpSize][varDTOList.size()];
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
		case PrincipalComponentAnalysisCondition.EXTRACT_METHOD_FACTOR:{
			int tmpSize = condition.getFactorExtraNum();
			double[] eigTotalExtra = new double[tmpSize];
			double[] accEigExtra = new double[tmpSize];
			double[] varEigExtra = new double[tmpSize];
			double[][] componentArr = new double[tmpSize][varDTOList.size()];
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
		default:
			break;
		}
		
		//公因子方差
		double[] communaList = communality(pcaDTO.getComponentArr());
		double[][] communalityArr = new double[2][communaList.length];
		for (int i = 0; i < communaList.length; i++) {
			communalityArr[0][i]=1.0;
			communalityArr[1][i]=communaList[i];
			
		}
		pcaDTO.setCommunalityArr(DataFormatUtil.transposition(communalityArr));
		rePcaDTO.setData(pcaDTO);
		return rePcaDTO;
	}
	@Override
	public double kmo(double[][] correlationArr) {
	double rSum = 	basicStatisticsService.crossSquareSum(correlationArr);
	double[][] cofactorArr = MatrixUtil.cofactor(correlationArr);
	double[][] pcArr = MatrixUtil.partialCorrelation(cofactorArr);
	double cofac =	basicStatisticsService.crossSquareSum(pcArr);
	double kmo = rSum / (rSum + cofac);
		return kmo;
	}

}
