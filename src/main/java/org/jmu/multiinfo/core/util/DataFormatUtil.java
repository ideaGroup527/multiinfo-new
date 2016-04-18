package org.jmu.multiinfo.core.util;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;
import org.jmu.multiinfo.dto.basestatistics.DataVariety;

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
}
