package org.jmu.multiinfo.service.cluster.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;

import org.jmu.multiinfo.core.exception.DataErrException;
import org.jmu.multiinfo.core.util.DataFormatUtil;
import org.jmu.multiinfo.core.util.ExcelUtil;
import org.jmu.multiinfo.core.util.PositionBean;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.dto.cluster.PointGroupCondition;
import org.jmu.multiinfo.dto.cluster.PointGroupDTO;
import org.jmu.multiinfo.dto.cluster.StepClusterDTO;
import org.jmu.multiinfo.dto.cluster.StepNodeDTO;
import org.jmu.multiinfo.service.basestatistics.BasicStatisticsService;
import org.jmu.multiinfo.service.cluster.ClusterService;
import org.jmu.multiinfo.service.correlation.CorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterServiceImpl implements ClusterService{
	
	@Autowired
	private BasicStatisticsService basicStatisticsService;
	
	@Autowired
	private CorrelationService correlationService;

	@Override
	public PointGroupDTO pointGroup(PointGroupCondition condition) {
		PointGroupDTO pointDTO = new PointGroupDTO();
		DataDTO[][] dataGrid =	condition.getDataGrid();
		VarietyDTO factorVar =	condition.getFactorVarVariable();
		List<VarietyDTO>  independVarList=	condition.getIndependentVariable();
		Map<String, List<Double>> dataMap =	DataFormatUtil.converToDouble(dataGrid, independVarList);
		List<List<Double>> dataGridList = new ArrayList<>();
		for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
			 List<Double> dataList = entry.getValue();
			 dataGridList.add(dataList);
		}
		
		// 样本号数据
		List<Object> factorVarList = new ArrayList<Object>();

		
		
		double[][] oraData=null;
//		switch (condition.getClusterMethod()) {
//		case PointGroupCondition.TYPE_Q:{
//			//个体间
//			 dataArr = DataFormatUtil.transposition(dataGridList);
//				// 样本号
//				PositionBean factorRange = ExcelUtil.splitRange(factorVar.getRange());
//				for (int i = factorRange.getFirstRowId() - 1; i < factorRange.getLastRowId(); i++) {
//					for (int j = factorRange.getFirstColId() - 1; j < factorRange.getLastColId(); j++) {
//						factorVarList.add(dataGrid[i][j].getData());
//					}
//				}
//			break;
//		}
//		case PointGroupCondition.TYPE_R:{
//
//			
//			dataArr = new double[dataGridList.size()][dataGridList.get(0).size()];
//			for (int i = 0; i < dataGridList.size(); i++) {
//				dataArr[i] =	DataFormatUtil.converToDouble(dataGridList.get(i));
//			}
//			
//			for (int i = 0; i < independVarList.size(); i++) {
//				factorVarList.add(independVarList.get(i).getVarietyName());
//			}
//			break;
//			}
//		default:
//			break;
//		}
		
		oraData = new double[dataGridList.size()][dataGridList.get(0).size()];
		for (int i = 0; i < dataGridList.size(); i++) {
			oraData[i] =	DataFormatUtil.converToDouble(dataGridList.get(i));
		}
		
		int row = oraData.length;
		int col = oraData[0].length;
		switch (condition.getNormalizationMethod()) {
		case PointGroupCondition.NORMALIZATION_RANGE:
			for (int i = 0; i < row; i++) {
				try {
					oraData[i] = basicStatisticsService.regularizationRange(oraData[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					return pointDTO;
				}
			}
			break;
		case PointGroupCondition.NORMALIZATION_RANGE_SD:
			for (int i = 0; i < row; i++) {
				try {
					oraData[i] = basicStatisticsService.RangeNormalization(oraData[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					pointDTO.setRet_err(e.getMessage());
					return pointDTO;
				}
			}
			break;
		case PointGroupCondition.NORMALIZATION_STANDARD_SD:
			for (int i = 0; i < row; i++) {
				try {
					oraData[i] = basicStatisticsService.StandardDeviationNormalization(oraData[i]);
				} catch (DataErrException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pointDTO.setRet_code("-1");
					pointDTO.setRet_err(e.getMessage());
					return pointDTO;
				}
			}
			break;
		default:
			break;
		}
		
		double[][] dataArr=null;
		switch (condition.getClusterMethod()) {
		case PointGroupCondition.TYPE_Q:{
			//个体间
			dataArr = new double[dataGridList.get(0).size()][dataGridList.size()];
			 dataArr = DataFormatUtil.transposition(oraData);
				 row = dataArr.length;
				 col = dataArr[0].length;
				// 样本号
				PositionBean factorRange = ExcelUtil.splitRange(factorVar.getRange());
				for (int i = factorRange.getFirstRowId() - 1; i < factorRange.getLastRowId(); i++) {
					for (int j = factorRange.getFirstColId() - 1; j < factorRange.getLastColId(); j++) {
						factorVarList.add(dataGrid[i][j].getData());
					}
				}
			break;
		}
		case PointGroupCondition.TYPE_R:{

			
			dataArr = oraData;
			 row = dataArr.length;
			 col = dataArr[0].length;
			
			for (int i = 0; i < independVarList.size(); i++) {
				factorVarList.add(independVarList.get(i).getVarietyName());
			}
			break;
			}
		default:
			break;
		}
		
		List<StepClusterDTO> stepList = new ArrayList<>();
		List<Integer> indexExist = new ArrayList<>();
		List<Integer>  groupIndexList = new ArrayList<>();
		for (int i = 0; i < row; i++){
			groupIndexList.add(1);
		}
		for(int lo=0;lo< row-1;lo++){

		switch (condition.getStatisticsMethod()) {
		case PointGroupCondition.STATISTICS_CORRELATION:{
			
			StepClusterDTO maxiDTO =  null;
			
			
			for (int i = 0; i < row; i++) {
				for (int j = i+1; j < row; j++) {
					try {
						if(!indexExist.contains(j+1)  && !indexExist.contains(i+1)){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = correlationService.pearsonRCoefficient(dataArr[j], dataArr[i]);
						e.setData(data);
						e.setRowIndex(j+1);
						e.setColIndex(i+1);
						if(maxiDTO == null || e.getData()> maxiDTO.getData()) maxiDTO =e;
						
						}

					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			int rowIndex = maxiDTO.getRowIndex();
			int colIndex = maxiDTO.getColIndex();
			dataArr[colIndex - 1] =	calNewGroup(dataArr[rowIndex -1],dataArr[colIndex - 1],groupIndexList.get(rowIndex-1),groupIndexList.get(colIndex-1));
			dataArr[rowIndex - 1] = dataArr[colIndex - 1];
			groupIndexList.set(rowIndex-1, groupIndexList.get(rowIndex-1)+1)	;
			groupIndexList.set(colIndex-1, groupIndexList.get(colIndex-1)+1)	;
			stepList.add(maxiDTO);
			indexExist.add(maxiDTO.getRowIndex());
			break;
			
		}
		case PointGroupCondition.STATISTICS_ANGLE_COSINE:{
			
			StepClusterDTO maxiDTO =  null;
			
			
			for (int i = 0; i < row; i++) {
				for (int j = i+1; j < row; j++) {
					try {
						if(!indexExist.contains(j+1)  && !indexExist.contains(i+1) ){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = basicStatisticsService.cos(dataArr[j], dataArr[i]);
						e.setData(data);
						e.setRowIndex(j+1);
						e.setColIndex(i+1);
						if(maxiDTO == null || e.getData()> maxiDTO.getData()) maxiDTO =e;
						
						}

					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			int rowIndex = maxiDTO.getRowIndex();
			int colIndex = maxiDTO.getColIndex();
			dataArr[colIndex - 1] =	calNewGroup(dataArr[rowIndex -1],dataArr[colIndex - 1],groupIndexList.get(rowIndex-1),groupIndexList.get(colIndex-1));
			dataArr[rowIndex - 1] = dataArr[colIndex - 1];
			groupIndexList.set(rowIndex-1, groupIndexList.get(rowIndex-1)+1)	;
			groupIndexList.set(colIndex-1, groupIndexList.get(colIndex-1)+1)	;
			stepList.add(maxiDTO);
			indexExist.add(maxiDTO.getRowIndex());
			break;
			
		}
		case PointGroupCondition.STATISTICS_DISTANCE:{
			
			StepClusterDTO miniDTO =  null;
			for (int i = 0; i < row; i++) {
				for (int j = i+1; j < row; j++) {
					try {
						if(!indexExist.contains(j+1) && !indexExist.contains(i+1)){
							StepClusterDTO e =  new StepClusterDTO();
						Double data = basicStatisticsService.euclideanDistance(dataArr[j], dataArr[i]);
						e.setData(data);
						e.setRowIndex(j+1);
						e.setColIndex(i+1);
						if(miniDTO == null || e.getData()< miniDTO.getData()) miniDTO =e;
						
						}

					} catch (DataErrException e) {
						e.printStackTrace();
						pointDTO.setRet_code("-1");
						pointDTO.setRet_err(e.getMessage());
						return pointDTO;
					}
				}
			}
			int rowIndex = miniDTO.getRowIndex();
			int colIndex = miniDTO.getColIndex();
			dataArr[colIndex - 1] =	calNewGroup(dataArr[rowIndex -1],dataArr[colIndex - 1],groupIndexList.get(rowIndex-1),groupIndexList.get(colIndex-1));
			dataArr[rowIndex - 1] = dataArr[colIndex - 1];
			groupIndexList.set(rowIndex-1, groupIndexList.get(rowIndex-1)+1)	;
			groupIndexList.set(colIndex-1, groupIndexList.get(colIndex-1)+1)	;

			stepList.add(miniDTO);
			indexExist.add(miniDTO.getRowIndex());
			break;
			
		}
		default:
			break;
		}
		}
		StepBinaryTree tree = makeClusterTree(stepList,row);
		List<Integer> orderStepList =lrd(tree);
		pointDTO.setTree(tree );
		pointDTO.setFactorVarList(factorVarList);
		pointDTO.setStepList(stepList);
		pointDTO.setOrderStepList(orderStepList);
		return pointDTO;
	}
	
	
	
	private double[] calNewGroup(double[] ds, double[] ds2, int n1,int n2) {
		int N = ds.length;
		double[] newGroup = new double[N];
		for(int i=0;i<N;i++){
			newGroup[i]=	(ds[i] * n1 + ds2[i] * n2) / (n1 + n2);
		}
		
		return newGroup;
	}




	//后续遍历求叶子
	private List<Integer> lrd(StepBinaryTree tree) {
		Stack<TreeNode> nodeStack = new Stack<>();
		List<Integer> stepList = new ArrayList<>();
		nodeStack.push(tree.getRoot());
		while(!nodeStack.isEmpty()){
			TreeNode topNode = nodeStack.pop();
			if(topNode.getLefTreeNode() == null && topNode.getRightNode() ==null){
				stepList.add(topNode.getValue().getPos());
			}else{
				if( topNode.getRightNode() !=null)
				nodeStack.push(topNode.getRightNode());
				if(topNode.getLefTreeNode() != null)
				nodeStack.push(topNode.getLefTreeNode());
			}
		}
		return stepList;
		
	}




	private StepBinaryTree makeClusterTree(List<StepClusterDTO> stepList,final int N) {
		HashMap<Integer,TreeNode> map = new HashMap<>();
		StepBinaryTree tree = new StepBinaryTree();
		List<List<Integer>>  nodeIndexList = new ArrayList<>();
			for(int i=0;i<N;i++){
				TreeNode node = new TreeNode();
				node.setLefTreeNode(null);
				node.setRightNode(null);
				StepNodeDTO value = new StepNodeDTO();
				value.setPos(i+1);
				node.setValue(value );
				map.put(i+1, node);
				
			List<Integer> ea =	new ArrayList<>();
			ea.add(i+1);
				nodeIndexList.add(ea);
			}
			for (int i = 0; i < stepList.size(); i++) {
				StepClusterDTO step = stepList.get(i);
				Integer pos = Math.min(step.getRowIndex(),step.getColIndex());
				Integer maxpos = Math.max(step.getRowIndex(),step.getColIndex());
				TreeNode pNode = new TreeNode();
				pNode.setLefTreeNode(map.get(pos));
				pNode.setRightNode(map.get(maxpos));
				StepNodeDTO value = new StepNodeDTO();
				value.setData(step.getData());
				value.setPos(pos);
				pNode.setValue(value);
				
				List<Integer> rowIndexList=	nodeIndexList.get(step.getRowIndex()-1);
				
				for(int j=0;j<rowIndexList.size();j++){
					map.put(rowIndexList.get(j), pNode);
				}
				rowIndexList.add(step.getColIndex());
				List<Integer> colIndexList=	nodeIndexList.get(step.getColIndex()-1);
				for(int j=0;j<colIndexList.size();j++){
					map.put(colIndexList.get(j), pNode);
				}
				colIndexList.add(step.getRowIndex());
				if(i== stepList.size()-1) tree.setRoot(pNode);
			}
			return tree;
			
	}

}
