package dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JDBCQuery<T> extends JDBCTemplate<T> {
	
	public JDBCQuery()	{
		super("", "", "");
	}
	
	public JDBCQuery(String db, String user, String password)	{
		super(db, user, password);
	}
	
	protected abstract T doQuery(Connection conn) throws SQLException;
	
	@Override
	public T excute() {
		T ret = null;
		Connection conn = null;
		try {
			conn = getConnection();
			if (conn != null) {
				ret = doQuery(conn);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return ret;
	}

}
