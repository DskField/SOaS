package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import game.GameColor;
import game.Player;

class PlayerDAO extends BaseDAO {

	ArrayList<Player> getPlayersOfGame(int gameID) {
		ArrayList<Player> results = new ArrayList<>();
		String query = "SELECT * FROM player WHERE game_idgame = " + Integer.toString(gameID);
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				int playerID = dbResultSet.getInt("idplayer");
				int seqnr = dbResultSet.getInt("seqnr");
				GameColor personalGoalCard;
				
				// only set color if it's a valid color
				try {
					personalGoalCard = GameColor
							.valueOf(dbResultSet.getString("private_objectivecard_color").toUpperCase());
				} catch (Exception e) {
					e.printStackTrace();
					personalGoalCard = null;
					System.out.println("Invalid color at PlayerDAO");
				}
				Player player = new Player(playerID, seqnr, personalGoalCard);
				results.add(player);
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PlayerDAO " + e.getMessage());
		}
		return results;
	}
}
