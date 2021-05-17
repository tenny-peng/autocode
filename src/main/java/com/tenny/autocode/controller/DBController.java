package com.tenny.autocode.controller;

import static com.tenny.autocode.util.ParamUtil.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenny.autocode.common.Result;
import com.tenny.autocode.entity.CodeEntity;
import com.tenny.autocode.util.MysqlUtil;
import com.tenny.autocode.util.OracleUtil;
import com.tenny.autocode.util.PostgresqlUtil;

@RestController
@RequestMapping("db")
public class DBController {
	
	@RequestMapping("getTables")
	public Result getTables(CodeEntity entity) {
		Result result = new Result();
    	List<String> tables = new ArrayList<String>();
    	
    	// 从数据库获取表
    	if(!isEmpty(entity.getDbType())){
    		if(entity.getDbType().equals("Postgresql")){
    			PostgresqlUtil.init(entity.getDbUrl(), entity.getDbUser(), entity.getDbPw());
    			tables = PostgresqlUtil.getTables();
    		}
    		if(entity.getDbType().equals("Mysql")){
    			MysqlUtil.init(entity.getDbUrl(), entity.getDbUser(), entity.getDbPw());
    			tables = MysqlUtil.getTables();
    		}
    		if(entity.getDbType().equals("Oracle")){
    			OracleUtil.init(entity.getDbUrl(), entity.getDbUser(), entity.getDbPw());
    			tables = OracleUtil.getTables();
    		}
    	}
    	
    	result.setData(tables);
    	return result;
    }
	
}
