package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.GameColor;
import game.Player;

class PlayerDAO extends BaseDAO {
	Connection con = super.getConnection();

	
	private ArrayList<Player> selectDie(String query) {
		ArrayList<Player> results = new ArrayList<Player>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			stmt.close();
			while (dbResultSet.next()) {
				
				//TODO MAAK PLAYER OBJECTEN
//				int playerID = dbResultSet.getInt("idplayer");
//				int seqnr = dbResultSet.getInt("seqnr");
//				String username = dbResultSet.getString("username");
//				
//				Player player = new Player(playerID, seqnr, personalGoalCard, username);
//				results.add(die);
			}

		} catch (SQLException e) {
			System.err.println("PlayerDAO " + e.getMessage());
			try {
				con.rollback();

			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");

			}
			
		}
		return results;
	}
	
	

	ArrayList<Player> getPlayersOfGame(int gameID) {
		ArrayList<Player> results = new ArrayList<>();
		String query = "SELECT * FROM player WHERE game_idgame = " + Integer.toString(gameID);
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int playerID = dbResultSet.getInt("idplayer");
				int seqnr = dbResultSet.getInt("seqnr");
				String username = dbResultSet.getString("username");
				GameColor personalGoalCard;
				// Only set color if it's a valid color
				try {
					personalGoalCard = GameColor
							.valueOf(dbResultSet.getString("private_objectivecard_color").toUpperCase());
				} catch (Exception e) {
					e.printStackTrace();
					personalGoalCard = null;
					System.err.println("Invalid color at PlayerDAO");
				}
				Player player = new Player(playerID, seqnr, personalGoalCard, username);
				results.add(player);
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("PlayerDAO " + e.getMessage());
		}
		return results;
	}
}
