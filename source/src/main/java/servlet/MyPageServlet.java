/* 作成日：2026/06/15
 * 作成者：深井
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.BpDAO;
import dao.ImgDAO;
import dao.PurposeDAO;
import model.Bp;
import model.Img;
import model.Purpose;
import model.User;

@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("マイページ：doGet");
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			String mail = loginUser.getMail();
			
			// 名前 / 目標金額
			String name = loginUser.getName();
			int target = loginUser.getTarget();
			request.setAttribute("name", name);
			request.setAttribute("target", target);
			
			// 目的
			PurposeDAO pDao = new PurposeDAO();
			List<Purpose> purposeList = pDao.findByMail(mail);
			request.setAttribute("purposeList", purposeList);
			
			// 貯金額
			BpDAO bDao = new BpDAO();
			List<Bp> incomeList = new ArrayList<Bp>();
			List<Bp> expenseList = new ArrayList<Bp>();

			int incomeSum = 0;
			int expenseSum = 0;
			int savingSum = 0;

			incomeList = bDao.mailSelect(mail, 1);
			expenseList = bDao.mailSelect(mail, 2);
			for (Bp income : incomeList) {
				incomeSum += income.getMoney();
			}
			for (Bp expense : expenseList) {
				expenseSum += expense.getMoney();
			}
			savingSum = incomeSum - expenseSum;
			request.setAttribute("saving", savingSum);
			
			// 画像取得
			ImgDAO iDao = new ImgDAO();
			int count = iDao.countByMail(mail);
			List<Img> imgList = iDao.findByMail(mail);
			request.setAttribute("count", count);
			request.setAttribute("imgList", imgList);
			
			// マイページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mypage.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("goTo", "/d4/MyPageServlet");
			request.setAttribute("errorMsg", e.getMessage());
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("マイページ：doPost");
			
			// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser == null) {
				response.sendRedirect("/d4/LoginServlet");
				return;
			}
			String mail = loginUser.getMail();

			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");
			String value = request.getParameter("submit");
			
			ImgDAO iDao = new ImgDAO();
			int count = iDao.countByMail(mail);
			request.setAttribute("count", count);
			
			if(value != null) {
				// 削除
				if(value == "削除") {
					int id = Integer.parseInt(request.getParameter("imgId"));
					iDao.delete(id);
					response.sendRedirect("/d4/MyPageServlet");
				} else {
					// 画像
					// ここは調べてやったのでよくわかっていない
					Part part = request.getPart("photo");
					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					String path = "upload/" + fileName;

					if (count < 3) {
						boolean result = iDao.insert(mail, path);
						if (result) {
							response.sendRedirect("/d4/MyPageServlet");
						}
					}
				}
			} else {
				request.setAttribute("errorMsg", "取得処理に失敗しました！<br>管理者に連絡してください！");
				request.setAttribute("goTo", "/d4/MyPageServlet");
				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MyPageServlet");
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
	}
}
