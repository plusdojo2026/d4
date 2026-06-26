/* 作成日：2026/06/22
 * 作成者：服部
 * 更新日：深井
 * 更新者：2026/06/25 */

'use strict';

const TITLE = document.querySelector('.purpose-title');
const NICK_NAME = document.getElementById('nick-name');
const NAME_LENGTH = NICK_NAME.textContent.length;
console.log(NAME_LENGTH);
if(NAME_LENGTH >= 16) {
	TITLE.style.fontSize = '11px';
} else if(NAME_LENGTH >= 13) {
	TITLE.style.fontSize = '13px';
} else if(NAME_LENGTH >= 10) {
	TITLE.style.fontSize = '15px';
} else if(NAME_LENGTH >= 8) {
	TITLE.style.fontSize = '18px';
}

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

/*function clampTo8Digits(selector) {
    const el = document.querySelector(selector);
    if (!el) return; 

    let num = el.textContent.replace(/,/g, "");

    if (!/^[0-9]+$/.test(num)) return;

    if (num.length > 8) {
        const short = num.substring(0, 5);
        el.textContent = short + "...";
        return;
    }

    el.textContent = Number(num).toLocaleString() + "円";
} 

clampTo8Digits(".target-amount");
clampTo8Digits(".saving-amount"); */