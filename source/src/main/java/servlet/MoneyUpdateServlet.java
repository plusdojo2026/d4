/* 作成日：2026/06/16
 * 作成者：木下
 * 更新日：2026/06/17
 * 更新者：服部 */

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

import dao.BpDAO;
import dao.CategoryDAO;
import model.Bp;
import model.Category;
import model.User;

@WebServlet("/MoneyUpdateServlet")
public class MoneyUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			
			System.out.println("収支編集：doGet(情報を取得して収支編集画面表示)");
					    
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			
			// 既に登録されている収支情報を、編集ページに入力した状態にする
			// セッションスコープからメールの値を取得
			String mail = loginUser.getMail();
			
			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			int id = Integer.parseInt(request.getParameter("id"));
			int cid = Integer.parseInt(request.getParameter("cid"));
			int money = Integer.parseInt(request.getParameter("money"));
			String date = request.getParameter("date");		
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String day = request.getParameter("day");
			String memo = request.getParameter("memo");
			
			date = date.replaceAll("/", "-");
			
			// カテゴリー取得
			CategoryDAO dao = new CategoryDAO();
			List<Category> incomeCategoryList = dao.selectList(loginUser.getMail(), 1);
			List<Category> expenseCategoryList = dao.selectList(loginUser.getMail(), 2);
			
			// インスタンスを生成→フォワードするときに送る情報をセット
			Bp bp = new Bp(id, mail,cid, money, memo, year, month, day);
			request.setAttribute("bp", bp);
			request.setAttribute("date", date);
			request.setAttribute("incomeCategoryList", incomeCategoryList);
			request.setAttribute("expenseCategoryList", expenseCategoryList);

			System.out.println("-----------------------------------");
			// 編集ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/moneyUpdate.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyUpdateServlet");
			
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			
		}

	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			System.out.println("収支編集：doPost");
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
	
			// セッションスコープからメールの値を取得
			String mail = loginUser.getMail();
	
			// リクエストパラメータを取得する
			request.setCharacterEncoding("UTF-8");
			int id = Integer.parseInt(request.getParameter("id"));
			int cid = Integer.parseInt(request.getParameter("cid"));
			int money = Integer.parseInt(request.getParameter("money"));
			String date = request.getParameter("date");
			String memo = request.getParameter("memo");
	
			// 取得したdateをyear,month,dayに分割する処理
			String[] parts = date.split("\\-");
	
			String year = parts[0];
			String month = parts[1];
			String day = parts[2];
	
			// 登録処理を行う
			BpDAO bpDao = new BpDAO();
			Bp bp = new Bp(id, mail, cid, money, memo, year, month, day);

			boolean result = bpDao.update(bp);
			if (result) {
				System.out.println("収支編集成功！");
				request.setAttribute("success", "true");
				System.out.println("-----------------------------------");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/moneyUpdate.jsp");
				dispatcher.forward(request, response);
			} else {
				System.out.println("収支編集失敗、、");
				System.out.println("-----------------------------------");
				request.setAttribute("errorMsg", "収支編集に失敗しました！<br>管理者に連絡してください。");
				request.setAttribute("goTo", "/d4/SearchServlet");
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {

			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/SearchServlet");

			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);

		}

	}

}