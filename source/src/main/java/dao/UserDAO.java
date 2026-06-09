/* 作成日：
 * 作成者：
 * 更新者：
 * 更新日： */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	
	// データベースの情報を格納するフィールド
	private final String URL = "jdbc:mysql://localhost:3306/d4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private final String USER = "root";
	private final String PASS = "password";

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

}
