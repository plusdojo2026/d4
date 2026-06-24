/* 作成日：2026/06/17
 * 作成者：服部
 * 更新日：服部
 * 更新者：2026/06/22 */

'use strict';

let kind = 'income';
let form = document.getElementById('set-form');
let del = document.getElementById('delete-form');
const INCOME_BTN = document.querySelector('.income-btn');
const EXPENSE_BTN = document.querySelector('.expense-btn');

const NAME_INPUT = document.getElementById('nick-name');
const NAME_ERROR = document.getElementById("name-error");
const PURPOSE_INPUTS = document.querySelectorAll('.purpose');
const PURPOSE_ERROR = document.getElementById('purpose-error');
const TARGET_INPUT = document.getElementById("target");
const TARGET_ERROR = document.getElementById("target-error");
const CATEGORY_INPUTS = document.querySelectorAll(".category");
const CATEGORY_ERROR = document.getElementById("cat-error");

let incomeHasError = false;
let expenseHasError = false;

/* カテゴリーの表示変更 */
function changeCategory(type) {
	kind = type;
	const LIST = (type === 'income') ? INCOME_CATEGORY_LIST : EXPENSE_CATEGORY_LIST;

	LIST.forEach(c => {
		document.getElementById('name' + c.number).value = c.name;
		document.getElementById('name' + c.number).name = (type === 'income') ? 'income' : 'expense';
		
		if(c.error) {
			document.getElementById('name' + c.number).style.border = 'solid 2px #FF0000';
		} else {
			document.getElementById('name' + c.number).style.border = 'solid 1px #000000';
		}

	});
	if (LIST === INCOME_CATEGORY_LIST) {
		INCOME_BTN.classList.add('active');
		EXPENSE_BTN.classList.remove('active');
	} else if (LIST === EXPENSE_CATEGORY_LIST) {
		EXPENSE_BTN.classList.add('active');
		INCOME_BTN.classList.remove('active');
	}
}

/* 収支切り替えてもテキストボックスに入力したものが保存される */
function onInput(number) {
	const LIST = (kind === 'income') ? INCOME_CATEGORY_LIST : EXPENSE_CATEGORY_LIST;

	const ITEM = LIST.find(c => c.number === number);
	if (ITEM) {
		ITEM.name = document.getElementById('name' + number).value;
	}
}

/* 送信ボタンが押された際の処理 */
form.onsubmit = function(event) {

	form.querySelectorAll("input[name='income_id']").forEach(e => e.remove());
	form.querySelectorAll("input[name='expense_id']").forEach(e => e.remove());
	
	let nickName = NAME_INPUT.value;
	let target = TARGET_INPUT.value;

	let hasError = false;
	let judge = true;

	// 収入
	INCOME_CATEGORY_LIST.forEach(c => {
		const INCOME_ID = document.createElement('input');
		INCOME_ID.type = 'hidden';
		INCOME_ID.name = 'income-id';
		INCOME_ID.value = c.id;

		const INCOME_NAME = document.createElement('input');
		INCOME_NAME.type = 'hidden';
		INCOME_NAME.name = 'income-name';
		INCOME_NAME.value = c.name;

		form.appendChild(INCOME_ID);
		form.appendChild(INCOME_NAME);
	});

	// 支出
	EXPENSE_CATEGORY_LIST.forEach(c => {
		const EXPENSE_ID = document.createElement('input');
		EXPENSE_ID.type = 'hidden';
		EXPENSE_ID.name = 'expense-id';
		EXPENSE_ID.value = c.id;

		const EXPENSE_NAME = document.createElement('input');
		EXPENSE_NAME.type = 'hidden';
		EXPENSE_NAME.name = 'expense-name';
		EXPENSE_NAME.value = c.name;

		form.appendChild(EXPENSE_ID);
		form.appendChild(EXPENSE_NAME);
	});

	// ニックネームのチェック
	NAME_ERROR.textContent = '';
	NAME_INPUT.style.border = 'solid 1px #000000';

	if (nickName.length > 15) {
		NAME_ERROR.textContent = "※15文字以内で入力してください";
		NAME_INPUT.style.border = 'solid 2px #FF0000';
		judge = false;
	}

	// 目的のチェック
	PURPOSE_ERROR.textContent = '';
	
	PURPOSE_INPUTS.forEach(p => {
		let purpose = p.value.trim();
		p.style.border = 'solid 1px #000000';

		if (purpose.length > 20) {
			hasError = true;
			p.style.border = 'solid 2px #FF0000';
		}
	});
	if(hasError) {
		PURPOSE_ERROR.textContent = '※20文字以内で入力してください';
		judge = false;
	}

	// 目標金額のチェック
	
	TARGET_ERROR.textContent = '';
	TARGET_INPUT.style.border = 'solid 1px #000000';

	if (target === '') {
		TARGET_ERROR.textContent = "※0以上の数字を入力してください";
		TARGET_INPUT.style.border = 'solid 2px #FF0000';
		judge = false;
	} else if(!/^[0-9]*$/.test(target)){
		TARGET_ERROR.textContent = "※数字のみ入力できます";
		TARGET_INPUT.style.border = 'solid 2px #FF0000';
		judge = false;
	}

	// カテゴリーのチェック
	CATEGORY_ERROR.textContent = "";
	
	hasError = validateCategory();
	updateError();
	
	if(hasError === 'both') {
		CATEGORY_ERROR.textContent = '※10文字以内で収支それぞれ1つ以上登録してください。';
		judge = false;
	} else if(hasError === 'nameCheck') {
		CATEGORY_ERROR.textContent = '※10文字以内で入力してください。';
		judge = false;
	} else if(hasError === 'emptyCategory') {
		CATEGORY_ERROR.textContent = '※収支それぞれ1つ以上登録してください。'
		judge = false;
	}
	
	if(!judge) {
		event.preventDefault();
	} else if(!window.confirm('更新してもよろしいですか？　')){
			event.preventDefault();
	}
};

// カテゴリの文字数チェック
function validateCategory() {
	let nameCheck = true;
	let incomeEmpty = true;
	let expenseEmpty = true;
	
	INCOME_CATEGORY_LIST.forEach(item => {
		let name = item.name.trim();
			if(name !== '') {
				incomeEmpty = false;
			}
			if(name.length > 10) {
				item.error = true;
				nameCheck = false;
			} else {
				item.error = false;
			}
	});
	
	EXPENSE_CATEGORY_LIST.forEach(item => {
		let name = item.name.trim();
		if(name !== '') {
			expenseEmpty = false;
		}
		if(name.length > 10) {
			item.error = true;
			nameCheck = false;
		} else {
			item.error = false;
		}
	});
	
	if((incomeEmpty || expenseEmpty) && !nameCheck) {
		return 'both';
	} else if(incomeEmpty || expenseEmpty){
		return 'emptyCategory';
	} else if(!nameCheck) {
		return 'nameCheck';
	}
	
	return false;
}

// エラー表示アップデート
function updateError() {
	const LIST = (kind === 'income') ? INCOME_CATEGORY_LIST : EXPENSE_CATEGORY_LIST;
	
	LIST.forEach(item => {
		let input = document.getElementById('name' + item.number);
		
		if(item.error) {
			input.style.border = 'solid 2px #FF0000';
		} else {
			input.style.border = 'solid 1px #000000';
		}
	});
}

/* アカウント削除ボタンを押した際の処理 */
del.onsubmit = function(event) {
	if (!window.confirm('本当に削除してよろしいですか？')) {
		event.preventDefault();
	}
}