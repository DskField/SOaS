package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
	Connection con;

	public AccountDAO(Connection con) {
		this.con = con;
	}

	private String[] selectUsername(String username) {
		String[] result = new String[2];

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM account WHERE username = ?");
			stmt.setString(1, username);
			ResultSet dbResultSet = stmt.executeQuery();
			if (dbResultSet.next()) {
				result[0] = dbResultSet.getString("username");
				result[1] = dbResultSet.getString("password");
			} else {
				result[0] = "";
				result[1] = "";
			}

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("AccountDAO (selectUsername) --> " + e.getMessage());
		}
		return result;
	}

	private boolean insertAccount(String username, String password) {

		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO account VALUES(?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.executeUpdate();
			con.commit();
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.err.println("AccountDAO (insertAccount) --> " + e.getMessage());
			return false;
		}
	}

	ArrayList<String> selectAllUsername() {
		ArrayList<String> result = new ArrayList<>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM account");
			ResultSet dbResultSet = stmt.executeQuery();
			while (dbResultSet.next()) {
				result.add(dbResultSet.getString("username"));
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("AccountDAO (selectAllUsername) --> " + e.getMessage());
		}
		return result;
	}

	public ArrayList<String> getAllUsernames() {
		return selectAllUsername();
	}

	public boolean loginCorrect(String username, String password) {
		String[] dbResult = selectUsername(username);
		return username.equals(dbResult[0]) && password.equals(dbResult[1]);
	}

	public boolean insertCorrect(String username, String password) {
		return selectUsername(username)[0].equals(username) ? false : insertAccount(username, password);
	}
}
