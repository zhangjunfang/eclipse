var grid =  null;
var fields_data = null;
var current_method_data = null;
var current_savebtn_state = null;//当前保存按钮状态：add、update
var current_dataset_id = null;//当前存储过程key值
var currentSelectedData = null;//当前选中grid列数据
var fields_checked_value = null;//存储过程字段管理，字段选中状态
var deldata = null;
$(function(){
	initGrid();
	initDialog();
	appendDsSelectOptions();
});
function checkUnique(){
	var url = path+"dataset/checkuniquename";
	if(currentSelectedData != null && currentSelectedData.name == $('#dataname').val()){
		return false;
	}else{
		$.post(url,{name:$('#dataname').val()},function(item){
			if(item != 0){//已存在
				var msg = "数据集名称已占用";
				ChangeCss('#dataname',msg);
			}
		});
	}
}
//验证表单
function CheckDataValid() {
    if (!JudgeValidate('#listdata_form')) {
        return false;
    } else {
        return true;
    }
}
function appendDsSelectOptions(){
	var url = path + "ds/queryall";
	$.post(url,function(data){
//		var data_json = JSON.parse(data);
		var data_json = data;
		$.each(data_json,function(index,item){
			var option = '<option value="'+item.id+'">'+item.name+'</option>';
			$('#ds_id').append(option);
//			if(index == 0) appendProcedureSelectOptions(item.id);
		});
	});
}
//根据数据源id获取该数据源的所有存储过程
function appendProcedureSelectOptions(id,setvalue){
	var data_type = $('input[name="datatype"]:checked').val();
	if( data_type == 'sql')return ;
	
	$('#method option[value !="0"]').remove();
	var url = path+"dataset/queryprocedure";
	var param = {id:id};
	$.post(url,param,function(data){
		var item = data.substring(1,data.length-1).split(",");
		$.each(item,function(index,item){
			var option = '<option value="'+item+'">'+item+'</option>';
			$('#method').append(option);
		});
		if(current_savebtn_state == "update" && setvalue != ''){
			setFormValue(setvalue);
			getProcedureData('');
		}
	});
}
//根据存储过程名称获取输入、输出参数
function getProcedureData(value){
	$('#fields option').remove();
	$('#params option').remove();
	var url = path+"dataset/getprofiledsparams";;
	if(current_savebtn_state == "update" && $('#method').val() == current_method_data){
		value = current_method_data;
	} 
	var param = {data:value,dsId:$('#ds_id').val()};
	$.post(url,param,function(data){
//		var json = JSON.parse(data);
		var json = data;
		if(json.fields != undefined){
			$.each(json.fields,function(index,item){
				var optiontext = item.name+" / "+item.alias+" / "+item.type;
				var optionvalue = "{name:'"+item.name+"',alias:'"+item.alias+"',type:'"+item.type+"'}";
				var option = '<option value="'+optionvalue+'">'+optiontext+'</option>';
				$('#fields').append(option);
			});
			$.each(json.params,function(index,item){
				var optiontext = item.name+" / "+item.type+" / in";
				var optionvalue = "{name:'"+item.name+"',type:'"+item.type+"'}";
				var option = '<option value="'+optionvalue+'">'+optiontext+'</option>';
				$('#params').append(option);
			});
		}else{
			$.MessageBox_alert('提示',json.msg,json.state);
		}
	});
}

//function setFields_data(){
//	var url = path+"manage/dataset/getprofiledsparams";
//	var param = {data:$('#method').val(),dsId:$('#ds_id').val(),dcId:currentSelectedData.id};
//	$.post(url,param,function(data){
//		var json = JSON.parse(data);
//		fields_data = json.fields;
//	});
//}
//字段管理弹出框
//function fieldmanage(){
//	if(fields_data == null ) return;
//	$('#fields_dialog').dialog({
//		autoOpen:false,
//		modal: true,
//		width:600,
//		height:450,
//		buttons:{
//			"保存":function(){
//				$('#fields option').remove();
//				var inputs = $('#fields_table input');
//				fields_checked_value = new Array();
//				$.each(inputs,function(index,input){
//					if(input.type=="checkbox" && input.checked && index != 0){
//						
//						var currentTr = $(input).parent().parent();
//						var tds = $(currentTr).find('td');
//						var name = $(tds).eq(1).text();
//						fields_checked_value.push(name);
//						var alias = $(tds).eq(2).text();
//						var type = $(tds).eq(3).text();
//						fields_data[index-1].alias = alias;
//						var optiontext = name+" / "+alias+" / "+type;
//						var optionvalue = "{name:'"+name+"',alias:'"+alias+"',type:'"+type+"'}";
//						var option = '<option value="'+optionvalue+'">'+optiontext+'</option>';
//						$('#fields').append(option);
//					}
//				});
//				$(this).dialog( "close" );
//			},"关闭":function(){
//				$(this).dialog( "close" );
//			}
//		}
//	});
//	if($('#fields_table tbody').length > 0){
//		$('#fields_table tbody tr').remove();
//		$.each(fields_data,function(index,item){
//			var domNodes = '<tr><td><input type="checkbox" id="fields_table'+item.name+'" value="'+(index+1)+'"></td>'+
//				'<td  width="100px;">'+item.name+'</td>'+
//				'<td class="editable simpleInput" width="200px;">'+item.alias+'</td>'+
//				'<td  width="80px;">'+item.type+'</td><tr>';
//			$('#fields_table tbody').append(domNodes);
//		});
//	}
//	EdTable.initBindGridEvent();
//	$('#fields_dialog').dialog('open');
//	if(fields_checked_value != null)
//		$.each(fields_checked_value,function(index,item){
//			$('#fields_table'+item).attr('checked',true);
//		});
//}

function __checkAllFileds(currentdom,tableId){
	var inputs = $('#'+tableId+' input');
	$.each(inputs,function(index,input){
		if(input.type=="checkbox")
			input.checked = currentdom.checked;
	});
}

function initGrid(){
	var opts = {
		columns:[
			{dataIndex:"name",header:"数据集名称",width:'15%',align:"left"},
			{dataIndex:"type",header:"数据集类型",width:'20%',align:"left"},
			{dataIndex:"datatype",header:"数据类型",width:'20%',align:"left"},
			{dataIndex:"dsname",header:"所属数据源",width:'20%',align:"left"},
			{dataIndex:"summary",header:"描述",width:'20%',align:"left"}
		],
		useCheckbox : {checked:true,width:'2%'},
		proxy:{url:path+"dataset/list"},
		pageSize:20,
		width:"100%",
		height:$(window).height()-40,
		imgBasePath	:"/styles/foundation/report/images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}
	
function initDialog(){
	$('#listdata_dialog').dialog({
		autoOpen:false,
		modal: true,
		height:$(window).height(),
		width:800,
		close:function(){
			$('#tipTable').remove();
			$('input').removeClass('tooltipinputerr');
		},
		buttons:{
			"保存":function(){
					var check = CheckDataValid();
					if(check == false) return;
					var fieldsoptions = $('#fields option');
					var fieldsvalue = new Array();
					$.each(fieldsoptions,function(index,option){
						fieldsvalue.push(option.value);
					});
					var paramsoptions = $('#params option');
					var paramsvalue = new Array();
					$.each(paramsoptions,function(index,option){
						paramsvalue.push(option.value);
					});
					var data = {"id":$("#id").val(),"name":$("#dataname").val(),"ds_id":$('#ds_id').val(),"type":$('#type').val(),
							"datatype":$('input[name="datatype"]:checked').val(),"method":$('#method').val(),"sql_content":$('#sql_content').val(),
							"summary":$('#summary').val(),"fields":fieldsvalue.toString(),"params":paramsvalue.toString(),"filter_field":$('#filter_field').val()};
					
					var url = null;
					if(current_savebtn_state == 'update'){
						url = path+"dataset/update";
					}else{
						url = path+"dataset/add";
					}
					$.post(url,data,function(data){
						if(data != "" && data != 'null'){
//							var json = JSON.parse(data);
							var json = data;
							$.MessageBox_alert('提示',json.msg,json.state);
						}else{
							$('#listdata_dialog').dialog( "close" );	
						}
						grid.reload();
					});
					
					current_savebtn_state = null;
			},"关闭":function(){
				$(this).dialog( "close" );
			}
		}
	});
}
function querydata(){
	var value = $('#listds_name').val();
	grid.reload({name:value,start:0});
}
function add(){
	$('#listdata_dialog').dialog('option','title','增加');
	removeValidateMsg();
	$('#datatype[value="procedure"]').removeAttr('checked');
	$('#listdata_form')[0].reset();
	showAndHideTr('sql');
	current_savebtn_state = null;
	$('#fields option').remove();
	$('#params option').remove();
	appendProcedureSelectOptions($('#ds_id').val(),'');
	$('#listdata_dialog').dialog('open');
}

function removeValidateMsg(){
	if($('#listdata_form').find(".formtips").length >0){
		$('#listdata_form').find(".formtips").remove();
	}
}

function modify(){
	$('#listdata_dialog').dialog('option','title','修改');
	removeValidateMsg();
	var selected = grid.getSelectedValue();
	var formvalue = selected[0];
	currentSelectedData = selected[0]; 
	var length = selected.length;
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
		current_method_data = formvalue.method;
		current_savebtn_state = "update";
		current_dataset_id = formvalue.id;
		$('#datatype[value="'+formvalue.datatype+'"]').attr('checked',true);
		var setvalue = {id:formvalue.id,dataname:formvalue.name,ds_id:formvalue.ds_id,filter_field:formvalue.filter_field,
				datatype:formvalue.type,method:formvalue.method,summary:formvalue.summary,sql_content:formvalue.content};
		if(formvalue.datatype == 'sql'){
			$('#sql_tr').show();
			$('#fields_tr').hide();
			$('#params_tr').hide();
			$('#method_tr').hide();
			setFormValue(setvalue);
		}else{
			$('#sql_tr').hide();
			$('#fields_tr').show();
			$('#params_tr').show();
			$('#method_tr').show();
		}
//		getProcedureData(formvalue.method);
		appendProcedureSelectOptions(formvalue.ds_id,setvalue);
		$('#listdata_dialog').dialog('open');
	}
}
function setFormValue(value){
	$('#listdata_form').setFormValue(value);
}
function deleteData(){
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
			var url = path+"dataset/delete";
			$.post(url,{ids:ids},function(){
				grid.reload();
			});	
		});
	}
}
//控制选择数据类型时相关tr的显示、隐藏
function showAndHideTr(value){
	if(value == 'sql'){
		$('#fields option').remove();
		$('#params option').remove();
		$('#method option[value !="0"]').remove();
		$('#sql_tr').show();
		$('#fields_tr').hide();
		$('#params_tr').hide();
		$('#method_tr').hide();
	}else if(value == 'procedure'){
		$('#sql_tr').hide();
		$('#fields_tr').show();
		$('#params_tr').show();
		$('#method_tr').show();
		appendProcedureSelectOptions($('#ds_id').val(),'');
	}
}