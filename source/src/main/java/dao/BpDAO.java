/* 作成日：2026/06/10
 * 作成者：木下
 * 更新者：服部
 * 更新日：2026/06/15 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bp;
import model.SearchResult;

public class BpDAO {
	
	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "d4";
	private final String PASS = "password";
	
	// 引数bpで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(Bp bp) throws Exception {
		System.out.println("DAO: 収支登録開始");
		Connection con = null;
		PreparedStatement pStmt = null;
		
		// 登録結果を格納する
		boolean result = false;
		
		try {
			con = getConnection();
			
			// INSERT文を準備する
			String sql = "INSERT INTO Bp VALUES (0, ?, ?, ?, ?, ?, ?, ?)";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる処理
			pStmt.setString(1, bp.getMail());
			pStmt.setInt(2, bp.getCid());
			pStmt.setInt(3, bp.getMoney());
			if (bp.getMemo() != null) {
				pStmt.setString(4, bp.getMemo());
			} else {
				pStmt.setString(4, "");
			}
			pStmt.setString(5, bp.getYear());
			pStmt.setString(6, bp.getMonth());
			pStmt.setString(7, bp.getDay());
			
			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
			
		// 例外処理	
		} catch (SQLException e){
			
			throw new Exception("収支登録に失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con, null, pStmt);
			
		}
		
		// 結果を返す
		return result;
		
	}
	
	// 引数bpで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(Bp bp) throws Exception {
		System.out.println("DAO: 収支編集開始");
		Connection con = null;
		PreparedStatement pStmt = null;
		
		// 登録結果を格納する
		boolean result = false;
		
		try {
			con = getConnection();
			
			// UPDATE文を準備する
			String sql = "UPDATE Bp SET cid=?, money=?, memo=?, year=?, month=?, day=?"
					+ "WHERE id=?";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる処理
			pStmt.setInt(1, bp.getCid());
			pStmt.setInt(2, bp.getMoney());
			if (bp.getMemo() != null) {
				pStmt.setString(3, bp.getMemo());
			} else {
				pStmt.setString(3, "");
			}
			pStmt.setString(4, bp.getYear());
			pStmt.setString(5, bp.getMonth());
			pStmt.setString(6, bp.getDay());
			pStmt.setInt(7, bp.getId());
			pStmt.setString(8, bp.getMail());
			
			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
			
		// 例外処理	
		} catch (SQLException e){
			
			throw new Exception("収支編集に失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con, null, pStmt);
			
		}
		
		// 結果を返す
		return result;
	}
	
	// 引数idで指定されたレコードを削除し、成功ならtrueを返す
	public boolean delete(int id) throws Exception {
		System.out.println("DAO: 収支削除開始");
		Connection con = null;
		PreparedStatement pStmt = null;
			
		// 削除結果を格納する
		boolean result = false;
			
		try {
			con = getConnection();
				
			// DELETE文を準備する
			String sql = "DELETE FROM Bp WHERE id=?";
			pStmt = con.prepareStatement(sql);
				
			// ？の部分に値を入れる処理
			pStmt.setInt(1, id);
				
			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				System.out.println("収支削除完了");
				result = true;
			}
				
		// 例外処理	
		} catch (SQLException e){
				
			throw new Exception("収支削除に失敗しました！\n管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
				
			closeAll(con, null, pStmt);
				
		}
			
		// 結果を返す
		return result;
		
	}
	
	// 引数mail,kindが一致するものを検索して、取得されたデータのリストを返す(マイページ用)
	public List<Bp> mailSelect(String mail, int kind) throws Exception {
		System.out.println("DAO: 収支一覧取得開始");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;
			
		List<Bp> bpList = new ArrayList<Bp>();
			
		try {
			con = getConnection();
				
			// SELECT文を準備する
			String sql = "SELECT b.month, SUM(b.money) FROM Bp b INNER JOIN Category c ON b.cid = c.id "
					+ "WHERE b.mail=? AND c.kind=? GROUP BY month";
			pStmt = con.prepareStatement(sql);
				
			// ？の部分に値を入れる処理
			pStmt.setString(1, mail);
			pStmt.setInt(2, kind);
			
			// SELECT文を実行し、結果表を取得する
			rs = pStmt.executeQuery();
				
			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Bp bpTable = new Bp(mail, rs.getInt(2), rs.getString("month"));
				bpList.add(bpTable);
			}
				
			System.out.println("収支一覧取得完了");
			
		// 例外処理	
		} catch (SQLException e){
				
			throw new Exception("収支合計取得に失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
				
			closeAll(con,rs,pStmt);
				
		}
			
		// 結果を返す
		return bpList;
			
	}
	
	// 引数year, month, keywordで指定された項目で検索して、取得されたデータのリストをsortで指定された順に並び替えて返す(検索ページ用)
	public List<SearchResult> searchSelect(String mail, String year, String month, int sort, String keyWord) throws Exception {
		System.out.println("DAO: 検索開始");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		
		List<SearchResult> srList = new ArrayList<SearchResult>();
		
		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "SELECT * FROM Bp b INNER JOIN Category c ON b.cid = c.id "
					+ "WHERE b.mail=? AND (c.name LIKE ? OR b.memo LIKE ?) AND (b.year=? AND b.month=?)";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる処理
			pStmt.setString(1, mail);
			if (keyWord != null) {
				pStmt.setString(2, "%" + keyWord + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (keyWord != null) {
				pStmt.setString(3, "%" + keyWord + "%");
			} else {
				pStmt.setString(3, "%");
			}
			if (year != null) {
				pStmt.setString(4, year);
			} else {
				pStmt.setString(4, "%");
			}
			if (month != null) {
				pStmt.setString(5, month);
			} else {
				pStmt.setString(5, "%");
			}
			if (sort == 1) {
				pStmt.setString(6, "DESC");
			} else if(sort == 2) {
				pStmt.setString(6, "ASC");
			} else {
				pStmt.setString(6, "DESC");
			}
			
			// SELECT文を実行し、結果表を取得する
			rs = pStmt.executeQuery();
			
			// 結果表をコレクションにコピーする
			while (rs.next()) {
				SearchResult sr = new SearchResult (rs.getInt("b.id"), rs.getString("b.mail"), rs.getInt("b.cid"), 
						rs.getInt("b.money"), rs.getString("b.memo"), rs.getString("b.year"), rs.getString("b.month"), 
						rs.getString("b.day"), rs.getString("c.cname"), "", 0);
				srList.add(sr);		
			}
			
			System.out.println("検索完了");
			
		// 例外処理	
		} catch (SQLException e){
			
			throw new Exception("検索に失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con,rs,pStmt);
			
		}
		
		// 結果を返す
		return srList;
		
	}
	
	// 引数mail,year,kindで指定された項目で検索して、取得されたデータのリストを返す(集計表ページ用)
		public List<Bp> tableSelect(String mail, String year, int kind) throws Exception {
			System.out.println("DAO: 集計表作成開始");
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement pStmt = null;
			
			List<Bp> bpList = new ArrayList<Bp>();
			
			try {
				con = getConnection();
				
				// SELECT文を準備する
				String sql = "SELECT b.month, SUM(b.money) FROM Bp b INNER JOIN Category c ON b.cid = c.id "
						+ "WHERE b.mail=? AND b.year=? AND c.kind=? GROUP BY month ORDER BY b.month ASC";
				pStmt = con.prepareStatement(sql);
				
				// ？の部分に値を入れる処理	
				pStmt.setString(1, mail);
				pStmt.setString(2, year);
				pStmt.setInt(3, kind);
				
				// SELECT文を実行し、結果表を取得する
				rs = pStmt.executeQuery();
				
				// 結果表をコレクションにコピーする
				while (rs.next()) {
					Bp bpTable = new Bp(rs.getString("mail"), rs.getInt(2), rs.getString("month"));
					bpList.add(bpTable);		
				}
				
			// 例外処理	
			} catch (SQLException e){
				
				throw new Exception("集計表作成に失敗しました！<br>管理者に連絡してください。");
			
			// 最終的に必ず行う処理
			} finally {
				
				closeAll(con,rs,pStmt);
				
			}
			
			// 結果を返す
			return bpList;
			
		}
	
		// 接続を行うメソッド
		private Connection getConnection() throws Exception {

			Connection con = null;
			try {
				// JDBCドライバを読み込む
				Class.forName("com.mysql.cj.jdbc.Driver");
				// データベースに接続する
				con = DriverManager.getConnection(URL, USER, PASS);
				System.out.println("DB接続");
				// 例外処理
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("DB接続処理に失敗しました！<br>管理者に連絡してください。");
			} catch (SQLException e) {
				throw new Exception("DB接続処理に失敗しました！<br>管理者に連絡してください。");
			}

			return con;
		}
			
		// 切断を行うメソッド
		private void closeAll(Connection con, ResultSet rs, PreparedStatement pStmt) throws Exception {

			// Connection切断
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new Exception("DB切断処理に失敗しました！<br>管理者に連絡してください。");
				}
			}
			
			// ResultSet切断
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new Exception("DB切断処理に失敗しました！<br>管理者に連絡してください。");
				}
			}

			// PreparedStatement切断
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch (SQLException e) {
					throw new Exception("DB切断処理に失敗しました！<br>管理者に連絡してください。");
				}
			}

			System.out.println("DB切断");
		}
	}