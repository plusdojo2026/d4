package model;

public class BpView {
	// フィールド
	private Bp bp;			// 収支リスト
	private String cname;	// カテゴリー名
	private int kind;		// 収支
	
	// セッター
	public void setBp(Bp bp) { this.bp = bp; }
	public void setCname(String cname) { this.cname = cname; }
	public void setKind(int kind) { this.kind = kind; }
	
	// ゲッター
	public Bp getBp() { return this.bp; }
	public String getCname() { return this.cname; }
	public int getKind() { return this.kind; }
	
	// コンストラクタ
	public BpView(Bp bp, String cname, int kind) {
		this.bp = bp;
		this.cname = cname;
		this.kind = kind;
	}
	public BpView() {
		this.bp = new Bp();
		this.cname = "";
		this.kind = 0;
	}
}
