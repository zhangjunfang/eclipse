var grid = null;
var reportId = null;
var reportName = null;
var deldata = null;
var updateAndAdd = "";//如果为空是增加，否则是更新状态
var setting = {
		view:{
			showLine:true
		},
		async: {
			enable: true,
			url:path+"manage/customquery/querytree",
			autoParam:["id", "name=n", "level=lv"],
		},
		callback:{
			onClick:treeNodeClick
		}
	};
$(function(){
	var width = $(window).width()-200;
	$('#right').attr('style','float:left,width:'+width+'px;overflow:hidden');
	$.fn.zTree.init($("#tree"), setting);
	initGrid();
	initDialog();
	//appendWidgetSelectOption();
});

function treeNodeClick(event, treeId, treeNode, clickFlag){
	reportId = treeNode.id;
	reportName = treeNode.name;
	grid.reload({id:reportId,start:0});
	appendForm_Widget_Id(treeNode.dc_id);
}
function appendForm_Widget_Id(dc_id){
	var url = path + "manage/customquery/queryparams";
	var data = {dc_id:dc_id};
	$.post(url,data,function(item){
		var data_json = JSON.parse(item);
		var option = "";
		$.each(data_json,function(index,data){
			option += "<option value='"+data.name+"'>"+data.name+"</option>";
		});
		$('#form_widget_id').append(option);
	});
}
//初始化报表管理列表信息
function initGrid(){
	var opts = {
		columns:[
			{dataIndex:"name",header:"属性名称",width:'15%',align:"left"},
			{dataIndex:"report_name",header:"所属报表",width:'15%',align:"left"},
			{dataIndex:"form_widget_id",header:"控件ID",width:'10%',align:"left"},
			{dataIndex:"form_widget_type",header:"控件类型",width:'10%',align:"left"},
			{dataIndex:"widget_id",header:"控件数据",width:'10%',align:"left"},
			{dataIndex:"form_widget_length",header:"控件宽度",width:'10%',align:"left"},
			{dataIndex:"sortCode",header:"显示顺序",width:'10%',align:"left"},
			{dataIndex:"summary",header:"描述",width:'17%',align:"left"}
		],
		useCheckbox : {checked:true,width:'2%'},
		proxy:{url:path+"manage/customquery/query",params:{id:0}},
		pageSize:20,
		width:"100%",
		height:$(window).height()-40,
		imgBasePath	:"../images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}
function initDialog(){
	$('#customquery_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:700,
		buttons:{
			"保存":function(){
				var check = CheckDataValid();
				if(check){
					var formvalue = $('#customquery_form').getFormValue();
					var url = null;
					if(updateAndAdd == "update"){
						url = path+"manage/customquery/update";
					}else{
						url = path + "manage/customquery/add";
					}
					$.post(url,{data:formvalue},function(data){
						grid.reload();
					});
					$(this).dialog( "close" );
				}
				
			},"关闭":function(){
				$(this).dialog( "close" );
			}
		}
	});
}
function showAddDialog(){
	if(reportId == null){
		$.MessageBox_alert('提示','请选择左侧报表节点','warn');
		return;
	}
	updateAndAdd = "";
	$('#customquery_form')[0].reset();
	$('#report_id').val(reportId);
	$('#report_name').val(reportName);
	$('#customquery_dialog').dialog('open');
}

function showUpdateInfo(){
	$('#customquery_dialog').dialog('option','title','修改');
	var selected = grid.getSelectedValue();
	var formvalue = selected[0];
//	currentSelectedData = selected[0]; 
	var length = selected.length;
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
		updateAndAdd = "update";
		$('#customquery_form').setFormValue(formvalue);
		$('#customquery_dialog').dialog('open');
	}
}
//验证表单
function CheckDataValid() {
    if (!JudgeValidate('#customquery_form')) {
        return false;
    } else {
        return true;
    }
}
function changeTextState(value){
	$("#widget_id option[value != '']").remove();
	if(value == 'select' || value == 'dropdowntree'){
		appendWidgetSelectOption(value);
	}
}
function appendWidgetSelectOption(value){
	var url = path + "manage/widget/queryByType";
	$.post(url,{type:value},function(item){
		var data_json = JSON.parse(item);
		var option = "<option value='无'>无</option>";
		$.each(data_json,function(index,data){
			option += "<option value='"+data.id+"'>"+data.name+"</option>";
		});
		$('#widget_id').append(option);
	});
}

function deleteFormInfo(){
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
			var url = path+"manage/customquery/del";
			$.post(url,{ids:ids},function(){
				grid.reload();
			});	
		});
	}
}
