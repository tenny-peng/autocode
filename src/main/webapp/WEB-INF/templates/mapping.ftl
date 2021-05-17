<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.starcare.ecg.dao.${entityName}Dao">
	<resultMap type="com.starcare.ecg.${entityName}Entity" id="BaseResultMap">
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
	
	<!-- 数据总量 -->
	<select id="count" resultType="Integer">
		SELECT
			count(${entityName?uncap_first}_id)
		FROM ${tableName}
		<where>
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
		</where>
	</select>
	
	<!-- 分页查询 -->
	<select id="list" resultMap="BaseResultMap">
		SELECT
			<include refid="Base_Column_List"></include>
		FROM ${tableName}
		<where>
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
		</where>
		ORDER BY ${r'${'}orderColumn} ${r'${'}orderType}, ${entityName?uncap_first}_id LIMIT ${r'#{'}length} OFFSET ${r'#{'}offset}
	</select>
	
	<!-- 添加 -->
	<insert id="add">
		<#assign keyField = params[0].fieldName>
		<#list params as param>
		<#if param.isKey == "true">
		<#assign keyField = param.fieldName>
		</#if>
		</#list>
		INSERT INTO ${tableName}(
			<#list params as param>
			<#if param.isKey != "true">
			${param.columnName},
			</#if>
			</#list>
		)
		VALUES(
			<#list params as param>
				<#if param.isKey != "true">
			${r'#{'}${param.fieldName}},
				</#if>
			</#list>
		)
	</insert>
	
	<!-- 修改 -->
	<update id="update">
		UPDATE ${tableName}
		<set>
			<#list params as param>
			<#if param.isKey != "true">
			<if test="${param.fieldName} != null">
				${param.columnName} = ${r'#{'}${param.fieldName}},
			</if>
			</#if>
			</#list>
		</set>
		WHERE
		<#list params as param>
			<#if param.isKey == "true">
				${param.columnName} = ${r'#{'}${param.fieldName}}
			</#if>
		</#list>
	</update>
	
	<!-- 删除 -->
	<delete id="delete">
		DELETE FROM ${tableName}
		WHERE ${entityName?uncap_first}_id IN
		<foreach collection="list" item="item" index="index" separator="," open="(" close=")">
			${r'#{'}item}
		</foreach>
	</delete>
	
	<!-- 查询已存在 -->
	<select id="exist" resultMap="BaseResultMap">
		SELECT
			<include refid="Base_Column_List" />
		FROM ${tableName}
		WHERE ${entityName?uncap_first}_code = ${r'#{'}${entityName?uncap_first}Code} 
		<if test="${entityName?uncap_first}Id != null">
			AND ${entityName?uncap_first}_id != ${r'#{'}${entityName?uncap_first}Id}
	    </if>
	</select>
	
</mapper>