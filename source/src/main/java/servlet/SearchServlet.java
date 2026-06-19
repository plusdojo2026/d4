/* 作成日：2026/06/16
 * 作成者：
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import model.Bp;
import model.BpView;
import model.SearchResult;
import model.User;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// sort用定数
	private static final int SORT_DEFAULT = 0; 	// 初期表示
	private static final int SORT_DESC = 1;		// 降順
	private static final int SORT_ASC = 2;		// 昇順

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			System.out.println("検索：doGet");
			
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
			
			// サーバー側で現在日時を取得
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date = sdf.format(now);
			
			// 取得したdateを分割する処理
			String[] parts = date.split("-"); 
					
			String nYear = parts[0];
			String nMonth = parts[1];
			
			request.setAttribute("selectedYear", nYear);
			request.setAttribute("selectedMonth", String.valueOf(Integer.parseInt(nMonth)));
			
			// セレクトボックスに表示する年と月の範囲指定
			List<String> yearList = new ArrayList<>();
			int iYear = Integer.parseInt(nYear);
						
			for (int i = iYear; i >= iYear - 10; i--) {
				
				yearList.add(String.valueOf(i));
				
			}
			
			List<String> monthList = new ArrayList<>();
						
			for (int i = 1; i <= 12; i++) {
				
				monthList.add(String.valueOf(i));
				
			}
			
			monthList.add("ALL");
			
			request.setAttribute("yearList", yearList);
			request.setAttribute("monthList", monthList);
			
			// 初期表示として当月の収支データを取得
			List<SearchResult> srList = new ArrayList<SearchResult>();
			BpDAO bpDao = new BpDAO();
			
			// 登録情報をリストに格納
			srList = bpDao.searchSelect(mail, nYear, nMonth, SORT_DEFAULT, ""); 
			int incomeSum = 0;
			int expenseSum = 0;
			for (SearchResult sr : srList) {
				
				for (BpView bp : sr.getBpView()) {
					
					if (bp.getKind() == 1) {
						incomeSum += bp.getBp().getMoney(); 
					} else {
						expenseSum += bp.getBp().getMoney();
					}
					
				}

			}
			
			request.setAttribute("incomeSum", incomeSum);
			request.setAttribute("expenseSum", expenseSum);
			request.setAttribute("searchList", srList);
			request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
			
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/SearchServlet");
			
			// 例外内容表示
			e.printStackTrace();
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
			System.out.println("収支登録：doPost");
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				System.out.println("ログインユーザが存在しません。");
				System.out.println("-----------------------------------");
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			
			// サーバー側で現在日時を取得
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String nYear = sdf.format(now);
			
			// セレクトボックスに表示する年と月の範囲指定
			List<String> yearList = new ArrayList<>();
			int iYear = Integer.parseInt(nYear);
						
			for (int i = iYear; i >= iYear - 10; i--) {
				
				yearList.add(String.valueOf(i));
				
			}
			
			List<String> monthList = new ArrayList<>();
						
			for (int i = 1; i <= 12; i++) {
				
				monthList.add(String.valueOf(i));
				
			}
			
			monthList.add("ALL");
			
			// セッションスコープからメールの値を取得
			String mail = loginUser.getMail();
			
			// リクエストパラメータを取得する
			request.setCharacterEncoding("UTF-8");
			String year = request.getParameter("year");
			String selectedMonth = request.getParameter("month");
			String month = selectedMonth;
			if ("ALL".equals(month)) {
				month="%";
			} else if (month != null) {
				month = String.format("%02d", Integer.parseInt(month));
			}
			String day = request.getParameter("day");
			String memo = request.getParameter("memo");
			String sort = request.getParameter("sort");
			String keyWord = request.getParameter("keyword");
			String search = request.getParameter("search");
			String submit = request.getParameter("submit");
			
			List<SearchResult> srList = new ArrayList<SearchResult>();
			BpDAO bpDao = new BpDAO();
			
			// フォワード時に選択項目が残るようリクエストスコープにセット
			request.setAttribute("yearList", yearList);
			request.setAttribute("monthList", monthList);
			request.setAttribute("selectedYear", year);
			request.setAttribute("selectedMonth", selectedMonth);
			request.setAttribute("enteredKeyword", keyWord);
			request.setAttribute("selectedSort", sort);
			
			if (search != null) {
				
				System.out.println("検索処理");
				
				int sortType;
				
				if ("ASC".equals(sort)) {
					sortType = SORT_ASC;
				} else if ("DESC".equals(sort)) {
					sortType = SORT_DESC;
				} else {
					sortType = SORT_DEFAULT;
				}
				
				// 検索処理を行う
				srList = bpDao.searchSelect(mail, year, month, sortType, keyWord);
				int incomeSum = 0;
				int expenseSum = 0;
				for (SearchResult sr : srList) {
					
					for (BpView bp : sr.getBpView()) {
						
						if (bp.getKind() == 1) {
							incomeSum += bp.getBp().getMoney(); 
						} else {
							expenseSum += bp.getBp().getMoney();
						}
						
					}

				}
				
				request.setAttribute("incomeSum", incomeSum);
				request.setAttribute("expenseSum", expenseSum);
				request.setAttribute("searchList", srList);
				request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
				
			} else if ("削除".equals(submit)) {
				
				int id = Integer.parseInt(request.getParameter("id"));
				
				System.out.println("削除処理");
				// 削除処理を行う
				boolean result = bpDao.delete(id); 
				if (result) {
					
					System.out.println("収支削除成功！");
					
					int sortType;
					
					if ("ASC".equals(sort)) {
						sortType = SORT_ASC;
					} else if ("DESC".equals(sort)) {
						sortType = SORT_DESC;
					} else {
						sortType = SORT_DEFAULT;
					}
					
					// 検索処理を行う
					srList = bpDao.searchSelect(mail, year, month, sortType, keyWord);
					int incomeSum = 0;
					int expenseSum = 0;
					for (SearchResult sr : srList) {
						
						for (BpView bp : sr.getBpView()) {
							
							if (bp.getKind() == 1) {
								incomeSum += bp.getBp().getMoney(); 
							} else {
								expenseSum += bp.getBp().getMoney();
							}
							
						}

					}
					
					request.setAttribute("incomeSum", incomeSum);
					request.setAttribute("expenseSum", expenseSum);
					request.setAttribute("searchList", srList);
					request.setAttribute("success", true);
					request.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(request, response);
					
				} else {
					
					System.out.println("収支削除失敗、、");
					request.setAttribute("errorMsg", "収支削除に失敗しました！<br>管理者に連絡してください。");
					request.setAttribute("goTo", "/d4/SearchServlet");
					// 結果ページにフォワードする
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
					dispatcher.forward(request, response);
					
				}
				
			} else if ("編集".equals(submit)) {
				
				int id = Integer.parseInt(request.getParameter("id"));
				int cid = Integer.parseInt(request.getParameter("cid"));
				int money = Integer.parseInt(request.getParameter("money"));
				Bp bp = new Bp(id, mail, cid, money, year, month, day, memo);
				
				System.out.println("編集ページ遷移");
				// 編集ページ遷移処理を行う
				request.setAttribute("bp", bp);
				request.getRequestDispatcher("/WEB-INF/jsp/moneyUpdate.jsp").forward(request, response);
				
			}
				
		} catch (Exception e) {
			
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/SearchServlet");
			
			// 例外内容表示
			e.printStackTrace();
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

}
