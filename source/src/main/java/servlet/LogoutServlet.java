/* 作成日：2026/06/11
 * 作成者：田中綾真
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ログアウトをします");	
		// セッションスコープを破棄する
		HttpSession session = request.getSession();
		session.invalidate();

		// ログインページにリダイレクトする
		response.sendRedirect("/d4/LoginServlet");
	}

}
