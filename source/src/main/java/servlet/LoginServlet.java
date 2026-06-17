/* 作成日：2026/06/11
 * 作成者：佐藤
 * 更新日：2026/06/17
 * 更新者：服部 */

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ログイン：doGet(ログイン画面表示)");
		System.out.println("-----------------------------------");
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			System.out.println("ログイン：doPost(ログイン処理)");
			// リクエストパラメータを取得する
			request.setCharacterEncoding("UTF-8");
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			
			// ログイン処理を行う
			UserDAO dao = new UserDAO();
			User user = new User(mail,pass);
			user = dao.isLoginOk(user);
	
			if (user != null) { // ログイン成功
				System.out.println("ログイン成功！");
				System.out.println("-----------------------------------");
				// セッションスコープにIDを格納する
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", new User(user.getMail(), "", user.getName(), user.getTarget(), user.getTrans()));
	
				// 任意のサーブレットにリダイレクトする
				response.sendRedirect(user.getTrans());
			} else { // ログイン失敗
				System.out.println("ログイン失敗");
				// リクエストスコープに、メール、パスワード、結果、メッセージを格納する
				request.setAttribute("cancelMail", mail);
				request.setAttribute("cancelPass", pass);
				request.setAttribute("result", "失敗");
				request.setAttribute("message", "メールアドレスまたはパスワードが違います。");
	
				System.out.println("-----------------------------------");
				// ログインページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/LoginServlet");
			
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}


}
