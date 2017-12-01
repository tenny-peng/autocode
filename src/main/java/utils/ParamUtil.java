package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamUtil {
	
	// 数据库类型转属性类型
	public static Map<String, String> dataTypeMap;
	
	static{
		dataTypeMap = new HashMap<String, String>();
		dataTypeMap.put("NUMBER", "Integer");
		dataTypeMap.put("NVARCHAR2", "String");
		dataTypeMap.put("TIMESTAMP", "Date");
		dataTypeMap.put("TIMESTAMP(6)", "Date");
		dataTypeMap.put("varchar", "String");
		dataTypeMap.put("int", "Integer");
		dataTypeMap.put("datetime", "Date");
	}
	
	/**
	 * 产生最终参数
	 * @param db_type 数据库类型
	 * @param db_url 数据库url
	 * @param db_user 数据库用户名
	 * @param db_pw 数据库密码
	 * @param db_table 数据库表名
	 * @param entityName 实体类名
	 * @param interfaceName 接口名
	 * @param fieldList 字段属性
	 * @return Map<String, Object> 最终模板参数
	 */
	public static Map<String, Object> getFinalParam(String db_type, String db_url, String db_user, String db_pw, String db_table, String entityName, String interfaceName, List<Map<String,String>> fieldList){
	    Map<String, Object> beanMap = new HashMap<String, Object>();
	    // 从已有数据库表获取字段
        if(!isEmpty(db_type) && !isEmpty(db_url) && !isEmpty(db_user) && !isEmpty(db_pw) && !isEmpty(db_table) && !isEmpty(entityName)){
            beanMap = getFinalParam(db_table, entityName, interfaceName, getFieldsFromTable(db_type, db_url, db_user, db_pw, db_table));
        }else if(!isEmpty(entityName)){
            // 从页面表单字段
            beanMap = getFinalParam(entityName, entityName, interfaceName, getFieldsFromForm(fieldList));
        }
        return beanMap;
	}

	/**
	 * 拼装最终参数
	 * @param entityName
	 * @param interfaceName
	 * @param fieldList
	 * @return
	 */
	public static Map<String, Object> getFinalParam(String tableName, String entityName, String interfaceName, List<Map<String,String>> fieldList){
		Map<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("tableName", tableName);// 数据库表名
		beanMap.put("entityName", entityName);// 实体类名
		beanMap.put("interfaceName", interfaceName);// 接口名
		beanMap.put("params", fieldList);
		return beanMap;
	}
	
	/**
     * 将数据库字段解析为模板参数
     * @param fieldList
     * @return
     */
    public static List<Map<String,String>> getFieldsFromTable(String db_type, String db_url, String db_user, String db_pw, String db_table){
        List<Map<String, String>> columnList = new ArrayList<Map<String, String>>();
        if(db_type.equals("Oracle")){
            OracleUtil.init(db_url, db_user, db_pw);
            columnList = OracleUtil.getTableCloumns(db_table);
        }
        if(db_type.equals("Mysql")){
            MysqlUtil.init(db_url, db_user, db_pw);
            columnList = MysqlUtil.getTableCloumns(db_table);
        }
        for(Map<String, String> column : columnList){
            column.put("columnName", column.get("columnName"));// 数据库字段名
            column.put("fieldName", column2Property(column.get("columnName")));// 实体属性名
            column.put("fieldType", columnType2FieldType(column.get("dataType")));// 实体属性类型
            column.put("fieldNote", column.get("comment"));// 字段说明，即属性说明
            column.put("notNull", column.get("notNull"));// 非空
            column.put("isKey", column.get("isKey"));// 是否主键
        }
        return columnList;
    }
	
	/**
	 * 将页面字段解析为模板参数
	 * @param fieldList
	 * @return
	 */
	public static List<Map<String,String>> getFieldsFromForm(List<Map<String,String>> fieldList){
	    for(Map<String, String> field : fieldList){
            field.put("columnName", field.get("fieldName"));// 数据库字段名
            // The only legal comparisons are between two numbers, two strings, or two dates.
            field.put("isKey", String.valueOf(field.get("isKey")));// true-->"true", false-->"false"
            field.put("notNull", String.valueOf(field.get("notNull")));// 同上
        }
	    return fieldList;
	}
	
	// 数据库字段转属性【eg：USER_ID --> userId】
 	public static String column2Property(String fieldName){
 		StringBuffer result = new StringBuffer();
 		
 		fieldName = fieldName.toLowerCase();
 		String[] fields = fieldName.split("_");
 		for(int i=0; i<fields.length; i++){
 			String field = fields[i];
 			if(i == 0){
 				result.append(field);
 			}else{
 				result.append(toUpString(field));
 			}
 		}
 		
 		return result.toString();
 	}
 	
 	 // 首字母大写【eg：user --> User】
     public static String toUpString(String className) {
         char[] cs = className.toCharArray();
         cs[0] -= 32;
         String ClassName = String.valueOf(cs);
         return ClassName;
     }
     
     // 首字母小写【eg：User --> user】
     public static String toLowString(String className) {
    	 char[] cs = className.toCharArray();
    	 cs[0] += 32;
    	 String ClassName = String.valueOf(cs);
    	 return ClassName;
     }
     
     // 数据库类型转属性类型【eg: NVARCHAR2 --> String】
     public static String columnType2FieldType(String columnType){
     	return dataTypeMap.get(columnType);
     }
     
     public static boolean isEmpty(String value){
     	if(null == value || value.equals("")){
     		return true;
     	}
     	return false;
     }
	
}
