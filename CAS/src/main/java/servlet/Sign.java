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
		request.setAttribute("sign", 1);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = null;
		String email = null;
		String phone = null;
		String password1 = null;
		String password2 = null;
		
		if (!checkParam(request, response, username, email, phone, password1, password2))
			return;
				
		if (!transactionUser(username, email, phone, password1, password2)) {
			backToSign(request, response, "注册失败");
			return;
		}
		
		UserBean user = Login.queryUser(username, password1);
		if (user == null) {
			backToSign(request, response,  "登录失败");
			return;
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

	protected boolean checkParam(HttpServletRequest request, HttpServletResponse response, 
			String username, String email, String phone,
			String password1, String password2) throws ServletException, IOException {
		username = request.getParameter("username");
		email = request.getParameter("email");
		phone = request.getParameter("phone");
		password1 = request.getParameter("password1");
		password2 = request.getParameter("password2");
		boolean ret = (username != null || email != null || phone != null) &&
				password1 != null && password2 != null;
		
		if (!ret) {
			backToSign(request, response, "参数错误");
		}
		
		if (password1.compareTo(password2) != 0) {
			backToSign(request, response,  "两次输入的密码不一致");
		}
		
		return ret;
	}
	
	protected void backToSign(HttpServletRequest request, HttpServletResponse response, String err) throws ServletException, IOException {
		request.setAttribute("err", err);
		request.getRequestDispatcher("/sign").forward(request, response);
	}
	
	protected boolean transactionUser(String username, String email, String phone,
	String password1, String password2) {
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
		
		return db.excute();
	}
}
