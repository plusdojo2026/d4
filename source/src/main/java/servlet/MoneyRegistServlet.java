/* 作成日：2026/6/12
 * 作成者：木下
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BpDAO;
import model.Bp;
import model.User;

@WebServlet("/MoneyRegistServlet")
public class MoneyRegistServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		System.out.println("収支登録：doGet");
		    
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser") == null) {
			response.sendRedirect("/d4/LoginServlet");
			return;
		}
		
		// サーバー側で現在日時を取得
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("today", sdf.format(now));

		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/moneyRegist.jsp");
		dispatcher.forward(request, response);
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		System.out.println("収支登録：doPost");
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
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
		String[] parts = date.split("-"); 
		
		String year = parts[0];
		String month = parts[1];
		String day = parts[2];
		
		// 登録処理を行う
		BpDAO bpDao = new BpDAO();
		Bp bp = new Bp(0, mail, cid, money, year, month, day, memo);
		try {
			
			boolean result = bpDao.insert(bp); 
			if (result) {
				System.out.println("収支登録成功！");
				request.setAttribute("success", true);
				request.getRequestDispatcher("/WEB-INF/jsp/moneyRegist.jsp").forward(request, response);
			} else {
				System.out.println("収支登録失敗、、");
				
				request.setAttribute("errorMsg", "収支登録に失敗しました！<br>管理者に連絡してください。");
				request.setAttribute("goTo", "/d4/MoneyRegistServlet");
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyRegistServlet");
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

}
