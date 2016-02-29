package org.jmu.multiinfo.core.dto;


/***
 * 
* @Title: ExcelBean.java 
* @Package org.jmu.multiinfo.common.domain 
* @Description: 解析excel后映射成实体
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月4日 下午4:00:22 
* @version V1.0
 */
public class ExcelBean {
	private String fileName;
	private String filePath;
	private String[][] data;
	private String[] xAxis;
	private String[] yAxis;
	private String text;
	private String subtext;
	private String[] legend;
	
	
	public ExcelBean() {
	}

	public ExcelBean(String fileName, String filePath, String[][] data, String[] xAxis, String[] yAxis, String text,
			String subtext, String[] legend) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.data = data;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.text = text;
		this.subtext = subtext;
		this.legend = legend;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String[][] getData() {
		return data;
	}


	public void setData(String[][] data) {
		this.data = data;
	}


	public String[] getxAxis() {
		return xAxis;
	}


	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}


	public String[] getyAxis() {
		return yAxis;
	}


	public void setyAxis(String[] yAxis) {
		this.yAxis = yAxis;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getSubtext() {
		return subtext;
	}


	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}


	public String[] getLegend() {
		return legend;
	}


	public void setLegend(String[] legend) {
		this.legend = legend;
	}
	

}
