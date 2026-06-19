<!-- 
 作成日：2026/06/16
 作成者：服部
 更新者：木下
 更新日：2026/06/19
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>検索</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/search.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div>
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
		<div>
			<label class="year-box">
				<select name="year" form="search-form">
					<c:forEach var="year" items="${yearList}">
						<option value="${year}"<c:if test="${year == selectedYear}">selected</c:if>><c:out value="${year}"></c:out></option>
					</c:forEach>
				</select>
			</label>
			<label class="month-box">
				<select name="month" form="search-form">
					<c:forEach var="month" items="${monthList}">
						<option value="${month}"<c:if test="${month == selectedMonth}">selected</c:if>><c:out value="${month}"></c:out></option>
					</c:forEach>
				</select>
			</label>
		</div>
	</header>
	<!-- メイン -->
	<main>
		<!-- 検索フォーム -->
		<form method="POST" action="/d4/SearchServlet" id="search-form">
			<!-- 検索バー -->
			<div class="input-box">
				<input type="text" name="keyword" class="input" placeholder="キーワードを入力してください" value="${enteredKeyword}">
				<button type="submit" name="search">
					<img src="img/search-btn.png" class="search-btn">
				</button>
			</div>
			<!-- 並び替え -->
			<div class="sort">
				<label>
					日付
					<select name="sort">
						<option value="DESC"<c:if test="${selectedSort == 'DESC'}">selected</c:if>>降順</option>
						<option value="ASC"<c:if test="${selectedSort == 'ASC'}">selected</c:if>>昇順</option>
					</select>
				</label>
			</div>
		</form>
		
		<!-- 収支と貯蓄合計 -->
		<div class="sum-box">
			<div class="sum">
				収入<br>
				<p class="income"><c:out value="${incomeSum}"></c:out></p>
			</div>
			<div class="sum">
				支出<br>
				<p class="expense"><c:out value="${expenseSum}"></c:out></p>
			</div>
			<div class="sum">
				貯蓄<br>
				<c:out value="${incomeSum - expenseSum}"></c:out>
			</div>
		</div>
		
		<!-- 収支表示 -->
		<div class="">
			<c:forEach var="search" items="${searchList}">
				<div class="date-block">
				
					<!-- 日付ヘッダ -->
					<div class="date-header" onclick="toggleDetail(this)">
						<span><c:out value="${search.date}"></c:out></span>
						<span><c:out value="${search.sum}">円</c:out></span>
						<img src="img/down-list.png" class="arrow">
					</div>
					
					<!-- 明細 -->
					<div class="date-detail">
						<c:forEach var="bpView" items="${search.bpView}">
							<div class="bp">
								<span><c:out value="${bpView.cname}"></c:out></span>
								<c:if test="${bpView.kind == 1}">
									<span style="color: #00b9ef;"><c:out value="${bpView.bp.money}"></c:out></span>
								</c:if>
								<c:if test="${bpView.kind == 2}">
									<span style="color: #ef0000;"><c:out value="${bpView.bp.money}"></c:out></span>
								</c:if>
								<span><c:out value="${bpView.bp.memo}"></c:out></span>
							</div>
							
							<!-- 隠しメニュー表示用ボタン -->
							<button class="menu-btn" onclick="toggleMenu(this)">︙</button>
							
							<!-- 隠しメニュー -->
							<div class="menu">
								<form method="GET" action="/d4/MoneyUpdateServlet">
									<input type="submit" name="submit" value="編集">
									<input type="hidden" name="id" value="${bpView.bp.id}">
									<input type="hidden" name="cid" value="${bpView.bp.cid}">
									<input type="hidden" name="money" value="${bpView.bp.money}">
									<input type="hidden" name="memo" value="${bpView.bp.memo}">
									<input type="hidden" name="year" value="${bpView.bp.year}">
									<input type="hidden" name="month" value="${bpView.bp.month}">
									<input type="hidden" name="day" value="${bpView.bp.day}">
									<input type="hidden" name="date" value="${search.date}">
								</form>
								<form method="POST" id="bp-delete" action="/d4/SearchServlet">
									<input type="submit" name="submit" value="削除">
									<input type="hidden" name="id" value="${bpView.bp.id}">
								</form>
							</div>
						</c:forEach>
						<input type="hidden" form="bp-delete" name="year" value="${selectedYear}">
						<input type="hidden" form="bp-delete" name="month" value="${selectedMonth}">
						<input type="hidden" form="bp-delete" name="keyword" value="${keyWord}">
						<input type="hidden" form="bp-delete" name="sort" value="${selectedSort}">
					</div>
				</div>
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
	<script src="js/search.js"></script>
</body>
</html>