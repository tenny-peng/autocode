package com.tenny.autocode.common;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
	
	private static final long serialVersionUID = -5325393443309985277L;
	
	public Result() {
		this("00000", "handle success", true);
	}
	
	public Result(String code, Object message, Boolean isSuccess) {
		this.put("code", code);
		this.put("message", message);
		this.put("success", isSuccess);
	}

	public void setCode(String code) {
		this.put("code", code);
	}
	
	public String getCode() {
		return String.valueOf(this.get("code"));
	}

	public void setMessage(String message) {
		this.put("message", message);
	}
	
	public String getMessage() {
		return String.valueOf(this.get("message"));
	}

	public void setSuccess(Boolean isSuccess) {
		this.put("success", isSuccess);
	}
	
	public Boolean getSuccess() {
		return (Boolean) this.get("success");
	}
	
	public void setData(Object data) {
		this.put("data", data);
	}
	
	public Object getData() {
		return this.get("data");
	}

}
