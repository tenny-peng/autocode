package com.tenny.autocode.controller;

import static com.tenny.autocode.util.ParamUtil.isEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenny.autocode.common.Result;
import com.tenny.autocode.entity.CodeEntity;
import com.tenny.autocode.util.FreemarkerUtil;

@RestController
@RequestMapping("code")
public class CodeController {
    
    @RequestMapping("getCode")
    public Result getCode(CodeEntity entity) {
    	Map<String, String> bzClass = new HashMap<String, String>();
    	
    	// 从已有数据库表获取字段产生业务类
    	if(!isEmpty(entity.getDbTable()) && !isEmpty(entity.getEntityName())){
    		bzClass = FreemarkerUtil.getAllClassFromDB(entity);
    	}else if(!isEmpty(entity.getEntityName())){
    		// 从页面表单字段产生业务类
    		bzClass = FreemarkerUtil.getAllClassFromPage(entity);
    	}
    
    	return Result.ok(bzClass);
    }
    
}
