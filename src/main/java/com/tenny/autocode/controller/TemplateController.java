package com.tenny.autocode.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenny.autocode.common.Result;
import com.tenny.autocode.util.FreemarkerUtil;

@RestController
@RequestMapping("template")
public class TemplateController {
	
	@RequestMapping("getTemplates")
	public Result doPost() throws ServletException, IOException {
		Result result = new Result();
    	result.setData(FreemarkerUtil.getAllTemplateStr());
    	return result;
    }
	
}
