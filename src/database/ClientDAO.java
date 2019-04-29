package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.Client;

@SuppressWarnings("unused")
public class ClientDAO extends BaseDAO {

	Client client;

	Connection con = super.getConnection();

	public void loginUser(String user, String pass) {

		String loginQuery = "SELECT p.username AS username, a.password AS password\r\n" + "FROM account AS a \r\n"
				+ "LEFT JOIN player AS p ON a.username = p.username\r\n"
				+ "WHERE p.username LIKE '?' AND a.password LIKE '?'";

		try {

			PreparedStatement stmt = con.prepareStatement(loginQuery);
			stmt.setString(1, client.getUsername());
			stmt.setString(2, client.getPassword());

			final ResultSet resultSet = stmt.executeQuery();

			con.commit();

			while (resultSet.next()) {

				try {
					String username = resultSet.getString("username");
					String password = resultSet.getString("password");

					if (username.equals(user) && password.equals(pass)) {

						System.out.println("Success, you've logged in!");
						// Continue rest of login procedure
					}

					else {
						System.out.println("Log-in was not a success!");
					}

				} catch (SQLException e) {
					System.err.println("MessageDAO " + e.getMessage());
				}
			}
			stmt.close();

		} catch (SQLException e) {

			System.err.println("ClientDAO " + e.getMessage());

			try {

				con.rollback();

			} catch (SQLException e1) {

				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}

	private void registerUser(String user, String pass) {

		String registerQuery = "INSERT INTO account VALUES(?,?)";

		try {

			PreparedStatement stmt = con.prepareStatement(registerQuery);
			stmt.setString(1, user);
			stmt.setString(2, pass);

			final ResultSet resultSet = stmt.executeQuery();
			stmt.executeUpdate();
			con.commit();
			stmt.close();

			System.out.println("Successfully registered!");

		} catch (SQLException e) {

			System.err.println("ClientDAO " + e.getMessage());

			try {

				con.rollback();

			} catch (SQLException e1) {

				System.err.println("The rollback failed: Please check the Database!");
			}
		}

	}

	// This method returns the latest player ID from the username provided
	private int findUser(String user) {

		String findQuery = "SELECT idplayer\r\n" + "FROM player\r\n" + "WHERE username LIKE '?'\r\n"
				+ "ORDER BY idplayer DESC\r\n" + "LIMIT 1";

		int result = 0;

		try {

			PreparedStatement stmt = con.prepareStatement(findQuery);
			stmt.setString(1, user);

			final ResultSet resultSet = stmt.executeQuery();

			con.commit();

			while (resultSet.next()) {
				result = resultSet.getInt("idplayer");
			}

			stmt.close();

		} catch (SQLException e) {

			System.err.println("ClientDAO " + e.getMessage());

			try {

				con.rollback();

			} catch (SQLException e1) {

				System.err.println("The rollback failed: Please check the Database!");
			}
		}

		return result;
	}

}
