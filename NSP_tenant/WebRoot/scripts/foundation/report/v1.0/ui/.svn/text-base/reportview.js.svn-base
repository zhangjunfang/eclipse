var grid = null;
var columns = null;
$(function(){
	createQueryConditon();
});

function OnReady(){
	var url = path + "view/queryReportXml";
	var data = {id:reportId};
	$.post(url,data,function(data){
//		var json = JSON.parse(data);
		var json = data;
		var content = json.content;
		AF.func("Build", content);
//		if(data == 'false'){
//			$.MessageBox_alert('提示','报表解析失败','error');
//		}else{
//			AF.func("Build", data);
//			AF.func("CallFunc", "301\r\n3");
//		}
	});
}
function OnEvent(id, Event, p1, p2, p3, p4) {
};
function createReport(){
	AF.func("CallFunc","18\r\n");
//	AF.func("Print", "1\r\n");
}
//验证表单
function CheckDataValid(value) {
    if (!JudgeValidate('#'+value)) {
        return false;
    } else {
        return true;
    }
}
//点击查询按钮，获取数据
function queryData(){
	var data = $('#queryPanel').getFormValue();
	var checked = CheckDataValid('queryPanel');
	var value = {data:data,dc_id:dc_id, reportId:reportId};
	if(checked){
		var url = path + "view/queryReportData";
		$.post(url,value,function(data){
			if(data != null){
//				var json = JSON.parse(data);
				var json = data;
				grid.setStore(json);
				var root = "[";
				$.each(json.root,function(index,data){
					var cc = "{";
					$.each(columns,function(i,item){
						var str = i == 0 ? "":",";
						// 原来的，通过列表的索引值dataIndex作为key
						//cc += str+"\""+item.dataIdex+"\":"+"\""+eval("data."+item.dataIdex)+"\"";
						
						// 现在统一使用列表的header[显示列名称]作为key
						cc += str+"\""+item.header+"\":"+"\""+eval("data."+item.header)+"\"";
					});
					cc += "}";
					//console.log("-----> " + cc);
					var douhao = index == 0 ? "":",";
					root += douhao + cc;
				});
				root +="]";
				AF.func("SetSource", "ds1\r\n"+root);
                AF.func("Calc","");
			}
		});
	}
}
//获取要展示的字段信息
function queryReportFormData(){
	var url = path+"design/queryDsParamAndReportFields";
	var data = {reportId:reportId};
	columns = new Array();
	$.post(url,data,function(data){
//		var json = JSON.parse(data);
		var json = data;
		var data = json.checked;
		var width = 100/data.length;
		$.each(data,function(index,data){
			var column = {dataIdex:data.field_name,header:data.field_title,width:width+"%",align:"left"};
			columns.push(column);
		});
		initGrid(columns);
	});
}

function initGrid(columns){
	//console.log(columns);
	var opts = {
		columns:columns,
		proxy:{url:''},
		pageSize:20,
		width:"100%",
		height:$(window).height()-$('#toolbar1').height(),
		imgBasePath	:"/styles/foundation/report/images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}


function createQueryConditon(){
	var url = path + "customquery/queryparams" + bizParams;
	var data = {reportId:reportId};
	$.post(url,data,function(data){
		queryReportFormData();
		if(data == null) return;
//		var json = JSON.parse(data);
		var json = data;
		var form = json.form;
		var length = form.length;
		if(length>8){
			var totalline =  Math.ceil(length / 4);
			var tr = "";
			for(var i=1;i<=totalline;i++){
				tr += "<tr id='tr"+i+"'></tr>";
			}
			$('#queryPanel').append(tr);
			var j = 1;
			$.each(form,function(index,data){
				var widgetId = data.form_widget_id;
				var widgettype = data.form_widget_type;
				var validator = data.form_widget_validator;
				var widget_name = data.widget_id;
				var label = data.name;
				var val = data.default_value;
				var num = index + 1;
				var top_default = data.top_default;
				matchDom(widgetId,label,widgettype,j,validator,widget_name,top_default, val);
				if(num%4 == 0){
					j++;
				}
			});
		}else{
			var totalline =  Math.ceil(length / 2);
			var tr = "";
			for(var i=1;i<=totalline;i++){
				tr += "<tr id='tr"+i+"'></tr>";
			}
			$('#queryPanel').append(tr);
			var j = 1;
			$.each(form,function(index,data){
				var widgetId = data.form_widget_id;
				var widgettype = data.form_widget_type;
				var validator = data.form_widget_validator;
				var widget_name = data.widget_id;
				var label = data.name;
				var val = data.default_value;
				//console.log("------data.form_widget_id = " + data.form_widget_id);
				//console.log("val = " + data.value_field);
				var num = index + 1;
				var top_default = data.top_default;
				matchDom(widgetId,label,widgettype,j,validator,widget_name,top_default, val);
				if(num%2 == 0){
					j++;
				}
			});
		};
		
	});
}

function matchDom(widgetId,label,type,trnum,validator,widget_name,top_default, val){
	//
	var widget_validator = "";
	if(validator == '允许为空'){
		widget_validator = "";
	}else if(validator == '不能为空'){
		widget_validator = 'datacol="yes" err="'+label+'" checkexpession="NotNull"'; 
	}else if(validator == '日期'){
		widget_validator = 'datacol="yes" err="'+label+'" checkexpession="DateOrNull"';
	}
	switch(type){
		case '文本框':
			var input = "<td align='right'>"+label+":</td><td><input id='"+widgetId+"' name='"+widgetId+"' type='text' "+widget_validator;
			if(val != "" && val != "null"){
				input += " value='" + val + "' readonly=readonly />";
			} else
				input += " />";
			input += "</td>";
			$('#tr'+trnum).append(input);
			break;
		case '日期框':
			var input = "<td align='right'>"+label+":</td><td><input  id='"+widgetId+"' name='"+widgetId+"' type='text' "+widget_validator+"></td>";
			$('#tr'+trnum).append(input);
			$( "#"+widgetId).datepicker({
				changeMonth: true,
			    changeYear: true,
				dateFormat:'yy-mm-dd',
			    showOn: "button",
			    buttonImage: "/styles/css/report/v1.0/css/themes/cupertino/images/calendar.gif",
			    buttonImageOnly: true
			});
			break;
		case '下拉树':
			var input = "<td align='right'>"+label+":</td><td><input type='hidden' id='"+widgetId+"' name='"+widgetId+"'>" +
					"<input readonly title='下拉树，点击选择' id='"+widgetId+"text' name='"+widgetId+"text' type='text' "+widget_validator+"></td>";
			$('#tr'+trnum).append(input);
			var treeDom = '<div id="'+widgetId+'menuContent" class="menuContent" style="display:none; position: absolute;">'+
				'<ul id="tree-'+widgetId+'" class="ztree" style="margin-top:0; width:160px;"></ul></div>';
			$('body').append(treeDom);
			var setting = {
					view: {dblClickExpand: false},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: top_default
						}
					},
					callback: {
						onClick: treeNodeClick
					}
				};
			var zNodes = null;
			// encodeURI(bizParams)
			 $.ajax({
			        url : path+"design/queryWidgetData" + bizParams,
			        data:{widget_name:widget_name},
			        type : "post",
//			        dataType : "json",
			        dataType : "text",
//			        global : false,
			        async : false,
//			        async : true,
			        success : function(data) {
//			        	zNodes = data;
//			        	zNodes = JSON.stringify(data)
//			        	zNodes = Json.parse(data);
			        	zNodes = eval('(' + data + ')'); 
//			        	console.log("data = " + zNodes);
			        }
			   });
			 try
			 {
				 $.fn.zTree.init($("#tree-"+widgetId), setting, zNodes);
			 }
			 catch(e)
			 {
				 //console.log(e);
			 }
			 
			 $('#'+widgetId+"text").bind('click',function(){
					var cityObj = $("#"+widgetId+"text");
					var cityOffset = $("#"+widgetId+"text").offset();
					$("#"+widgetId+"menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
					$("body").bind("mousedown", function(event){
						if (!(event.target.id == "menuBtn" || event.target.id == widgetId+"menuContent" || $(event.target).parents("#"+widgetId+"menuContent").length>0)) {
							$("#"+widgetId+"menuContent").fadeOut("fast");
							$("body").unbind("mousedown");
						}
					});
				});
			break;
		case '下拉框':
			var input = "<td align='right'>"+label+":</td><td><select style='width:155px;' id='"+widgetId+"' name='"+widgetId+"' "+widget_validator+"></select></td>";
			$('#tr'+trnum).append(input);
			var url = path + "design/queryWidgetData";
			var data = {widget_name:widget_name};
			$.post(url,data,function(data){
//				var json = JSON.parse(data);
				var json = data;
				var option = "";
				$.each(json,function(index,item){
					option += "<option value='"+item.value+"'>"+item.text+"</option>";
				});
				$('#'+widgetId).append(option);
			});
			break;
		case 'checkbox':
			
			break;
		default:
			break;
	}
}

function treeNodeClick(event, treeId, treeNode, clickFlag){
	var dom = treeId.split("-")[1];
	$('#'+dom+"text").val(treeNode.name);
	$('#'+dom).val(treeNode.id);
}