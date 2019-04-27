package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import game.CollectiveGoalCard;

class CollectiveGoalCardDAO extends BaseDAO {

	private ArrayList<CollectiveGoalCard> selectCollectiveGoalCard(String query) {
		ArrayList<CollectiveGoalCard> results = new ArrayList<CollectiveGoalCard>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int cardID = dbResultSet.getInt("idpublic_objectivecard");
				String name = dbResultSet.getString("name");
				String description = dbResultSet.getString("description");
				int points = dbResultSet.getInt("points");
				CollectiveGoalCard card = new CollectiveGoalCard(cardID, name, description, points);
				results.add(card);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO: " + e.getMessage());
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

	// set 3 random shared CollectiveGoalCards IF no cards have been appointed to the game
	void setRandomSharedCollectiveGoalCards(int idGame) {
		if (getSharedCollectiveGoalCards(idGame).size() == 0) {
			ArrayList<CollectiveGoalCard> list = getAllCollectiveGoalCards();
			Collections.shuffle(list);
			selectCollectiveGoalCard("INSERT INTO sharedpublic_objectivecard VALUES " + "( " + Integer.toString(idGame)
					+ ", " + Integer.toString(list.get(0).getCardID()) + ")" + "( " + Integer.toString(idGame) + ", "
					+ Integer.toString(list.get(1).getCardID()) + ")" + "( " + Integer.toString(idGame) + ", "
					+ Integer.toString(list.get(2).getCardID()) + ")");
		} else {
			System.err.println("CollectiveGoalCardDAO trying to select 3 random cards for a game that already has 3 cards");
		}
	}
}
