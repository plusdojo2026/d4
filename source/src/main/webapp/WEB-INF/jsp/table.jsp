<!-- 
 作成日：2026/06/11
 作成者：服部瑚夏
 更新日：2026/06/17
 更新者：服部瑚夏
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<div class="year">
			<form method="GET" action="/d4/TableServlet">
				<button class="year-btn">＜</button>
				<input type="hidden" name="year" value="${year-1}">
			</form>
			<c:out value="${year}"/>年
			<form method="GET" action="/d4/TableServlet">
				<button class="year-btn">＞</button>
				<input type="hidden" name="year" value="${year+1}">
			</form>
		</div>
		
		<!-- 収支と貯蓄合計 -->
		<div class="sum-box">
			<div class="sum">
				収入<br>
				<p class="income"><fmt:formatNumber value="${yearIncome}" type="number"/></p>
			</div>
			<div class="sum">
				支出<br>
				<p class="expense"><fmt:formatNumber value="${yearExpense}" type="number"/></p>
			</div>
			<div class="sum">
				貯蓄<br>
				<p class="saving"><fmt:formatNumber value="${yearIncome-yearExpense}" type="number"/></p>
			</div>
		</div>
		
		<!-- 集計表 -->
		<div class="table">
			<c:if test="${judge}">
				<table>
						<tr>
							<th>月</th><th>収入</th><th>支出</th><th>貯蓄</th>
						</tr>
						<c:forEach var="i" begin="1" end="12">
							<c:set var="income" value="${incomeList[i-1].money}"/>
							<c:set var="expense" value="${expenseList[i-1].money}"/>
							<c:set var="savings" value="${income - expense}"/>
							<c:if test="${income > 0 || expense > 0}">
								<tr>
									<td><c:out value="${i}"></c:out></td>
									<td><fmt:formatNumber value="${income}" type="number"/></td>
									<td><fmt:formatNumber value="${expense}" type="number"/></td>
									<c:choose>
										<c:when test="${savings > 0}">
											<td style="color: #00b9ef;"><fmt:formatNumber value="${savings}" type="number"/></td>
										</c:when>
										<c:when test="${savings < 0}">
											<td style="color: #ef0000;"><fmt:formatNumber value="${savings}" type="number"/></td>
										</c:when>
										<c:when test="${savings == 0}">
											<td><fmt:formatNumber value="${savings}" type="number"/></td>
										</c:when>
									</c:choose>
								</tr>
							</c:if>
						</c:forEach>
				</table>
			</c:if>
			<c:if test="${!judge}">
				<c:out value="${year}"/>年は収支データがありません。
			</c:if>
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
	<script src="js/common.js"></script>
	<script src="js/table.js"></script>
</body>
</html>