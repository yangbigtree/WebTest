package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBCTemplate<T> {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String db = "";
	private String user = "";
	private String password = "";
	
	public JDBCTemplate(String db, String user, String password) {
		super();
		this.db = db;
		this.user = user;
		this.password = password;
	}

	private String dbUrlStr() {
		return new String("jdbc:mysql://localhost/" +
						db + "?" +
						"user=" + user +
						"&password=" + password); 
	}

	protected Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrlStr());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	protected void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void rollback(Connection conn) {
		try {
			if (conn != null)
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public abstract T excute();
}
