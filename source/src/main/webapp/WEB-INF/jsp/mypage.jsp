<!-- 
 作成日：2026/06/11
 作成者：深井、服部
 更新者：
 更新日： 
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>マイページ</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/myapage.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div>
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
		<form method="GET" action="/d4/LogoutServlet" id="logout-form">
			<input type="submit" value = "ログアウト">
		</form>
	</header>
		
	<main>
		<!-- 目的題名 -->
		<div class="title">
			<img src="img/purpose-icon.png">
			<c:if test="${not empty loginUser.name}">
				<c:out value="${loginUser.name}">さんの</c:out>
			</c:if>
			目的
		</div>
		<!-- 目的・目標金額・貯金額表示欄 -->
		<div class="">
			<!-- 目的表示 -->
			<div class="purpose">
				<ul>
					<c:forEach var="purpose" items="${purposeList}">
						<li><c:out value="${purpose.text}"></c:out>
					</c:forEach>
				</ul>
			</div>
			<!-- 目標金額と貯金額のテーブル -->
			<table>
				<tr>
					<td>目標金額</td>
					<td>
						<c:if test="${not empty loginUser.target}">
							<c:out value="${loginUser.target}"></c:out>
						</c:if>
						<c:if test="${empty loginUser.target}">
							---
						</c:if>
						円
				</tr>
				<tr>
					<td>貯金額</td>
					<td>
						<c:out value="${savings}"></c:out>円
					</td>
				</tr>
			</table>
		</div>
		<!-- 画像設定題名 -->
		<div class="title">
			<img src="img/image.png">
			モチベを上げる画像
		</div>
		<form method="POST" action="/d4/MyPageServlet" id="input-img-form">
			<label>追加
				<input type="file" name="photo" accept="image/*">
				<input type = "submit" value = "アップロード">
			</label>
		</form>
		<c:forEach var="img" items="${imgList}">
			<form method="POST" action="/d4/MyPageServlet" class="delete-img-form">
				<img src="${img.path}">
				<input type="hidden" name="imgId" value="${img.id}">
				<button type="submit"><img src="img/dust-box.png"></button>
			</form>
		</c:forEach>
	</main>
	<footer>
		<nav class="nav">
			<ul>
				<li><a href="/d4/MyPageServlet"><img src="img/mypage.png" alt="マイページ"></a></li>
				<li><a href="/d4/MoneyRegistServlet"><img src="img/money-regist.png" alt="収支登録"></a></li>
				<li><a href="/d4/SearchServlet"><img src="img/search.png" alt="検索"></a></li>
				<li><a href="/d4/TableServlet"><img src="img/table.png" alt="集計表"></a></li>
				<li><a href="/d4/SetServlet"><img src="img/set.png" alt="設定"></a></li>
			</ul>
		</nav>
	</footer>
	
	<!-- スクリプト -->
	<script src="js/common.js"></script>
	<script src="js/mypage.js"></script>
</body>
</html>