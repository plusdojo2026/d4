/* 作成日：2026/6/10
 * 作成者：深井
 * 更新者：
 * 更新日： */

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
	
	// 引数で指定されたuserでログイン成功ならtrueを返す
		public User isLoginOk(User user) {
			System.out.println("DAO: ログイン開始");
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement pStmt = null;
			
			try {
				con = getConnection();
				
				// SELECT文を準備する
				String sql = "SELECT * FROM User WHERE `mail`=? AND `pass`=?";
				pStmt = con.prepareStatement(sql);
				
				// ？の部分に値を入れる処理
				pStmt.setString(1, user.getMail());
				pStmt.setString(2, user.getPass());
				
				
				// SELECT文を実行し、結果表を取得する
				rs = pStmt.executeQuery();
				
				// ユーザIDとパスワードが一致するユーザがいれば結果をtrueにする
				if(rs.next()) {
	                user.setName(rs.getString("name"));
	                user.setTarget(rs.getInt("target"));
	                user.setTrans(rs.getString("trans"));
	            }
				
			// 例外処理
			} catch(SQLException e) {
				e.printStackTrace();
				
			// 最終的に必ず行う処理
			} finally {
				closeAll(con,rs,pStmt,null);
			}
			
			// 結果を返す
			return user;
		}
		public User update(User user) {
		    System.out.println("DAO: 更新開始");
		    Connection con = null;
		    ResultSet rs = null;
		    PreparedStatement pStmt = null;
		    PreparedStatement pStmt1 = null;

		    try {
		        con = getConnection();

		        String sql = "UPDATE User SET name=?, target=?, trans=? WHERE mail=?";
		        
		        pStmt = con.prepareStatement(sql);
		        pStmt.setString(1, user.getName());
		        pStmt.setInt(2, user.getTarget());
		        pStmt.setString(3, user.getTrans());
		        pStmt.setString(4, user.getMail());
		        pStmt.executeUpdate();

		     // SELECT文を準備する
				sql = "SELECT * FROM User WHERE `mail`=?";
				pStmt1 = con.prepareStatement(sql);
				
				// ？の部分に値を入れる処理
				pStmt1.setString(1, user.getMail());
				
				// SELECT文を実行し、結果表を取得する
				rs = pStmt1.executeQuery();
				
				// ユーザIDとパスワードが一致するユーザがいれば結果をtrueにする
				if(rs.next()) {
	                user.setName(rs.getString("name"));
	                user.setTarget(rs.getInt("target"));
	                user.setTrans(rs.getString("trans"));
	            }

		    } catch(SQLException e) {
		        e.printStackTrace();

		    } finally {
		        closeAll(con, rs, pStmt,pStmt1);
		    }

		    return user;
		}
		public boolean insert(User user) {
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
		            result = true;
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return result;
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
	private void closeAll(Connection con, ResultSet rs, PreparedStatement pStmt, PreparedStatement pStmt1) {
		
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
		if(pStmt1 != null) {
			try {
				pStmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("DB切断");
	}

}
