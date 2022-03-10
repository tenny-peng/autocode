package com.tenny.autocode.common;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
	
	private static final long serialVersionUID = -5325393443309985277L;
	
	public static Result ok() {
		return ok(null);
	}
	
	public static Result ok(Object data) {
		Result result = new Result();
		result.setData(data);
		return result;
	}
	
	public static Result ok(Integer totalCount, Object data) {
		Result result = new Result();
		result.setTotalCount(totalCount);
		result.setData(data);
		return result;
	}
	
	public static Result ok(String code, String message, Object data) {
		Result result = new Result(code, message, true, data);
		result.setData(data);
		return result;
	}
	
	public static Result fail() {
		return fail("500", "系统错误，请联系管理员");
	}
	
	public static Result fail(String code, String message) {
		return fail(code, message, null);
	}
	
	public static Result fail(String code, String message, Object data) {
		Result result = new Result(code, message, false, data);
		result.setData(data);
		return result;
	}
	
	private Result() {
		this("00000", "操作成功", true, null);
	}
	
	private Result(String code, String message, Boolean isSuccess, Object data) {
		this.setCode(code);
		this.setMessage(message);
		this.setSuccess(isSuccess);
		this.setData(data);
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
	
	public void setTotalCount(Integer totalCount) {
		this.put("totalCount", totalCount);
	}
	
	public Integer getTotalCount() {
		return (Integer) this.get("totalCount");
	}

}
