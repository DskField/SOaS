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

	private Lobby selectLobby(String query, int idGame, String username) {
		Lobby lobby = null;
		
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setInt(2, idGame);
			ResultSet dbResultSet = stmt.executeQuery();
			dbResultSet.next();
			
			boolean isCurrentPlayer = dbResultSet.getBoolean("isCurrentPlayer");
			
			int finalScore = dbResultSet.getInt("score");
			
			// lobbyResponse
			PreparedStatement stmtLobbyResponse = con.prepareStatement("SELECT COUNT(idplayer) AS responsesize FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND game_idgame = " + idGame);
			ResultSet dbResultSetLobbyResponse = stmtLobbyResponse.executeQuery();
			dbResultSetLobbyResponse.next();
			int lobbyResponse = dbResultSetLobbyResponse.getInt("responsesize");
			stmtLobbyResponse.close();
			
			// lobbySize
			PreparedStatement stmtLobbySize = con.prepareStatement("SELECT COUNT(idplayer) AS lobbySize FROM player WHERE game_idgame = " + idGame);
			ResultSet dbResultSetLobbySize = stmtLobbySize.executeQuery();
			dbResultSetLobbySize.next();
			int lobbySize = dbResultSetLobbySize.getInt("lobbysize");
			stmtLobbySize.close();

			// isWon
			PreparedStatement stmtIsWon = con.prepareStatement("SELECT username, playstatus_playstatus FROM player WHERE game_idgame = ? ORDER BY score DESC LIMIT 1");
			stmtIsWon.setInt(1, idGame);
			ResultSet dbResultSetIsWon = stmtIsWon.executeQuery();
			dbResultSetIsWon.next();
			boolean won = dbResultSetIsWon.getString("playstatus_playstatus").equals("uitgespeeld") && dbResultSetIsWon.getString("username").equals(username) ? true : false;
			stmtIsWon.close();

			// currentRound
			PreparedStatement stmtCurrentRound = con.prepareStatement("SELECT MAX(roundtrack) AS currentround FROM gamedie WHERE idgame = ?");
			stmtCurrentRound.setInt(1, idGame);
			ResultSet dbResultSetCurrentRound = stmtCurrentRound.executeQuery();
			dbResultSetCurrentRound.next();
			int currentRound = dbResultSetCurrentRound.getInt("currentround") + 1;
			
			PreparedStatement stmtGameState = con.prepareStatement("SELECT playstatus_playstatus FROM player WHERE game_idgame = " + idGame);
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
			
			lobby = new Lobby(idGame, gameState, isCurrentPlayer, lobbyResponse, lobbySize, finalScore, won, currentRound);

			con.commit();
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println("LobbyDAO: " + e.getMessage());
		}
		return lobby;
	}
	
	private ArrayList<Integer> selectAllLobbies(String query, String username) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet dbResultSet = stmt.executeQuery();
			
			while(dbResultSet.next()) {
				results.add(dbResultSet.getInt("game_idgame"));
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("LobbyDAO: " + e.getMessage());
		}
		return results;
	}
	
	Lobby getLobby(int idGame, String username) {
		return selectLobby("SELECT * FROM player WHERE username = ? AND game_idgame = ?", idGame, username);
	}
	
	ArrayList<Integer> getAllLobbyID(String username) {
		return selectAllLobbies("SELECT * FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND username = ?", username);
	}
}
