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

/* カテゴリーの表示変更 */
function changeCategory(type) {
	kind = type;
	const LIST = (type === 'income') ? INCOME_CATEGORY_LIST : EXPENSE_CATEGORY_LIST;

	LIST.forEach(c => {
		document.getElementById('name' + c.number).value = c.name;
		document.getElementById('name' + c.number).name = (type === 'income') ? 'income' : 'expense';
	});
	if(LIST === INCOME_CATEGORY_LIST){
		INCOME_BTN.classList.add('active');
		EXPENSE_BTN.classList.remove('active');
	} else if(LIST === EXPENSE_CATEGORY_LIST) {
		EXPENSE_BTN.classList.add('active');
		INCOME_BTN.classList.remove('active');
	}
}

/* 収支切り替えてもテキストボックスに入力したものが保存される */
function onInput(number) {
	const LIST = (kind === 'income') ? INCOME_CATEGORY_LIST : EXPENSE_CATEGORY_LIST;
	
	const ITEM = LIST.find(c => c.number === number);
	if(ITEM) {
		ITEM.name = document.getElementById('name'+number).value;
	}
}

/* 送信ボタンが押された際の処理 */
form.onsubmit = function() {
	
	form.querySelectorAll("input[name='income_id']").forEach(e => e.remove());
	form.querySelectorAll("input[name='expense_id']").forEach(e => e.remove());
	
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

}

del.onsubmit = function(event) {
    if(!window.confirm('本当に削除してよろしいですか？')){
        event.preventDefault();
    }
}