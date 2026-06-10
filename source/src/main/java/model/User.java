/* 作成日：2026/6/10
 * 作成者：深井
 * 更新者：
 * 更新日： */

package model;

import java.io.Serializable;

public class User implements Serializable{
	
	// フィールド
	private String mail;
	private String pass;
	private String name;
	private int target;
	private String trans;
	
	// セッター
	public void setMail(String mail) {
		this.mail = mail;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	
	// ゲッター
	public String getMail() {
		return this.mail;
	}
	public String getPass() {
		return this.pass;
	}
	public String getName() {
		return this.name;
	}
	public int getTarget() {
		return this.target;
	}
	public String getTrans() {
		return this.trans;
	}
	
	// コンストラクタ
	public User(String mail, String pass, String name, int target, String trans) {
		this.mail = mail;
		this.pass = pass;
		this.name = name;
		this.target = target;
		this.trans = trans;
	}
	public User() {
		this.mail = "";
		this.pass = "";
		this.name = "";
		this.target = 0;
		this.trans = "";
	}
}

