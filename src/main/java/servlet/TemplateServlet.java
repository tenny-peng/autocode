package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import utils.FreemarkerUtil;

public class TemplateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void init(){
		// 初始化模板资源到内存
		String tempServletContext = this.getServletConfig().getServletContext().getRealPath("/");
		FreemarkerUtil.init(tempServletContext);
		System.out.println("templates init success...");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	JSONObject objJson = new JSONObject();
    	objJson.put("templates", FreemarkerUtil.getAllTemplateStr());
    	out.println(objJson);
    	out.flush();
    }
	
}
