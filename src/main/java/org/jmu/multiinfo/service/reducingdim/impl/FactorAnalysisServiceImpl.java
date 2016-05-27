package org.jmu.multiinfo.service.reducingdim.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.MatrixUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.reducingdim.FactorAnalysisCondition;
import org.jmu.multiinfo.dto.reducingdim.FactorAnalysisDTO;
import org.jmu.multiinfo.dto.reducingdim.PCAEigenvalueDTO;
import org.jmu.multiinfo.dto.reducingdim.PrincipalComponentAnalysisDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.reducingdim.FactorAnalysisService;
import org.jmu.multiinfo.service.reducingdim.PrincipalComponentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactorAnalysisServiceImpl implements FactorAnalysisService{

	@Autowired
	private PrincipalComponentAnalysisService pcaService;
	
	@Autowired
	private BasicStatisticsService basicService;
	
	@Override
	public FactorAnalysisDTO factorAnalysis(FactorAnalysisCondition condition) {
		FactorAnalysisDTO fcaDTO = new FactorAnalysisDTO();
		Double variancePam = condition.getVariance();
		DataDTO[][] dataGrid = condition.getDataGrid();
		List<VarietyDTO>  independVarList=	condition.getVariableList();
		fcaDTO.setVariableList(independVarList);
		Map<String, List<Double>> dataMap =	DataFormatUtil.converToDouble(dataGrid, independVarList);
		double[][] oraArr = new double[independVarList.size()][];
		int index = 0;
		for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
			 List<Double> dataList = entry.getValue();
			 oraArr[index++] =  DataFormatUtil.converToDouble(dataList);
		}
		
		PrincipalComponentAnalysisDTO pcaDTO=	pcaService.principalComponentAnalysis(condition);
		fcaDTO.setRet_msg(pcaDTO.getRet_msg());
		fcaDTO.setRet_code(pcaDTO.getRet_code());
		fcaDTO.setRet_err(pcaDTO.getRet_err());
		PCAEigenvalueDTO pcaEvDTO = pcaDTO.getData();
		//成分矩阵
		double[][] componentArr = pcaEvDTO.getComponentArr();
		double[][] orRotaArr =	Varimax_orthogonal_rotation(componentArr,variancePam);
		pcaEvDTO.setOrRotaArr(orRotaArr);
		
		
		for(int i=0;i< oraArr.length;i++){
		try {
			oraArr[i] = basicService.StandardDeviationNormalization(oraArr[i]);
		} catch (DataErrException e) {
			fcaDTO.setRet_err(e.getMessage());
			fcaDTO.setRet_code("-1");
		}
		}
		oraArr = DataFormatUtil.transposition(oraArr);
		double[][] correlationArr =pcaEvDTO.getCorrelationArr();
		//At * R-1 
		double[][] atr = MatrixUtil.product(MatrixUtil.transpose(orRotaArr),MatrixUtil.inverse(correlationArr));
		//X *(At * R-1)t 
		double[][] orFacArr = MatrixUtil.product(oraArr, MatrixUtil.transpose(atr));
		pcaEvDTO.setOrFacArr(orFacArr);
		fcaDTO.setData(pcaEvDTO);
		return fcaDTO;
	}
	
	
	//方差极大正交旋转矩阵 va,aa
		public double[][] Varimax_orthogonal_rotation(double[][] componentArr,double variancePam){
			double vb = 1.0E+20,va;
			int kl=1;
			boolean value=false;
			int p = componentArr[0].length;
			int m = componentArr.length;
			double[][] aa = MatrixUtil.clone(componentArr);
			
			do{
				double b=0,c=0;
				for(int j=0;j<p;j++){
					double d = 0;
					for(int i=0;i<m;i++){
						b = b + FastMath.pow(aa[i][j],4); 
						d = d + FastMath.pow(aa[i][j],2);
					}
					c = c + FastMath.pow(d,2);
				}
				va = (b - c / m) / m;
				double PI = FastMath.PI;
				if(FastMath.abs(va - vb) < variancePam){
					value=false;
				}
				else{
					vb = va;
					//第KL次旋转方差值VA =(vb)
					double d,g,u,v;
					for(int k=0;k<p-1;k++){
						for(int j=k+1;j<p;j++){
							b = 0;
							c = 0;
							d = 0 ;
							g = 0;
							for(int i=0;i<m;i++){
								u = FastMath.pow(aa[i][k],2)-FastMath.pow(aa[i][j],2) ;
								v = aa[i][k] * aa[i][j] ;
								g = g + u ;
								b = b + v ;
								c = c + FastMath.pow(u ,2) - 4 * FastMath.pow(v, 2) ;
								d = d + u * v;
							}
							b = 2 * b ;
							d = 4 * d ;
							c = m * c - FastMath.pow(g,2) + FastMath.pow(b, 2) ;
							b = m * d - 2 * b * g;
							if(FastMath.abs(c) < 0.0000000001)
								d = PI / 2;
							else
								d = FastMath.abs(FastMath.atan(b / c));
							if(c<0)
								d = PI - d;
							if(b<0)
								d = -d;
							d = d * 0.25 ;
							b = FastMath.sin(d);
							c = FastMath.cos(d);
							for(int i=0;i<m;i++){
								d = aa[i][k];
								g = aa[i][j];
								aa[i][k] = d * c + g * b;
								aa[i][j] = g * c - d * b;
							}
						}
					}
					kl = kl + 1;
					value = true;
				}
			}while(value);
			// 输出 因子载荷矩阵方差 (va)   
			//方差极大正交旋转矩阵 aa
			return aa;
		}
		

}
