package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	java.sql.Statement st = null;
	ResultSet rs = null;
	Connection con = null;

	public DBConnector() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/newsdb", "root", "recommendation");
			Class.forName("com.mysql.jdbc.Driver");
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		try {
			rs = st.executeQuery(sql);
			if (st.execute(sql)) {
				rs = st.getResultSet();
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		return rs;
	}

	public void executeUpdate(String sql) throws SQLException {
		st.executeUpdate(sql);
	}
}
