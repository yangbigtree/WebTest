package dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCTransaction<T> extends JDBCTemplate<T> {
	
	public JDBCTransaction() {
		super("", "", "");
	}
	
	public JDBCTransaction(String db, String user, String password) {
		super(db, user, password);
	}
	
	protected abstract T doTransaction(Connection conn) throws SQLException;
	
	@Override
	public T excute() {
		T ret = null;
		Connection conn = null;
		try {
			conn = getConnection();
			if (conn != null) {
				conn.setAutoCommit(false);
				ret = doTransaction(conn);
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			closeConnection(conn);
		}
		return ret;
	}

}
