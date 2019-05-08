package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class BaseDAO {

	private Properties props = new Properties();
	private Connection con;

	public BaseDAO() {
		// https://jdbc.postgresql.org/documentation/80/connect.html
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			props.setProperty("user", "amhkempe");
			props.setProperty("password", "Ab12345");
			// props.setProperty("ssl", "true");
			// props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Contact the developer with a screenshot of this error" + e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		String url = "jdbc:mysql://databases.aii.avans.nl:3306/amhkempe_db2";
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