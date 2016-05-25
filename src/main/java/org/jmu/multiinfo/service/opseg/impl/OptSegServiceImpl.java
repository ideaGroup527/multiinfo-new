package org.jmu.multiinfo.service.opseg.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.opseg.OptArrDTO;
import org.jmu.multiinfo.dto.opseg.OptSegCondition;
import org.jmu.multiinfo.dto.opseg.OptSegDTO;
import org.jmu.multiinfo.dto.opseg.OptSegResDTO;
import org.jmu.multiinfo.dto.opseg.SegDataDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.basestatistics.MatrixStatisticsService;
import org.jmu.multiinfo.service.opseg.OptSegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptSegServiceImpl implements OptSegService {
@Autowired
private BasicStatisticsService basicStatisticsService;
@Autowired
private MatrixStatisticsService matrixStatisticsService;
	@Override
	public OptArrDTO optimalKSegmentation(int N, int K, double[][] D) {
		OptArrDTO optArr = new OptArrDTO();
		//k-1次分割最优
		int a[][] = new int[K][N];
		int resA[][] =  new int[K][N];
		double w[][] = new double[K][N];
		double resW[][] =  new double[K][N];
		//2次分割
		for(int m=N ;m>=2;m-- ){
			double min = Double.MAX_VALUE;
			int tempQ = 0 ;
			for(int q=m-1;q>=2-1;q--){
				double temp = D[0][q-1]+D[q][m-1];
				if(temp < min) {
					min = temp;
					tempQ = q;
				}
			}
			a[1][m-1]=tempQ;
			resA[1][m-1]=tempQ;
			w[1][m-1] = min;
			resW[1][m-1] = min;
		}
		
		for(int k=3;k<=K;k++){
		for(int m=N ;m>=k;m-- ){
			double min = Double.MAX_VALUE;
			int tempQ = 0 ;
			for(int q=m-1;q>=k-1;q--){
				double temp = w[k-2][q-1]+D[q][m-1];
				if(temp < min) {
					min = temp;
					tempQ = q;
				}
			}
			a[k-1][m-1]=tempQ;
			resA[k-1][m-1]=tempQ;
			a[k-2][m-1] = a[k-2][tempQ-1];
			w[k-1][m-1] = min;
			resW[k-1][m-1] = min;
		}
		}
		optArr.setA(a);
		optArr.setResA(resA);
		optArr.setW(w);
		optArr.setResW(resW);
		return optArr;

	}

	@Override
	public OptSegDTO optimalKSegmentation(OptSegCondition condition) {
		OptSegDTO osDTO =  new OptSegDTO();
		 DataDTO[][] dataGrid =	condition.getDataGrid();
		int K = condition.getSegNum();
	
		 List<VarietyDTO> independVarList	= condition.getVariableList();
		 Map<String, List<Double>> dataMap=	 DataFormatUtil.converToDouble(dataGrid, independVarList);
			List<List<Double>> dataGridList = new ArrayList<>();
			for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
				 List<Double> dataList = entry.getValue();
				 dataGridList.add(dataList);
			}
			int N = dataGridList.get(0).size();
			int row = dataGridList.size();
			double[][]	dataArr = new double[row][N];
			for (int i = 0; i < row; i++) {
				dataArr[i] =	DataFormatUtil.converToDouble(dataGridList.get(i));
			}
			
			
			//极差正规化
			for (int i = 0; i < row; i++) {
				try {
					dataArr[i] = basicStatisticsService.regularizationRange(dataArr[i]);
				} catch (DataErrException e) {
					osDTO.setRet_code("-1");
					osDTO.setRet_msg(e.getMessage());
					return osDTO;
				}
			}
		
			try {
				//计算变差矩阵
			double[][] D=	matrixStatisticsService.variation(dataArr);
			osDTO.setD(D);
			OptArrDTO optArrDTO=optimalKSegmentation(N,K,D);
			List<OptSegResDTO> optRes = new ArrayList<>();
			for(int i=2;i<=K;i++){
				OptSegResDTO e = new OptSegResDTO();
				e.setSegNum(i);
				List<SegDataDTO> segDataList = new ArrayList<>();
				double sst = 0.0;
				int[][] a=	optArrDTO.getA();
				int[][] resA=	optArrDTO.getResA();
				//计算每次分割所有分割点
				List<Integer> segList = new ArrayList<>();
				
				for(int j=2;j<i;j++)
					segList.add(a[j-1][N-1]);
				segList.add(resA[i-1][N-1]);
				int initFrom = 1;
				for(int j=0;j<segList.size();j++){
					
					SegDataDTO segData = new SegDataDTO();
					int from = initFrom;
					int to = segList.get(j);
					initFrom = to+1;
					
					segData.setFrom(from);
					
					segData.setTo(to);
					Double sswg = D[from-1][to-1];
					segData.setSswg(sswg);
					sst +=sswg;
					
					segDataList.add(segData );
				}
				SegDataDTO lastData = new SegDataDTO();
				lastData.setFrom(initFrom);
				lastData.setTo(N);
				lastData.setSswg(D[lastData.getFrom()-1][lastData.getTo()-1]);
				sst+=lastData.getSswg();
				segDataList.add(lastData);
				e.setSegDataList(segDataList );
				e.setSst(sst);
				optRes.add(e);
			}
			osDTO.setOptRes(optRes);
			} catch (DataErrException e) {
				e.printStackTrace();
				osDTO.setRet_msg(e.getMessage());
				osDTO.setRet_code("-1");
				return osDTO;
			}
		return osDTO;
	}

}
