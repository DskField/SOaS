package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import game.ToolCard;

class ToolCardDAO {
	private Connection con;

	public ToolCardDAO(Connection connection) {
		con = connection;
	}

	private ArrayList<ToolCard> selectToolCards(String query) {
		ArrayList<ToolCard> results = new ArrayList<ToolCard>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int cardID = dbResultSet.getInt("idtoolcard");
				String name = dbResultSet.getString("name");
				int seqnr = dbResultSet.getInt("seqnr");
				String description = dbResultSet.getString("description");

				ToolCard card = new ToolCard(cardID, name, seqnr, description);
				results.add(card);
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("ToolCardDAO " + e.getMessage());
		}
		return results;
	}

	// Get all the cards from the look up table
	private ArrayList<ToolCard> getAllToolCards() {
		return selectToolCards("SELECT * FROM toolcard");
	}

	// Get all the cards that are used in the game
	ArrayList<ToolCard> getGameToolCards(int idGame) {
		return selectToolCards("SELECT * FROM gametoolcard" + " JOIN toolcard ON toolcard.idtoolcard = gametoolcard.idtoolcard" + " WHERE idGame = " + idGame);
	}

	// Get one specific card
	ToolCard getSelectedToolCard(int idtoolcard) {
		return selectToolCards("SELECT * FROM toolcard WHERE idtoolcard = " + idtoolcard).get(0);
	}

	void insertRandomGameToolCards(int idGame) {
		if (getGameToolCards(idGame).size() == 0) {
			ArrayList<ToolCard> list = getAllToolCards();
			Collections.shuffle(list);

			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO gametoolcard VALUES (null,?,?), (null,?,?), (null,?,?)");
				// card 1
				stmt.setInt(1, list.get(0).getCardID());
				stmt.setInt(2, idGame);

				// card 2
				stmt.setInt(3, list.get(1).getCardID());
				stmt.setInt(4, idGame);

				// card 3
				stmt.setInt(5, list.get(2).getCardID());
				stmt.setInt(6, idGame);

				stmt.executeUpdate();
				con.commit();
				stmt.close();
			} catch (SQLException e) {
				System.err.println("ToolCardDAO " + e.getMessage());
				try {
					con.rollback();
				} catch (SQLException e1) {
					System.err.println("The rollback failed: Please check the Database!");
				}
			}
		} else {
			System.err.println("ToolCardDAO trying to select 3 random cards for a game that already has 3 cards");
		}
	}
}
