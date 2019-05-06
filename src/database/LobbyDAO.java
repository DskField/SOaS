package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import client.Lobby;

public class LobbyDAO extends BaseDAO {
	Connection con = super.getConnection();

	private ArrayList<Lobby> selectLobbies(String query) {
		ArrayList<Lobby> results = new ArrayList<Lobby>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity

				// int gameID
				int gameID = dbResultSet.getInt("game_idgame");

				// String gameState
				PreparedStatement stmtGameState = con
						.prepareStatement("SELECT playstatus_playstatus FROM player WHERE game_idgame = " + gameID);
				ResultSet dbResultSetGameState = stmtGameState.executeQuery();
				ArrayList<String> listResultSetGameState = new ArrayList<>();
				String gameState;
				while (dbResultSetGameState.next())
					listResultSetGameState.add(dbResultSetGameState.getString("playstatus_playstatus"));
				if (listResultSetGameState.contains("afgebroken"))
					gameState = "afgebroken";
				else if (listResultSetGameState.contains("uitgedaagde"))
					gameState = "wachtende";
				else if (listResultSetGameState.contains("uitgespeeld"))
					gameState = "uitgespeeld";
				else
					gameState = "aan de gang";
				stmtGameState.close();

				// isCurrentPlayer
				boolean isCurrentPlayer = dbResultSet.getBoolean("isCurrentPlayer");

				// lobbyResponse
				PreparedStatement stmtLobbyResponse = con.prepareStatement(
						"SELECT COUNT(idplayer) AS responsesize FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND game_idgame = "
								+ gameID);
				ResultSet dbResultSetLobbyResponse = stmtLobbyResponse.executeQuery();
				int lobbyResponse = dbResultSetLobbyResponse.getInt("responsesize");
				stmtLobbyResponse.close();

				// lobbySize
				PreparedStatement stmtLobbySize = con.prepareStatement(
						"SELECT COUNT(idplayer) AS lobbySize FROM player WHERE game_idgame = " + gameID);
				ResultSet dbResultSetLobbySize = stmtLobbySize.executeQuery();
				int lobbySize = dbResultSetLobbySize.getInt("lobbysize");
				stmtLobbySize.close();

				Lobby lobby = new Lobby(gameID, gameState, isCurrentPlayer, lobbyResponse, lobbySize);
				results.add(lobby);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("LobbyDAO " + e.getMessage());
		}
		return results;
	}

	ArrayList<Lobby> getLobbies(String username) {
		return selectLobbies("SELECT * FROM player WHERE username = " + username);
	}
}
