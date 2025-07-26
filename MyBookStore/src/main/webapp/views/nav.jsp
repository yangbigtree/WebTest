<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>图书管理系统</title>
<link rel="stylesheet" type="text/css" href="styles/nav.css">
<script src="scripts/nav.js"></script>
</head>

<body>
	<div class="header">
		<h1>图书管理系统</h1>
		<div class="message-notifications">
			HI, <span class="user-name">用户名</span> <span> <span
				class="num-unread">3</span>条 未读信息
			</span>
			<div class="message-list">
				<a class="message-item">消息1</a> <a class="message-item">消息1</a> <a
					class="message-item">消息1</a>
			</div>
		</div>
	</div>
	<div class="page">
		<div class="sidebar">
			<ul>
				<li class="sidebar-active"><a href="#">我的书籍</a></li>
				<li><a href="#">查找书籍</a></li>
				<li><a href="#">书籍录入</a></li>
				<li><a href="#">书籍管理</a></li>
			</ul>
		</div>
		<main class="content">
			<h2>已借书籍</h2>
			<div class="book-list">
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余: 3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<!-- 重复 book-item 以显示更多书籍 -->
			</div>
			<h2>心愿书单</h2>
			<div class="book-list">
				<div class="book-item">
					<img src="book-placeholder.jpg" alt="书籍封面">
					<p>《这是书名》 - 作者</p>
					<p>剩余：3天</p>
					<div class="dropdown">
						<a href="#">立即还书</a> <a href="#">详细信息</a>
					</div>
				</div>
				<!-- 重复 book-item 以显示更多书籍 -->
			</div>
		</main>
	</div>
</body>

</html>