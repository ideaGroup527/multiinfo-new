package org.jmu.multiinfo.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.jmu.multiinfo.core.dto.DataVariety;
import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;
import org.jmu.multiinfo.web.utils.CommonUtil;
import org.springframework.util.CollectionUtils;

public class DataFormatUtil {
public static Double converToDouble(String data) throws NumberFormatException{
	return Double.valueOf(data);
}

public static void print(double[][] data){
	for (int i = 0; i < data.length; i++) {
		for (int j = 0; j < data[i].length; j++) {
			System.out.print(data[i][j]+" ");
		}
		System.out.println();
	}
}

public static void print(int[][] data){
	for (int i = 0; i < data.length; i++) {
		for (int j = 0; j < data[i].length; j++) {
			System.out.print(data[i][j]+" ");
		}
		System.out.println();
	}
}

public static void print(int[] data){
	for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]+" ");
	}
}

public static void print(double[] data){
	for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]+" ");
	}
}

public static Double converToDouble(DataDTO dataDTO){
	Double data = null ;
	Integer dataType = dataDTO.getType();
	String oraData = dataDTO.getData().toString();
	switch (dataType) {
	case DataVariety.DATA_TYPE_STRING:
		oraData = 	oraData.replaceAll(" ", "");
		if(CommonUtil.isRealNumber(oraData)) return Double.valueOf(oraData);
		throw new NumberFormatException("position:"+dataDTO.getPosition()+"data:"+dataDTO.getData().toString()+"! is not a number");
	case DataVariety.DATA_TYPE_NUMERIC_VIRG:
		data = converToDouble(oraData.replaceAll(",", ""));
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_SCIENCE:
		String[] scienceData = oraData.split("[Ee]");
		Double a = converToDouble(scienceData[0]);
		Double e =	converToDouble(scienceData[1]);
		data = 	a * FastMath.pow(10 , e );
		break;	
	case DataVariety.DATA_TYPE_FAULT:
		data = null;
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_DOLLAL:
		oraData = 	oraData.replaceAll("[$￥]", "");
		data = converToDouble(oraData);
		break;
	case DataVariety.DATA_TYPE_NUMERIC:
		data = converToDouble(oraData);
		break;	
	case DataVariety.DATA_TYPE_DATE:
		//TODO			
		break;	
	case DataVariety.DATA_TYPE_CUSTOM:
		//TODO
		break;
	default:
		break;
	}
	
	return data;
}


public static Object converToObject(DataDTO dataDTO){
	Object data = null ;
	Integer dataType = dataDTO.getType();
	String oraData = dataDTO.getData().toString();
	switch (dataType) {
	case DataVariety.DATA_TYPE_STRING:
		if(CommonUtil.isRealNumber(oraData)) return Double.valueOf(oraData);
		data = oraData;
		break; 
	case DataVariety.DATA_TYPE_NUMERIC_VIRG:
		data = converToDouble(oraData.replaceAll(",", ""));
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_SCIENCE:
		String[] scienceData = oraData.split("[Ee]");
		Double a = converToDouble(scienceData[0]);
		Double e =	converToDouble(scienceData[1]);
		data = 	a * FastMath.pow(10 , e );
		break;	
	case DataVariety.DATA_TYPE_FAULT:
		data = null;
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_DOLLAL:
		oraData = 	oraData.replaceAll("[$￥]", "");
		data = converToDouble(oraData);
		break;
	case DataVariety.DATA_TYPE_NUMERIC:
		data = converToDouble(oraData);
		break;	
	case DataVariety.DATA_TYPE_DATE:
		data = oraData;
		break;	
	case DataVariety.DATA_TYPE_CUSTOM:
		data = oraData;
		break;
	default:
		break;
	}
	
	return data;
}

public static double[][] converToDouble(DataDTO[][] dataGrid){
	int rows = dataGrid.length;
	int cols = dataGrid[0].length;
	double[][] dataArr = new double[rows][cols];
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {
			dataArr[i][j] = converToDouble(dataGrid[i][j]);
		}
	}
	
	return dataArr;
	
}

/***
 * 数据转换根据变量制成键值 <变量名，数据>
 * @param dataGrid
 * @param variableList
 * @return
 */
public static Map<String, List<Double>> converToDouble(DataDTO[][] dataGrid,List<VarietyDTO> variableList){
	Map<String,List<Double>> variableDataMap = new LinkedHashMap<>();
	for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
		List<Double> dataList = new ArrayList<Double>();
		VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
		PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
		for (int i = varRange.getFirstRowId() - 1; i < varRange.getLastRowId(); i++) {
			for (int j = varRange.getFirstColId() - 1; j < varRange.getLastColId(); j++) {
				DataDTO dataDTO = dataGrid[i][j];
				dataList.add(DataFormatUtil.converToDouble(dataDTO));
			}
		}
		
		variableDataMap.put(varietyDTO.getVarietyName(), dataList);
	}
	return variableDataMap;
}

public static Map<String, List<Double>> converToDouble(DataDTO[][] dataGrid,List<VarietyDTO> variableList,VarietyDTO factorVariable){
	Map<String,List<Double>> resDataMap = new LinkedHashMap<>();
	PositionBean 	factorVarRange =ExcelUtil.splitRange( factorVariable.getRange());//因子变量范围如A1:G1--
	
	for (Iterator<VarietyDTO> iterator = variableList.iterator(); iterator.hasNext();) {
		VarietyDTO varietyDTO = (VarietyDTO) iterator.next();
		PositionBean varRange = ExcelUtil.splitRange(varietyDTO.getRange());
		for (int i = factorVarRange.getFirstRowId() - 1; i < factorVarRange.getLastRowId(); i++) {
			for (int j = factorVarRange.getFirstColId() - 1; j < factorVarRange.getLastColId(); j++) {
				DataDTO varDTO = dataGrid[i][j];
				String factName = varDTO.getData().toString();
				List<Double> list =	resDataMap.get(factName);
				if(CollectionUtils.isEmpty(list)) list = new ArrayList<>();
				resDataMap.put(factName, list);
				for (int k = varRange.getFirstColId() - 1; k < varRange.getLastColId(); k++) {
					DataDTO dataDTO = dataGrid[i][k];
					list.add(DataFormatUtil.converToDouble(dataDTO));
					
				}
			}
		}
		
	}
	return resDataMap;
}

/***
 * 转置数据
 * @param dataGrid
 * @return
 */
public static DataDTO[][] transposition(DataDTO[][] dataGrid){
	DataDTO[][] tDataGrid = new DataDTO[dataGrid[0].length][dataGrid.length];
	for (int i = 0; i < dataGrid.length; i++) {
		for (int j = 0; j < dataGrid[0].length; j++) {
			tDataGrid[j][i] = dataGrid[i][j];
		}
	}
	return tDataGrid;
}


/***
 * 转置数据
 * @param dataGrid
 * @return
 */
public static double[][] transposition(double[][] dataGrid){
	double[][] tDataGrid = new double[dataGrid[0].length][dataGrid.length];
	for (int i = 0; i < dataGrid.length; i++) {
		for (int j = 0; j < dataGrid[0].length; j++) {
			tDataGrid[j][i] = dataGrid[i][j];
		}
	}
	return tDataGrid;
}


public static double[][] transposition(List<List<Double>> dataGridList){
	
	double[][]  tDataGrid = new double[dataGridList.get(0).size()][dataGridList.size()];
	for (int i = 0; i < dataGridList.size(); i++) {
		 List<Double> dataGrid =	dataGridList.get(i);
		for (int j = 0; j < dataGrid.size(); j++) {
			Double data  = dataGrid.get(j);
			tDataGrid[j][i] = data;
		}
	}
	return tDataGrid;
}

public static double[]  converToDouble(List<Double> dataList){
	double[] dataArr = new double[dataList.size()];
	for (int i = 0; i < dataList.size(); i++) {
		dataArr[i] = dataList.get(i);
	}
	return dataArr;
}

public static void copy(List<Double> destList, List<Double> srcList) {
	if(destList==null) destList = new ArrayList<>(srcList.size());
	destList.clear();
	for(int i=0;i<srcList.size();i++){
		destList.add(srcList.get(i));
	}	
}
}
