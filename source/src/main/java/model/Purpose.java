/* 作成日：2026/06/11
 * 作成者：深井
 * 更新者：
 * 更新日： */

package model;

import java.io.Serializable;

public class Purpose implements Serializable{
	
	// フィールド
	private int id;		// ID
	private String mail;	// ユーザメール
	private String text;	// 目的
	
	// セッター
	public void setId(int id) { this.id = id; }
	public void setMail(String mail) { this.mail = mail; }
	public void setText(String text) { this.text = text; }
	
	// ゲッター
	public int getId() { return this.id; }
	public String getMail() { return this.mail; }
	public String getText() { return this.text; }
	
	// コンストラクタ
	public Purpose(int id, String mail, String text) {
		this.id = id;
		this.mail = mail;
		this.text = text;
	}	
	public Purpose() {
		this.id = 0;
		this.mail = "";
		this.text = "";
	}
}

