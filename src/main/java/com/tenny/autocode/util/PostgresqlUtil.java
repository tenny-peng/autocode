package com.tenny.autocode.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresqlUtil {
	private static String DRIVER_CLASS = "org.postgresql.Driver";
    private static String DATABASE_URL = "jdbc:postgresql://127.0.0.1:54321/postgres";
    private static String DATABASE_URL_PREFIX = "jdbc:postgresql://";
    private static String DATABASE_USER = "postgres";
    private static String DATABASE_PASSWORD = "tenny2019";
    private static Connection con = null;
    
    /**
     * 初始化数据库链接
     * @param db_url 数据库地址
     * @param db_user 用户名
     * @param db_pw 密码
     */
    public static void init(String db_url, String db_user, String db_pw){
    	try {
    		DATABASE_URL = DATABASE_URL_PREFIX + db_url;
    		DATABASE_USER = db_user;
    		DATABASE_PASSWORD = db_pw;
    		Class.forName(DRIVER_CLASS);
    		con = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 获取当前用户所有表
     * @return
     */
    public static List<String> getTables(){
    	List<String> tables = new ArrayList<String>();
    	
    	try {
    		Statement stmt = con.createStatement();
    		String sql = "SELECT tablename FROM pg_tables WHERE tablename NOT LIKE 'pg%' AND tablename NOT LIKE 'sql_%' ORDER BY tablename";
    		ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				tables.add(rs.getString("tablename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return tables;
    }
    
    /**
     * 获取表字段信息
     * @param tableName
     * @return
     */
    public static List<Map<String,String>> getTableCloumns(String tableName){
    	List<Map<String,String>> columns = new ArrayList<Map<String,String>>();
    	try{
            Statement stmt = con.createStatement();
             
            String sql = "select column_name, data_type, column_default, is_nullable from information_schema.columns where table_name='" + tableName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("columnName", rs.getString("column_name"));
                map.put("dataType", rs.getString("data_type"));
                map.put("isKey", ParamUtil.isEmpty(rs.getString("column_default"))?"false":"true");
                map.put("notNull", rs.getString("is_nullable").equals("YES")?"false":"true");
                map.put("comment", rs.getString("column_name"));
                columns.add(map);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(null != con){
	            try {
	        		con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
		return columns;
    }
    
}
