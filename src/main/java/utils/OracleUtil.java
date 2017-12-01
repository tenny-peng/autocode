package utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleUtil {
	private static String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static String DATABASE_URL = "jdbc:oracle:thin:@192.168.2.90:1521:orcl";
    private static String DATABASE_URL_PREFIX = "jdbc:oracle:thin:@";
    private static String DATABASE_USER = "swdev";
    private static String DATABASE_PASSWORD = "swdev";
    private static Connection con = null;
    private static DatabaseMetaData dbmd = null;
    
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
    		dbmd = con.getMetaData();
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
			ResultSet rs = dbmd.getTables("null", DATABASE_USER.toUpperCase(), "%", new String[] { "TABLE" });
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
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
    public static List<String> getColumns(String tableName){
    	List<String> columns = new ArrayList<String>();
		try {
			ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
			while (rs.next()) {
				columns.add(rs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return columns;
    }
    
    /**
     * 获取表字段
     * @param Table
     * @return
     */
    public static List<Map<String,String>> getTableCloumns(String Table){
    	
        List<Map<String,String>> columns = new ArrayList<Map<String,String>>();
         
        try{
            Statement stmt = con.createStatement();
             
            String sql = 
             "select "+
             "         comments as \"comments\","+
             "         a.COLUMN_NAME \"columnName\","+
             "         a.DATA_TYPE as \"dataType\","+
             "         b.comments as \"comment\","+
             "         decode(c.column_name,null,'false','true') as \"isKey\","+
             "         decode(a.NULLABLE,'N','true','Y','false','') as \"notNull\","+
             "         '' \"sequence\""+
             "   from "+
             "       all_tab_columns a, "+
             "       all_col_comments b,"+
             "       ("+
             "        select a.constraint_name, a.column_name"+
             "          from user_cons_columns a, user_constraints b"+
             "         where a.constraint_name = b.constraint_name"+
             "               and b.constraint_type = 'P'"+
             "               and a.table_name = '"+ Table +"'"+
             "       ) c"+
             "   where "+
             "     a.Table_Name=b.table_Name "+
             "     and a.column_name=b.column_name"+
             "     and a.Table_Name='"+ Table +"'"+
             "     and a.owner=b.owner "+
             "     and a.owner='"+ DATABASE_USER.toUpperCase() +"'"+
             "     and a.COLUMN_NAME = c.column_name(+)" +
             "  order by a.COLUMN_ID";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("comments", rs.getString("comments"));
                map.put("columnName", rs.getString("columnName"));
                map.put("dataType", rs.getString("dataType"));
                map.put("comment", rs.getString("comment"));
                map.put("isKey", rs.getString("isKey"));
                map.put("notNull", rs.getString("notNull"));
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
