/* 作成日：2026/06/12
 * 作成者：深井
 * 更新者：
 * 更新日： */

package model;

import java.io.Serializable;

public class Img implements Serializable{
	
	// フィールド
	private int id;		// ID
	private String mail;	// ユーザーメール
	private String path;	// 画像パス
	
	// セッター
	public void setId(int id) { this.id = id; }
	public void setMail(String mail) {	this.mail = mail; }
	public void setPath(String path) {	this.path = path; }
	
	// ゲッター
	public int getId() { return this.id; }
	public String getMail() { return this.mail; }
	public String getPath() { return this.path; }
	
	// コンストラクタ
	public Img(int id, String mail, String path) {
		this.id = id;
		this.mail = mail;
		this.path = path;
	}
	public Img() {
		this.id = 0;
		this.mail = "";
		this.path = "";
	}
}
