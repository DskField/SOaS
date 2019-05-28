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

	private ArrayList<Integer> updateGame(String query, String username) {
		ArrayList<Integer> results = new ArrayList<>();

		// get all games you created
		ArrayList<Integer> allGameID = selectAllChallenges(
				"SELECT game_idgame FROM player WHERE playstatus_playstatus = \"uitdager\" AND username = ?", username);
		try {
			// check for every game
			for (Integer id : allGameID) {
				ArrayList<String> allStatus = new ArrayList<>();
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				ResultSet dbResultSet = stmt.executeQuery();

				// put all playstatus in a string
				while (dbResultSet.next()) {
					// if a playstatus is 'uitgedaagde' quite the loop
					if (dbResultSet.getString("playstatus_playstatus").equals("uitgedaagde"))
						break;
					allStatus.add(dbResultSet.getString("playstatus_playstatus"));
				}
				dbResultSet.last();
				int rowcount = dbResultSet.getRow();

				// Check if everyone answered
				System.out.println(rowcount + " + " + allStatus.size());
				if (rowcount == allStatus.size()) {
					if (allStatus.contains("geweigerd")) {
						PreparedStatement stmtUpdate = con
								.prepareStatement("UPDATE player SET playstatus_playstatus = \"afgebroken\" WHERE game_idgame = ?");
						stmtUpdate.setInt(1, id);
						stmtUpdate.executeUpdate();
						stmtUpdate.close();
					} else if (!allStatus.contains("uitgespeeld")) {
						// check if game already started
						PreparedStatement stmtCheckGameStats = con
								.prepareStatement("SELECT COUNT(*) AS count FROM player WHERE game_idgame = ? AND seqnr != null");
						stmtCheckGameStats.setInt(1, id);
						ResultSet dbResultSetCheckGameStats = stmtCheckGameStats.executeQuery();
						dbResultSetCheckGameStats.next();
						if (dbResultSetCheckGameStats.getInt("count") == 0)
							// return idGame to start game if game didnt start
							results.add(id);
						dbResultSetCheckGameStats.close();
					}
				}

				con.commit();
				stmt.close();
			}
		} catch (SQLException e) {
			System.err.println("ChallengeDAO: " + e.getMessage());
		}
		return results;
	}

	public ArrayList<Integer> checkCreatedChallenges(String username) {
		return updateGame("SELECT playstatus_playstatus FROM player WHERE game_idgame = ?", username);
	}

	public boolean hasOpenInvite(String username, String opponentname) {
		return openInvite("SELECT COUNT(*) AS openinvites\r\n FROM player AS p1\r\n" + "JOIN player AS p2 ON p1.game_idgame = p2.game_idgame\r\n"
				+ "WHERE p1.username = ? AND p1.playstatus_playstatus = \"uitdager\" AND p2.username = ? AND p2.playstatus_playstatus = \"uitgedaagde\"",
				username, opponentname);
	}

	public ArrayList<Integer> getChallenges(String username) {
		return selectAllChallenges("SELECT game_idgame FROM player WHERE playstatus_playstatus = \"uitgedaagde\" AND username = ?", username);
	}

	public void updateStatus(String username, boolean accepted, int idGame) {
		updatePlayerStatus("UPDATE player SET playstatus_playstatus = ? WHERE username = ? AND game_idgame = ?", username, accepted, idGame);
	}
}
