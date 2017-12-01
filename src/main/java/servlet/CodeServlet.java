package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import utils.FreemarkerUtil;

public class CodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String db_type = request.getParameter("db_type");
    	String db_url = request.getParameter("db_url");
    	String db_user = request.getParameter("db_user");
    	String db_pw = request.getParameter("db_pw");
    	String db_table = request.getParameter("db_table");
    	String entityName = request.getParameter("entityName");// 大写开头
    	String interfaceName = request.getParameter("interfaceName");// 大写开头
    	if(null == interfaceName || interfaceName.equals("")){
    		interfaceName = entityName;
    	}
    	String fieldListStr = request.getParameter("fieldList");// 小写开头
    	List<Map<String,String>> fieldList = (List<Map<String, String>>) JSON.parse(fieldListStr);
    	
    	Map<String, String> bzClass = FreemarkerUtil.getAllClass(db_type, db_url, db_user, db_pw, db_table, entityName, interfaceName, fieldList);
    	
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	JSONObject objJson = new JSONObject();
    	objJson.put("bzClass", bzClass);
    	out.println(objJson);
    	out.flush();
    }
    
}
