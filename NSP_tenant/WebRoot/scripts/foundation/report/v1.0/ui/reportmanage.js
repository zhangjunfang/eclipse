var grid = null;
var current_savebt_state = null;
var currentSelectedData = null;
var next_btn= null;
var up_btn = null;
var deldata = null;
var _reportId = null;
var _widget_dc =  null;
var _publish_data = null;//当前选中要修改发布状态的数据
$(function(){
	initGrid();
	initReportDialog();
	initDesignReportDialog();
	initDcSelect();
});
//function checkUnique($parent,url,value){
//	$.post(url,{name:value},function(item){
//		if(item != 0){
//			var errorMsg = "名称已被占用";
//			$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
//		}
//	});
//}

function removeValidateMsg(){
	if($('#report_form').find(".formtips").length >0){
		$('#report_form').find(".formtips").remove();
	}
}

//初始化报表管理增加、修改弹出窗口
function initReportDialog(){
	$('#report_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:700,
		height:400,
		close:function(){
			$('#tipTable').remove();
			$('input').removeClass('tooltipinputerr');
		}
	});
	$('#report_dialog').parent().append('<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix"><div class="ui-dialog-buttonset"></div></div>');

}

//验证表单
function CheckDataValid(value) {
    if (!JudgeValidate('#'+value)) {
        return false;
    } else {
        return true;
    }
}
//初始化报表数据集下拉菜单
function initDcSelect(){
	
//	var url = path + "widget/queryDcByType";
	var url2 = path+"widget/queryDcByType";
//	var url2 = "http://localhost:8080/" + path + "widget/queryDcByType";
	var value = {type:'报表'};
	$.post(url2,value,function(data){
//		var data_json = JSON.parse(data);
		var data_json = data;
		var option = "";
		$.each(data_json,function(index,item){
			option += '<option value="'+item.id+'">'+item.name+'</option>';
		});
		$('#dc_id').append(option);
	});
}
//初始化报表设计dialog
function initDesignReportDialog(){
	var height = $(window).height();
	var width = $(window).width()-2;
	$('#designReport_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:width,
		height:height,
		buttons:{
			"保存":function(){
				window.frames['loadreport'].saveReport();
			},"关闭":function(){
				$(this).dialog( "close" );
			}
		}
	});
}
//初始化报表管理列表信息
function initGrid(){
	var opts = {
		columns:[
			{dataIndex:"name",header:"报表名称",width:'15%',align:"left"},
			{dataIndex:"dcname",header:"数据集",width:'20%',align:"left"},
			{dataIndex:"state",header:"发布状态",width:'20%',align:"left",render:function(data){
				if(data.state == 1){
					return "已发布";
				}else{
					return "未发布";
				}
			}},
			{dataIndex:"summary",header:"描述",width:'20%',align:"left"}
			
		],
		useCheckbox : {checked:true,width:'2%'},
//		proxy:{url:path+"design/list"},
		proxy:{url:"list"},
		pageSize:20,
		width:"100%",
		height:$(window).height()-39,
		imgBasePath	:"/styles/foundation/report/images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}
//删除报表信息
function deleteReportInfo(){
	deldata = grid.getSelectedValue();
	var length = deldata.length;
	if(length == 0 ){
		$.MessageBox_alert('提示','请选择要删除的数据','warn');
	}else{
		$.MessageBox_confirm('提示','您确定要删除选中的数据吗？',function(){
			var ids = "";
			$.each(deldata,function(i,item){
				ids += item.id+",";
			}); 
			ids = ids.substr(0,ids.length - 1);
			var url = path+"design/delete";
			$.post(url,{ids:ids},function(){
				grid.reload();
			});
		});
	}
}
//弹出更新窗口,设置更新相关信息
function showUpdateInfo(){
	$('#report_dialog').parent().find('.ui-dialog-buttonset').find('button').remove();
	$('#report_form_fields').hide();
	$('#report_form_addupdate').show();
	$('#customquery_form').hide();
	appendBtns();
	$('#save').bind("click",saveReportInfo);
	$('#next').bind("click",next);
	$('#up').bind("click",up);
	next_btn = "report_form_fields";
	$('#report_dialog').dialog('option','title','修改');
	var selected = grid.getSelectedValue();
	var formvalue = selected[0];
	currentSelectedData = formvalue;
	var length = selected.length;
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
		current_savebt_state = 'update';
		_reportId = formvalue.id;
		$('#report_form_addupdate').setFormValue(formvalue);
		$('#report_dialog').dialog('open');
	}
}

function appendBtns(){
	var btnset = $('#report_dialog').parent().find('.ui-dialog-buttonset');
	var btns = '<button type="button" id="save"  class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">保存</span></button>'+
	'<button type="button" style="display:none"  id="up" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">上一步</span></button>'+
	'<button type="button"  id="next" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">下一步</span></button>'+
	'<button type="button" style="display:none"  id="report_design" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">报表设计</span></button>'+
	'<button type="button" onclick="closeDialog()" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false"><span class="ui-button-text">关闭</span></button>';
	btnset.append(btns);
}
function saveReportInfo(){
	var checked = CheckDataValid('report_form_addupdate');
	if(checked){
		var value = $('#report_form_addupdate').getFormValue();
		var url = null;
		if(current_savebt_state == 'update')
			url = path + "design/update";
		else 
			url = path + "design/add";
		var data = {data:value};
		$.post(url,data,function(data){
			if(data != null){
				_reportId = data;
				$('#reportId').val(data);
				next();
			}
		});
	}
}
//关闭report—dialog
function closeDialog(){
	$('#report_dialog').dialog('close');
}
//获取报表字段
function queryReportFields(){
	
	var url = "queryDsParamAndReportFields";
	var param = {dcId:$('#dc_id').val(),reportId:_reportId};
	$.post(url,param,function(data){
//		var json = JSON.parse(data);
		var json = data;
		$('#fields_table tbody tr').remove();
		var domNodes = "";
		$.each(json.noChecked,function(index,item){
			domNodes += '<tr><td><input type="checkbox" id="fields_table'+item.paramId+'" value="'+(index+1)+'"></td>'+
				'<td  width="100px;">'+item.name+'</td>'+
				'<td class="editable simpleInput" width="150px;"></td>'+
				'<td class="editable simpleInput"></td>'+
				'<td  width="80px;" class="editable simpleSelect" id="_field_type">'+item.type+'</td>'+
				'<td  width="200px;">'+item.alias+'</td><tr>';
		});
		$.each(json.checked,function(index,item){
			domNodes += '<tr><td><input type="checkbox" checked="checked" id="fields_table'+item.id+'" ></td>'+
				'<td  width="100px;">'+item.field_name+'</td>'+
				'<td class="editable simpleInput" width="150px;">'+item.field_title+'</td>'+
				'<td class="editable simpleInput">'+item.sort+'</td>'+
				'<td  width="80px;" class="editable simpleSelect" id="_field_type">'+item.field_type+'</td>'+
				'<td  width="200px;">'+item.summary+'</td><tr>';
		});
		$('#fields_table tbody').append(domNodes);
		EdTable.initBindGridEvent();
	});
}
function next(){
	$('#save').unbind("click");
	if(next_btn == "report_form_fields"){
		$('#report_dialog').dialog('option','title','报表字段');
		$('#save').bind("click",saveReportFields);
		$('#report_form_fields').show();
		$('#report_form_addupdate').hide();
		$('#customquery_form').hide();
		$('#up').show();
		queryReportFields();
		up_btn = "report_form_addupdate";
		next_btn = "customquery_form"; 
	}else if(next_btn == "customquery_form"){
		appendForm_Widget_Id();
		$('#report_id').val($('#dc_id').val());
		$('#report_dialog').dialog('option','title','报表查询表单');
		$('#save').bind("click",saveCustomQuery);
		$('#report_form_fields').hide();
		$('#report_form_addupdate').hide();
		$('#customquery_form').show();
		$('#up').show();
		$('#next').hide();
		$('#report_design').show();
		up_btn = "report_form_fields";
	}
}
function saveReportFields(){
	
	var inputs = $('#fields_table input');
	var	 fields_checked_value = new Array();
	
	$.each(inputs,function(index,input){
		if(input.type=="checkbox" && input.checked && index != 0){
			var currentTr = $(input).parent().parent();
			var tds = $(currentTr).find('td');
			var name = $(tds).eq(1).text();
			var title = $(tds).eq(2).text();
			var sort = $(tds).eq(3).text();
			var type = $(tds).eq(4).text();
			var summary = $(tds).eq(5).text();
			var data = {field_name:name,field_title:title,sort:sort,field_type:type,summary:summary,report_id:_reportId};
			
			fields_checked_value.push(JSON.stringify(data));
		}
	});
	var url = path + "design/addreportfields";
	var data = {data:fields_checked_value.toString(),reportId:_reportId};
	$.post(url,data,function(data){
		if(data){
			next();
		}else{
			alert('保存失败');
		}
	});
	
}
function saveCustomQuery(){
	
	var inputs = $('#params_table tbody tr');
	var	 params_checked_value = new Array();
	
	$.each(inputs,function(index,currentTr){
		var tds = $(currentTr).find('td');
		var form_widget_id = $(tds).eq(0).text();
		
		var name = $(tds).eq(1).text();
		var form_widget_type = $(tds).eq(2).text();
		var widget_id = $(tds).eq(3).text();
		var form_widget_validator = $(tds).eq(4).text();
		var summary = $(tds).eq(5).text();
		if(form_widget_id != null && form_widget_id != ""){
			var data = {
					form_widget_id:form_widget_id,
					name:name,
					form_widget_type:form_widget_type,
					widget_id:widget_id,
					form_widget_validator:form_widget_validator,
					summary:summary
				};
				params_checked_value.push(JSON.stringify(data));
		}
	});
	var url = path + "customquery/add";
	var data = {data:params_checked_value.toString(),reportId:_reportId};
	submitData(url,data);
}

function up(){
	$('#save').unbind("click");
	if(up_btn == "report_form_fields"){
		$('#report_dialog').dialog('option','title','报表字段');
		$('#save').bind("click",saveReportFields);
		$('#report_form_fields').show();
		$('#report_form_addupdate').hide();
		$('#customquery_form').hide();
		$('#up').show();
		$('#next').show();
		$('#report_design').hide();
//		$('#up').bind("click",up);
		up_btn = "report_form_addupdate";
		next_btn = "customquery_form"; 
	}else if(up_btn == "report_form_addupdate"){
		$('#report_dialog').dialog('option','title','报表基础信息');
		$('#save').bind("click",saveReportInfo);
		$('#report_form_fields').hide();
		$('#report_form_addupdate').show();
		$('#customquery_form').hide();
		$('#up').hide();
		$('#next').show();
		$('#report_design').hide();
		next_btn = "report_form_fields"; 
	}
}

//弹出添加窗口，设置保存相关信息
function showAddDialog(){
	$('#report_dialog').parent().find('.ui-dialog-buttonset').find('button').remove();
	$('#report_form_fields').hide();
	$('#report_form_addupdate').show();
	$('#customquery_form').hide();
	appendBtns();
	$('#save').bind("click",saveReportInfo);
	$('#next').bind("click",next);
	$('#up').bind("click",up);
	next_btn = "report_form_fields";
	current_savebt_state = null;
	removeValidateMsg();
	$('#customquery_form')[0].reset();
	$('#report_form_addupdate')[0].reset();
	$('#report_dialog').dialog('option','title','报表基础信息添加');
	$('#report_dialog').dialog('open');
}
//弹出报表设计窗口
function showDesignDialog(){
	var selected = grid.getSelectedValue();
	var formvalue = selected[0];
	var length = selected.length;
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
//		var src = 'designreport.jsp?id='+formvalue.id+'&dc_id='+formvalue.dc_id;
		var src = path + 'design/showDesignor?id='+formvalue.id+'&dc_id='+formvalue.dc_id;
		$('#loadreport').attr('src',src);
		$('#designReport_dialog').dialog('open');
	}
}
function querydata(){
	var value = $('#report_name').val();
	grid.reload({name:value,start:0});
}
function submitData(url,data){
	$.post(url,data,function(data){
//		grid.reload();
	});
}
function appendWidgetSelectOption(value){
	var url = path + "widget/queryByType";
	_widget_dc = "<select onblur='EdTable.saveEdit(this,this.value)' style='width:100%;padding:0px; margin:0px;'>" +
			"<option value='无'>无</option>";
	$.post(url,{type:value},function(item){
		if(item.length>0){
//			var data_json = JSON.parse(item);
			var data_json = item;
			$.each(data_json,function(index,data){
				_widget_dc += "<option value='"+data.name+"'>"+data.name+"</option>";
			});
		}
		_widget_dc +="</select>";
	});
}
//获取当前数据集的输入参数作为查询表单控件的dom id
function appendForm_Widget_Id(){
	var url = path + "customquery/queryparams";
	var param = {dc_id:$('#dc_id').val(),reportId:_reportId};
	$.post(url,param,function(data){
//		var json = JSON.parse(data);
		var json = data;
		$('#params_table tbody tr').remove();
		var domNodes = "";
		$.each(json.params,function(index,item){
			domNodes += '<tr>'+
				'<td width="120px;">'+item.name+'</td>'+
				'<td class="editable simpleInput" width="120px;"></td>'+
				'<td class="editable simpleSelect" id="_widget_type" width="80px;">文本框</td>'+
				'<td class="editable simpleSelect" id="_widget_dc" width="120px;">无</td>'+
				'<td class="editable simpleSelect" id="_form_widget_validator" width="80px;">允许为空</td>'+
				'<td class="editable simpleSelect" id="_field_type" width="80px;">'+item.type+'</td><tr>';
		});
		$.each(json.form,function(index,item){
			domNodes += '<tr>'+
				'<td width="120px;">'+item.form_widget_id+'</td>'+
				'<td class="editable simpleInput" width="120px;">'+item.name+'</td>'+
				'<td class="editable simpleSelect" id="_widget_type" width="80px;">'+item.form_widget_type+'</td>'+
				'<td class="editable simpleSelect" id="_widget_dc" width="120px;">'+item.widget_id+'</td>'+
				'<td class="editable simpleSelect" id="_form_widget_validator" width="80px;">'+item.form_widget_validator+'</td>'+
				'<td class="editable simpleSelect" id="_field_type" width="80px;">'+item.summary+'</td><tr>';
		});
		$('#params_table tbody').append(domNodes);
		appendWidgetSelectOption('');
		EdTable.initBindGridEvent();
		
	});
}
function checkUnique(){
	
	var url = "checkuniquename";	
	if(currentSelectedData != null && currentSelectedData.name == $('#name').val()){
		return false;
	}else{
		$.post(url,{name:$('#name').val()},function(item){
			if(item != 0){//已存在
				var msg = "数据源名称已占用";
				ChangeCss('#name',msg);
			}
		});
	}
}
function __checkAllFileds(currentdom,tableId){
	var inputs = $('#'+tableId+' input');
	$.each(inputs,function(index,input){
		if(input.type=="checkbox")
			input.checked = currentdom.checked;
	});
}
//报表发布
function publishReport(){
	_publish_data = grid.getSelectedValue();
	var length = _publish_data.length;
	if(length !=  1  ){
		$.MessageBox_alert('提示','请选择一条报表信息','warn');
	}else{
		
		$.MessageBox_confirm('提示','您确定要修改报表发布状态吗？',function(){
			var ids = _publish_data[0].id;
			var state = _publish_data[0].state == 0 ? 1:0;
//			$.each(_publish_data,function(i,item){
//				ids += item.id+",";
//			}); 
//			ids = ids.substr(0,ids.length - 1);
			var url = path+"design/updateReportState";
			$.post(url,{ids:ids,state:state},function(){
				grid.reload();
				parent.appendReportList();
			});
		});
	}
}

//角色列表（包含查询）
function reload(offset){
	//总分页数目
	var totalpage = $("#totalpage").val();
	//角色名称
	var rolename = $("#report_name").val()
	
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	//拼json参数
	var params = {offset:offset,name:rolename};
	loadDiv("/restful/reportProxyService/design/showPageList",params,null);
}