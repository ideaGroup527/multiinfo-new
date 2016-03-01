package org.jmu.multiinfo.core.dto;

public class BaseDTO {
private String ret_code = "";
private String ret_msg = "";
private String ret_err = "";
public String getRet_code() {
	return ret_code;
}
public void setRet_code(String ret_code) {
	this.ret_code = ret_code;
}
public String getRet_msg() {
	return ret_msg;
}
public void setRet_msg(String ret_msg) {
	this.ret_msg = ret_msg;
}
public String getRet_err() {
	return ret_err;
}
public void setRet_err(String ret_err) {
	this.ret_err = ret_err;
}

}
