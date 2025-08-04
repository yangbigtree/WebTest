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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCQuery;
import domain.UserBean;
import service.ServerTicket;
import service.ServerToken;

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
		
		String token = getTokenInCookies(request);
		if (token == null) {
			// cookie中没有token，重新登录
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		else {
			// cookie中有token，说明已经成功登录过
			// 此时直接产生ticket并且返回业务系统即可
			backToApp(request, response, token);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		UserBean user = queryUser(account, password);
		if (user == null) {
			backToSign(request, response, "账号或密码错误");
			return;
		}
		
		_login(request, response, account, password);
	}
	
	public static void login(HttpServletRequest request, HttpServletResponse response, String account, String password) throws IOException, ServletException {
		Login obj = new Login();
		obj._login(request, response, account, password);
	}
	
	private void _login(HttpServletRequest request, HttpServletResponse response, String account, String password) throws IOException, ServletException {
		
		UserBean user = queryUser(account, password);
		if (user == null) {
			backToSign(request, response,  "登录失败");
			return;
		}
		
		String token = ServerToken.addUser(user);
		if (token == null) {
			backToSign(request, response,  "登录失败");
			return;
		}
		
		// 账号密码验证成功，设置cookie记录token
		// 下次如果再需要验证身份，可以从cookie中取出token验证身份
		Cookie tokenCookie = new Cookie("token", token);
	    tokenCookie.setMaxAge(60 * 60 * 24);   // 24 小时，单位为秒
	    tokenCookie.setPath("/");              // 整个站点有效
	    // tokenCookie.setDomain("example.com");  // 可选，跨子域共享
	    tokenCookie.setHttpOnly(true);         // 防 XSS
		response.addCookie(tokenCookie);
	    
		backToApp(request, response, token);
	}
	
	protected static String getTokenInCookies(HttpServletRequest request) {
		String token = null;
		Cookie [] cookies = request.getCookies();
		for	(Cookie cookie : cookies) {
			if ("token".compareTo(cookie.getName()) == 0) {
				token = cookie.getValue();
				break;
			}
		}
		return token;
	}
	
	protected void backToApp(HttpServletRequest request, HttpServletResponse response, String token) throws ServletException, IOException {
		// 创建ticket，用于后续验证
		// 并且转跳至目标业务系统
		String app = request.getParameter("server");
		if (app == null) {
			// 登录成功，但是没有设置目标业务系统的地址，转跳回去登录页面
			backToSign(request, response,  "登录成功");
			return;
		}
		
		String ticket = ServerTicket.addTicket(app, token);
		if (ticket == null) {
			backToSign(request, response,  "创建ticket失败");
			return;
		}
		
		// 转跳到目标业务系统
		String appUrl = app + "?ticket=" + ticket;
		response.sendRedirect(appUrl);
	}
	
	protected void backToSign(HttpServletRequest request, HttpServletResponse response, String err) throws ServletException, IOException {
		request.setAttribute("err", err);
		doGet(request, response);
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
