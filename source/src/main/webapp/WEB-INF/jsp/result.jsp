<!-- 
 作成日：2026/06/10
 作成者：服部、深井
 更新日：2026/06/16
 更新者：服部
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>例外結果</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/result.css">
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
		<div class="error">
			<img src="img/error.png">
			<h1>例外が発生しました</h1>
		</div>
		<div class="message">
			<c:out value="${errorMsg}" escapeXml="false"></c:out><br>
		</div>
		<div class="trans">
			<a href="${goTo}" class="trans">元の画面に戻る</a>
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
</body>
</html>