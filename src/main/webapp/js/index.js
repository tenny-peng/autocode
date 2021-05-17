$(function(){
	// 默认添加一个字段
	addField();
	// 获取输出标签
	getTabs();
	// 获取模板数据
	getTemplates();
	// 获取缓存信息
	getStorage();
})

var divIndex = 0;
// 初始生成动态字段域
function addField() {
	var divId = "div" + divIndex;
	var oDiv = $("<div id=" + divId + ">").appendTo($('#mainDiv'));
	$(oDiv).append("字段名：");
	$(oDiv).append($("<input type='text' name='fieldName' />"));
	$(oDiv).append("类型：");
	$(oDiv).append($("<select name='fieldType'></select>")
			.append("<option value='String'>String</option>")
			.append("<option value='Integer'>Integer</option>")
			.append("<option value='Date'>Date</option>"));
	$(oDiv).append("长度：");
	$(oDiv).append($("<input type='text' name='fieldLength' value='30' maxlength='5' class='input_length' onkeyup='value=value.replace(/[^0-9]/, \"\")' />"));
	$(oDiv).append("非空");
	$(oDiv).append($("<input type='checkbox' name='notNull' />"));
	$(oDiv).append("主键");
	$(oDiv).append($("<input type='checkbox' name='isKey' />"));
	$(oDiv).append("备注：");
	$(oDiv).append($("<input type='text' name='fieldNote' />"));
	$(oDiv).append($("<button class='delBtn' onclick=$('#" + divId + "').remove();>删除字段</button>"));
	$(oDiv).append($("<br/>"))
	divIndex++;
}

// 初始加载输出域
function getTabs(){
	// 外层tab
	var $tab_li = $('#tab > ul > li');
	$tab_li.hover(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
		var index = $tab_li.index(this);
		$('#tab > .tab_box > div').eq(index).show().siblings().hide();
	});
	
	// 业务类tab
	$('#class > .tab_box > textarea').html("等待生成业务类。。。");
	var $class_li = $('#class > ul > li');
	$class_li.hover(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
		var index = $class_li.index(this);
		$('#class > .tab_box > textarea').eq(index).show().siblings().hide();
	});
	
	// 模板tab
	$('#template > .tab_box > pre').html("等待加载模板。。。");
	var $template_li = $('#template > ul > li');
	$template_li.hover(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
		var index = $template_li.index(this);
		$('#template > .tab_box > pre').eq(index).show().siblings().hide();
	});
}

function getTemplates(){
	$.ajax({
		type: "post",
		url: "template/getTemplates",
		data: {},
		dataType: 'json',
		success: function(result) {
			$("#controllerTemplate").html(result.data.controller);
			$("#serviceTemplate").html(result.data.service);
			$("#serviceImplTemplate").html(result.data.serviceImpl);
			$("#daoTemplate").html(result.data.dao);
			$("#mappingTemplate").html(result.data.mapping);
			$("#entityTemplate").html(result.data.entity);
		}
	});
}

// 获取数据库表列表
function getTables(){
	 var dataObj = {
		"dbType": $("select[name='db_type']").val(),
		"dbUrl": $("input[name='db_url']").val(),
		"dbUser": $("input[name='db_user']").val(),
		"dbPw": $("input[name='db_pw']").val()
    }
	$.ajax({
		type: "post",
		url: "db/getTables",
		data: dataObj,
		dataType: 'json',
		success: function(result) {
			if(!result || !result.data){
				return;
			}
			var tableSelect = $("select[name='db_table']");
			$.each(result.data, function(){
				tableSelect.append("<option value='" + this + "'>"  + this + "</option>");
			});
		}
	});
}

// 选择数据库表后生成默认实体名
function getDafaultEntityName(){
	var entityName = "";
	var tableSplit =  $("select[name='db_table']").val().split("_");
	for(var i=0; i<tableSplit.length; i++){
		if(i == 0){
			continue;
		}
		entityName = entityName + toUpString(tableSplit[i].toLowerCase()); 
	}
	$("input[name='entityName']").val(entityName);
	$("input[name='interfaceName']").val(entityName);
}

// 首字母大写
function toUpString(value){
	if(!value){
		return value;
	}
	if(value.length == 1){
		return value.toUpperCase();
	}
	return value.substring(0, 1).toUpperCase() + value.substring(1, value.length);
}

//后台获取业务类代码
function getCode() {
	var fieldList = [];
    $("#mainDiv>div").each(function(){
    	fieldList.push({
    		fieldName: $(this).find("input[name='fieldName']").val(),
            fieldType: $(this).find("select[name='fieldType']").val(),
            fieldLength: $(this).find("input[name='fieldLength']").val(),
            notNull: $(this).find("input[type=checkbox][name='notNull']").is(':checked'),
            isKey: $(this).find("input[type=checkbox][name='isKey']").is(':checked'),
            fieldNote: $(this).find("input[name='fieldNote']").val()
        });
    });
    var dataObj = {
		"dbType": $("select[name='db_type']").val(),
		"dbUrl": $("input[name='db_url']").val(),
		"dbUser": $("input[name='db_user']").val(),
		"dbPw": $("input[name='db_pw']").val(),
		"dbTable": $("select[name='db_table']").val(),
		"entityName": $("input[name='entityName']").val(),
		"interfaceName": $("input[name='interfaceName']").val(),
		"fieldList": fieldList
    }
    setStorage(dataObj);
	$.ajax({
		type: "post",
		url: "code/getCode",
		data: dataObj,
		dataType: 'json',
		success: function(result) {
			$("#controller").html(result.data.controller);
			$("#service").html(result.data.service);
			$("#serviceImpl").html(result.data.serviceImpl);
			$("#dao").html(result.data.dao);
			$("#mapping").html(result.data.mapping);
			$("#entity").html(result.data.entity);
		}
	});
}

function setStorage(data){
	localStorage.setItem("dbType", data["dbType"]); 
	localStorage.setItem("dbUrl", data["dbUrl"]); 
	localStorage.setItem("dbUser", data["dbUser"]); 
	localStorage.setItem("dbPw", data["dbPw"]); 
	localStorage.setItem("dbTable", data["dbTable"]); 
	localStorage.setItem("entityName", data["entityName"]); 
	localStorage.setItem("interfaceName", data["interfaceName"]); 
	localStorage.setItem("fieldList", data["fieldList"]); 
}

function getStorage(){
	$("select[name='db_type']").val(localStorage.getItem("dbType")?localStorage.getItem("dbType"):"Postgresql"),
	$("input[name='db_url']").val(localStorage.getItem("dbUrl")),
	$("input[name='db_user']").val(localStorage.getItem("dbUser")),
	$("input[name='db_pw']").val(localStorage.getItem("dbPw")),
	$("select[name='db_table']").val(localStorage.getItem("dbTable")?localStorage.getItem("dbTable"):"none"),
	$("input[name='entityName']").val(localStorage.getItem("entityName")),
	$("input[name='interfaceName']").val(localStorage.getItem("interfaceName"))
}

//回到顶部
function toTop(){
	window.scrollTo(0, 0);
}