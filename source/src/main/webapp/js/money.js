/* 作成日：2026/06/17
 * 作成者：服部
 * 更新日：服部
 * 更新者：2026/06/22 */

'use strict';

const INCOME_BTN = document.querySelector(".income-btn");
const EXPENSE_BTN = document.querySelector(".expense-btn");
const SELECT_BOX = document.querySelector(".select-box");
const HIDDEN = document.querySelector(".hidden-cid");

/* カテゴリをセレクトボックスのoptionに追加 */
function changeSelect(category, SELECTED_ID = null) {
	SELECT_BOX.innerHTML = "";
	
	category.forEach( c => {
		const OPTION = document.createElement("option");
		OPTION.value = c.id;
		OPTION.textContent = c.name;
		
		if(SELECTED_ID != null && c.id == SELECTED_ID) {
			OPTION.selected = true;
		}
		SELECT_BOX.appendChild(OPTION);
	});
	
	HIDDEN.value = SELECT_BOX.value;
}

/* セレクトボックスの選択が変更されたときの処理 */
SELECT_BOX.addEventListener("change", () => {
	HIDDEN.value = SELECT_BOX.value;
});

/* 収支カテゴリ切り替え */
INCOME_BTN.addEventListener("click", () => {
	changeSelect(INCOME_CATEGORY_LIST);
	INCOME_BTN.classList.add('active');
	EXPENSE_BTN.classList.remove('active');
});
EXPENSE_BTN.addEventListener("click", () => {
	changeSelect(EXPENSE_CATEGORY_LIST);
	INCOME_BTN.classList.remove('active');
	EXPENSE_BTN.classList.add('active');
});