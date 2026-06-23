/* 作成日：2026/06/12
 * 作成者：深井
 * 更新者：服部
 * 更新日：2026/06/18 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Img;

public class ImgDAO {

	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "d4";
	private final String PASS = "spDzuBN8JAwDZttc";

	// ユーザーメールをもとに、Imgテーブルに登録されている数を数える
	public int countByMail(String mail) throws Exception {
		System.out.println("DAO:数を数える");
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = getConnection();
			
			// SELECT文を準備する
			String sql = "SELECT COUNT(*) FROM Img WHERE mail = ?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, mail);

			// 結果票を取得
			rs = pStmt.executeQuery();

			if (rs.next()) {
				System.out.println("カウント成功");
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new Exception("カウント失敗しました！<br>管理者に連絡してください。");
		}

		return count;
	}

	// 画像を登録して、成功ならtrueを返す
	public boolean insert(String mail, String path) throws Exception {
		System.out.println("DAO: 追加");
		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();

			// INSERT文を準備する
			String sql = "INSERT INTO Img (mail, path) VALUES (?, ?)";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, mail);
			pStmt.setString(2, path);

			// 追加できたかを確認する
			if (pStmt.executeUpdate() >= 1) {
				System.out.println("登録成功");
				return true;
			}

			// 例外処理
		} catch (SQLException e) {
			throw new Exception("画像登録失敗しました！<br>管理者に連絡してください。");

			// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}
		return false;
	}

	// メールアドレスで画像一覧を取得
	public List<Img> findByMail(String mail) throws Exception {
		System.out.println("DAO: 画像一覧取得");

		List<Img> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM Img WHERE mail = ?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, mail);

			// 結果票を取得
			rs = pStmt.executeQuery();

			// 取得した結果票をリストに格納
			while (rs.next()) {
				Img p = new Img(rs.getInt("id"), rs.getString("mail"), rs.getString("path"));
				list.add(p);
			}

			System.out.println("画像一覧取得完了");

			// 例外処理
		} catch (SQLException e) {
			throw new Exception("画像取得に失敗しました！<br>管理者に連絡してください。");

			// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		return list;
	}

	// 画像を削除
	public boolean delete(int id) throws Exception{
		System.out.println("DAO: 削除");

		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// DELETE文を準備する
			String sql = "DELETE FROM Img WHERE id = ?";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる
			pStmt.setInt(1, id);

			// 削除できたかを確認する
			if(pStmt.executeUpdate() >= 01) {
				System.out.println("削除成功");
				return true;
			}

		} catch (SQLException e) {
			throw new Exception("削除に失敗しました！<br>管理者に連絡してください。");
		} finally {
			closeAll(con, null, pStmt);
		}
		
		return false;
	}

	// 画像を削除
	public boolean delete(String mail) throws Exception{
		System.out.println("DAO: 削除処理");

		Connection con = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();
			
			// DELETE文を準備する
			String sql = "DELETE FROM Img WHERE mail = ?";
			pStmt = con.prepareStatement(sql);
			
			// ？の部分に値を入れる
			pStmt.setString(1, mail);

			// 削除できたかを確認する
			if(pStmt.executeUpdate() >= 1) {
				System.out.println("削除成功");
				return true;
			}

		} catch (SQLException e) {
			throw new Exception("画像削除に失敗しました！<br>管理者に連絡してください。");
		} finally {
			closeAll(con, null, pStmt);
		}
		
		return false;
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
