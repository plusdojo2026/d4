<!-- 
 作成日：2026/06/16
 作成者：服部
 更新者：服部
 更新日：2026/06/22 
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
	<header class="header">
		<div class="logo">
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
		<input type="submit" name="submit" form="set-form" value="更新">
	</header>
	<main>
		<form method="POST" action="/d4/SetServlet" id="set-form">
			<div class="user-setting">
				<div class="set-box">
					ニックネーム<br>
					<div class="input-box">
						<input type="text" name="name" id="nickname" class="input" placeholder="ホーム画面で表示されます" value="${name}">
						<div id="name-error" class="error"></div>
					</div>
				</div>
				<div class="set-box">
					目的<br>
					<c:forEach var="i" begin="1" end="3">
						<c:set var="purpose" value="${purposeList[i-1]}"/>
						<div class="input-box">
							<input type="text" name="text" id="purposerist" class="input" placeholder="${i}つ目" value="${purpose.text}"><br>
							<div id="purpose-error" class="error"></div>
						</div>
						<input type="hidden" name="id" value="${purpose.id}">
					</c:forEach>
				</div>
				<div class="set-box">
					目標金額<br>
					<div class="input-box">
						<input type="text" name="target" id="targetrist" class="input" placeholder="目的達成に必要な金額" value="${target}">
						<div id="target-error" class="error"></div>
					</div>
				</div>
			</div>
			
			<div class="category-setting">
				<div class="category-title">
					カテゴリ設定
					<div class="in-ex-btns">
						<button type="button" class="income-btn active" onclick="changeCategory('income')">収入</button>
						<button type="button" class="expense-btn" onclick="changeCategory('expense')">支出</button>
					</div>
				</div>
				<div id="cat-error" class="error"></div>
				<div class="category-box">
					<c:forEach var="i" begin="1" end="10">
						<c:set var="category" value="${incomeCategoryList[i-1]}"/>
						<div id="number${i}" class="category-input">
							<input type="text" id="name${i}" oninput="onInput(${i})" name="cname" placeholder="${i}つ目" value="${category.name}" oninput="onInput(${i})">
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="radio-box">
				起動時遷移先<br>
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
		</form>
		<div class="delete-user">
			<form method="POST" action="/d4/SetServlet" id="delete-form">
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
		const INCOME_CATEGORY_LIST = [];
		<c:forEach var="category" items="${incomeCategoryList}">
			INCOME_CATEGORY_LIST.push({id: ${category.id}, name: "${category.name}", number: ${category.number}});
		</c:forEach>
		const EXPENSE_CATEGORY_LIST = [];
		<c:forEach var="category" items="${expenseCategoryList}">
			EXPENSE_CATEGORY_LIST.push({id: ${category.id}, name: "${category.name}", number: ${category.number}});
		</c:forEach>
	</script>
	<script src="js/common.js"></script>
	<script src="js/set.js"></script>
	<c:if test="${result}">
		<script>
			alert("更新成功！\nマイページ画面に遷移します。");
			window.location.href = "/d4/MyPageServlet";
		</script>
	</c:if>
	<c:if test="${result == false}">
		<script>
			alert("更新失敗。\nもう一度やり直してください。");
		</script>
	</c:if>
	<c:if test="${success == true}">
		<script>
			alert("アカウント削除成功！\nログイン画面に遷移します。");
			window.location.href = "/d4/LoginServlet";
		</script>
	</c:if>
</body>
</html>