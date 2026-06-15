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

import model.Category;

public class CategoryDAO {
	
	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "d4";
	private final String PASS = "password";
	
	// 追加
	public boolean insert(String mail) throws Exception {
		System.out.println("DAO: 追加");
		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "INSERT INTO Category (mail, number, name, kind) VALUES "
					+ "(?, 1, '給料', 1), (?, 2, '', 1), (?, 3, '', 1), (?, 4, '', 1), (?, 5, '', 1), "
					+ "(?, 6, '', 1), (?, 7, '', 1), (?, 8, '', 1), (?, 9, '', 1), (?, 10, '', 1), "
					+ "(?, 1, '買い物', 2), (?, 2, '固定費', 2), (?, 3, '娯楽', 2), (?, 4, '', 2), (?, 5, '', 2), "
					+ "(?, 6, '', 2), (?, 7, '', 2), (?, 8, '', 2), (?, 9, '', 2), (?, 10, '', 2)";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			for(int i = 1; i <= 20; i++) {
				pStmt.setString(i, mail);
			}

			// 追加できたかを確認する
			if(pStmt.executeUpdate() == 20) {
				System.out.println("追加成功");
				return true;
			}
		
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("カテゴリ登録失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}
		return false;
	}

//	//削除
//	public void delete(int id) {
//		System.out.println("DAO: 削除");
//
//		Connection con = null;
//		PreparedStatement pStmt = null;
//
//		try {
//			con = getConnection();
//
//			String sql = "DELETE FROM category WHERE id = ?";
//			pStmt = con.prepareStatement(sql);
//			pStmt.setInt(1, id);
//
//			pStmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			closeAll(con, null, pStmt);
//		}
//	}
	
	// 編集
	public boolean update(Category category) throws Exception {
		System.out.println("DAO: 編集");
		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "UPDATE Category SET `name`=? WHERE `id`=?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, category.getName());
			pStmt.setInt(2, category.getId());

			// 追加できたかを確認する
			if(pStmt.executeUpdate() >= 1) {
				System.out.println("編集成功");
				return true;
			}
		
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("カテゴリ編集を失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}
		return false;
	}
	
	// 一覧(設定画面用)
	public List<Category> findByMail(String mail, int kind) throws Exception {
		System.out.println("DAO: カテゴリ一覧取得");

		List<Category> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM Category WHERE mail = ? AND kind = ? ORDER BY number ASC";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる
			pStmt.setString(1, mail);
			pStmt.setInt(2, kind);

			// 結果票を取得
			rs = pStmt.executeQuery();

			// 取得した結果票をリストに格納
			while (rs.next()) {
				Category p = new Category(rs.getInt("id"), rs.getString("mail"), rs.getInt("number"), rs.getString("name"), rs.getInt("kind"));
				list.add(p);
			}
			
			System.out.println("カテゴリ一覧取得完了");
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("カテゴリ一覧取得を失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		return list;
	}
	
	// 一覧(収支登録/編集画面用)
	public List<Category> selectList(String mail, int kind) throws Exception {
		System.out.println("DAO: カテゴリ一覧取得");

		List<Category> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM Category WHERE mail = ? AND kind = ? AND name <> '' ORDER BY number ASC";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる
			pStmt.setString(1, mail);
			pStmt.setInt(2, kind);

			// 結果票を取得
			rs = pStmt.executeQuery();

			// 取得した結果票をリストに格納
			while (rs.next()) {
				Category p = new Category(rs.getInt("id"), rs.getString("mail"), rs.getInt("number"), rs.getString("name"), rs.getInt("kind"));
				list.add(p);
			}
			
			System.out.println("カテゴリ一覧取得完了");
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("カテゴリ一覧取得を失敗しました！<br>管理者に連絡してください。");
		
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
				throw new Exception("DB接続処理に失敗しました！<br>管理者に連絡してください。");
			}
		}
		
		// ResultSet切断
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new Exception("DB接続処理に失敗しました！<br>管理者に連絡してください。");
			}
		}

		// PreparedStatement切断
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				throw new Exception("DB接続処理に失敗しました！<br>管理者に連絡してください。");
			}
		}

		System.out.println("DB切断");
	}

}