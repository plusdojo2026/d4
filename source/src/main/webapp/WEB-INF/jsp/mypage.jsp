<!-- 
 作成日：2026/06/11
 作成者：深井、服部
 更新者：2026/06/22
 更新日：服部
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>マイページ</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/mypage.css">
</head>
<body>
	<!-- ヘッダー -->
	<header class="header">
		<div>
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
		<form method="GET" action="/d4/LogoutServlet" id="logout-form">
			<input type="submit" id="form-logout" value="ログアウト">
		</form>
	</header>
	<!-- メイン -->
	<main>
		<!-- 目的題名 -->
		<div class="purpose-title">
			<img src="img/purpose-icon.png">
			<c:if test="${not empty loginUser.name}">
				<c:out value="${loginUser.name}"/>さんの
			</c:if>
			目的
		</div>
		<!-- 目的・目標金額・貯金額表示欄 -->
		<div class="purpose-box">
			<!-- 目的表示 -->
			<c:set var="allEmpty" value="true"/>
			<c:forEach var="purpose" items="${purposeList}">
				<c:if test="${not empty purpose.text}">
					<c:set var="allEmpty" value="false"/>
				</c:if>
			</c:forEach>
			<div class="purpose">
				<c:if test="${allEmpty}">
					<br>目的が設定されていません
				</c:if>
				<c:if test="${not allEmpty}">
					<ul>
						<c:forEach var="purpose" items="${purposeList}">
							<c:if test="${not empty purpose.text}">
								<li><c:out value="${purpose.text}"></c:out>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<!-- 目標金額と貯金額のテーブル -->
			<table class="purpose-table">
				<tr>
					<td>目標金額</td>
					<td class="target-amount">
						<c:if test="${not empty loginUser.target}">
							<fmt:formatNumber value="${loginUser.target}" type="number"/>
						</c:if>
						<c:if test="${empty loginUser.target}">
							---
						</c:if>
						円
				</tr>
				<tr>
					<td class="saving-title">貯金額</td>
					<td class="saving-amount">
						<c:if test="${not empty savings}">
							<fmt:formatNumber value="${savings}" type="number"/>
						</c:if>
						<c:if test="${empty savings}">
							0
						</c:if>
						円
					</td>
				</tr>
			</table>
		</div>
		<!-- 画像設定題名 -->
		<div class="img-title">
			<img src="img/image.png">
			モチベを上げる画像
		</div>
		<!-- 画像登録欄 -->
		<div class="img-input">
			<input type="file" form="input-img-form" name="photo" accept="image/*">
			<form method="POST" action="/d4/MyPageServlet" id="input-img-form" enctype="multipart/form-data">
				<label class="upload-box">
					<input type="submit" class="up-load" value="アップロード">
				</label>
			</form>
		</div>
		<div class="mypage-message-area">
			<c:if test="${not empty sessionScope.mypageMessage}">
				<p class="error">${sessionScope.mypageMessage}</p>
				<c:remove var="mypageMessage" scope="session" />
			</c:if>
		</div>
		<div class="upload-area">
			<c:forEach var="img" items="${imgList}">
				<form method="POST" action="/d4/MyPageServlet" class="delete-img-form">
					<div class="img-item">
						<img class="img" src="${img.path}">
						<input type="hidden" name="imgId" value="${img.id}">
						<button type="submit" name="submit" value="削除">
							<img class="dust-box" src="img/dust-box.png">
						</button>
					</div>
				</form>
			</c:forEach>
		</div>
	</main>
	<!-- フッター -->
	<footer>
		<nav class="nav">
			<ul class="imgs">
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