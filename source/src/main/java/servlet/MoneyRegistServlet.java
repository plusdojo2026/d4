/* 作成日：2026/6/12
 * 作成者：木下
 * 更新日：2026/6/17
 * 更新者：服部 */

package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@WebServlet("/MoneyRegistServlet")
public class MoneyRegistServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			System.out.println("収支登録：doGet(情報を取得して収支登録画面表示)");
			    
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			
			// パラメータを取得
			request.setCharacterEncoding("UTF-8");
			String success = (String) session.getAttribute("success");	
			if(success != null && success == "true") {
				request.setAttribute("success", true);
				session.removeAttribute("success");
			}
			
			// サーバー側で現在日時を取得
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// カテゴリー取得
			CategoryDAO dao = new CategoryDAO();
			List<Category> incomeCategoryList = dao.selectList(loginUser.getMail(), 1);
			List<Category> expenseCategoryList = dao.selectList(loginUser.getMail(), 2);
			
			request.setAttribute("today", sdf.format(now));
			request.setAttribute("incomeCategoryList", incomeCategoryList);
			request.setAttribute("expenseCategoryList", expenseCategoryList);
	
			System.out.println("-----------------------------------");
			// 登録ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/moneyRegist.jsp");
			dispatcher.forward(request, response);
		
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyRegistServlet");

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
			System.out.println("収支登録：doPost(登録処理)");
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
			Bp bp = new Bp(0, mail, cid, money, memo, year, month, day);
			
			boolean result = bpDao.insert(bp); 
			if (result) {
				System.out.println("収支登録成功！");
				session.setAttribute("success", "true");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/MoneyRegistServlet");
			} else {
				System.out.println("収支登録失敗、、");
				System.out.println("-----------------------------------");
				request.setAttribute("errorMsg", "収支登録に失敗しました！<br>管理者に連絡してください。");
				request.setAttribute("goTo", "/d4/MoneyRegistServlet");
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyRegistServlet");

			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

}
