package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BaseDAO {
	private static final String USER = "amhkempe";
	private static final String PASS = "Ab12345";
	private static final String DB = "amhkempe_db2";

	private Properties props = new Properties();
	private Connection con;

	public BaseDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			props.setProperty("user", USER);
			props.setProperty("password", PASS);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Contact the developer with a screenshot of this error" + e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		String url = "jdbc:mysql://databases.aii.avans.nl/" + DB;
		try {
			con = DriverManager.getConnection(url, props);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	final Connection getConnection() {
		return con;
	}

	final void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}