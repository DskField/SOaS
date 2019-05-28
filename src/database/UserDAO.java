package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.User;
import game.GameColor;

class UserDAO {
	private Connection con;

	public UserDAO(Connection connection) {
		con = connection;
	}

	private User selectUser(String username) {
		User result = null;
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT idplayer, username, score, MAX(score) AS maxscore, playstatus_playstatus FROM player GROUP BY idplayer");
			ResultSet dbResultSet = stmt.executeQuery();

			int gamesPlayed = 0;
			int maxScore = 0;
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity

				// GamesPlayed
				if (dbResultSet.getString("playstatus_playstatus").equals("uitgespeeld")) {
					if (dbResultSet.getString("username").equals(username))
						gamesPlayed++;
				}

				// MaxScore
				maxScore = maxScore < dbResultSet.getInt("score") && dbResultSet.getString("username").equals(username) ? dbResultSet.getInt("score") : maxScore;
			}
			stmt.close();

			// MostPlacedColor
			PreparedStatement stmtMostPlacedColor = con.prepareStatement("SELECT diecolor FROM player AS p1 " + "JOIN playerframefield AS p2 ON p1.idplayer = p2.player_idplayer " + "WHERE username = ? "
					+ "GROUP BY diecolor " + "ORDER BY COUNT(diecolor) DESC " + "LIMIT 1");
			stmtMostPlacedColor.setString(1, username);
			ResultSet dbResultSetMostPlacedColor = stmtMostPlacedColor.executeQuery();
			GameColor mostPlacedColor = dbResultSetMostPlacedColor.next() && dbResultSetMostPlacedColor.getString("diecolor") != null ? GameColor.getEnum(dbResultSetMostPlacedColor.getString("diecolor"))
					: GameColor.EMPTY;
			stmtMostPlacedColor.close();

			// MostPlacedValue
			PreparedStatement stmtMostPlacedValue = con.prepareStatement("SELECT eyes\r\n" + "FROM player AS p1\r\n" + "JOIN playerframefield AS p2 ON p1.idplayer = p2.player_idplayer\r\n"
					+ "JOIN gamedie AS gd ON p2.idgame = gd.idgame AND p2.dienumber = gd.dienumber AND p2.diecolor = gd.diecolor\r\n" + "WHERE username = ?\r\n" + "GROUP BY eyes\r\n" + "ORDER BY COUNT(eyes) DESC\r\n"
					+ "LIMIT 1");
			stmtMostPlacedValue.setString(1, username);
			ResultSet dbResultSetMostPlacedValue = stmtMostPlacedValue.executeQuery();
			int mostPlacedValue = dbResultSetMostPlacedValue.next() ? dbResultSetMostPlacedValue.getInt("eyes") : 0;
			stmtMostPlacedValue.close();

			result = new User(username, gamesPlayed, maxScore, mostPlacedColor, mostPlacedValue);

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("UserDAO (selectUser) --> " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public User getUser(String username) {
		return selectUser(username);
	}

	public boolean checkUpdate(String username, User oldUser) {
		if (oldUser.equals(getUser(username)))
			return true;
		else
			return false;
	}
}
