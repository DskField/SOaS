package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import game.CollectiveGoalCard;

class CollectiveGoalCardDAO {
	private Connection con;

	public CollectiveGoalCardDAO(Connection connection) {
		con = connection;
	}

	private ArrayList<CollectiveGoalCard> selectCollectiveGoalCards(String query) {
		ArrayList<CollectiveGoalCard> results = new ArrayList<CollectiveGoalCard>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int cardID = dbResultSet.getInt("idpublic_objectivecard");
				String name = dbResultSet.getString("name");
				String description = dbResultSet.getString("description");
				int points = dbResultSet.getInt("points");

				CollectiveGoalCard card = new CollectiveGoalCard(cardID, name, description, points);
				results.add(card);
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO (select) --> " + e.getMessage());
		}
		return results;
	}

	// Get all the cards from the look up table
	private ArrayList<CollectiveGoalCard> getAllCollectiveGoalCards() {
		return selectCollectiveGoalCards("SELECT * FROM public_objectivecard");
	}

	// Get all the cards that are used in the game
	ArrayList<CollectiveGoalCard> getSharedCollectiveGoalCards(int idGame) {
		return selectCollectiveGoalCards(
				"SELECT * FROM sharedpublic_objectivecard" + " JOIN public_objectivecard ON sharedpublic_objectivecard.idpublic_objectivecard = public_objectivecard.idpublic_objectivecard" + " WHERE idGame = " + idGame);
	}

	// Get one specific card
	CollectiveGoalCard getSelectedCollectiveGoalCard(int idpublic_objectivecard) {
		return selectCollectiveGoalCards("SELECT * FROM public_objectivecard WHERE idpublic_objectivecard = " + idpublic_objectivecard).get(0);
	}

	// set 3 random shared CollectiveGoalCards IF no cards have been appointed to
	// the game
	void insertRandomSharedCollectiveGoalCards(int idGame) {
		if (getSharedCollectiveGoalCards(idGame).size() == 0) {
			ArrayList<CollectiveGoalCard> list = getAllCollectiveGoalCards();
			Collections.shuffle(list);

			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO sharedpublic_objectivecard VALUES (?,?), (?,?), (?,?)");
				// card 1
				stmt.setInt(1, idGame);
				stmt.setInt(2, list.get(0).getCardID());

				// card 2
				stmt.setInt(3, idGame);
				stmt.setInt(4, list.get(1).getCardID());

				// card 3
				stmt.setInt(5, idGame);
				stmt.setInt(6, list.get(2).getCardID());

				stmt.executeUpdate();
				con.commit();
				stmt.close();
			} catch (SQLException e) {
				System.err.println("CollectiveGoalCardDAO (insert) --> " + e.getMessage());
				try {
					con.rollback();
				} catch (SQLException e1) {
					System.err.println("CollectiveGoalCardDAO (insert) --> The rollback failed: Please check the Database!");
				}
			}
		} else {
			System.err.println("CollectiveGoalCardDAO (insert) --> trying to select 3 random cards for a game that already has 3 cards");
		}
	}
}
