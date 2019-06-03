package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import client.Lobby;

class LobbyDAO {
	private Connection con;

	public LobbyDAO(Connection con) {
		this.con = con;
	}
	
	ArrayList<ArrayList<String>> getScoreboard(int idGame) {
		return selectScoreboard("SELECT username, score FROM player WHERE game_idgame = ? ORDER BY score DESC", idGame);
	}

	Lobby getLobby(int idGame) {
		return selectLobby("SELECT * FROM player WHERE game_idgame = ?", idGame);
	}

	ArrayList<Integer> getAllLobbyID(boolean orderASC) {
		return selectAllLobbies(orderASC ? "SELECT idgame FROM game ORDER BY creationdate ASC" : "SELECT idgame FROM game ORDER BY creationdate DESC",
				true);
	}

	ArrayList<Integer> getAllPlayerLobbyID(String username) {
		return selectAllLobbies("SELECT * FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND username = \"" + username + "\"", false);
	}

	private Lobby selectLobby(String query, int idGame) {
		Lobby lobby = null;

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, idGame);
			ResultSet dbResultSet = stmt.executeQuery();
			dbResultSet.next();

			boolean isCurrentPlayer = dbResultSet.getBoolean("isCurrentPlayer");

			int finalScore = dbResultSet.getInt("score");

			// lobbyResponse
			PreparedStatement stmtLobbyResponse = con.prepareStatement(
					"SELECT COUNT(idplayer) AS responsesize FROM player WHERE playstatus_playstatus != \"uitgedaagde\" AND game_idgame = " + idGame);
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

			if (listResultSetGameState.contains("afgebroken") || listResultSetGameState.contains("geweigerd"))
				gameState = "afgebroken";
			else if (listResultSetGameState.contains("uitgedaagde"))
				gameState = "wachtende";
			else if (listResultSetGameState.contains("uitgespeeld"))
				gameState = "uitgespeeld";
			else
				gameState = "aan de gang";
			stmtGameState.close();

			lobby = new Lobby(idGame, gameState, isCurrentPlayer, lobbyResponse, lobbySize, finalScore, currentRound);

			con.commit();
			stmt.close();

		} catch (SQLException e) {
			System.err.println("LobbyDAO: " + e.getMessage());
		}
		return lobby;
	}

	private ArrayList<Integer> selectAllLobbies(String query, boolean allLobbies) {
		ArrayList<Integer> results = new ArrayList<Integer>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				if (allLobbies)
					results.add(dbResultSet.getInt("idgame"));
				else
					results.add(dbResultSet.getInt("game_idgame"));
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("LobbyDAO: " + e.getMessage());
		}
		return results;
	}

	private ArrayList<ArrayList<String>> selectScoreboard(String query, int idGame) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, idGame);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				results.add(new ArrayList<String>(Arrays.asList(dbResultSet.getString("username"), dbResultSet.getString("score"))));
			}

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("LobbyDAO: " + e.getMessage());
		}
		return results;
	}
}
