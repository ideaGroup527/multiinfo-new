package org.jmu.multiinfo.dto.cluster;

public class StepClusterDTO {
private Double data;
private Integer rowIndex;
private Integer colIndex;
public Double getData() {
	return data;
}
public void setData(Double data) {
	this.data = data;
}
public Integer getRowIndex() {
	return rowIndex;
}
public void setRowIndex(Integer rowIndex) {
	this.rowIndex = rowIndex;
}
public Integer getColIndex() {
	return colIndex;
}
public void setColIndex(Integer colIndex) {
	this.colIndex = colIndex;
}
@Override
public String toString() {
	return "StepClusterDTO [data=" + data + ", rowIndex=" + rowIndex + ", colIndex=" + colIndex + "]";
}


}
