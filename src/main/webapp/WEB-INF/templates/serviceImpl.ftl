package com.starcare.ecg.${entityName?uncap_first}.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.starcare.ecg.dao.${entityName?uncap_first}.${entityName}Dao;
import com.starcare.ecg.exception.EcgException;
import com.starcare.ecg.${entityName?uncap_first}.I${entityName}Service;
import com.starcare.ecg.${entityName?uncap_first}.${entityName}Entity;

@Service
public class ${entityName}ServiceImpl implements I${entityName}Service {
	
	@Resource
	private ${entityName}Dao ${entityName?uncap_first}Dao;
	
	@Override
	public Integer count(${entityName}Entity ${entityName?uncap_first}Entity) {
		return ${entityName?uncap_first}Dao.count(${entityName?uncap_first}Entity);
	}
	
	@Override
	public List<${entityName}Entity> list(${entityName}Entity ${entityName?uncap_first}Entity) {
		return ${entityName?uncap_first}Dao.list(${entityName?uncap_first}Entity);
	}

	@Override
	public void add(${entityName}Entity ${entityName?uncap_first}Entity) {
		if(exist(${entityName?uncap_first}Entity)){
			throw new EcgException("0x00y");
		}
		
		${entityName?uncap_first}Dao.add(${entityName?uncap_first}Entity);
	}

	@Override
	public void update(${entityName}Entity ${entityName?uncap_first}Entity) {
		// 检测字段值已存在
		if(exist(${entityName?uncap_first}Entity)){
			throw new EcgException("0x00y");
		}
		
		${entityName?uncap_first}Dao.update(${entityName?uncap_first}Entity);
	}

	@Override
	public void delete(List<Integer> ${entityName?uncap_first}Ids) {
		${entityName?uncap_first}Dao.delete(${entityName?uncap_first}Ids);
	}

	@Override
	public boolean exist(${entityName}Entity ${entityName?uncap_first}Entity) {
		${entityName}Entity exist${entityName}Entity = ${entityName?uncap_first}Dao.exist(${entityName?uncap_first}Entity);
		if(exist${entityName}Entity != null){
			return true;
		}
		
		return false;
	}

}
