package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCTransaction;
import domain.UserBean;
import service.ServerToken;

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
			
		Boolean ret = transactionUser(username, email, phone, password1);
		if (ret == null	|| !ret) {
			backToSign(request, response, "注册失败");
			return;
		}
		
		String account = username != null ? username : null;
		if (account == null)
			account = email != null ? email : null;
		if (account == null)
			account = phone != null ? phone : null;
		
		UserBean user = Login.queryUser(account, password1);
		if (user == null) {
			backToSign(request, response,  "登录失败");
			return;
		}
		
		String token = ServerToken.addUser(user);
		if (token == null) {
			backToSign(request, response,  "登录失败");
			return;
		}
		
		// 账号密码验证成功
		Cookie tokenCookie = new Cookie("token", token);
	    tokenCookie.setMaxAge(60 * 60 * 24);   // 24 小时，单位为秒
	    tokenCookie.setPath("/");              // 整个站点有效
	    // tokenCookie.setDomain("example.com");  // 可选，跨子域共享
	    tokenCookie.setHttpOnly(true);         // 防 XSS
		response.addCookie(tokenCookie);
	    
		// 转跳到目标业务系统
		response.sendRedirect(request.getParameter("dst"));
	}

	private static final Pattern PATTERN_USERNAME = Pattern.compile("^[A-Za-z0-9]{8,16}$");
	private static final Pattern PATTERN_PHONE = Pattern.compile("^(?:(?:\\+?86)?1[3-9]\\d{9}|0\\d{2,3}-?\\d{7,8}(?:-\\d{1,4})?)$");
	private static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private static final Pattern PATTERN_PASSWORD = Pattern.compile(
		    "^(?:(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"          // 小写+大写+数字
		  + "|(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"  // 小写+大写+特殊
		  + "|(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/])"    // 小写+数字+特殊
		  + "|(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]))"   // 大写+数字+特殊
		  + "[A-Za-z\\d~!@#$%^&*()_+\\-={}\\[\\]|;:\"'<>,.?/]{8,32}$"
		);
	
	protected boolean checkParam(HttpServletRequest request, HttpServletResponse response, 
			String username, String email, String phone,
			String password1, String password2) throws ServletException, IOException {
		
		username = request.getParameter("username");
		email = request.getParameter("email");
		phone = request.getParameter("phone");
		if (username == null && email == null && phone == null) {
			backToSign(request, response, "用户名、邮件、电话不能都为空");
			return false;
		}
		
		if (username != null && !PATTERN_USERNAME.matcher(username).matches()) {
			backToSign(request, response, "用户名只能是8~16位的数字或字母");
			return false;
		}
		
		if (email != null && !PATTERN_EMAIL.matcher(email).matches()) {
			backToSign(request, response, "电子邮件格式错误");
			return false;
		}
		
		if (phone != null && !PATTERN_PHONE.matcher(phone).matches()) {
			backToSign(request, response, "电话号码格式错误");
			return false;
		}
		
		password1 = request.getParameter("password1");
		password2 = request.getParameter("password2");
		
		if (password1 == null || password2 == null || password1.isEmpty() || password2.isEmpty()) {
			backToSign(request, response, "密码为空");
			return false;
		}
		
		if (password1.compareTo(password2) != 0) {
			backToSign(request, response,  "两次输入的密码不一致");
			return false;
		}
		
		if (password1 != null && !PATTERN_PASSWORD.matcher(password1).matches()) {
			backToSign(request, response, "密码至少包含大写、小写、数字或特殊字符中的三种");
			return false;
		}
		
		return true;
	}
	
	protected void backToSign(HttpServletRequest request, HttpServletResponse response, String err) throws ServletException, IOException {
		request.setAttribute("err", err);
		request.getRequestDispatcher("/sign").forward(request, response);
	}
	
	protected boolean transactionUser(String username, String email, String phone,
	String password) {
		JDBCTransaction<Boolean> db = new JDBCTransaction<Boolean>("mycas", "root", "gaoda-00") {
			protected Boolean doTransaction(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO users SET (username, email, phone, password)"
						+ " VALUES (?, ?, ?, ?);");
				if (ps == null)
					return false;
				
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, phone);
				ps.setString(4, password);
				/*
				 * execute返回的并不是操作执行是否成功，而是第一个操作的类型
				 * 如果返回的是个ResultSet，那么就是true
				 * 如果返回的不是ResultSet，那么返回false
				 * 如果在执行的过程中抛出了异常，那么会被捕获
				 * 所以这里直接返回true就行了
				 * */
				ps.execute();		
				return true;
			}
		};
		
		return db.excute();
	}
}
