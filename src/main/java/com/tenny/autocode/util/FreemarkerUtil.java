package com.tenny.autocode.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tenny.autocode.entity.CodeEntity;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.StringUtil;

public class FreemarkerUtil {
	
	// 公共资源
	public static String TEMP_SERVLET_CONTEXT = ""; // 项目路径[D:\xxxx\autocode\]
	
	public static String MODEL_ROOTPATH = "WEB-INF/templates/";
	
	public static String MODEL_SUFFIX = ".ftl";
	
	// 类模板名
	public static final List<String> modelNameList = new ArrayList<String>();; // 存储所有模板名
	
	public static final List<String> needTransferNameList = new ArrayList<String>();; // 存储结果需要转换【StringUtil.XMLEnc】的模板名
	
	public static String SERVICE_MODEL = "service";
	
	public static String SERVICEIMPL_MODEL = "serviceImpl";
	
	public static String DAO_MODEL = "dao";
	
	public static String MAPPING_MODEL = "mapping";
	
	public static String ENTITY_MODEL = "entity";
	
	public static String CONTROLLER_MODEL = "controller";
	
	// 工具类配置
	private static String DECODE = "UTF-8";
	
	private static final Configuration config  = new Configuration();;
	
	private static final StringTemplateLoader stringTemplateLoader = new StringTemplateLoader(); 
	
	private static Template template;
	
	private static Writer out = null;
	
	public static Map<String, String> templateMap; // 所有模板的字符串
	
	public static void init(String tempServletContext) {
		modelNameList.add(SERVICE_MODEL);
		modelNameList.add(SERVICEIMPL_MODEL);
		modelNameList.add(DAO_MODEL);
		modelNameList.add(MAPPING_MODEL);
		modelNameList.add(ENTITY_MODEL);
		modelNameList.add(CONTROLLER_MODEL);
		
		needTransferNameList.add(MAPPING_MODEL);
		
		TEMP_SERVLET_CONTEXT = tempServletContext;
		templateMap = getAllFileStr();
		for(Entry<String,String> entry : templateMap.entrySet()){
			stringTemplateLoader.putTemplate(entry.getKey(), entry.getValue());
		}
		config.setTemplateLoader(stringTemplateLoader);
	}
	
	/**
	 * 产生所有业务类(来源为表单输入)
	 * @param entity 实体类
	 */
	public static Map<String, String> getAllClassFromPage(CodeEntity entity){
		Map<String, Object> beanMap = ParamUtil.getFinalParamFromPage(entity);
		return getAllClass(beanMap);
	}
	
	/**
	 * 产生所有业务类(来源为数据库)
	 * @param entity 实体类
	 */
	public static Map<String, String> getAllClassFromDB(CodeEntity entity){
		Map<String, Object> beanMap = ParamUtil.getFinalParamFromDB(entity);
		return getAllClass(beanMap);
	}
	
	/**
	 * 产生所有业务类
	 * @param beanMap 属性集合
	 * @return Map<String, String> 业务类集合
	 */
	public static Map<String, String> getAllClass(Map<String, Object> beanMap){
		Map<String, String> bzClassMap = new HashMap<String, String>();
		
		String entityName = (String) beanMap.get("entityName");
		
		for(String modelName : modelNameList){
			if(needTransferNameList.contains(modelName)){
				bzClassMap.put(modelName, StringUtil.XMLEnc(getCLass(modelName, modelName + " of " + entityName, beanMap)));
			}else{
				bzClassMap.put(modelName, getCLass(modelName, modelName + " of " + entityName, beanMap));
			}
		}
		
		return bzClassMap;
	}

	/**
	 * 产生java类
	 * @param modle 模板字符串名【eg：entityTemplate】
	 * @param target java类名【eg：User】，仅打印调试用，实际产生结果为字符串
	 * @param rootMap 参数，包括接口名，实体名，实体属性等
	 * @return String 业务类
	 */
	public static String getCLass(String modle, String target, Object rootMap){
		StringWriter stringWriter = new StringWriter();
		try {
			template = config.getTemplate(modle, DECODE);
			out = new BufferedWriter(stringWriter);
			template.process(rootMap, out);
			out.flush();
			out.close();
			System.out.println("---" + target + "--- is already ok");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
	
	/**
	 * 将所有.ftl文件内容转换为字符串
	 * @return
	 */
	public static Map<String, String> getAllFileStr(){
		Map<String, String> fileStrMap = new HashMap<String, String>();
		
		for(String modelName : modelNameList){
			fileStrMap.put(modelName, file2Str(modelName + MODEL_SUFFIX));
		}
		
		return fileStrMap;
	}
    
	/**
	 * 将模板内容转成字符串
	 * @param modle 模板名【eg：entity.ftl】
	 * @return
	 */
	public static String file2Str(String modle){
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(TEMP_SERVLET_CONTEXT + MODEL_ROOTPATH + modle));
			String line = "";
			while((line = br.readLine()) != null){
				buffer.append(line).append(System.getProperty("line.separator"));// 保持原有换行格式
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 获取所有模板【为前端能正常显示，部分特殊符号需转换，如"<"转为"&lt;"】
	 * @return
	 */
	public static Map<String, String> getAllTemplateStr(){
		Map<String, String> templateStrMap = new HashMap<String, String>();
		for(String modelName : modelNameList){
			if(needTransferNameList.contains(modelName)){
				templateStrMap.put(modelName, StringUtil.XMLEnc(templateMap.get(modelName)));
			}else{
				templateStrMap.put(modelName, templateMap.get(modelName));
			}
		}
		return templateStrMap;
	}
}
