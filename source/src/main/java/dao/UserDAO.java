/* 作成日：2026/6/10
 * 作成者：深井
 * 更新日：2026/6/18
 * 更新者：服部, 深井 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "d4";
	private final String PASS = "password";

	// 引数で指定されたUserでログイン成功ならuser情報を返す
	public User isLoginOk(User inputUser) throws Exception {
		System.out.println("DAO: ログイン開始");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;

		User user = new User();

		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM User WHERE `mail`=? AND `pass`=?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる処理
			pStmt.setString(1, inputUser.getMail());
			pStmt.setString(2, inputUser.getPass());

			// SELECT文を実行し、結果表を取得する
			rs = pStmt.executeQuery();

			// ユーザIDとパスワードが一致するユーザがいれば結果をtrueにする
			if (rs.next()) {
				System.out.println("ユーザ取得完了");
				user.setMail(rs.getString("mail"));
				user.setName(rs.getString("name"));
				user.setTarget(rs.getInt("target"));
				user.setTrans(rs.getString("trans"));
			} else {
				System.out.println("ユーザ情報なし");
				user = null;
			}

		// 例外処理
		} catch (SQLException e) {
			throw new Exception("ログイン失敗しました！<br>管理者に連絡してください。");

		// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		// 結果を返す
		return user;
	}

	// 引数で指定されたUserで更新成功ならuser情報を返す
	public boolean update(User user) throws Exception {
		System.out.println("DAO: 更新開始");
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;

		try {
			con = getConnection();

			// UPDATE文を準備する
			String sql = "UPDATE User SET name=?, target=?, trans=? WHERE mail=?";
			pStmt = con.prepareStatement(sql);

			// ?の部分に値を入れる処理
			pStmt.setString(1, user.getName());
			pStmt.setInt(2, user.getTarget());
			pStmt.setString(3, user.getTrans());
			pStmt.setString(4, user.getMail());

			// UPDATE文を実行してpStmtのclose処理
			if(pStmt.executeUpdate() >= 1) {
				System.out.println("更新完了");
				return true;
			}
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("情報更新に失敗しました！<br>管理者に連絡してください。");

		// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		return false;
	}

	// 引数で指定されたUserで新規登録成功ならtrueを返す
	public boolean insert(User user) throws Exception {
		System.out.println("DAO: 新規登録");
		Connection con = null;
		PreparedStatement pStmt = null;
		boolean result = false;

		try {
			con = getConnection();

			String sql = "INSERT INTO User(mail, pass) VALUES(?,?)";
			pStmt = con.prepareStatement(sql);

			pStmt.setString(1, user.getMail());
			pStmt.setString(2, user.getPass());

			if (pStmt.executeUpdate() == 1) {
				System.out.println("ユーザ登録完了");
				result = true;
			}
			
		// 例外処理
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				System.out.println("登録済みユーザのため登録未完了");
				return false;
			}
			throw new Exception("ユーザー登録に失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}

		return result;
	}

	// アカウントを削除(追加)
	public boolean delete(String mail) throws Exception {
		System.out.println("DAO: 削除");

		Connection con = null;
		PreparedStatement pStmt = null;
		boolean result = false;

		try {
			con = getConnection();

			// DELETE文を準備する
			String sql = "DELETE FROM User WHERE mail = ?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる
			pStmt.setString(1, mail);

			// 削除できたかを確認する
			if (pStmt.executeUpdate() >= 1) {
				System.out.println("削除成功");
				result = true;
			}
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("アカウント削除に失敗しました！<br>管理者に連絡してください。");
			
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, null, pStmt);
		}

		return result;
	}
	
	// ユーザー情報取得
	public User select(String mail) throws Exception {
		System.out.println("DAO: ユーザー取得開始");
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pStmt = null;

		User user = new User("", "", "", 0 , "");
		
		try {
			con = getConnection();

			// SELECT文を準備する
			String sql = "SELECT * FROM User WHERE `mail`=?";
			pStmt = con.prepareStatement(sql);

			// ？の部分に値を入れる処理
			pStmt.setString(1, mail);

			// SELECT文を実行し、結果表を取得する
			rs = pStmt.executeQuery();

			// ユーザの値をsetする
			if (rs.next()) {
				System.out.println("ユーザ情報再取得");
				user.setMail(rs.getString("mail"));
				user.setName(rs.getString("name"));
				user.setTarget(rs.getInt("target"));
				user.setTrans(rs.getString("trans"));
			} else {
				System.out.println("ユーザ情報なし");
				user = null;
			}
			
		// 例外処理
		} catch (SQLException e) {
			throw new Exception("情報更新に失敗しました！<br>管理者に連絡してください。");
		
		// 最終的に必ず行う処理
		} finally {
			closeAll(con, rs, pStmt);
		}

		return user;
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