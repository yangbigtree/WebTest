<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录系统</title>
<link rel="stylesheet" type="text/css" href="/CAS/styles/login.css">
</head>
<body>
	<div class="login-container">
		<h2>欢迎来到${param.app}</h2>
		<ul>
			<li><a class="${empty sign ? 'opt-active' : 'opt-unactive'}"
				href="login">登录</a></li>
			<li><a class="${empty sign ? 'opt-unactive' : 'opt-active'}"
				href="sign">注册</a></li>
		</ul>
		<div>
			<jsp:include page="/WEB-INF/views/${empty sign ? 'login.jsp' : 'sign.jsp'}"></jsp:include>
		</div>
	</div>
</body>
</html>