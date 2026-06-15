/* 作成日：2026/6/10
 * 作成者：木下
 * 更新者：2026/06/15
 * 更新日：服部 */

package model;

import java.io.Serializable;

public class Category implements Serializable {
	
	// フィールド
	private int id;		// カテゴリーID
	private String mail;	// ユーザーメール
	private int number;	// カテゴリー番号
	private String name;	// カテゴリー名
	private int kind;		// 収支
	
	// セッター
	public void setId(int id) { this.id = id; }
	public void setMail(String mail) { this.mail = mail; }
	public void setNumber(int number) { this.number = number; }
	public void setName(String name) { this.name = name; }
	public void setKind(int kind) { this.kind = kind; }
	
	// ゲッター
	public int getId() { return this.id; }
	public String getMail() { return this.mail; }
	public int getNumber() { return this.number; }
	public String getName() { return this.name; }
	public int getKind() { return this.kind; }
	
	// コンストラクタ
	public Category() {
		this.id = 0;
		this.mail = "";
		this.number = 0;
		this.name = "";
		this.kind = 0;
	}
	
	public Category(int id, String mail, int number, String name, int kind) {
		this.id = id;
		this.mail = mail;
		this.number = number;
		this.name = name;
		this.kind = kind;
	}
}
