package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import game.CollectiveGoalCard;

class CollectiveGoalCardDAO extends BaseDAO {
	Connection con = super.getConnection();

	// This method is only for selects for inserts 'n shit use
	// super.prepStmnt("REALLY NICE STATEMENT");
	private ArrayList<CollectiveGoalCard> selectCollectiveGoalCard(String query) {
		ArrayList<CollectiveGoalCard> results = new ArrayList<CollectiveGoalCard>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			stmt.close();
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int cardID = dbResultSet.getInt("idpublic_objectivecard");
				String name = dbResultSet.getString("name");
				String description = dbResultSet.getString("description");
				int points = dbResultSet.getInt("points");
				CollectiveGoalCard card = new CollectiveGoalCard(cardID, name, description, points);
				results.add(card);
			}

		} catch (SQLException e) {
			System.err.println("DieDAO " + e.getMessage());
			try {
				con.rollback();

			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");

			}

		}
		return results;
	}

	// Get all the cards from the look up table
	ArrayList<CollectiveGoalCard> getAllCollectiveGoalCards() {
		return selectCollectiveGoalCard("SELECT * FROM public_objectivecard");
	}

	// Get all the cards that are used in the game
	ArrayList<CollectiveGoalCard> getSharedCollectiveGoalCards(int idGame) {
		return selectCollectiveGoalCard("SELECT * FROM sharedpublic_objectivecard"
				+ "JOIN public_objectivecard ON sharedpublic_objectivecard.idpublic_objectivecard = public_objectivecard.idpublic_objectivecard"
				+ "WHERE idGame = " + Integer.toString(idGame));
	}

	// Get one specific card
	ArrayList<CollectiveGoalCard> getSelectedCollectiveGoalCard(int idpublic_objectivecard) {
		return selectCollectiveGoalCard("SELECT * FROM public_objectivecard WHERE idpublic_objectivecard = "
				+ Integer.toString(idpublic_objectivecard));
	}
}
