package org.jmu.multiinfo.dto.upload;

import java.util.List;

import org.jmu.multiinfo.core.dto.BaseDTO;

public class TokenDTO extends BaseDTO {
	private String token;
	private long createTime;
	private boolean isMultiSheet;
	private String fileName;
	private int sheetNum;
	private String version;
	private List<String> sheetNameList;
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


	public boolean getIsMultiSheet() {
		return isMultiSheet;
	}

	public void setIsMultiSheet(boolean isMultiSheet) {
		this.isMultiSheet = isMultiSheet;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getSheetNameList() {
		return sheetNameList;
	}

	public void setSheetNameList(List<String> sheetNameList) {
		this.sheetNameList = sheetNameList;
	}

}
