package com.tenny.${interfaceName?lower_case}.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tenny.${interfaceName?lower_case}.dao.${interfaceName}Dao;
import com.tenny.${interfaceName?lower_case}.entity.${entityName};
import com.tenny.${interfaceName?lower_case}.service.${interfaceName}Service;

@Service
public class ${interfaceName}ServiceImpl implements ${interfaceName}Service {

	@Resource
	private ${interfaceName}Dao ${interfaceName?uncap_first}Dao;
	
	@Override
	public List list(${entityName} ${entityName?uncap_first}) {
		List<${entityName}> ${entityName?uncap_first}List = ${interfaceName?uncap_first}Dao.list(${entityName?uncap_first});
		return ${entityName?uncap_first}List;
	}

	@Override
	public void add(${entityName} ${entityName?uncap_first}) throws Exception {
		// 检测xyz字段值已存在
		if(exist(${interfaceName?uncap_first})){
			throw new Exception("xyz is already exist!");
		}
		
		// 通常只有一个主键，如果有两个及以上，此处代码需手动修改合并主键项
		<#list params as param>
		<#if param.isKey == "true">
		${interfaceName?uncap_first}Dao.add(${entityName?uncap_first});
		</#if>
		</#list>
	}

	@Override
	public void update(${entityName} ${entityName?uncap_first}) throws Exception {
		// 验证是否存在
		${entityName} query${entityName} = new ${entityName}();
		<#list params as param>
		<#if param.isKey == "true">
		query${entityName}.set${param.fieldName?cap_first}(${entityName?uncap_first}.get${param.fieldName?cap_first}());
		</#if>
		</#list>
		List<${entityName}> ${entityName?uncap_first}List = ${interfaceName?uncap_first}Dao.list(query${entityName});
		if(null == ${entityName?uncap_first}List || 0 == ${entityName?uncap_first}List.size()){
			throw new Exception("${entityName} is not exist!");
		}
		// 某些状态不允许修改
		if(${entityName?uncap_first}List.get(0).get${interfaceName}Status() != -1){
			throw new Exception("${entityName} can not be modify!");
		}
		// 检测字段值已存在
		if(exist(${interfaceName?uncap_first})){
			throw new Exception("xyz is already exist!");
		}
		
		// 通常只有一个主键，如果有两个及以上，此处代码需手动修改合并主键项
		<#list params as param>
		<#if param.isKey == "true">
		${interfaceName?uncap_first}Dao.update(${entityName?uncap_first});
		</#if>
		</#list>
	}

	@Override
	public void delete(${entityName} ${entityName?uncap_first})  throws Exception {
		// 验证是否存在
		${entityName} query${entityName} = new ${entityName}();
		<#list params as param>
		<#if param.isKey == "true">
		query${entityName}.set${param.fieldName?cap_first}(${entityName?uncap_first}.get${param.fieldName?cap_first}());
		</#if>
		</#list>
		List<${entityName}> ${entityName?uncap_first}List = ${interfaceName?uncap_first}Dao.list(query${entityName});
		if(null == ${entityName?uncap_first}List || 0 == ${entityName?uncap_first}List.size()){
			throw new Exception("${entityName} is not exist!");
		}
		
		// 通常只有一个主键，如果有两个及以上，此处代码需手动修改合并主键项
		<#list params as param>
		<#if param.isKey == "true">
		${interfaceName?uncap_first}Dao.delete(${entityName?uncap_first}.get${param.fieldName?cap_first}());
		</#if>
		</#list>
	}

	@Override
	public boolean exist(${entityName} ${entityName?uncap_first}) {
		${entityName} exist${entityName} = ${interfaceName?uncap_first}Dao.exist(${entityName?uncap_first});
		if(exist${entityName} != null){
			return true;
		}
		
		return false;
	}

}