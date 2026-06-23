/* 作成日：2026/06/15
 * 作成者：深井
 * 更新者：
 * 更新日： */

package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
public class MyPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String WEB_PATH = "img";

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("マイページ：doGet");

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
			request.setAttribute("savings", savingSum);

			// 画像取得
			ImgDAO iDao = new ImgDAO();
			int count = iDao.countByMail(mail);
			List<Img> imgList = iDao.findByMail(mail);
			request.setAttribute("count", count);
			request.setAttribute("imgList", imgList);

			System.out.println("-----------------------------------");
			
			// マイページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mypage.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("goTo", "/d4/MyPageServlet");
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
			System.out.println("マイページ：doPost");

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
			ImgDAO iDao = new ImgDAO();

			// リクエストパラメータの取得
			request.setCharacterEncoding("UTF-8");

			// 削除
			String value = request.getParameter("submit");

			if ("削除".equals(value)) {
				int id = Integer.parseInt(request.getParameter("imgId"));
				if(iDao.delete(id)) {
					System.out.println("画像削除成功！");
					System.out.println("-----------------------------------");
					response.sendRedirect("/d4/MyPageServlet");
					return;
				} else {
					System.out.println("画像削除失敗、、、");
					System.out.println("-----------------------------------");
					response.sendRedirect("/d4/MyPageServlet");
					return;
				}
			}

			// 画像
			String fileName = "";
			String mypageMessage = "";

			Part part = request.getPart("photo");

			if (part == null) {
				mypageMessage = "ファイルが選択されていません。";
				// ★ セッションに保存
				request.getSession().setAttribute("fileMessage", mypageMessage);
				// ★ リダイレクト
				response.sendRedirect("/WEB-INF/jsp/mypage.jsp");
				return;
			}

			// 画像ファイル名取得
			fileName = getSubmittedFileName(part);

			if (fileName != null && !fileName.isEmpty()) {
				
				String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

				// サーバー内の絶対パス
				String uploadPath = getServletContext().getRealPath("/") + "img";
				part.write(uploadPath + File.separator + uniqueFileName);

				// ブラウザ用のパス
				String path = WEB_PATH + "/" + uniqueFileName;

				// 枚数チェック

				int count = iDao.countByMail(mail);

				if (count < 3) {
					boolean result = iDao.insert(mail, path);

					if (result) {
						System.out.println("画像登録成功！");
						System.out.println("-----------------------------------");
						response.sendRedirect("/d4/MyPageServlet");
						return;
					}
				} else {
					System.out.println("画像枚数3枚のため登録失敗");
					System.out.println("-----------------------------------");
					session.setAttribute("mypageMessage", "画像は3枚までです。");
					response.sendRedirect("/d4/MyPageServlet");
					return;
				}

			} else {
				System.out.println("画像選択なし");
				System.out.println("-----------------------------------");
				session.setAttribute("mypageMessage", "画像が選択されていません。");
				response.sendRedirect("/d4/MyPageServlet");
				return;
			}
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("goTo", "/d4/MyPageServlet");
			System.out.println("画像登録成功！");
			System.out.println("-----------------------------------");
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
		}
		response.sendRedirect("/d4/MyPageServlet");

	}

	// 画像パス
	private String getSubmittedFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		if (contentDisp == null) {
			return "";
		}

		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				String filename = token.substring(token.indexOf("=") + 2, token.length() - 1);
				return Paths.get(filename).getFileName().toString();
			}
		}
		return "";
	}
}
