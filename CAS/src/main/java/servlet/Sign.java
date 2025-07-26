package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCTransaction;
import domain.UserBean;
import service.ServerTicket;

/**
 * Servlet implementation class Sign
 */
@WebServlet("/sign")
public class Sign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (password1.compareTo(password2) != 0) {
			request.setAttribute("err", "两次输入的密码不一致");
			request.getRequestDispatcher("/Sign").forward(request, response);
		}
		
		JDBCTransaction<Boolean> db = new JDBCTransaction<Boolean>("mycas", "root", "gaoda-00") {
			protected Boolean doTransaction(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO users SET (username, email, phone, password)"
						+ " VALUES (?, ?, ?, ?);");
				if (ps == null)
					return false;
				
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, phone);
				ps.setString(4, password1);
				return ps.execute();
			}
			
		};
		
		if (!db.excute()) {
			request.setAttribute("err", "注册失败");
			request.getRequestDispatcher("/Sign").forward(request, response);
		}
		
		UserBean user = Login.queryUser(username, password1);
		if (user == null) {
			request.setAttribute("err", "登录失败");
			request.getRequestDispatcher("/Sign").forward(request, response);
		}
		
		// 账号密码验证成功
		HttpSession session = request.getSession();
		if (session != null) {
			// 将当前用户注册到ServerTicket
			session.setAttribute("st", ServerTicket.addUser(user));
		}
		
		// 转跳到目标业务系统
		response.sendRedirect(request.getParameter("dst"));
	}

}
