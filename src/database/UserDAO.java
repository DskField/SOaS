package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.Client;

public class UserDAO extends BaseDAO {

	Client client;

	Connection con = super.getConnection();

	public int getTotalGames(String user) {

		String totGameQuery = "SELECT COUNT(game_idgame) AS totalGames " + "FROM player "
				+ "WHERE username LIKE '?' AND playstatus_playstatus LIKE 'uitgespeeld' " + "GROUP BY username";

		int result = 0;

		try {

			PreparedStatement stmt = con.prepareStatement(totGameQuery);
			stmt.setString(1, user);

			final ResultSet resultSet = stmt.executeQuery();

			con.commit();

			while (resultSet.next()) {
				result = resultSet.getInt("totalGames");
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

	public int getWonGames(String user) {

		String wonGameQuery = "SELECT game_idgame, MAX(score), username " + "FROM player "
				+ "WHERE playstatus_playstatus LIKE 'uitgespeeld' " + "GROUP BY game_idgame";

		int amountOfWonGames = 0;

		try {

			PreparedStatement stmt = con.prepareStatement(wonGameQuery);

			final ResultSet resultSet = stmt.executeQuery();

			con.commit();

			while (resultSet.next()) {

				try {
					String username = resultSet.getString("username");

					if (username.equals(user)) {

						amountOfWonGames++;
					}

				} catch (SQLException e) {
					System.err.println("ClientDAO " + e.getMessage());
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

		return amountOfWonGames;
	}

	public int getHighestScore(String user) {

		String highestScoreQuery = "SELECT username, MAX(score) " + "FROM player " + "WHERE username LIKE '?'";

		int highestScore = 0;

		try {

			PreparedStatement stmt = con.prepareStatement(highestScoreQuery);
			stmt.setString(1, user);
			final ResultSet resultSet = stmt.executeQuery();

			con.commit();

			while (resultSet.next()) {

				highestScore = resultSet.getInt("score");

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

		return highestScore;
	}
	
	public void getMostPlacedColor() {
		
		// TODO Get a script to grab the highest picked color in the DB
	}
	
	public void getMostPlacedValue() {
		
		// TODO Nigga I don't know what this means
	}
	
	
}
