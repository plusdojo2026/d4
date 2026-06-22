/* 作成日：2026/06/19
 * 作成者：服部
 * 更新日：服部、木下
 * 更新者：2026/06/22 */

'use strict';

// 51日目以降の表示
let displayCount = 50;

document.getElementById("more").onclick = function() {
	
	let items = document.querySelectorAll(".date-block");
	
	for (let i = displayCount; i <displayCount + 50; i++) {
		
		if (items[i]) {
			items[i].style.display = "block";
		}
		
	}
	
	displayCount += 50;
	
	if(displayCount >= items.length) {
		this.style.display = "none";
		
	}
}

const FORM = document.querySelectorAll('.bp-delete');

function toggleDetail(dateHeader) {
	const DATE_BLOCK = dateHeader.parentElement;
	const DETAIL = DATE_BLOCK.querySelectorAll('.date-detail');
	const ARROW = dateHeader.querySelector('.arrow');
	
	const IS_OPEN = DETAIL[0].style.display === 'block';
	
	DETAIL.forEach(d => {
		d.style.display = IS_OPEN ? 'none' : 'block' ;
	});
	
	ARROW.src = IS_OPEN ? 'img/down-list.png' : 'img/up-list.png';
}

function toggleMenu(menuBtn) {
	const MENU = menuBtn.parentElement.nextElementSibling;
	const ALL_MENU = document.querySelectorAll('.menu');
	const ALL_BTN = document.querySelectorAll('.menu-btn');
	ALL_MENU.forEach(a => {
		if(a != MENU) {
			a.style.display = 'none';
		}
	});
	ALL_BTN.forEach(b => {
		if(b != menuBtn) {
			b.classList.remove('clicked');
		}
	});
	
	const IS_OPEN = MENU.style.display === 'block';
	MENU.style.display = IS_OPEN ? 'none' : 'block' ;
	menuBtn.classList.add('clicked');
}

// 削除ボタン押下時
FORM.forEach(f => {
	f.onsubmit = function(event) {
		if(!window.confirm('本当に削除しますか？')){
			event.preventDefault();
		}
	}
});
