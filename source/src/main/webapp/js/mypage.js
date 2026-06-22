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