<!-- 
 作成日：2026/06/10
 作成者：木下、佐藤
 更新者：服部
 更新日：2026/06/11
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%
    // サーバー側で現在日時を取得
    Date now = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    request.setAttribute("today", sdf.format(now));
%>
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
	<header>
		<div class="logo">
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
	</header>
	<main>
		<!-- タイトルと収支ボタン -->
		<div class="money-title">
			<img src="img/money-regist-logo.png">
			収支登録
			<form method="POST" action="/d4/MoneyRegistServlet" id="change-kind">
				<div class="button">
					<button type="submit" value="1">収入</button>
					<button type="submit" value="2">支出</button><br>
				</div>
			</form>
		</div>
		
		<!-- 実際の入力フォーム -->
		<form method="POST" action="/d4/MoneyRegistServlet" id="regist-form">
			<div class="content">
			
				<div class="money-input">
					日付<input type="date" name="date" value="${today}"><br>
				</div>
				
				<div class="money-input">
					金額<input type="text" name="money"><br>
				</div>
				
				<div class="money-input">
					カテゴリ
					<select>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category.id}">
								<c:out value="${category.name}"></c:out>
							</option>
						</c:forEach>
					</select><br>
				</div>
				
				<div class="money-input">
					<div class="memo">
						メモ<br>
						<input type="text" name="memo"><br>
					</div>
				</div>
				
			</div>
			
			<input class="regist" type="submit" name="regist" value="登録">
			
		</form>
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
	<script src="js/money.js"></script>
	<script src="js/moneyRegist.js"></script>
</body>
</html>