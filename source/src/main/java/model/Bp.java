/* 作成日：2026/06/10
 * 作成者：服部
 * 更新者：
 * 更新日： */

package model;

import java.io.Serializable;

public class Bp implements Serializable {
	
	// フィールド
	private int id;		// 収支ID
	private String mail;	// ユーザーメール
	private int cid;		// カテゴリーID
	private int money;		// 金額
	private String memo;	// メモ
	private String year;	// 年
	private String month;	// 月
	private String day;		// 日
	
	// セッター
	public void setId(int id) { this.id = id; }
	public void setMail(String mail) { this.mail = mail; }
	public void setCid(int cid) { this.cid = cid; }
	public void setMoney(int money) { this.money = money; }
	public void setMemo(String memo) { this.memo = memo; }
	public void setYear(String year) { this.year = year; }
	public void setMonth(String month) { this.month = month; }
	public void setDay(String day) { this.day = day; }
	
	// ゲッター
	public int getId() { return this.id; }
	public String getMail() { return this.mail; }
	public int getCid() { return this.cid; }
	public int getMoney() { return this.money; }
	public String getMemo() { return this.memo; }
	public String getYear() { return this.year; }
	public String getMonth() { return this.month; }
	public String getDay() { return this.day; }
	
	// コンストラクタ
	public Bp(int id, String mail, int cid, int money, String memo, String year, String month, String day) {
		this.id = id;
		this.mail = mail;
		this.cid = cid;
		this.money = money;
		this.memo = memo;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public Bp() {
		this.id = 0;
		this.mail = "";
		this.cid = 0;
		this.money = 0;
		this.memo = "";
		this.year = "";
		this.month = "";
		this.day = "";
	}
	
}
