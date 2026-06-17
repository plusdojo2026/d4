/* 作成日：
 * 作成者：
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoryDAO;
import dao.PurposeDAO;
import dao.UserDAO;
import model.Category;
import model.Purpose;
import model.User;

@WebServlet("/SetServlet")
public class SetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("設定：doGet(情報を取得して設定画面表示)");

			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			String mail = loginUser.getMail();

			// カテゴリ取得
			CategoryDAO cDao = new CategoryDAO();
			List<Category> incomeCategoryList = cDao.findByMail(mail, 1);
			List<Category> expenseCategoryList = cDao.findByMail(mail, 2);

			// 目的取得
			PurposeDAO pDao = new PurposeDAO();
			List<Purpose> purposeList = pDao.findByMail(mail);
			
			// リクエストスコープに保存
			request.setAttribute("name", loginUser.getName());
			request.setAttribute("purposeList", purposeList);
			request.setAttribute("target", loginUser.getTarget());
			request.setAttribute("trans", loginUser.getTrans());
			request.setAttribute("incomeCategoryList", incomeCategoryList);
			request.setAttribute("expenseCategoryList", expenseCategoryList);
			
			System.out.println("-----------------------------------");
			// 設定画面にフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("goTo", "/d4/SetServlet");
			request.setAttribute("errorMsg", e.getMessage());
			
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("設定：doPost");

			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			String mail = loginUser.getMail();

			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String value = request.getParameter("submit");

			int id = Integer.parseInt(request.getParameter("id"));
			String text = request.getParameter("text");

			Purpose purpose = new Purpose();
			purpose.setId(id);
			purpose.setText(text);
			
			Category category = new Category();
			category.setId(id);
			category.setName(text);

			UserDAO iDao = new UserDAO();
			CategoryDAO dao1 = new CategoryDAO();
			PurposeDAO dao2 = new PurposeDAO();

			if (value != null) {
				// アカウント削除ボタン
				if (value == "削除") {
//					int id = Integer.parseInt(request.getParameter("userId"));
					iDao.delete(mail);
					response.sendRedirect("/d4/LoginServlet");
				}
				// 変更確定ボタン
				if (value == "変更") {
					boolean result1 = dao1.update(category);
					if (result1) {
						response.sendRedirect("/d4/SetServlet");
					} else {
						request.setAttribute("error", "更新に失敗しました");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
					boolean result2 = dao2.update(purpose);
					if (result2) {
						response.sendRedirect("/d4/SetServlet");
					} else {
						request.setAttribute("error", "更新に失敗しました");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/SetServlet");
			
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}

}
