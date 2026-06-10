/* 作成日：2026/6/10
 * 作成者：深井
 * 更新者：
 * 更新日： */

package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

@WebServlet("/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// doGetメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp").forward(request, response);
	}

	// doPostメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        String checkPass = request.getParameter("check-pass");

        User user = new User();
        user.setMail(mail);
        user.setPass(pass);

        UserDAO dao = new UserDAO();
        boolean result = dao.insert(user);

        if (result) {
            request.setAttribute("success", true);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } else {
            request.setAttribute("cancelMail", mail);
            request.setAttribute("cancelPass", pass);
            request.setAttribute("cancelCheckPass", checkPass);
            request.setAttribute("result", "失敗");
            request.setAttribute("message", "このメールアドレスは既に登録されています。");
            request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp").forward(request, response);
        }
	}
}
