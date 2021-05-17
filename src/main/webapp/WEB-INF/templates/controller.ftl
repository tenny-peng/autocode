package com.starcare.ecg.controller.${entityName?lower_case};

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starcare.common.util.Result;
import com.starcare.ecg.${entityName?uncap_first}.I${entityName}Service;
import com.starcare.ecg.${entityName?uncap_first}.${entityName}Entity;
import com.starcare.ecg.exception.EcgException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("${entityName?uncap_first}")
public class ${entityName}Controller {
	
	Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);
	
	@Autowired
	private I${entityName}Service ${entityName?uncap_first}Service;
	
	@ApiOperation("查询")
	@PostMapping("list")
	public Result list(${entityName}Entity ${entityName?uncap_first}Entity){
		Result result = new Result();
		
		// 总数
		Integer totalCount = ${entityName?uncap_first}Service.count(${entityName?uncap_first}Entity);
		result.put("totalCount", totalCount);
		
		List<${entityName}Entity> list = ${entityName?uncap_first}Service.list(${entityName?uncap_first}Entity);
		result.put("data", list);
		
		return result;
		
	}
	
	@ApiOperation("新增")
	@PostMapping("add")
	public Result add(${entityName}Entity ${entityName?uncap_first}Entity) {
		${entityName?uncap_first}Service.add(${entityName?uncap_first}Entity);
		
		return new Result();
	}
	
	@ApiOperation("更新")
	@PostMapping("update")
	public Result update(${entityName}Entity ${entityName?uncap_first}Entity) {
		Result result = new Result();
		
		// 验证主键id
		if(null == ${entityName?uncap_first}Entity.get${entityName}Id()){
			throw new EcgException("0x00y");
		}
		
		${entityName?uncap_first}Service.update(${entityName?uncap_first}Entity);
		
        return result;
	}
	
	@ApiOperation("删除")
	@PostMapping("delete")
	public Result delete(@RequestParam List<Integer> ${entityName?uncap_first}Ids) {
		Result result = new Result();
		
		// 验证主键id
		if(null == ${entityName?uncap_first}Ids || ${entityName?uncap_first}Ids.isEmpty()){
			throw new EcgException("0x00y");
		}
		
		${entityName?uncap_first}Service.delete(${entityName?uncap_first}Ids);
		
        return result;
	}
	
}
