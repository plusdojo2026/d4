<!-- 
 作成日：2026/06/10
 作成者：木下、佐藤
 更新日：2026/06/17
 更新者：服部
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>収支登録</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/money.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div class="logo">
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
	</header>
	
	<!-- メイン -->
	<main>
		<!-- タイトルと収支ボタン -->
		<div class="money-title">
			<img src="img/money-regist-logo.png">
			収支登録
		</div>
		<div class="btn-box">
			<div class="button">
				<button class="income-btn">収入</button>
			</div>
			<div class="button">
				<button class="expense-btn">支出</button><br>
			</div>
		</div>
		
		<!-- 実際の入力フォーム -->
		<form method="POST" action="/d4/MoneyRegistServlet" id="regist-form">
			<div class="content">
			
				<div class="money-input">
					日付
					<div class="input">
						<input type="date" name="date" class="date" value="${today}"><br>
					</div>
				</div>
				
				<div class="money-input">
					金額
					<div class="input">
						<input type="text" name="money" class="money"><br>
					</div>
					<span class="message" id="regist-money-msg"></span><br>
				</div>
				
				<div class="money-input">
					カテゴリ
					<div class="cat-input">
						<select class="select-box"></select><br>
					</div>
				</div>
				
				<div class="money-input">
					<div class="memo-box">
						メモ<br>
						<textarea name="memo" class="memo"></textarea><br>
					</div>
					<span class="message" id="regist-memo-msg"></span><br>
				</div>
				
			</div>
			<input type="hidden" name="cid" class="hidden-cid">
			<div class="regist-btn">
				<input class="regist" type="submit" name="regist" value="登録">
			</div>
			
		</form>
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
		const SELECTED_ID = "${cid}";
		const INCOME_CATEGORY_LIST = [];
		<c:forEach var="category" items="${incomeCategoryList}">
			INCOME_CATEGORY_LIST.push({id: "${category.id}", name: "${category.name}"});
		</c:forEach>
		const EXPENSE_CATEGORY_LIST = [];
		<c:forEach var="category" items="${expenseCategoryList}">
			EXPENSE_CATEGORY_LIST.push({id: "${category.id}", name: "${category.name}" });
		</c:forEach>
	</script>
	<script src="js/common.js"></script>
	<script src="js/money.js"></script>
	<script src="js/moneyRegist.js"></script>
	<c:if test="${success == true}">
		<script>
			alert("登録成功！");
			window.location.href = "/d4/MoneyRegistServlet";
		</script>
	</c:if>
</body>
</html>