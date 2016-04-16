package org.jmu.multiinfo.dto.upload;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class TokenDTO extends BaseDTO {
	private String token;
	private long createTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
