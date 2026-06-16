/* 作成日：2026/06/16
 * 作成者：深井
 * 更新日：2026/06/16
 * 更新者：服部 */

package servlet;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@WebServlet("/TableServlet")
public class TableServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("集計表：doGet");
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			String mail = loginUser.getMail();
			
			// リクエストパラメータ取得
			request.setCharacterEncoding("UTF-8");
			String year = request.getParameter("year");
			
			// リストの型を用意
			List<Bp> monthIncomeList = new ArrayList<Bp>();
			List<Bp> monthExpenseList = new ArrayList<Bp>();
			int yearIncome = 0;
			int yearExpense = 0;
			boolean judge = false;
			BpDAO dao = new BpDAO();
			
			// 初期表示の場合と年を指定した場合の処理
			if(year == null) {
				System.out.println("今年の集計表");
				// 今年を取得
				Year intYear = Year.now();
				year = intYear.toString();
			}
			
			// 月ごとの収支合計を取得
			monthIncomeList = dao.tableSelect(mail, year, 1);
			monthExpenseList = dao.tableSelect(mail, year, 2);

			// 収支の合計を計算
			for(Bp income : monthIncomeList) {
			    yearIncome += income.getMoney();
			}
			for(Bp expense : monthExpenseList) {
			    yearExpense += expense.getMoney();
			}
			
			// 収支のリストを完全版にする
			monthIncomeList = fillMissingMonths(monthIncomeList);
			monthExpenseList = fillMissingMonths(monthExpenseList);
			
			// 収支すべての金額が0かどうかを判定する
			for(int i = 0; i < 12; i++) {
				int income = monthIncomeList.get(i).getMoney();
				int expense = monthExpenseList.get(i).getMoney();
				
				if(income > 0 || expense > 0) {
					judge = true;
					break;
				}
			}
			
			// リクエストスコープに保存
			request.setAttribute("year", year);
			request.setAttribute("yearIncome", yearIncome);
			request.setAttribute("yearExpense", yearExpense);
			request.setAttribute("incomeList", monthIncomeList);
			request.setAttribute("expenseList", monthExpenseList);
			request.setAttribute("judge", judge);
			
			// 集計表画面にフォワードする
			System.out.println("-----------------------------------");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/table.jsp");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/TableServlet");
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	// 空いている月の収支を埋めるメソッド
	private List<Bp> fillMissingMonths(List<Bp> list) {
		// 月をキーにしたマップに変換
		Map<Integer, Bp> map = new HashMap<>();
		for(Bp bp : list) {
			map.put(Integer.parseInt(bp.getMonth()), bp);
		}
		
		// 1～12月のリストを作成
		List<Bp> result = new ArrayList<>();
		for(int i = 1; i <= 12; i++) {
			if(map.containsKey(i)) {
				result.add(map.get(i));
			} else {
				Bp empty = new Bp();
				empty.setMoney(0);
				empty.setMonth(String.valueOf(i));
				result.add(empty);
			}
		}
		
		return result;
	}
}
