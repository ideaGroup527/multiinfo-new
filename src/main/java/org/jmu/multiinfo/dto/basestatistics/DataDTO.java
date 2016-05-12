package org.jmu.multiinfo.dto.basestatistics;

import java.io.Serializable;

import org.jmu.multiinfo.core.dto.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "数据", value = "data")
public class DataDTO extends BaseBean {
	@ApiModelProperty(value = "位置", example = "A2")
	private String position;
	@ApiModelProperty(value = "位置描述", example = "A,2")
	private String positionDes;
	@ApiModelProperty(value = "类型描述", example = "标准型数字")
	private String typeDes;
	@ApiModelProperty(value = "类型", example = "1")
	private Integer type;
	@ApiModelProperty(value = "类型格式", example = "yyyy-MM-dd")
	private String typeFormat;
	@ApiModelProperty(value = "数据", example = "100.1")
	private Object data;
	@ApiModelProperty(value = "合并范围")
	private String mergedRange = "";
	@ApiModelProperty(value = "合并范围描述")
	private String mergedRangeDes = "";

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getTypeFormat() {
		return typeFormat;
	}

	public void setTypeFormat(String typeFormat) {
		this.typeFormat = typeFormat;
	}

	public String getMergedRange() {
		return mergedRange;
	}

	public void setMergedRange(String mergedRange) {
		this.mergedRange = mergedRange;
	}

	public String getPositionDes() {
		return positionDes;
	}

	public void setPositionDes(String positionDes) {
		this.positionDes = positionDes;
	}

	public String getMergedRangeDes() {
		return mergedRangeDes;
	}

	public void setMergedRangeDes(String mergedRangeDes) {
		this.mergedRangeDes = mergedRangeDes;
	}
}