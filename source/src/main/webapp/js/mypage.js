/* 作成日：2026/06/22
 * 作成者：服部
 * 更新日：
 * 更新者： */

'use strict';

const FORM = document.querySelectorAll('.delete-img-form');
const LOGOUT = document.getElementById('logout-form');

FORM.forEach(f => {
	f.addEventListener('submit', function(event){
		if(!window.confirm('削除してもよろしいですか?')) {
			event.preventDefault();
		}
	});
});

LOGOUT.onsubmit = function(event) {
	if(!window.confirm('ログアウトしますか？')){
		event.preventDefault();
	}
}

function clampTo8Digits(selector) {
    const el = document.querySelector(selector);
    if (!el) return; 

    let num = el.textContent.replace(/,/g, "");

    if (!/^[0-9]+$/.test(num)) return;

    if (num.length > 8) {
        el.textContent = "99,999,999円";
        return;
    }

    el.textContent = Number(num).toLocaleString() + "円";
}

clampTo8Digits(".target-amount");
clampTo8Digits(".saving-amount");