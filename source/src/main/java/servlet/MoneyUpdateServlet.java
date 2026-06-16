/* 作成日：
 * 作成者：
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;

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

@WebServlet("/MoneyUpdateServlet")
public class MoneyUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			
			System.out.println("収支編集：doGet");
					    
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
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
			
			// インスタンスを生成→フォワードするときに送る情報をセット
			Bp bp = new Bp(id, mail,cid, money, memo, year, month, day);
			request.setAttribute("bp", bp);
			request.setAttribute("date", date);
			
			// 編集ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/moneyUpdate.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyUpdateServlet");
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			
		}

	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		System.out.println("収支編集：doPost");
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

			boolean result = bpDao.update(bp);
			if (result) {
				System.out.println("収支編集成功！");
				request.setAttribute("success", true);
				request.getRequestDispatcher("/WEB-INF/jsp/moneyUpdate.jsp").forward(request, response);
			} else {
				System.out.println("収支編集失敗、、");

				request.setAttribute("errorMsg", "収支編集に失敗しました！<br>管理者に連絡してください。");
				request.setAttribute("goTo", "/d4/MoneyUpdateServlet");
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {

			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MoneyUpdateServlet");
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);

		}

	}

}
