package com.tenny.test;

import java.util.List;
import org.junit.Test;

import com.tenny.${interfaceName?uncap_first}.entity.${entityName};
import com.tenny.${interfaceName?uncap_first}.service.${interfaceName};
import com.tenny.${interfaceName?uncap_first}.service.impl.${interfaceName}Impl;

public class Test${interfaceName} {
	
	private static ${interfaceName}Service ${interfaceName?uncap_first}Service = null;
	
	private static ${entityName} ${entityName?uncap_first} = null;
	
	static{
		 ${interfaceName?uncap_first}Service = new ${interfaceName}ServiceImpl();
	}
	
	@Test
	public void list() {
		${entityName?uncap_first} = new ${entityName}();
		List<${entityName}> ${entityName?uncap_first}List = ${interfaceName?uncap_first}Service.list(${entityName?uncap_first});
		System.out.println("data: " + ${entityName?uncap_first}List);
	}
	
	@Test
	public void add() {
		${entityName?uncap_first} = new ${entityName}();
		// 其他必填属性
		<#list params as param>
		<#if param.isKey == "false" && param.notNull == "true">
		${entityName?uncap_first}.set${param.fieldName?cap_first}(null);
		</#if>
		</#list>
		try {
            ${interfaceName?uncap_first}Service.add(${entityName?uncap_first});
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void update() {
		${entityName?uncap_first} = new ${entityName}();
		// 主键id
		<#list params as param>
		<#if param.isKey == "true">
		${entityName?uncap_first}.set${param.fieldName?cap_first}(1);
		</#if>
		</#list>
		// 其他必填属性
		<#list params as param>
		<#if param.isKey == "false" && param.notNull == "true">
		${entityName?uncap_first}.set${param.fieldName?cap_first}(null);
		</#if>
		</#list>
		
		try {
            ${interfaceName?uncap_first}Service.update(${entityName?uncap_first});
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void delete() {
		${entityName?uncap_first} = new ${entityName}();
		// 主键id
		<#list params as param>
		<#if param.isKey == "true">
		${entityName?uncap_first}.set${param.fieldName?cap_first}(1);
		</#if>
		</#list>
		
		try {
            ${interfaceName?uncap_first}Service.delete(${entityName?uncap_first});
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void exist() {
		${entityName?uncap_first} = new ${entityName}();
		// 其他必填属性
		<#list params as param>
		<#if param.isKey == "false" && param.notNull == "true">
		${entityName?uncap_first}.set${param.fieldName?cap_first}(null);
		</#if>
		</#list>
		
		boolean isExist = ${interfaceName?uncap_first}Service.exist(${entityName?uncap_first});
		System.out.println("isExist: " + isExist);
	}
	
}