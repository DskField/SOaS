package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.ToolCard;

class ToolCardDAO extends BaseDAO {
	
	private ArrayList<ToolCard> selectToolCards(String query) {
		ArrayList<ToolCard> results = new ArrayList<ToolCard>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int cardID = dbResultSet.getInt("idpublic_objectivecard");
				String name = dbResultSet.getString("name");
				int seqnr = dbResultSet.getInt("seqnr");
				String description = dbResultSet.getString("description");
				ToolCard card = new ToolCard(cardID, name, seqnr, description);
				results.add(card);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ToolCardDAO: " + e.getMessage());
		}
		return results;
	}

	// Get all the cards from the look up table
	ArrayList<ToolCard> getAllToolCards() {
		return selectToolCards("SELECT * FROM toolcard");
	}

	// Get all the cards that are used in the game
	ArrayList<ToolCard> getGameToolCards(int idGame) {
		return selectToolCards("SELECT * FROM gametoolcard"
				+ "JOIN toolcard ON toolcard.idtoolcard = gametoolcard.idtoolcard"
				+ "WHERE idGame = " + Integer.toString(idGame));
	}

	// Get one specific card
	ArrayList<ToolCard> getSelectedToolCard(int idtoolcard) {
		return selectToolCards(
				"SELECT * FROM toolcard WHERE idtoolcard = " + Integer.toString(idtoolcard));
	}
}
