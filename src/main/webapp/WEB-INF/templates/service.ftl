package com.tenny.${interfaceName?lower_case}.service;

import java.util.List;

import com.tenny.${interfaceName?lower_case}.entity.${entityName};

public interface ${interfaceName}Service {
	
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
	public void add(${entityName} ${entityName?uncap_first}) throws Exception ;
	
	/**
	 * 修改
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public void update(${entityName} ${entityName?uncap_first}) throws Exception ;
	
	/**
	 * 删除
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public void delete(${entityName} ${entityName?uncap_first}) throws Exception ;
	
	/**
	 * 查询已存在
	 * @param ${entityName?uncap_first}
	 * @return
	 */
	public boolean exist(${entityName} ${entityName?uncap_first});

}
