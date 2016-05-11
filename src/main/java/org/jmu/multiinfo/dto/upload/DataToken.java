package org.jmu.multiinfo.dto.upload;

import java.io.Serializable;

public class DataToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5148403119278490814L;
	private Object data;
	private Long createTime;
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}


}
