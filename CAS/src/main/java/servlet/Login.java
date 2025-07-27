package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCQuery;
import domain.UserBean;
import service.ServerTicket;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		UserBean user = queryUser(account, password);
		if (user == null) {
			// 查找不到对应的账号密码
			request.setAttribute("err", "账号或密码错误");
			request.getRequestDispatcher("/login").forward(request, response);
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
	
	public static UserBean queryUser(String account, String password) {
		JDBCQuery<UserBean> db = new JDBCQuery<UserBean>("mycas", "root", "gaoda-00") {
			@Override
			public UserBean doQuery(Connection conn) throws SQLException {
				UserBean user = null;
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM users "
						+ "WHERE password=? AND "
						+ "(username=? OR email=? OR phone=?)");
				if (ps == null)
					return user;
				
				ps.setString(1, password);
				ps.setString(2, account);
				ps.setString(3, account);
				ps.setString(4, account);
				
				if (!ps.execute())
					return user;
				
				ResultSet results = ps.getResultSet();
				if (results.next())
				{
					user = new UserBean();
					user.setId(results.getInt("id"));
					user.setUsername(results.getString("username"));
					user.setPassword(results.getString("password"));
					user.setEmail(results.getString("email"));
					user.setPhone(results.getString("phone"));
					user.setCreated_at(results.getTimestamp("created_at"));
					user.setUpdated_at(results.getTimestamp("updated_at"));
					user.setLogined_at(Timestamp.valueOf(LocalDateTime.now()));
				}
				return user;
			}
			
		};
		
		return db.excute();
	}
}
