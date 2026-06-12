/* 作成日：2026/6/10
 * 作成者：深井
 * 更新者：服部
 * 更新日：2026/06/11 */

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PurposeDAO;
import dao.UserDAO;
import model.User;

@WebServlet("/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ユーザ登録：doGet");
		request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp").forward(request, response);
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		
			System.out.println("ユーザ登録：doPost");
			
			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String mail = request.getParameter("mail");
			String pass = request.getParameter("pass");
			String checkPass = request.getParameter("check-pass");
	
			// ユーザ情報をセット
			User user = new User();
			user.setMail(mail);
			user.setPass(pass);
	
			// ユーザ登録
			UserDAO dao = new UserDAO();
			PurposeDAO pDao = new PurposeDAO();
			boolean result = dao.insert(user);
			
			if (result) {	// 登録成功
				System.out.println("ユーザ登録成功！");
				for(int i = 0; i < 3; i++) {
					System.out.println("目的登録：" + (i+1) + "つ目");
					result = pDao.insert(mail);
				}
				request.setAttribute("success", true);
				request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp").forward(request, response);
			} else {		// 登録失敗
				System.out.println("ユーザ登録失敗、、");
				request.setAttribute("cancelMail", mail);
				request.setAttribute("cancelPass", pass);
				request.setAttribute("cancelCheckPass", checkPass);
				request.setAttribute("result", "失敗");
				request.setAttribute("message", "このメールアドレスは既に登録されています。");
				request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("errorMsg",e.getMessage());
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}
}
