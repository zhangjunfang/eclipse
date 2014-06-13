<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Object object = session.getAttribute("land");
	if (null != object) {
		boolean bool = (Boolean) object;
		if (bool) {
			out.println("<script type='text/javascript'>alert('操作成功')</script>");
		} else  {
			out.println("<script type='text/javascript'>alert('无此人信息')</script>");
		}
	}
	//session.setAttribute("land", null);
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
<link rel="shortcut icon"
	href="<%=basePath%>docs-assets/ico/favicon.png">
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
<style>
body {
	background-color: #F5F5F5;
	padding-bottom: 40px;
	padding-top: 40px;
}

#addFrm {
	background-color: #FFFFFF;
	border: 1px solid #E5E5E5;
	border-radius: 5px;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
	margin: 0 auto 20px;
	max-width: 350px;
	padding: 19px 29px 29px;
}

#alert_info {
	max-width: 350px;
	margin: 0 auto 20px;
}
</style>
</head>
<body>
	<section class="container">
		<form id="addFrm" method="post"  action="<%=basePath%>Login">
			<h3>《职业卫生安全评价，从入门到精通》</h3>
			<div class="form-group">
				<input id="addFrm_xm" name="xm" type="text" class="form-control"
					placeholder="姓名">
			</div>
			<div class="form-group">
				<input id="addFrm_sfzh" name="sfzh" type="text" class="form-control"
					placeholder="身份证号">
			</div>
			<button id="btn_submit" type="submit" class="btn btn-primary">登录</button>
		</form>
	</section>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/jquery/2.0.2/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/bootstrap/3.0.3/dist/js/bootstrap.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/jquery/ext/ext.form.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/underscore/1.5.1/underscore.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>cdn/js/olxjs/1.0.0/js/olx.min.js?v12"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/step0.js"></script>
</body>
<%
session.setAttribute("land", null);
%>
</html>


