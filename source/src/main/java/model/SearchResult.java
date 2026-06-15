package model;

public class SearchResult extends Bp {
	
	// フィールド
	private String cname;	// カテゴリー名
	private String date;	// 日付
	private int sum;		// 収支合計
	
	// セッター
	public void setCname(String cname) { this.cname = cname; }
	public void setDate(String date) { this.date = date; }
	public void setSum(int sum) { this.sum = sum; }
	
	// ゲッター
	public String getCname() { return this.cname; }
	public String getDate() { return this.date; }
	public int getSum() { return this.sum; }
	
	// コンストラクタ
	public SearchResult(int id, String mail, int cid, int money, String memo, String year, String month, String day, String cname, String date, int sum) {
		super(id, mail, cid, money, memo, year, month, day);
		this.cname = cname;
		this.date = date;
		this.sum = sum;
	}
	public SearchResult() {
		super();
		this.cname = "";
		this.date = "";
		this.sum = 0;
	}
	
}
