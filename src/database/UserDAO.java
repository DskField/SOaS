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
			PreparedStatement stmt = con.prepareStatement(
					"SELECT idplayer, username, score, MAX(score) AS maxscore, playstatus_playstatus FROM player GROUP BY idplayer");
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
				maxScore = maxScore < dbResultSet.getInt("score") && dbResultSet.getString("username").equals(username)
						? dbResultSet.getInt("score")
						: maxScore;
			}
			stmt.close();

			// MostPlacedColor
			// TODO review this, don't know how to do this more efficient
			GameColor mostPlacedColor = GameColor.EMPTY;
			int totalPlacedColor = 0;
			PreparedStatement stmtMostPlacedColor = con
					.prepareStatement("SELECT SUM(CASE WHEN diecolor = \"rood\" THEN 1 ELSE 0 END) AS totalred,\r\n"
							+ "SUM(CASE WHEN diecolor = \"geel\" THEN 1 ELSE 0 END) AS totalyellow,\r\n"
							+ "SUM(CASE WHEN diecolor = \"groen\" THEN 1 ELSE 0 END) AS totalgreen,\r\n"
							+ "SUM(CASE WHEN diecolor = \"blauw\" THEN 1 ELSE 0 END) AS totalblue,\r\n"
							+ "SUM(CASE WHEN diecolor = \"paars\" THEN 1 ELSE 0 END) AS totalpurple\r\n"
							+ "FROM playerframefield\r\n" + "JOIN player ON player_idplayer = idplayer\r\n"
							+ "WHERE dienumber IS NOT NULL AND username = ?");
			stmtMostPlacedColor.setString(1, username);
			ResultSet dbResultSetMostPlacedColor = stmtMostPlacedColor.executeQuery();
			dbResultSetMostPlacedColor.next();
			if (dbResultSetMostPlacedColor.getInt("totalred") > totalPlacedColor) {
				totalPlacedColor = dbResultSetMostPlacedColor.getInt("totalred");
				mostPlacedColor = GameColor.RED;
			}
			if (dbResultSetMostPlacedColor.getInt("totalyellow") > totalPlacedColor) {
				totalPlacedColor = dbResultSetMostPlacedColor.getInt("totalyellow");
				mostPlacedColor = GameColor.YELLOW;
			}
			if (dbResultSetMostPlacedColor.getInt("totalgreen") > totalPlacedColor) {
				totalPlacedColor = dbResultSetMostPlacedColor.getInt("totalgreen");
				mostPlacedColor = GameColor.GREEN;
			}
			if (dbResultSetMostPlacedColor.getInt("totalblue") > totalPlacedColor) {
				totalPlacedColor = dbResultSetMostPlacedColor.getInt("totalblue");
				mostPlacedColor = GameColor.BLUE;
			}
			if (dbResultSetMostPlacedColor.getInt("totalpurple") > totalPlacedColor) {
				totalPlacedColor = dbResultSetMostPlacedColor.getInt("totalpurple");
				mostPlacedColor = GameColor.PURPLE;
			}
			stmtMostPlacedColor.close();

			// MostPlacedValue
			// TODO review this, don't know how to do this more efficient
			int mostPlacedValue = 0;
			int totalPlacedValue = 0;
			PreparedStatement stmtMostPlacedValue = con
					.prepareStatement("SELECT SUM(CASE WHEN dienumber = 1 THEN 1 ELSE 0 END) AS totalone,\r\n"
							+ "SUM(CASE WHEN dienumber = 2 THEN 1 ELSE 0 END) AS totaltwo,\r\n"
							+ "SUM(CASE WHEN dienumber = 3 THEN 1 ELSE 0 END) AS totalthree,\r\n"
							+ "SUM(CASE WHEN dienumber = 4 THEN 1 ELSE 0 END) AS totalfour,\r\n"
							+ "SUM(CASE WHEN dienumber = 5 THEN 1 ELSE 0 END) AS totalfive,\r\n"
							+ "SUM(CASE WHEN dienumber = 6 THEN 1 ELSE 0 END) AS totalsix\r\n"
							+ "FROM playerframefield\r\n" + "JOIN player ON player_idplayer = idplayer\r\n"
							+ "WHERE dienumber IS NOT NULL AND username = ?");
			stmtMostPlacedValue.setString(1, username);
			ResultSet dbResultSetMostPlacedValue = stmtMostPlacedValue.executeQuery();
			dbResultSetMostPlacedValue.next();
			if (dbResultSetMostPlacedValue.getInt("totalone") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totalone");
				mostPlacedValue = 1;
			}
			if (dbResultSetMostPlacedValue.getInt("totaltwo") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totaltwo");
				mostPlacedValue = 2;
			}
			if (dbResultSetMostPlacedValue.getInt("totalthree") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totalthree");
				mostPlacedValue = 3;
			}
			if (dbResultSetMostPlacedValue.getInt("totalfour") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totalfour");
				mostPlacedValue = 4;
			}
			if (dbResultSetMostPlacedValue.getInt("totalfive") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totalfive");
				mostPlacedValue = 5;
			}
			if (dbResultSetMostPlacedValue.getInt("totalsix") > totalPlacedValue) {
				totalPlacedValue = dbResultSetMostPlacedValue.getInt("totalsix");
				mostPlacedValue = 6;
			}
			stmtMostPlacedValue.close();

			result = new User(gamesPlayed, maxScore, mostPlacedColor, mostPlacedValue);
		} catch (SQLException e) {
			System.err.println("UserDAO " + e.getMessage());
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
