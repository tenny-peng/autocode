package com.starcare.ecg.${entityName?uncap_first};

import java.util.List;

public interface I${entityName}Service {

	/**
	 * 获取数据总量
	 * @param ${entityName?uncap_first}Entity
	 * @return
	 */
	public Integer count(${entityName}Entity ${entityName?uncap_first}Entity);
	
	/**
	 * 分页查询
	 * @param ${entityName?uncap_first}Entity
	 * @return
	 */
	public List<${entityName}Entity> list(${entityName}Entity ${entityName?uncap_first}Entity);
	
	/**
	 * 新增
	 * @param ${entityName?uncap_first}Entity
	 * @return
	 */
	public void add(${entityName}Entity ${entityName?uncap_first}Entity);
	
	/**
	 * 修改
	 * @param ${entityName?uncap_first}Entity
	 * @return
	 */
	public void update(${entityName}Entity ${entityName?uncap_first}Entity);
	
	/**
	 * 删除
	 * @param ${entityName?uncap_first}Ids
	 * @return
	 */
	public void delete(List<Integer> ${entityName?uncap_first}Ids);
	
	/**
	 * 查询已存在
	 * @param ${entityName?uncap_first}Entity
	 * @return
	 */
	public boolean exist(${entityName}Entity ${entityName?uncap_first}Entity);

}
