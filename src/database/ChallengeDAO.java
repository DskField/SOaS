package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import client.Challenge;

class ChallengeDAO {
	Connection con;

	public ChallengeDAO(Connection con) {
		this.con = con;
	}

	private ArrayList<Integer> selectAllChallenges(String query, String username) {
		ArrayList<Integer> results = new ArrayList<>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				results.add(dbResultSet.getInt("game_idgame"));
			}

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("ChallengeDAO: " + e.getMessage());
		}
		return results;
	}

	private Challenge selectChallenge(String query, int idGame) {
		Challenge challenge = null;

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, idGame);
			ResultSet dbResultSet = stmt.executeQuery();
			HashMap<String, String> players = new HashMap<>();
			while (dbResultSet.next()) {
				String playername = dbResultSet.getString("username");
				String playerstatus = dbResultSet.getString("playstatus_playstatus");
				players.put(playername, playerstatus);
			}
			challenge = new Challenge(idGame, players);

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("ChallengeDAO: " + e.getMessage());
		}
		return challenge;
	}

	public Challenge getChallenge(int idGame) {
		return selectChallenge("SELECT * FROM player WHERE game_idgame = ?", idGame);
	}

	private void updatePlayerStatus(String query, String username, boolean accepted, int idGame) {
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, accepted ? "geaccepteerd" : "geweigerd");
			stmt.setString(2, username);
			stmt.setInt(3, idGame);
			stmt.executeUpdate();
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("ChallengeDAO: " + e.getMessage());
		}
	}

	private boolean openInvite(String query, String username, String opponentname) {
		boolean hasOpenInvite = false;

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, opponentname);
			ResultSet dbResultSet = stmt.executeQuery();
			dbResultSet.next();
			hasOpenInvite = dbResultSet.getInt("openinvites") != 0 ? true : false;
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("ChallengeDAO: " + e.getMessage());
		}
		return hasOpenInvite;
	}

	public boolean hasOpenInvite(String username, String opponentname) {
		return openInvite("SELECT COUNT(*) AS openinvites\r\n FROM player AS p1\r\n"
				+ "JOIN player AS p2 ON p1.game_idgame = p2.game_idgame\r\n"
				+ "WHERE p1.username = ? AND p1.playstatus_playstatus = \"uitdager\" AND p2.username = ? AND p2.playstatus_playstatus = \"uitgedaagde\"",
				username, opponentname);
	}

	public ArrayList<Integer> getChallenges(String username) {
		return selectAllChallenges(
				"SELECT game_idgame FROM player WHERE playstatus_playstatus = \"uitgedaagde\" AND username = ?",
				username);
	}

	public void updateStatus(String username, boolean accepted, int idGame) {
		updatePlayerStatus("UPDATE player SET playstatus_playstatus = ? WHERE username = ? AND game_idgame = ?",
				username, accepted, idGame);
	}
}
