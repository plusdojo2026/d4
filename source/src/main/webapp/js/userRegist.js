/* 作成日：2026/06/10
 * 作成者：服部
 * 更新者：
 * 更新日： */

'use strict';

// HTML要素をオブジェクトとして取得
let form = document.getElementById('user-regist-form');
const MAIL_INPUT = document.getElementById('regist-mail');
const PASS_INPUT = document.getElementById('regist-pass');
const CHECK_PASS_INPUT = document.getElementById('check-pass');
let mailErrorMsg = document.getElementById('regist-mail-error');
let psErrorMsg = document.getElementById('regist-ps-error');
let checkPsErrorMsg = document.getElementById('check-ps-error');
let registErrorMsg = document.getElementById('regist-error');

const CHANGE_EYE = document.getElementById('change-pass');
const EYE_IMG = document.getElementById('eye-img');
const CHANGE_CHECK_EYE = document.getElementById('change-check-pass');
const CHECK_EYE_IMG = document.getElementById('check-eye-img');

// 目のボタンが押された際の処理
CHANGE_EYE.addEventListener('click',()=> {
	
	const IS_HIDDEN = PASS_INPUT.type === 'password';
	PASS_INPUT.type = IS_HIDDEN ? 'text' : 'password';
	EYE_IMG.src = IS_HIDDEN ? 'img/open-eye.png' : 'img/close-eye.png';
	
});
CHANGE_CHECK_EYE.addEventListener('click',()=> {
	
	const IS_HIDDEN = CHECK_PASS_INPUT.type === 'password';
	CHECK_PASS_INPUT.type = IS_HIDDEN ? 'text' : 'password';
	CHECK_EYE_IMG.src = IS_HIDDEN ? 'img/open-eye.png' : 'img/close-eye.png';
	
});

// 送信ボタンが押された際の処理
form.onsubmit = function(event) {
	
	let judge = true;
	let mail = document.getElementById('regist-mail').value;
	let pass = document.getElementById('regist-pass').value;
	let checkPass = document.getElementById('check-pass').value;
	
	let mailValidation = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(mail);
	let psValidation = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,30}$/.test(pass);
	
	// メールアドレスのチェック
	if (mail === '') {
		mailErrorMsg.textContent = '※メールアドレスを入力してください。';
		judge = false;
		MAIL_INPUT.style.border = 'solid 2px #FF0000';
	} else if (mailValidation === false) {
		mailErrorMsg.textContent = '※メールアドレスを正しい形式で入力してください。';
		judge = false;
		MAIL_INPUT.style.border = 'solid 2px #FF0000';
	} else {
		mailErrorMsg.textContent = '';
		MAIL_INPUT.style.border = 'solid 2px #000000';
	}
	
	// パスワードのチェック
	if (pass === '') {
		psErrorMsg.textContent = '※パスワードを入力してください。';
		judge = false;
		PASS_INPUT.style.border = 'solid 2px #FF0000';
	} else if (psValidation === false) {
		psErrorMsg.textContent = '※英数字を必ず含む8文字以上30文字以下で入力してください。';
		judge = false;
		PASS_INPUT.style.border = 'solid 2px #FF0000';
	} else {
		psErrorMsg.textContent = '';
		PASS_INPUT.style.border = 'solid 2px #000000';
	}
	
	// パスワード確認のチェック
	if (checkPass === '') {
		checkPsErrorMsg.textContent = '※パスワード確認を入力してください。';
		judge = false;
		CHECK_PASS_INPUT.style.border = 'solid 2px #FF0000';
	} else if (pass != checkPass) {
		checkPsErrorMsg.textContent = '※上記と同じパスワードを入力してください。';
		judge = false;
		CHECK_PASS_INPUT.style.border = 'solid 2px #FF0000';
	} else {
		checkPsErrorMsg.textContent = '';
		CHECK_PASS_INPUT.style.border = 'solid 2px #FF0000';
	}

	//正しい記述がされていない部分があれば送信取り消し
	if (judge === false) {
		event.preventDefault();
		registErrorMsg.style.display = 'none';
	} else if(!window.confirm('以下で情報を登録してもよろしいですか？\nメールアドレス：' + mail + '\nパスワード：' + pass)) {
		event.preventDefault();
	}
};