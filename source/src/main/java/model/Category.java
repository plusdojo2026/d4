/* 作成日：2026/6/10
 * 作成者：木下
 * 更新者：
 * 更新日： */

package model;

import java.io.Serializable;

public class Category implements Serializable {
	
	// フィールド
	private int id;			// カテゴリーID
	private String mail;	// ユーザーメール
	private String name;	// カテゴリー名
	private int kind;		// 収支
	
	// セッター
	public void setId(int id) { this.id = id; }
	public void setMail(String mail) { this.mail = mail; }
	public void setName(String name) { this.name = name; }
	public void setKind(int kind) { this.kind = kind; }
	
	// ゲッター
	public int getId() { return id; }
	public String getMail() { return mail; }
	public String getName() { return name; }
	public int getKind() { return kind; }
	
	// コンストラクタ
	public Category() {
		this.id = 0;
		this.mail = "";
		this.name = "";
		this.kind = 0;
	}
	
	public Category(int id, String mail, String name, int kind) {
		this.id = id;
		this.mail = mail;
		this.name = name;
		this.kind = kind;
	}
}
