<!-- 
 作成日：2026/06/16
 作成者：服部
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
	<title>設定</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/set.css">
</head>
<body>
	<header>
		<div class="logo">
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
		<div>
			<input type="submit" name="submit" form="set-form" value="更新">
		</div>
	</header>
	<main>
		<form method="POST" action="/d4/SetServlet" id="set-form">
			<div class="category-setting">
				<div class="category-title">
					カテゴリ設定
					<button class="income-btn">収入</button>
					<button class="expense-btn">支出</button>
				</div>
				<div class="category">
					<c:forEach var="category" items="${incomeCategoryList}">
						<div class="category-input">
							<input type="text" value="${category.name}">
							<img src="img/dust-box.png">
							<input type="hidden" name="cid" value="${category.id}">
						</div>
					</c:forEach>
					<button onclick="" class="add">追加</button>
				</div>
			</div>
			
			<div class="user-setting">
				<div class="set-box">
					ニックネーム
					<input type="text" name="name" class="input" placeholder="ホーム画面で表示されます" value="${name}">
				</div>
				<div class="set-box">
					目的
					<c:forEach var="purpose" items="${purposeList}">
						<input type="text" name="text" class="input" value="${purpose.text}">
						<input type="hidden" name="id" value="${purpose.id}">
					</c:forEach>
				</div>
				<div class="set-box">
					目標金額
					<input type="text" name="target" class="input" <c:if test="${empty target}">placeholder="目的達成に必要な金額"</c:if> value="${target}">
				</div>
				<div class="set-box">
					起動時遷移先
					<div class="radio">
						<label>
							<input type="radio" name="trans" value="/d4/MyPageServlet" <c:if test="${trans == '/d4/MyPageServlet'}">checked="checked"</c:if>>
							マイページ
						</label>
						<label>
							<input type="radio" name="trans" value="/d4/MoneyRegistServlet" <c:if test="${trans == '/d4/MoneyRegistServlet'}">checked="checked"</c:if>>
							収支登録
						</label>
						<label>
							<input type="radio" name="trans" value="/d4/SearchServlet" <c:if test="${trans == '/d4/SearchServlet'}">checked="checked"</c:if>>
							検索
						</label>
						<label>
							<input type="radio" name="trans" value="/d4/TableServlet" <c:if test="${trans == '/d4/TableServlet'}">checked="checked"</c:if>>
							集計表
						</label>
					</div>
				</div>
			</div>
		</form>
		<div class="delete-user">
			<form method="POST" action="/d4/MyPageServlet" id="delete">
				<button type="submit" name="submit" value="削除">アカウント削除</button>
			</form>
		</div>
	</main>
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
	<script>
		const incomeCategoryList = [];
		<c:forEach var="category" items="${incomeCategoryList}">
			incomeCategoryList.push({id: "${category.id}", name: "${category.name}"});
		</c:forEach>
		const expenseCategoryList = [];
		<c:forEach var="category" items="${expenseCategoryList}">
			expenseCategoryList.push({id: "${category.id}", name: "${category.name}" });
		</c:forEach>
	</script>
	<script src="js/common.js"></script>
	<script src="js/set.js"></script>
</body>
</html>