$(function(){
	addField(); // 默认添加一个字段
	getTabs(); // 获取输出标签
	getTemplates(); // 获取模板数据
	getStorage(); // 获取缓存信息
	textAreaAutoHeight();// 所有textarea自适应高度
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
		url: "getTemplates",
		data: {},
		dataType: 'json',
		success: function(result) {
			$("#serviceTemplate").html(result.templates.service);
			$("#serviceImplTemplate").html(result.templates.serviceImpl);
			$("#daoTemplate").html(result.templates.dao);
			$("#mappingTemplate").html(result.templates.mapping);
			$("#entityTemplate").html(result.templates.entity);
			$("#controllerTemplate").html(result.templates.controller);
			$("#testTemplate").html(result.templates.test);
		}
	});
}

// 获取数据库表列表
function getTables(){
	 var dataObj = {
		"db_type": $("select[name='db_type']").val(),
		"db_url": $("input[name='db_url']").val(),
		"db_user": $("input[name='db_user']").val(),
		"db_pw": $("input[name='db_pw']").val()
    }
	$.ajax({
		type: "post",
		url: "getTables",
		data: dataObj,
		dataType: 'json',
		success: function(result) {
			if(!result || !result.tables){
				return;
			}
			var tableSelect = $("select[name='db_table']");
			$.each(result.tables, function(){
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
		"db_type": $("select[name='db_type']").val(),
		"db_url": $("input[name='db_url']").val(),
		"db_user": $("input[name='db_user']").val(),
		"db_pw": $("input[name='db_pw']").val(),
		"db_table": $("select[name='db_table']").val(),
		"entityName": $("input[name='entityName']").val(),
		"interfaceName": $("input[name='interfaceName']").val(),
		"fieldList": JSON.stringify(fieldList)
    }
    setStorage(dataObj);
	$.ajax({
		type: "post",
		url: "getCode",
		data: dataObj,
		dataType: 'json',
		success: function(result) {
			$("#service").html(result.bzClass.service);
			$("#serviceImpl").html(result.bzClass.serviceImpl);
			$("#dao").html(result.bzClass.dao);
			$("#mapping").html(result.bzClass.mapping);
			$("#entity").html(result.bzClass.entity);
			$("#controller").html(result.bzClass.controller);
			$("#test").html(result.bzClass.test);
		}
	});
}

function setStorage(data){
	localStorage.setItem("db_type", data["db_type"]); 
	localStorage.setItem("db_url", data["db_url"]); 
	localStorage.setItem("db_user", data["db_user"]); 
	localStorage.setItem("db_pw", data["db_pw"]); 
	localStorage.setItem("db_table", data["db_table"]); 
	localStorage.setItem("entityName", data["entityName"]); 
	localStorage.setItem("interfaceName", data["interfaceName"]); 
	localStorage.setItem("fieldList", data["fieldList"]); 
}

function getStorage(){
	$("select[name='db_type']").val(localStorage.getItem("db_type")?localStorage.getItem("db_type"):"Mysql"),
	$("input[name='db_url']").val(localStorage.getItem("db_url")),
	$("input[name='db_user']").val(localStorage.getItem("db_user")),
	$("input[name='db_pw']").val(localStorage.getItem("db_pw")),
	$("select[name='db_table']").val(localStorage.getItem("db_table")?localStorage.getItem("db_table"):"none"),
	$("input[name='entityName']").val(localStorage.getItem("entityName")),
	$("input[name='interfaceName']").val(localStorage.getItem("interfaceName"))
}

// 所有textarea高度自适应
function textAreaAutoHeight(){
	$.each($("textarea"), function(i, n){
		autoHeight($(n)[0]);
	});
}

/**
 * 文本框根据输入内容自适应高度
 * {HTMLElement}   输入框元素
 * {Number}        设置光标与输入框保持的距离(默认0)
 * {Number}        设置最大高度(可选)
 */
function autoHeight(elem, extra, maxHeight) {
    extra = extra || 0;
    var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
    isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
        addEvent = function (type, callback) {
            elem.addEventListener ?
                elem.addEventListener(type, callback, false) :
                elem.attachEvent('on' + type, callback);
        },
        getStyle = elem.currentStyle ? 
        function (name) {
            var val = elem.currentStyle[name];
            if (name === 'height' && val.search(/px/i) !== 1) {
                var rect = elem.getBoundingClientRect();
                return rect.bottom - rect.top -
                       parseFloat(getStyle('paddingTop')) -
                       parseFloat(getStyle('paddingBottom')) + 'px';       
            };
            return val;
        } : function (name) {
            return getComputedStyle(elem, null)[name];
        },
    minHeight = parseFloat(getStyle('height'));
    elem.style.resize = 'both';//如果不希望使用者可以自由的伸展textarea的高宽可以设置其他值

    var change = function () {
        var scrollTop, height,
            padding = 0,
            style = elem.style;

        if (elem._length === elem.value.length) return;
        elem._length = elem.value.length;

        if (!isFirefox && !isOpera) {
            padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
        };
        scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

        elem.style.height = minHeight + 'px';
        if (elem.scrollHeight > minHeight) {
            if (maxHeight && elem.scrollHeight > maxHeight) {
                height = maxHeight - padding;
                style.overflowY = 'auto';
            } else {
                height = elem.scrollHeight - padding;
                style.overflowY = 'hidden';
            };
            style.height = height + extra + 'px';
            scrollTop += parseInt(style.height) - elem.currHeight;
            document.body.scrollTop = scrollTop;
            document.documentElement.scrollTop = scrollTop;
            elem.currHeight = parseInt(style.height);
        };
    };

    addEvent('propertychange', change);
    addEvent('input', change);
    addEvent('focus', change);
    change();
};

//回到顶部
function toTop(){
	window.scrollTo(0, 0);
}