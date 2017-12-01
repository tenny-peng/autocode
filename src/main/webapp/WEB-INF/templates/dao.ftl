package com.tenny.${interfaceName?lower_case}.dao;

import java.util.List;

import com.tenny.${interfaceName?lower_case}.entity.${entityName};

public interface ${interfaceName}Dao {

	/**
	 * 分页查询
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public List<${entityName}> list(${entityName} ${entityName?uncap_first});
	
	/**
	 * 新增
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public void add(${entityName} ${entityName?uncap_first});
	
	/**
	 * 更新
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public void update(${entityName} ${entityName?uncap_first}N);
	
	<#list params as param>
	<#if param.isKey == "true">
	/**
	 * 删除
	 * @param ${param.fieldName}
	 */
	public void delete(Integer ${param.fieldName});
	</#if>
	</#list>
	
	/**
	 * 查询已存在
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public News exist(${entityName} ${entityName?uncap_first});
	
}