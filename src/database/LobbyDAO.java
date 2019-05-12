package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import client.Lobby;

class LobbyDAO {
	Connection con;

	public LobbyDAO(Connection con) {
		this.con = con;
	}

	private ArrayList<Lobby> selectLobbies(String query, String username) {
		ArrayList<Lobby> results = new ArrayList<Lobby>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity

				// int gameID
				int gameID = dbResultSet.getInt("game_idgame");

				// String gameState
				PreparedStatement stmtGameState = con.prepareStatement("SELECT playstatus_playstatus FROM player WHERE game_idgame = " + gameID);
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
				PreparedStatement stmtLobbyResponse = con.prepareStatement("SELECT COUNT(idplayer) AS responsesize FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND game_idgame = " + gameID);
				ResultSet dbResultSetLobbyResponse = stmtLobbyResponse.executeQuery();
				dbResultSetLobbyResponse.next();
				int lobbyResponse = dbResultSetLobbyResponse.getInt("responsesize");
				stmtLobbyResponse.close();

				// lobbySize
				PreparedStatement stmtLobbySize = con.prepareStatement("SELECT COUNT(idplayer) AS lobbySize FROM player WHERE game_idgame = " + gameID);
				ResultSet dbResultSetLobbySize = stmtLobbySize.executeQuery();
				dbResultSetLobbySize.next();
				int lobbySize = dbResultSetLobbySize.getInt("lobbysize");
				stmtLobbySize.close();

				// finalScore
				// only set final score when the game is finished
				PreparedStatement stmtFinalScore = con.prepareStatement("SELECT score, playstatus_playstatus FROM player WHERE game_idgame = ? AND username = ?");
				stmtFinalScore.setInt(1, gameID);
				stmtFinalScore.setString(2, username);
				ResultSet dbResultSetFinalScore = stmtFinalScore.executeQuery();
				dbResultSetFinalScore.next();
				int finalScore = dbResultSetFinalScore.getInt("score");
				stmtFinalScore.close();

				// isWon
				PreparedStatement stmtIsWon = con.prepareStatement("SELECT username, playstatus_playstatus FROM player WHERE game_idgame = ? ORDER BY score DESC LIMIT 1");
				stmtIsWon.setInt(1, gameID);
				ResultSet dbResultSetIsWon = stmtIsWon.executeQuery();
				dbResultSetIsWon.next();
				boolean won = dbResultSetIsWon.getString("playstatus_playstatus").equals("uitgespeeld") && dbResultSetIsWon.getString("username").equals(username) ? true : false;
				stmtIsWon.close();

				// currentRound
				PreparedStatement stmtCurrentRound = con.prepareStatement("SELECT MAX(roundtrack) AS currentround FROM gamedie WHERE idgame = ?");
				stmtCurrentRound.setInt(1, gameID);
				ResultSet dbResultSetCurrentRound = stmtCurrentRound.executeQuery();
				dbResultSetCurrentRound.next();
				int currentRound = dbResultSetCurrentRound.getInt("currentround") + 1;
				Lobby lobby = new Lobby(gameID, gameState, isCurrentPlayer, lobbyResponse, lobbySize, finalScore, won, currentRound);
				results.add(lobby);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("LobbyDAO " + e.getMessage());
		}
		return results;
	}

	public ArrayList<Lobby> getLobbies(String username) {
		return selectLobbies("SELECT * FROM player WHERE username = ?", username);
	}
}
