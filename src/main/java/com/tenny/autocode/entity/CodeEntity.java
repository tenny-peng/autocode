package com.tenny.autocode.entity;

import java.util.List;
import java.util.Map;

public class CodeEntity {
	
	private String dbType;
	
	private String dbUrl;
	
	private String dbUser;
	
	private String dbPw;
	
	private String dbTable;
	
	private String entityName;
	
	List<Map<String,String>> fieldList;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPw() {
		return dbPw;
	}

	public void setDbPw(String dbPw) {
		this.dbPw = dbPw;
	}

	public String getDbTable() {
		return dbTable;
	}

	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<Map<String, String>> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Map<String, String>> fieldList) {
		this.fieldList = fieldList;
	} 

}
