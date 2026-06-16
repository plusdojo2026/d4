package model;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	
	// フィールド
	private List<BpView> bpView;	// 収支リスト
	private String date;		// 日付
	private int sum;			// 収支合計
	
	// セッター
	public void setBpView(List<BpView> bpView) { this.bpView = bpView; }
	public void setDate(String date) { this.date = date; }
	public void setSum(int sum) { this.sum = sum; }
	
	// ゲッター
	public List<BpView> getBpView() { return this.bpView; }
	public String getDate() { return this.date; }
	public int getSum() { return this.sum; }
	
	// コンストラクタ
	public SearchResult(List<BpView> bpView, String date, int sum) {
		this.bpView = bpView;
		this.date = date;
		this.sum = sum;
	}
	public SearchResult() {
		this.bpView = new ArrayList<BpView>();
		this.date = "";
		this.sum = 0;
	}
	
}