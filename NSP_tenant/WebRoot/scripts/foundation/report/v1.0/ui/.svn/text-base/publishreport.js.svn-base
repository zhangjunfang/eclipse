var gird = null;
$(function(){
	initGrid();
});
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
		proxy:{url:path+"manage/dataset/query"},
		pageSize:20,
		width:"100%",
		height:$(window).height()-40,
		imgBasePath	:"../images/gridimgs/"
	};
	grid = new Grid("grid",opts);
}

function initDialog(){
	$('#listdata_dialog').dialog({
		autoOpen:false,
		modal: true,
		width:800,
		buttons:{
			"保存":function(){
				
			},"关闭":function(){
				$(this).dialog( "close" );
			}
		}
	});
}

function showAddDialog(){
	
}

function showUpdateDialog(){
	
}

function deleteInfo(){
	
}
