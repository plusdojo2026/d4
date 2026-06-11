<!-- 
 作成日：2026/6/10
 作成者：服部、深井
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
	<title>ユーザー登録</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/user.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div>
			<h1><img src="img/logo.png" alt="ぽちため"></h1>
		</div>
	</header>
	
	<!-- メイン -->
	<main>
		<!-- 入力フォーム -->
		<form method="POST" action="/d4/UserRegistServlet" id="user-regist-form">
		
			<div class="input-box">
				<label for="regist-mail">メールアドレス</label><br>
				<input type="text" id="regist-mail" class="input" name="mail" <c:if test="${result == '失敗'}">style="border: 2px solid #FF0000;"</c:if> value=<c:out value="${cancelMail}"/>>
				<span class="message" id="regist-mail-error"></span>
			</div>
			
			<div class="input-box">
				<label for="regist-pass">パスワード</label><br>
				<input type="password" id="regist-pass" class="input" name="pass" <c:if test="${result == '失敗'}">style="border: 2px solid #FF0000;"</c:if> value=<c:out value="${cancelPass}"/>>
				<span id="change-pass" class="eye"><img id="eye-img" src="img/close-eye.png"></span>
				<span class="message" id="regist-ps-error"></span>
			</div>
			
			<div class="input-box">
				<label for="check-pass">パスワード確認</label><br>
				<input type="password" id="check-pass" class="input" name="check-pass" <c:if test="${result == '失敗'}">style="border: 2px solid #FF0000;"</c:if> value=<c:out value="${cancelCheckPass}"/>>
				<span id="change-check-pass" class="eye"><img id="check-eye-img" src="img/close-eye.png"></span>
				<span class="message" id="check-ps-error"></span>
			</div>
			
			<div class="error">
				<span class="message" id="regist-error"><c:out value="${message}"></c:out></span>
			</div>
			
			<div>
				<input type="submit" name="regist" value="登録">
			</div>
		</form>
		
		<div class="trans">
			<a href="/d4/LoginServlet">ログイン</a>
		</div>
	</main>
	
	<!-- スクリプト -->
	<c:if test="${success == true}">
		<script>
			alert("登録成功！");
			window.location.href = "/d4/LoginServlet";
		</script>
	</c:if>
	<script src="js/userRegist.js"></script>
</body>
</html>