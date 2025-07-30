package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.UserBean;
import service.ServerToken;
import service.ServerTicket;

/**
 * Servlet implementation class GetUser
 */
@WebServlet(value = {"/serviceValidate", "/p3/serviceValidate"})
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String NAMESPACE =
            "<cas:serviceResponse xmlns:cas=\"http://www.yale.edu/tp/cas\">";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUser() {
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
		
		String ticket = request.getParameter("ticket");
		String server = request.getParameter("server");
		String fmt = request.getParameter("format");   // 空或 "json"
		
		// 1. 基础校验
        if (ticket == null || server == null) {
            write(response, fmt, false, null, "INVALID_REQUEST", "missing ticket or service");
            return;
        }
        
        // 2. 取票据（示例：内存仓库）
        if (!ServerTicket.hasTicket(ticket)) {
            write(response, fmt, false, null, "INVALID_TICKET", "ticket not found or expired");
            return;
        }

        // 3. service 必须完全一致
        String token = ServerTicket.getToken(server, ticket);
        if (token == null) {
            write(response, fmt, false, null, "INVALID_SERVICE", "service mismatch");
            return;
        }

        // 4. 成功
        UserBean user = ServerToken.getUser(token);
        write(response, fmt, true, user, null, null);
	}
	
    /* ---------- 输出工具 ---------- */
    private void write(HttpServletResponse resp, String fmt,
                       boolean success, UserBean user,
                       String failCode, String failMsg) throws IOException {

        boolean json = "json".equalsIgnoreCase(fmt);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("email", user.getEmail());
        attrs.put("phone",  user.getPhone());
        attrs.put("role",  user.getRole());
        
        if (json) {
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            if (success) {
                out.print("{\"serviceResponse\":{\"authenticationSuccess\":{"
                        + "\"user\":\"" + user.getUsername() + "\","
                        + "\"attributes\":" + toJson(attrs) + "}}}");
            } else {
                out.print("{\"serviceResponse\":{\"authenticationFailure\":{"
                        + "\"code\":\"" + failCode + "\","
                        + "\"description\":\"" + failMsg + "\"}}}");
            }
        } else {
            resp.setContentType("application/xml;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(NAMESPACE);
            if (success) {
                out.println("  <cas:authenticationSuccess>");
                out.println("    <cas:user>" + user.getUsername() + "</cas:user>");
                for (Map.Entry<String, Object> e : attrs.entrySet()) {
                    out.println("    <cas:" + e.getKey() + ">" + e.getValue() + "</cas:" + e.getKey() + ">");
                }
                out.println("  </cas:authenticationSuccess>");
            } else {
                out.println("  <cas:authenticationFailure code=\"" + failCode + "\">");
                out.println("    " + failMsg);
                out.println("  </cas:authenticationFailure>");
            }
            out.println("</cas:serviceResponse>");
        }
    }

    private String toJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("{");
        map.forEach((k,v) -> sb.append("\"").append(k).append("\":\"").append(v).append("\","));
        if (sb.length() > 1) sb.setLength(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
