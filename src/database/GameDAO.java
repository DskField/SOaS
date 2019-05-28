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
			PreparedStatement stmt = con.prepareStatement("INSERT INTO game (idgame, turn_idplayer, creationdate) VALUES(null, null, now());");
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
			System.err.println("GameDAO (createGame #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("GameDAO (createGame #2) --> The rollback failed: Please check the Database!");
			}
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
			System.err.println("GameDAO (updateCurrentPlayer #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("GameDAO (updateCurrentPlayer #2) --> The rollback failed: Please check the Database!");
			}
		}
	}

	int getCurrentRound(int idgame) {
		return selectCurrentRound("SELECT MAX(roundtrack) AS currentround FROM gamedie WHERE idgame = " + idgame);
	}

	private int selectCurrentRound(String query) {
		int currentRound = 0;
		try {

			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				currentRound = dbResultSet.getInt("currentround") + 1;
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("GameDAO (selectCurrentRound) --> " + e.getMessage());
		}
		return currentRound;
	}
}