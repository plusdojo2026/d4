/* 作成日：2026/06/16
 * 作成者：深井
 * 更新日：2026/06/18
 * 更新者：服部 */

package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BpDAO;
import dao.CategoryDAO;
import dao.ImgDAO;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			System.out.println(mail);

			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String value = request.getParameter("submit");
			
			String name = request.getParameter("name");
			String strTarget = request.getParameter("target");
			String trans = request.getParameter("trans");

			String[] strIdBox = request.getParameterValues("id");
			String[] textBox = request.getParameterValues("text");
			
			String[] incomeIdBox = request.getParameterValues("income-id");
			String[] incomeNameBox = request.getParameterValues("income-name");
			String[] expenseIdBox = request.getParameterValues("expense-id");
			String[] expenseNameBox = request.getParameterValues("expense-name");
			
			int id = 0;
			String text = "";
			int cid = 0;
			String cname = "";
			int target = 0;
			
			UserDAO uDao = new UserDAO();
			PurposeDAO pDao = new PurposeDAO();
			ImgDAO iDao = new ImgDAO();
			CategoryDAO cDao = new CategoryDAO();
			BpDAO  bDao = new BpDAO();

			// アカウント削除ボタン
			if (value.equals("削除")) {
				System.out.println("アカウント削除処理");
				
				if(!bDao.delete(mail)) {
					request.setAttribute("errorMsg", "カテゴリ削除に失敗しました！");
					request.setAttribute("goTo", "/d4/SetServlet");
					
					System.out.println("-----------------------------------");
					
					// 結果ページにフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
					return;
				}
				if(!iDao.delete(mail)) {
					request.setAttribute("errorMsg", "画像削除に失敗しました！");
					request.setAttribute("goTo", "/d4/SetServlet");
					
					System.out.println("-----------------------------------");
					
					// 結果ページにフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
					return;
				}
				if(pDao.delete(mail)) {
					request.setAttribute("errorMsg", "目的削除に失敗しました！");
					request.setAttribute("goTo", "/d4/SetServlet");
					
					System.out.println("-----------------------------------");
					
					// 結果ページにフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
					return;
				}
				if(!uDao.delete(mail)) {
					request.setAttribute("errorMsg", "ユーザー削除に失敗しました！");
					request.setAttribute("goTo", "/d4/SetServlet");
					
					System.out.println("-----------------------------------");
					
					// 結果ページにフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
					return;
					
				} else {
					System.out.println("削除成功！");
					System.out.println("-----------------------------------");
					request.setAttribute("success", "成功");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
					dispatcher.forward(request, response);
				}
				
			} else if(value.equals("更新")) {
				System.out.println("更新処理");
				
				// ユーザ情報
				if(strTarget != null) {
					target = Integer.parseInt(strTarget);
				}
				User user = new User(mail, "", name, target, trans);
				System.out.println(user.getMail());
				
				// 目的
				List<Purpose> purposeList = new ArrayList<Purpose>();
				for(int i = 0; i < strIdBox.length; i++) {
					id = Integer.parseInt(strIdBox[i]);
					text = textBox[i].trim();
					
					if(text.isEmpty()) {
						text = "";
					}
					
					purposeList.add(new Purpose(id, mail, text));
				}
				
				// 収入カテゴリー
				List<Category> incomeCategoryList = new ArrayList<Category>();
				for(int i = 0; i < incomeIdBox.length; i++) {

					cid = Integer.parseInt(incomeIdBox[i]);
					cname = incomeNameBox[i].trim();
					
					if(cname.isEmpty()) {
						cname = "";
					}
					Category category = new Category(cid, mail, i+1, cname, 1);
					incomeCategoryList.add(category);
					
					System.out.println(category.getId());
					System.out.println(category.getNumber());
					System.out.println(category.getName());
				}
				
				// 支出カテゴリー
				List<Category> expenseCategoryList = new ArrayList<Category>();
				for(int i = 0; i < expenseIdBox.length; i++) {
					cid = Integer.parseInt(expenseIdBox[i]);
					cname = expenseNameBox[i].trim();
					
					if(cname.isEmpty()) {
						cname = "";
					}
					
					expenseCategoryList.add(new Category(cid, mail, i+1, cname, 2));
				}
				
				// ユーザー情報更新
				if(uDao.update(user)) {
					user = uDao.select(mail);
					System.out.println(user.getMail());
					session.setAttribute("loginUser", user);
				} else {
					System.out.println("ユーザー更新失敗、、");
					System.out.println("-----------------------------------");
					request.setAttribute("result", false);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
					dispatcher.forward(request, response);
				}
				
				// 目的更新
				for(Purpose purpose : purposeList) {
					if (!pDao.update(purpose)) {
						System.out.println("目的更新失敗、、");
						System.out.println("-----------------------------------");
						request.setAttribute("result", false);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
						dispatcher.forward(request, response);
						break;
					}
				}
				
				// 収入カテゴリ更新
				for(Category income : incomeCategoryList) {
					System.out.println("収入カテゴリ更新中、、");
					if (!cDao.update(income)) {
						System.out.println("収入カテゴリ更新失敗、、");
						System.out.println("-----------------------------------");
						request.setAttribute("result", false);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
						dispatcher.forward(request, response);
						break;
					}
				}
				
				// 支出カテゴリ更新
				for(Category expense : expenseCategoryList) {
					if (!cDao.update(expense)) {
						System.out.println("支出カテゴリ更新失敗、、");
						System.out.println("-----------------------------------");
						request.setAttribute("result", false);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
						dispatcher.forward(request, response);
						break;
					}
				}
				
				System.out.println("更新成功！");
				System.out.println("-----------------------------------");
				request.setAttribute("result", true);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/set.jsp");
				dispatcher.forward(request, response);
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
