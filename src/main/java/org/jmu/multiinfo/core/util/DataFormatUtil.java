package org.jmu.multiinfo.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.DataVariety;
import org.jmu.multiinfo.dto.basestatistics.VarietyDTO;

public class DataFormatUtil {
public static Double converToDouble(String data) throws NumberFormatException{
	return Double.valueOf(data);
}

public static Double converToDouble(DataDTO dataDTO){
	Double data = null ;
	Integer dataType = dataDTO.getType();
	String oraData = dataDTO.getData().toString();
	switch (dataType) {
	case DataVariety.DATA_TYPE_STRING:
		throw new NumberFormatException("position:"+dataDTO.getPosition()+"data:"+dataDTO.getData().toString()+"! is not a number");
	case DataVariety.DATA_TYPE_NUMERIC_VIRG:
		data = converToDouble(oraData.replaceAll(",", ""));
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_SCIENCE:
		String[] scienceData = oraData.split("[Ee]");
		Double a = converToDouble(scienceData[0]);
		Double e =	converToDouble(scienceData[1]);
		data = 	a * Math.pow(10 , e );
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_DOT:
		//TODO
		break;	
	case DataVariety.DATA_TYPE_NUMERIC_DOLLAL:
		//TODO	
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

public static Map<String, List<Double>> converToDouble(DataDTO[][] dataGrid,List<VarietyDTO> variableList){
	Map<String,List<Double>> variableDataMap = new HashMap<>();
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

public static double[]  converToDouble(List<Double> dataList){
	double[] dataArr = new double[dataList.size()];
	for (int i = 0; i < dataList.size(); i++) {
		dataArr[i] = dataList.get(i);
	}
	return dataArr;
}

}
