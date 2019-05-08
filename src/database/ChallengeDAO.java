package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import client.Challenge;

public class ChallengeDAO extends BaseDAO {
	Connection con = super.getConnection();

	private ArrayList<Challenge> selectChallenges(String query, String username) {
		ArrayList<Challenge> results = new ArrayList<>();

		// Get all challenges gameID
		ArrayList<Integer> distinctGameID = new ArrayList<>();
		try {
			PreparedStatement stmtDistinctGameID = con.prepareStatement(query);
			stmtDistinctGameID.setString(1, username);
			ResultSet dbResultSetDistinctGameID = stmtDistinctGameID.executeQuery();
			while (dbResultSetDistinctGameID.next()) {
				distinctGameID.add(dbResultSetDistinctGameID.getInt("game_idgame"));
			}
			stmtDistinctGameID.close();
		} catch (SQLException e) {
			System.err.println("ChallengeDAO " + e.getMessage());
		}

		// for every challenge gameID make object for results
		for (Integer i : distinctGameID) {
			try {
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM player WHERE game_idgame = " + i);
				ResultSet dbResultSet = stmt.executeQuery();
				HashMap<String, String> players = new HashMap<>();
				while (dbResultSet.next()) {
					// Separated the variables on purpose for clarity

					// Hashmap
					String playername = dbResultSet.getString("username");
					String playstatus = dbResultSet.getString("playstatus_playstatus");
					players.put(playername, playstatus);
				}
				Challenge challenge = new Challenge(i, players);
				results.add(challenge);

				stmt.close();
			} catch (SQLException e1) {
				System.err.println("ChallengeDAO " + e1.getMessage());
			}
		}
		return results;
	}

	public ArrayList<Challenge> getChallenges(String username) {
		return selectChallenges("SELECT DISTINCT(game_idgame)\r\n" + "FROM player\r\n"
				+ "WHERE playstatus_playstatus IN (\"uitdager\", \"uitgedaagde\", \"geaccepteerd\", \"geweigerd\") AND username = ?",
				username);
	}

}
