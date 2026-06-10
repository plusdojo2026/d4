/* 作成日：2026/06/10
 * 作成者：服部
 * 更新者：
 * 更新日： */

'use strict';

//HTML要素をオブジェクトとして取得
let form = document.getElementById('login-form');
const MAIL_INPUT = document.getElementById('mail');
const PASS_INPUT = document.getElementById('pass');
let mailErrorMsg = document.getElementById('mail-error');
let psErrorMsg = document.getElementById('ps-error');
let loginErrorMsg = document.getElementById('login-error');

const CHANGE_EYE =document.getElementById('change-pass');
const EYE_IMG = document.getElementById('eye-img');

//目のボタンが押された際の処理
CHANGE_EYE.addEventListener('click',()=> {
	
	const IS_HIDDEN = PASS_INPUT.type === 'password';
	PASS_INPUT.type = IS_HIDDEN ? 'text' : 'password';
	EYE_IMG.src = IS_HIDDEN ? 'img/open-eye.png' : 'img/close-eye.png';
	
});

//送信ボタンが押された際の処理
form.onsubmit = function(event) {
	
	let judge = true;
	let mail = document.getElementById('mail').value;
	let pass = document.getElementById('pass').value;
	
	let mailValidation = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(mail);
	let psValidation = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,30}$/.test(pass);
	
	//メールアドレスのチェック
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
		mailBorder.style.border = 'solid 2px #000000';
	}
	
	//パスワードのチェック
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

	//正しい記述がされていない部分があれば送信取り消し
	if (judge === false) {
		event.preventDefault();
		loginErrorMsg.style.display = 'none';
	}
};