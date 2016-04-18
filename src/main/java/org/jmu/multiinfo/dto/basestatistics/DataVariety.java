package org.jmu.multiinfo.dto.basestatistics;

public interface DataVariety {
	
	/***
	 * 字符串型
	 */
	public final static int DATA_TYPE_STRING = 0;
	
	
	/****
	 * 标准型数字
	 */
	public final static int DATA_TYPE_NUMERIC = 1;
	
	
	/**
	 * 逗号型数值
	 */
	public final static int DATA_TYPE_NUMERIC_VIRG = 2;
	
	/**
	 * 圆点型数值
	 */
	public final static int DATA_TYPE_NUMERIC_DOT = 3;
	
	/**
	 * 科学型数值
	 */
	public final static int DATA_TYPE_NUMERIC_SCIENCE = 4;
	
	/**
	 * 美元型数值
	 */
	public final static int DATA_TYPE_NUMERIC_DOLLAL = 5;
	
	/**
	 * 日期型
	 */
	public final static int DATA_TYPE_DATE = 6;
	
	
	/***
	 * 用户自定义型
	 */
	public final static int DATA_TYPE_CUSTOM=7;
	
}
