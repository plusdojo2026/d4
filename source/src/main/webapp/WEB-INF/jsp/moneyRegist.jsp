<!-- 
 作成日：2026/06/10
 作成者：木下、佐藤
 更新者：
 更新日： 
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%
    // サーバー側で現在日時を取得
    Date now = new Date();
    request.setAttribute("now", now);
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
	<h1>収支登録</h1>
	<form method="POST" action="/d4/MoneyRegistServlet" id="regist-form">
	<div class="button">
	<button type="button">収入</button>
	<button type="button">支出</button><br>
	</div>
	
	<div class="content">
	<h2>日付</h2>
	<input type="date" value="<fmt:formatDate value='${now}' pattern='yyyy/MM/dd' />"><br>
	
	<h2>金額</h2>
	<input type="text"><br>
	
	<h2>カテゴリ</h2>
	<select>
		<c:forEach var="category" items="${categories}">
			<option>
				<c:out value="${category.name}"></c:out>
			</option>
		</c:forEach>
	</select><br>
	</div>
	
	<div class="memo">
	<h2>メモ</h2><br>
	<input type="text"><br>
	</div>
	
	<input class="regist" type="submit" value="登録">
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