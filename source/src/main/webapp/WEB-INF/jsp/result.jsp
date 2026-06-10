<!-- 
 作成日：服部、深井
 作成者：2026/06/10
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
	<title>結果</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/result.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div>
			<h1><img src="logo.png" alt="ぽちため"></h1>
		</div>
	</header>
	
	<!-- メイン -->
	<main>
		<div class="error">
			<img src="img/error.png">
			<h1><c:out value="${errorTitle}"></c:out></h1>
		</div>
		<c:out value="${errorMsg}" escapeXml="false"></c:out>
		<a href="${goTo}" class="trans">自動で遷移しない場合はこちら</a>
	</main>

	<!-- スクリプト -->
	<script>
		'use strict';
		
		setTimeout(function() {
			window.location.href = '${goTo}';
		}, 2200);
		
	</script>
</body>
</html>