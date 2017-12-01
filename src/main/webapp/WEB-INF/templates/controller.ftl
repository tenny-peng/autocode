package com.tenny.${interfaceName?lower_case}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.RestController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tenny.${interfaceName?lower_case}.entity.${entityName};
import com.tenny.${interfaceName?lower_case}.service.${interfaceName}Service;

@RestController
@RequestMapping("/${interfaceName?uncap_first}")
public class ${interfaceName}Controller {
	
	private Logger logger = LoggerFactory.getLogger(${interfaceName}Controller.class);
	
	@Autowired
	private ${interfaceName}Service ${interfaceName?uncap_first}Service;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> list(@ModelAttribute ${entityName} ${entityName?uncap_first}){
		logger.info("${interfaceName?uncap_first} controller --- into list...");
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			List<${entityName}> list = ${interfaceName?uncap_first}Service.list(${entityName?uncap_first});
			result.put("data", list);
		} catch(Exception e){
			logger.error(e);
			result.put("success", false);
			result.put("message", e);
		}
		
		result.put("success", true);
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add(@ModelAttribute ${entityName} ${entityName?uncap_first}) {
		logger.info("${interfaceName?uncap_first} controller --- into add...");
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 验证必填属性
		<#list params as param>
		<#if param.notNull == "true">
			<#if param.fieldType == "String">
		if(StringUtils.isEmpty(${entityName?uncap_first}.get${param.fieldName?cap_first}())){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			<#else>
		if(null == ${entityName?uncap_first}.get${param.fieldName?cap_first}()){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			</#if>
		</#if>
		</#list>
		
		try {
			${interfaceName?uncap_first}Service.add(${entityName?uncap_first});
		} catch (Exception e) {
			logger.error(e);
            result.put("success", false);
            result.put("message", e);
		}
		
		result.put("success", true);
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object> update(@ModelAttribute ${entityName} ${entityName?uncap_first}) {
		logger.info("${interfaceName?uncap_first} controller --- into update...");
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 验证主键id
		<#list params as param>
		<#if param.isKey == "true">
		if(null == ${entityName?uncap_first}.get${param.fieldName?cap_first}()){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
		</#if>
		</#list>
		// 验证必填属性
		<#list params as param>
		<#if param.notNull == "true">
			<#if param.fieldType == "String">
		if(StringUtils.isEmpty(${entityName?uncap_first}.get${param.fieldName?cap_first}())){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			<#else>
		if(null == ${entityName?uncap_first}.get${param.fieldName?cap_first}()){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			</#if>
		</#if>
		</#list>
		
		try {
			${interfaceName?uncap_first}Service.update(${entityName?uncap_first});
		} catch (Exception e) {
			logger.error(e);
            result.put("success", false);
            result.put("message", e);
		}
		
		result.put("success", true);
        return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@ModelAttribute ${entityName} ${entityName?uncap_first}) {
		logger.info("${interfaceName?uncap_first} controller --- into delete...");
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 验证主键id
		<#list params as param>
		<#if param.isKey == "true">
		if(null == ${entityName?uncap_first}.get${param.fieldName?cap_first}()){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
		</#if>
		</#list>
		
		try {
			${interfaceName?uncap_first}Service.delete(${entityName?uncap_first});
		} catch (Exception e) {
			logger.error(e);
            result.put("success", false);
            result.put("message", e);
		}
		
		result.put("success", true);
        return result;
	}
	
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	public Map<String, Object> exist(@ModelAttribute ${entityName} ${entityName?uncap_first}) {
		logger.info("${interfaceName?uncap_first} controller --- into exist...");
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 验证字段存在
		<#list params as param>
		<#if param.notNull == "true">
			<#if param.fieldType == "String">
		if(StringUtils.isEmpty(${entityName?uncap_first}.get${param.fieldName?cap_first}())){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			<#else>
		if(null == ${entityName?uncap_first}.get${param.fieldName?cap_first}()){
			result.put("success", false);
            result.put("message", "${param.fieldName} can not be null");
            return result;
		}
			</#if>
		</#if>
		</#list>
		
		try {
			boolean isExist = ${interfaceName?uncap_first}Service.exist(${entityName?uncap_first});
			result.put("data", isExist);
		} catch (Exception e) {
			logger.error(e);
            result.put("success", false);
            result.put("message", e);
		}
		
		result.put("success", true);
        return result;
	}
	
}