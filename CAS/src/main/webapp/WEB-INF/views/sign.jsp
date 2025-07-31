<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="/CAS/styles/login.css">
<script src="/CAS/scripts/sign.js"></script>

<form id="signForm" action="sign?app=${param.app}&dst=${param.dst}" method="post">
	<label for="username">用户名：</label> <input type="text" id="username" 
		name="username" value="${param.username}" placeholder="数字或英文字母" required title="请输入用户名" />
		<p id="user_err" style="display:none;"></p>
	<label for="email">邮箱：</label> <input type="text" id="email" 
		name="email" value="${param.email}" required title="请输入邮箱地址" />
		<p id="email_err" style="display:none;"></p>
	<label for="phone">电话号码：</label> <input type="text" id="phone" 
		name="phone" value="${param.phone}" required title="请输入电话号码" />
		<p id="phone_err" style="display:none;"></p>
	<label for="password">密码：</label> <input type="password" id="password" 
		name="password" value="${param.password}" required title="请输入密码" />
		<p id="password_err" style="display:none;"></p> 
	<label for="password2">再次输入密码：</label> <input type="password" id="password2" 
		name="password2" value="${param.password2}" required title="请确认密码" />
		<p id="password2_err" style="display:none;"></p>
	<c:if test="${!empty err}"><p>${err}</p></c:if>
	<button type="submit">注册</button>
</form>