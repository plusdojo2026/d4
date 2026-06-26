<!-- 
 作成日：2026/06/10
 作成者：服部、深井
 更新者：佐藤、深井
 更新日：2026/06/25
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>ログイン</title>
	<link rel="stylesheet" href="css/user.css">
</head>
<body>
	<!-- ヘッダー -->
	<header>
		<div class="logo">
			<!-- class追加 -->
			<h1>
				<img src="img/logo.png" alt="ぽちため">
			</h1>
		</div>
	</header>

	<!-- メイン -->
	<main>
		<!-- 入力フォーム -->
		<form method="POST" action="/d4/LoginServlet" id="login-form">

			<div class="input-form">
				<!-- class追加 -->
				<div class="input-box">
					<label for="mail">メールアドレス</label><br>
					<c:if test="${result == '失敗'}">
						<input type="text" id="mail" class="input" name="mail"
							style="border: 2px solid #FF0000;"
							value=<c:out value="${cancelMail}"/>>
					</c:if>
					<c:if test="${result != '失敗'}">
						<input type="text" id="mail" class="input" name="mail"
							value="demo.user@example.com">
					</c:if>
					<span class="message" id="mail-error"></span>
				</div>

				<div class="input-box">
					<label for="pass">パスワード</label><br>

					<div class="input-oya">
						<c:if test="${result == '失敗'}">
							<input type="password" id="pass" class="input" name="pass"
								style="border: 2px solid #FF0000;"
								value="<c:out value='${cancelPass}'/>">
						</c:if>

						<c:if test="${result != '失敗'}">
							<input type="password" id="pass" class="input" name="pass"
								value="DemoPass123">
						</c:if>

						<span id="change-pass"><img id="eye-img" class="eye" src="img/close-eye.png"></span>
					</div>

					<span class="message" id="ps-error"></span>
				</div>

			</div>

			<div class="error">
				<span class="error-message" id="login-error"><c:out
						value="${message}"></c:out></span>
			</div>

			<div class="button">
				<!-- class追加 -->
				<input type="submit" name="login" value="ログイン">
			</div>

		</form>

		<div class="trans">
			<a href="/d4/UserRegistServlet">ユーザ登録</a>
		</div>
	</main>

	<!-- スクリプト -->
	<script src="js/login.js"></script>
</body>
</html>