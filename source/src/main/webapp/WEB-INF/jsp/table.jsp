<!-- 
 作成日：2026/06/11
 作成者：服部瑚夏
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
	<title>集計表</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/table.css">
</head>
<body>
	<header>
		<div class="logo">
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
	</header>
	<main>
		<!-- 年変更部分 -->
		<div>
			<a href="/d4/TableServlet?year=${year-1}">＜</a>
			<c:out value="${year}">年</c:out>
			<a href="/d4/TableServlet?year=${year+1}">＞</a>
		</div>
		
		<!-- 収支と貯蓄合計 -->
		<div class="sum-box">
			<div class="sum">
				収入<br>
				<c:out value="${income}"></c:out>
			</div>
			<div class="sum">
				支出<br>
				<c:out value="${expense}"></c:out>
			</div>
			<div class="sum">
				貯蓄
				<c:out value="${saving}"></c:out>
			</div>
		</div>
		
		<!-- 集計表 -->
		<table>
			<tr>
				<th>月</th><th>収入</th><th>支出</th><th>貯蓄</th>
			</tr>
			
			<c:forEach var="sum" items="${sumList}">
				<tr>
					<td><c:out value="${sum.month}"></c:out></td>
					<td><c:out value="${sum.income}"></c:out></td>
					<td><c:out value="${sum.expense}"></c:out></td>
					<td><c:out value="${sum.income - sum.expense}"></c:out></td>
				</tr>
			</c:forEach>
		</table>
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
	<script src="js/table.js"></script>
</body>
</html>