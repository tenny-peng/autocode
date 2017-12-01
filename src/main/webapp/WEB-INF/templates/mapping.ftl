<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.tenny.${interfaceName?lower_case}.dao.${interfaceName}Dao">
	<resultMap type="com.tenny.${interfaceName?lower_case}.entity.${entityName}" id="BaseResultMap">
	<#list params as param>
		<result column="${param.columnName}" property="${param.fieldName}" />
	</#list>
	</resultMap>
	
	<!-- 手动去掉最后一个逗号和本注释 -->
	<sql id="Base_Column_List">
	<#list params as param>
		${param.columnName},
	</#list>
	</sql>
	
	<!-- 列表查询 -->
	<select id="list" resultMap="BaseResultMap">
		SELECT
			<include refid="Base_Column_List"></include>
		FROM ${tableName}
		WHERE 1=1
		<#list params as param>
		<#if param.fieldType == "String">
		<if test="${param.fieldName} != null and ${param.fieldName} != ''">
			AND ${param.columnName} like '%' || ${r'#{'}${param.fieldName}} || '%'
		</if>
		<#elseif param.fieldType == "Date">
		<if test="${param.fieldName}Start != null">
			<![CDATA[ AND ${param.columnName} >= ${r'#{'}${param.fieldName}Start}]]>
		</if>
		<if test="${param.fieldName}End != null">
			 <![CDATA[AND ${param.columnName} <= ${r'#{'}${param.fieldName}End}]]>
		</if>
		<#else>
		<if test="${param.fieldName} != null">
			AND ${param.columnName} = ${r'#{'}${param.fieldName}}
		</if>
		</#if>
		</#list>
	</select>
	
	<!-- 添加  Oracle主键不能自增，需要使用序列， Mysql主键可以自增，故Mysql时下方序列及id字段删除-->
	<insert id="add">
		<#assign keyField = params[0].fieldName>
		<#list params as param>
		<#if param.isKey == "true">
		<#assign keyField = param.fieldName>
		</#if>
		</#list>
		<selectKey resultType="Integer" keyProperty="${keyField}" order="BEFORE">  
	    	SELECT ${tableName}_SEQ.nextval FROM DUAL
	    </selectKey>
		INSERT INTO ${tableName}(
			<#list params as param>
			${param.columnName},
			</#list>
		)
		VALUES(
			<#list params as param>
			${r'#{'}${param.fieldName}},
			</#list>
		)
	</insert>
	
	<!-- 修改 -->
	<update id="update">
		UPDATE ${tableName}
		<set>
			<#list params as param>
			<if test="${param.fieldName} != null">
				${param.columnName} = ${r'#{'}${param.fieldName}},
			</if>
			</#list>
		</set>
		WHERE 1=1
		<#list params as param>
		<#if param.isKey == "true">
		AND　${param.columnName} = ${r'#{'}${param.fieldName}}
		</#if>
		</#list>
	</update>
	
	<!-- 删除模块 -->
	<delete id="delete">
		DELETE FROM ${tableName}
		WHERE 1=1
		<#list params as param>
		<#if param.isKey == "true">
		AND　${param.columnName} = ${r'#{'}${param.fieldName}}
		</#if>
		</#list>
	</delete>
	
	<!-- 查询已存在 xyz是要验证的字段名 -->
	<select id="exist" resultMap="BaseResultMap">
		SELECT
		<include refid="Base_Column_List" />
		FROM ${tableName}
		WHERE 1=1
		AND XYZ = ${r'#{'}xyz}
		<#list params as param>
		<#if param.isKey == "true">
		<if test="${param.fieldName} != null">
			AND ${param.columnName} != ${r'#{'}${param.fieldName}}
	    </if>
		</#if>
		</#list>
	</select>
	
</mapper>