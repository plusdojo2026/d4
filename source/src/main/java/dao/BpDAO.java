/* 作成日：2026/06/10
 * 作成者：木下
 * 更新者：
 * 更新日： */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bp;
import model.Category;

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
			
			throw new Exception("収支登録を失敗しました！\n管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con,pStmt);
			
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
			
			// INSERT文を準備する
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
			
			throw new Exception("収支編集を失敗しました！\n管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con,pStmt);
			
		}
		
		// 結果を返す
		return result;
	}
	
	// 引数bp,ctgで指定された項目で検索して、取得されたデータのリストを返す(メール照合用)
		public List<Bp> mailSelect(Bp bp) throws Exception {
			System.out.println("DAO: メール照合開始");
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement pStmt = null;
			
			List<Bp> bpList = new ArrayList<Bp>();
			
			try {
				con = getConnection();
				
				// SELECT文を準備する
				String sql = "SELECT * FROM Bp WHERE mail=?";
				pStmt = con.prepareStatement(sql);
				
				// ？の部分に値を入れる処理
				pStmt.setString(1, bp.getMail());
				
				// SELECT文を実行し、結果表を取得する
				rs = pStmt.executeQuery();
				
				// 結果表をコレクションにコピーする
				while (rs.next()) {
					Bp bpTable = new Bp(rs.getInt("id"), rs.getString("mail"), rs.getInt("cid"), 
							rs.getInt("money"), rs.getString("memo"), rs.getString("year"),
							rs.getString("month"), rs.getString("day"));
					bpList.add(bpTable);		
				}
				
			// 例外処理	
			} catch (SQLException e){
				
				throw new Exception("メール照合を失敗しました！\n管理者に連絡してください。");
			
			// 最終的に必ず行う処理
			} finally {
				
				closeAll(con,rs,pStmt);
				
			}
			
			// 結果を返す
			return bpList;
			
		}
	
	// 引数bp,ctgで指定された項目で検索して、取得されたデータのリストを返す(検索ページ用)
	public List<Bp> searchSelect(Bp bp, Category ctg) throws Exception {
		System.out.println("DAO: 検索開始");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;
		
		List<Bp> bpList = new ArrayList<Bp>();
		
		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "SELECT * FROM Bp INNER JOIN Category ON Bp.cid = Category.id"
					+ "WHERE (Category.name LIKE ? OR Bp.memo LIKE ?) AND (Bp.year LIKE ? AND Bp.month LIKE ?)";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる処理
			if (ctg.getName() != null) {
				pStmt.setString(1, "%" + ctg.getName() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (bp.getMemo() != null) {
				pStmt.setString(2, "%" + bp.getMemo() + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (bp.getYear() != null) {
				pStmt.setString(3, "%" + bp.getYear() + "%");
			} else {
				pStmt.setString(3, "%");
			}
			if (bp.getMonth() != null) {
				pStmt.setString(4, "%" + bp.getMonth() + "%");
			} else {
				pStmt.setString(4, "%");
			}
			
			// SELECT文を実行し、結果表を取得する
			rs = pStmt.executeQuery();
			
			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Bp bpTable = new Bp(rs.getInt("id"), rs.getString("mail"), rs.getInt("cid"), 
						rs.getInt("money"), rs.getString("memo"), rs.getString("year"),
						rs.getString("month"), rs.getString("day"));
				bpList.add(bpTable);		
			}
			
		// 例外処理	
		} catch (SQLException e){
			
			throw new Exception("検索を失敗しました！\n管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			
			closeAll(con,rs,pStmt);
			
		}
		
		// 結果を返す
		return bpList;
		
	}
	
	// 引数bp,ctgで指定された項目で検索して、取得されたデータのリストを返す(集計表ページ用)
		public List<Bp> tableSelect(Bp bp, Category ctg) throws Exception {
			System.out.println("DAO: 集計表作成開始");
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement pStmt = null;
			
			List<Bp> bpList = new ArrayList<Bp>();
			
			try {
				con = getConnection();
				
				// SELECT文を準備する
				String sql = "SELECT * FROM Bp INNER JOIN Category ON Bp.cid = Category.id"
						+ "WHERE Bp.year=? ";
				pStmt = con.prepareStatement(sql);
				
				// ？の部分に値を入れる処理	
				pStmt.setString(1, bp.getYear());
				
				// SELECT文を実行し、結果表を取得する
				rs = pStmt.executeQuery();
				
				// 結果表をコレクションにコピーする
				while (rs.next()) {
					Bp bpTable = new Bp(rs.getInt("id"), rs.getString("mail"), rs.getInt("cid"), 
							rs.getInt("money"), rs.getString("memo"), rs.getString("year"),
							rs.getString("month"), rs.getString("day"));
					bpList.add(bpTable);		
				}
				
			// 例外処理	
			} catch (SQLException e){
				
				throw new Exception("集計表作成を失敗しました！\n管理者に連絡してください。");
			
			// 最終的に必ず行う処理
			} finally {
				
				closeAll(con,rs,pStmt);
				
			}
			
			// 結果を返す
			return bpList;
			
		}
	
	// 接続を行うメソッド
	private Connection getConnection() {
		
		Connection con = null;
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			// データベースに接続する
			con = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("DB接続");
		// 例外処理
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return con;
	}
		
	// 切断を行うメソッド
	private void closeAll(Connection con, ResultSet rs, PreparedStatement pStmt) {
		
		// Connection切断
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// PreparedStatement切断
		if(pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("DB切断");
	}
	
	private void closeAll(Connection con, PreparedStatement pStmt) {
		
		// Connection切断
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// PreparedStatement切断
		if(pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("DB切断");
	}

}
