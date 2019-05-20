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
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO game (idgame, turn_idplayer, creationdate) VALUES(null, null, now());");
			stmt.executeUpdate();
			stmt.close();
			con.commit();

			PreparedStatement stmt2 = con.prepareStatement("SELECT LAST_INSERT_ID() last;");
			ResultSet dbResultSet = stmt2.executeQuery();

			while (dbResultSet.next()) {
				return dbResultSet.getInt("last");
			}
			stmt2.close();
		} catch (SQLException e) {
			System.err.println("GameDAO: " + e.getMessage());
		}
		return 0;
	}

	void updateCurrentPlayer(int idgame, Player player) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE game SET turn_idplayer = ? WHERE idgame = ?");
			stmt.setInt(1, player.getPlayerID());
			stmt.setInt(2, idgame);
			stmt.executeUpdate();
			stmt.close();
			con.commit();
		} catch (SQLException e) {
			System.err.println("GameDAO: " + e.getMessage());
		}
	}
}