var grid =  null;
var currentSelectedData = null;
var mark = null;
var deldata = null;
$(function(){
	initGrid();
	initDialog();
});
function initDialog(){
	$('#datasource_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:430,
		close:function(){
			$('#tipTable').remove();
			$('input').removeClass('tooltipinputerr');
		},
		buttons:{
			"测试":function(){
				if(CheckDataValid() == false ) return false;
				var url = path+"ds/checkconn";
				var value = $('#datasource_form').getFormValue();
				var data = {data:value};
				$.post(url,data,function(item){
					if(item == 'true'){
						$.MessageBox_alert('提示','测试成功','success');
					}else{
						$.MessageBox_alert('提示','测试失败','warn');
					}
				});
			},"保存":function(){
				if(CheckDataValid() == false ) return false;
				var value = $('#datasource_form').getFormValue();
				var url = "";	
				if(mark == "update"){
					url = path+"ds/update";	
				}else{
					url = path+"ds/add";
				}
				mark = null;
				submitData(url,{data:value});
				$( this ).dialog( "close" );
				$('#datasource_form')[0].reset();
			},"关闭":function(){
				$( this ).dialog( "close" );
				
			}
		}
	});
}

function checkForm(){
	$("#datasource_form .required:input").trigger('blur');
	var numError = $('#datasource_form .onError').length;
	if(numError) return false;
}

function initGrid(){
	var opts = {
		columns:[
			{dataIndex:"name",header:"数据源名称",width:'10%',align:"left"},
			{dataIndex:"type",header:"数据库类型",width:'10%',align:"left"},
			{dataIndex:"dsAddress",header:"服务地址",width:'20%',align:"left"},
			{dataIndex:"dsServer",header:"数据库名",width:'10%',align:"left"},
			{dataIndex:"dsUser",header:"用户名",width:'10%',align:"left"},
			{dataIndex:"summary",header:"描述",width:'20%',align:"left"}
		],
//		checkValueKey : "numberid",
		useCheckbox : {checked:true,width:'2%'},
		proxy:{url:path+"ds/list"},
//		reader:{totalProperty:"totalProperty",root:"root"},
		listeners:{
			rowclick:function(rowIndex,data){
					showCustDetails(data);
			}
		},
		pageSize	:	20,
		width:"100%",
		height:$(window).height()- 39,
		imgBasePath	:"/styles/foundation/report/images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}
function showCustDetails(data){
	console.log(data);
};
function addData(){
	$('#datasource_dialog').dialog('option','title','增加');
	$('#datasource_form')[0].reset();
	$("#datasource_dialog").dialog('open');
}
function modify(){
	//console.log($('#datasource_dialog').attr('title'));
	var selected = grid.getSelectedValue();
	currentSelectedData = selected[0];
	var length = selected.length;
	
	if(length != 1 ){
		$.MessageBox_alert('提示','请选择一条记录','warn');
	}else{
		mark = "update";
		$('#datasource_dialog').setFormValue(selected[0]);
		$('#datasource_dialog').dialog('open');
	}
	$('#datasource_dialog').dialog('option','title','修改');
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
			var url = path+"ds/delete";	
			var value = {ids:ids};
			submitData(url,value);
		});
	}
}

function submitData(url,data){
	$.post(url,data,function(item){
		grid.reload();
	});
}
function checkUnique(){
	
	var url = path+"ds/checkuniquename";	
	if(currentSelectedData != null && currentSelectedData.name == $('#name').val()){
		return false;
	}else{
		$.post(url,{name:$('#name').val()},function(item){
			if(item != 0){//已存在
				var msg = "数据源名称以占用";
				ChangeCss('#name',msg);
			}
		});
	}
}
//验证表单
function CheckDataValid() {
    if (!JudgeValidate('#datasource_form')) {
        return false;
    } else {
    	 return true;
    }
}

function query(){
	var value = $('#ds_name').val();
	grid.reload({ds_name:value,start:0});
}