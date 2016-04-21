package org.jmu.multiinfo.dto.dingchart;

import org.jmu.multiinfo.dto.basestatistics.DataDTO;

public class DingChartCondition {
 
 //数据
 private DataDTO[][] dataGrid;
 
 //计算方法
 private Integer calculateMethod;
 
	/****
	 * 按行计算
	 */
	public final static int CALCULATE_METHOD_ROW = 0;
	
	
	/****
	 * 按列计算
	 */
	public final static int CALCULATE_METHOD_COL = 1;
	
	/****
	 * 全表计算
	 */
	public final static int CALCULATE_METHOD_ALL = 2;


public DataDTO[][] getDataGrid() {
	return dataGrid;
}

public void setDataGrid(DataDTO[][] dataGrid) {
	this.dataGrid = dataGrid;
}

public Integer getCalculateMethod() {
	return calculateMethod;
}

public void setCalculateMethod(Integer calculateMethod) {
	this.calculateMethod = calculateMethod;
}
 
 
 
}
