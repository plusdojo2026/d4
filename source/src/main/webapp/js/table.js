/* 作成日：2026/06/23
 * 作成者：深井
 * 更新者：
 * 更新日： */

'use strict';
function clampTo8Digits(selector) {
    const el = document.querySelector(selector);
    if (!el) return;

    let num = el.textContent.replace(/,/g, "");

    if (!/^[0-9]+$/.test(num)) return;

    if (num.length > 8) {
        num = "99999999";
    }

    el.textContent = Number(num).toLocaleString();
}
clampTo8Digits(".income");
clampTo8Digits(".expense");
clampTo8Digits(".saving");