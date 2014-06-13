var grid = null;
var deldata = null;
var updateAndAdd = null;//保存、更新状态
var currentSelectedData = null;//当前grid选中行的值
$(function(){
	initGrid();
	initDialog();
	appendDcIdSelectOption();
});

//初始化报表管理列表信息
function initGrid(){
	var opts = {
		columns:[
			{dataIndex:"name",header:"控件名称",width:'15%',align:"left"},
			{dataIndex:"dcname",header:"依赖数据集",width:'15%',align:"left"},
			{dataIndex:"widgetType",header:"控件类型",width:'10%',align:"left"},
			{dataIndex:"summary",header:"描述",width:'30%',align:"left"},
		],
		useCheckbox : {checked:true,width:'2%'},
//		proxy:{url:path + "widget/list"},
		proxy:{url:"list"},
		pageSize:20,
		width:"100%",
		height:$(window).height()-40,
		imgBasePath	:"/styles/foundation/report/images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}
function initDialog(){
	
	$('#widget_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:700,
		close:function(){
			$('#tipTable').remove();
			$('input').removeClass('tooltipinputerr');
		},
		buttons:{
			"保存":function(){
				var check = CheckDataValid();
				if(check){
					var formvalue = $('#widget_form').getFormValue();
					var url = null;
					if(updateAndAdd == "update"){
//						url = path + "widget/update";
						url = "update";
					}else{
//						url = path + "widget/add";
						url = "add";
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
	$('#widget_dialog').dialog('option','title','增加');
	updateAndAdd = "";
	$('#widget_dialog').dialog('open');
}
function changeTextState(value){
	if(value == '下拉框'){
		$('#parentField option').remove();
		$('#topDefault').val('');
		$('#widget_select_tr').show();
		$('#widget_dropdowntree_tr').hide();
	}else if(value == '下拉树'){
		$('#widget_select_tr').show();
		$('#widget_dropdowntree_tr').show();
		var dcId = $('#dc_id').val();
//		var url = path + "widget/queryDcOutParams";
		var url = "queryDcOutParams";
		var value={datasetId:dcId,type:'1'};
		var dom = "parentField";
		createSelectOption(url,value,dom,null);
	}else{
		$('#widget_select_tr').hide();
		$('#widget_dropdowntree_tr').hide();
	}
}

function deleteInfo(){
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
//			var url = path+"widget/delete";
			var url = "delete";
			$.post(url,{ids:ids},function(){
				grid.reload();
			});	
		});
	}
}

function appendDcIdSelectOption(){
//	var url = path + "widget/queryDcByType";
	var url = "queryDcByType";
	var value={type:'控件'};
	createSelectOption(url,value,'dc_id',null);
}

function findParams(value){
	var widgetype = $('#widgetType').val();
	$('#valueField option').remove();
	$('#textField option').remove();
	$('#parentField option').remove();
//	var url = path + "widget/queryDcOutParams";
	var url = "queryDcOutParams";
	var value={datasetId:value,type:'1'};
	if(widgetype == "下拉框"){
		createSelectOption(url,value,'valueField,textField',null);
	} else if(widgetype == '下拉树'){
		createSelectOption(url,value,'valueField,textField,parentField',null);
	}
}
//验证表单
function CheckDataValid() {
    if (!JudgeValidate('#widget_form')) {
        return false;
    } else {
        return true;
    }
}
function createSelectOption(url,value,dom,formvalue){
	var option = "";
	$.post(url,value,function(data){
//		var json_data = JSON.parse(data);
		var json_data = data;
		if(dom=='dc_id'){
			$.each(json_data,function(index,item){
				option += "<option value='"+item.id+"'>"+item.name+"</option>";
			});
		}else{
			$.each(json_data,function(index,item){
				option += "<option value='"+item.name+"'>"+item.name+"</option>";
			});
		}
		var doms = dom.split(",");
		$.each(doms,function(index,item){
			$('#'+item).append(option);
		});
		if(formvalue != null && formvalue != ""){
			$('#widget_form').setFormValue(formvalue);
		}
	});
}
function showUpdateInfo(){
	$('#widget_dialog').dialog('option','title','修改');
	var selected = grid.getSelectedValue();
	var formvalue = selected[0];
	currentSelectedData = selected[0]; 
	var length = selected.length;
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
		updateAndAdd = "update";
		var widgetype = formvalue.widgetType;
		var dcId = formvalue.dc_id;
		$('#valueField option').remove();
		$('#textField option').remove();
		$('#parentField option').remove();
//		var url = path + "widget/queryDcOutParams";
		var url = "queryDcOutParams";
		var value={datasetId:dcId,type:'1'};
		if(widgetype == "下拉框"){
			$('#widget_select_tr').show();
			$('#widget_dropdowntree_tr').hide();
			createSelectOption(url,value,'valueField,textField',formvalue);
		} else if(widgetype == '下拉树'){
			$('#widget_select_tr').show();
			$('#widget_dropdowntree_tr').show();
			createSelectOption(url,value,'valueField,textField,parentField',formvalue);
		}
		$('#widget_dialog').dialog('open');
	}
}
function checkUnique(){
	
//	var url = path+"widget/checkuniquename";	
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