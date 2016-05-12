package org.jmu.multiinfo.dto.basestatistics;

import org.jmu.multiinfo.core.dto.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="变量",value="Variety")
public class VarietyDTO extends BaseBean{
	@ApiModelProperty(value="位置",example="A")
	private String position;
	@ApiModelProperty(value="类型描述",example="字符串型")
	private String typeDes;
	@ApiModelProperty(value="类型",example="0")
	private Integer type;
	@ApiModelProperty(value="类型格式",example="yyyy-MM-dd")
	private String typeFormat;
	@ApiModelProperty(value="变量名",example="降水")
	private String  varietyName;
	@ApiModelProperty(value="变量描述",example="降水")
	private String varietyDes;
	//数据范围
	@ApiModelProperty(value="数据范围",example="A2:A5")
	private String range;
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
	public String getTypeFormat() {
		return typeFormat;
	}
	public void setTypeFormat(String typeFormat) {
		this.typeFormat = typeFormat;
	}
	public String getVarietyName() {
		return varietyName;
	}
	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getVarietyDes() {
		return varietyDes;
	}
	public void setVarietyDes(String varietyDes) {
		this.varietyDes = varietyDes;
	}
}
