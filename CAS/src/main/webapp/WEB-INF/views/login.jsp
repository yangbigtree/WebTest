<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css" href="/CAS/styles/login.css">
<script src="/CAS/scripts/login.js"></script>

<form action="login?app=${param.app}&dst=${param.dst}" method="post">
	<label for="account">账号：</label> <input type="text" id="account"
		name="account" value="${param.account}" placeholder="电话号码或电子邮箱" required title="请输入账号">
	<label for="password">密码：</label> <input type="password" id="password"
		name="password" value="${param.password}" required title="请输入密码">
	<div style="display: flex; align-items: center; margin-bottom: 10px;">
		<input type="checkbox" id="admin" name="admin" checked> <label
			for="admin" style="margin-left: 5px;">管理员</label>
	</div>
	<c:if test="${!empty err}"><p>${err}</p></c:if>
	<button type="submit">登录</button>
</form>