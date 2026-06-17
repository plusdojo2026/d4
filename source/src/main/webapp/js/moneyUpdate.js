/* 作成日：2026/06/17
 * 作成者：服部
 * 更新日：
 * 更新者： */

'use strict';

// チェックされているカテゴリを調べる
function isIncomeCategory(cid) {
	return INCOME_CATEGORY_LIST.some(c => c.id == cid);
}

// チェックされているカテゴリによって、表示するカテゴリを変更する
if(isIncomeCategory(SELECTED_ID)) {
	cahngeSelect(INCOME_CATEGORY_LIST, SELECTED_ID);
} else {
	changeSelect(EXPENSE_CATEGORY_LIST, SELECTED_ID);
}

let form = document.getElementById("update-form");
let moneyErrorMsg = document.getElementById('regist-money-msg');
let memoErrorMsg = document.getElementById('regist-memo-msg');
const MONEY_INPUT = document.querySelector('.money');
const MEMO_INPUT = document.querySelector('.memo');

/* フォームの送信ボタンが押されたときの処理 */
form.onsubmit = function(event) {
	
	let judge = true;

	let money = document.querySelector(".money").value.trim();
	let memo = document.querySelector(".memo").value;
	
	const MONEY_VALIDATION = /^[0-9]+$/;
	
	// 金額のチェック
	if (money === "") {
		judge = false;
		moneyErrorMsg.textContent = '※金額を入力してください';
		MONEY_INPUT.style.border = 'solid 2px #FF0000';
	} else if(!MONEY_VALIDATION.test(money)) {
		judge = false;
		moneyErrorMsg.textContent = '※整数で入力してください';
		MONEY_INPUT.style.border = 'solid 2px #FF0000';
	} else if(MONEY_VALIDATION.test(money)) {
		let intMoney = Number(money);
		if(!Number.isInteger(intMoney) || intMoney <= 0 || intMoney >= 1000000000){
			judge = false;
			moneyErrorMsg.textContent = '※範囲外の数字です';
			MONEY_INPUT.style.border = 'solid 2px #FF0000';
		}
	} else {
		moneyErrorMsg.textContent = '';
		MONEY_INPUT.style.border = 'none';
	}
	
	// メモのチェック
	if (memo != null && memo.length > 150) {
		judge = false;
		memoErrorMsg.textContent = '※150文字以内で入力してください';
		MEMO_INPUT.style.border = 'solid 2px #FF0000';
	} else {
		memoErrorMsg.textContent = '';
		MEMO_INPUT.style.border = 'none';
	}
	
	//正しい記述がされていない部分があれば送信取り消し
	if (judge === false) {
		event.preventDefault();
	} else if(!window.confirm('編集を確定しますか?')) {
		event.preventDefault();
	}
}