package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.Player;

class GameDAO {
	private Connection con;

	public GameDAO(Connection connection) {
		con = connection;
	}

	int createGame() {
		int last = 0;

		try {
			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO game (idgame, turn_idplayer, creationdate) VALUES(null, null, now());");
			stmt.executeUpdate();
			stmt.close();

			PreparedStatement stmt2 = con.prepareStatement("SELECT LAST_INSERT_ID() last;");
			ResultSet dbResultSet = stmt2.executeQuery();

			while (dbResultSet.next()) {
				last = dbResultSet.getInt("last");
			}
			con.commit();
			stmt2.close();
		} catch (SQLException e) {
			System.err.println("GameDAO: " + e.getMessage());
		}
		return last;
	}

	void updateCurrentPlayer(int idgame, Player player) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE game SET turn_idplayer = ? WHERE idgame = ?");
			stmt.setInt(1, player.getPlayerID());
			stmt.setInt(2, idgame);
			stmt.executeUpdate();
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("GameDAO: " + e.getMessage());
		}
	}

	int getCurrentRound(int idgame) {
		int currentRound = 0;
		try {

			PreparedStatement stmtCurrentRound = con
					.prepareStatement("SELECT MAX(roundtrack) AS currentround FROM gamedie WHERE idgame = " + idgame);
			ResultSet dbResultSet = stmtCurrentRound.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				currentRound = dbResultSet.getInt("currentround") + 1;
			}
			con.commit();
			stmtCurrentRound.close();
		} catch (SQLException e) {
			System.err.println("DieDAO " + e.getMessage());
		}
		return currentRound;
	}
}