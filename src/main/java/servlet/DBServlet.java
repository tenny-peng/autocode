package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import utils.MysqlUtil;
import utils.OracleUtil;
import static utils.ParamUtil.isEmpty;;

public class DBServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<String> tables = new ArrayList<String>();
    	
    	String db_type = request.getParameter("db_type");
    	String db_url = request.getParameter("db_url");
    	String db_user = request.getParameter("db_user");
    	String db_pw = request.getParameter("db_pw");
    	
    	// 从数据库获取表
    	if(!isEmpty(db_type)){
    		if(db_type.equals("Oracle")){
    			OracleUtil.init(db_url, db_user, db_pw);
    			tables = OracleUtil.getTables();
    		}
    		if(db_type.equals("Mysql")){
    		    MysqlUtil.init(db_url, db_user, db_pw);
    		    tables = MysqlUtil.getTables();
    		}
    	}
    	
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	JSONObject objJson = new JSONObject();
    	objJson.put("tables", tables);
    	out.println(objJson);
    	out.flush();
    }
	
}
