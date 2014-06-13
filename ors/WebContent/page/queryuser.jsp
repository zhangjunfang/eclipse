<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>《职业卫生安全评价，从入门到精通》网上报名</title>
<meta charset="utf-8">
<meta name="robots" content="all" />
<meta name="author" content="huangxin" />
<meta name="generator" content="Foreworld" />
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

<!--[if lte IE 9]>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>cdn/js/bsie/0.0.0/css/bootstrap-ie6.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>cdn/js/bsie/0.0.0/css/ie.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>cdn/js/verybsie/1.0.0/css/bootstrap-verybsie.css">
		<![endif]-->

<!--[if lte IE 7]>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>cdn/js/verybsie/1.0.0/css/bootstrap-verybsie.css">
		<![endif]-->

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>cdn/js/bootstrap/3.0.3/dist/css/bootstrap.css?v3.0.3"
	media="screen">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>cdn/js/bootstrap/3.0.3/dist/css/bootstrap-theme.min.css?v3.0.3"
	media="screen">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>cdn/js/olxjs/1.0.0/css/olx/olx.css" media="screen">
<style>
.container {
	margin-top: -40px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							<strong>查询条件</strong>
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" >
							<div class="form-group">
								<input type="text" class="form-control" id="xm" name="name"
									placeholder="姓名">
							</div>
							<div class="form-group">
								<input type="text" class="form-control" id="sfzh" name="ids"
									placeholder="身份证号">
							</div>
							<button type="button"
								class="btn btn-default olx-glyphicon glyphicon-search olx-glyphicon-btn"
								onclick="findUser()">查询</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default table-responsive">
					<div class="panel-heading">
						<div class="btn-group">
							<b>《职业卫生安全评价，从入门到精通》报名人员</b>
						</div>
					</div>
					<table id="table1" data-olx-type="olx.grid"
						class="table table-bordered table-hover table-condensed table-striped"
						data-url="/manage/role/list" data-current="1" data-pagesize="20"
						data-count="561">
						<thead>
							<tr>
								<th class="olx-table-th-center">序号</th>
								<th>姓名</th>
								<th>身份证号</th>
								<th>性别</th>
								<th>政治面貌</th>
								<th>民族</th>
								<th>籍贯</th>
								<th>毕业院校</th>
								<th>毕业时间</th>
								<th>学历</th>
								<th>专业</th>
								<th>工作单位</th>
								<th>所在部门</th>
								<th>从事专业</th>
								<th>职位</th>
								<th>职称</th>
								<th>联系电话</th>
								<th>联系地址</th>
							</tr>
						</thead>
						<tbody>
							<tr id="addtr">
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="<%=basePath%>cdn/js/jquery/2.0.2/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/bootstrap/3.0.3/dist/js/bootstrap.js"></script>
	<script type="text/javascript">
		function findUser() {
			var name = $('#xm').val();
			var ids = $('#sfzh').val();
            $.ajax({
        		url : '<%=basePath%>QueryUser',
        		type : "post",
        		dataType : 'json',
        		data:{'name':name,'ids':ids},
        		contentType : "application/x-www-form-urlencoded",
        		success : function(data) {
        			$('table tbody tr').empty();
        			if("null"==data.id){
        				alert("无数据");
        			}else{
        				$('#addtr').append(
        				'<td class="olx-table-td-center">'+data.id+'</td>');
        				$('#addtr').append('<td>'+data.name+'</td>');
        				$('#addtr').append('<td>'+data.ids+'</td>');
        				$('#addtr').append('<td>'+data.sex+'</td>');
        				$('#addtr').append('<td>'+data.politicsStatus+'</td>');
        				$('#addtr').append('<td>'+data.nation+'</td>');
        				$('#addtr').append('<td>'+data.address+'</td>');
        				$('#addtr').append('<td>'+data.graduation+'</td>');
        				$('#addtr').append('<td>'+data.date+'</td>');
        				$('#addtr').append('<td>'+data.education+'</td>');
        				$('#addtr').append('<td>'+data.specialty+'</td>');
        				$('#addtr').append('<td>'+data.unit+'</td>');
        				$('#addtr').append('<td>'+data.department+'</td>');
        				$('#addtr').append('<td>'+data.workoccupation+'</td>');
        				$('#addtr').append('<td>'+data.job+'</td>');
        				$('#addtr').append('<td>'+data.professional+'</td>');
        				$('#addtr').append('<td>'+data.telephone+'</td>');
        				$('#addtr').append('<td>'+data.contactaddress+'</td>');
        			}
        		}
        	});
		}
	</script>
</body>
</html>


