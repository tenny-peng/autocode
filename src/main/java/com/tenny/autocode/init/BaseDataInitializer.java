package com.tenny.autocode.init;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tenny.autocode.util.FreemarkerUtil;

@Component
public class BaseDataInitializer implements ApplicationRunner {

	
	Logger logger = LoggerFactory.getLogger(BaseDataInitializer.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        // 初始化模板资源到内存
		String tempServletContext = courseFile + "\\src\\main\\webapp\\";
		FreemarkerUtil.init(tempServletContext);
		logger.info("---------->>> init templates success.");
		
	}

}
