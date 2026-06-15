/* 作成日：2026/06/12
 * 作成者：深井
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

import model.Purpose;

public class PurposeDAO {

	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "d4";
	private final String PASS = "password";

	// 目的を登録して、成功ならtrueを返す
	public boolean insert(String mail) throws Exception {
		System.out.println("DAO: 追加");
		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "INSERT INTO Purpose (mail, text) VALUES (?, ''), (?, ''), (?, '')";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			for(int i = 1; i <= 3; i++) {
				pStmt.setString(i, mail);
			}

			// 追加できたかを確認する
			if(pStmt.executeUpdate() >= 1) {
				System.out.println("追加成功");
				return true;
			}
		
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("目的登録に失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}
		return false;
	}

	// 目的を編集して、成功ならtrueを返す
	public boolean update(Purpose purpose) throws Exception {
		System.out.println("DAO: 編集");
		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "UPDATE Purpose SET `text`=? WHERE `id`=?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, purpose.getText());
			pStmt.setInt(2, purpose.getId());

			// 追加できたかを確認する
			if(pStmt.executeUpdate() >= 1) {
				System.out.println("編集成功");
				return true;
			}
		
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("目的編集に失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}
		return false;
	}
	
	// メールアドレスで目的一覧を取得
	public List<Purpose> findByMail(String mail) throws Exception {
		System.out.println("DAO: 目的一覧取得");

		List<Purpose> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM Purpose WHERE mail = ?";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる
			pStmt.setString(1, mail);

			// 結果票を取得
			rs = pStmt.executeQuery();

			// 取得した結果票をリストに格納
			while (rs.next()) {
				Purpose p = new Purpose(rs.getInt("id"), rs.getString("mail"), rs.getString("text"));
				list.add(p);
			}
			
			System.out.println("目的一覧取得完了");
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("目的一覧取得に失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		return list;
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
				throw new Exception("DB切断に失敗しました！<br>管理者に連絡してください。");
			}
		}
		
		// ResultSet切断
		if (rs != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new Exception("DB切断に失敗しました！<br>管理者に連絡してください。");
			}
		}

		// PreparedStatement切断
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				throw new Exception("DB切断に失敗しました！<br>管理者に連絡してください。");
			}
		}

		System.out.println("DB切断");
	}

}
