package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.CollectiveGoalCard;

public class CollectiveGoalCardDAO extends BaseDAO {
	private ArrayList<CollectiveGoalCard> selectCollectiveGoalCard(String query) {
		ArrayList<CollectiveGoalCard> results = new ArrayList<CollectiveGoalCard>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				CollectiveGoalCard collectiveGoalCard = new CollectiveGoalCard("Stub", dbResultSet.getString("description"),
						dbResultSet.getInt("idpublic_objectivecard"));
				results.add(collectiveGoalCard);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO: " + e.getMessage());
		}
		return results;
	}

	public ArrayList<CollectiveGoalCard> getCollectiveGoalCards(int[] id) {
		String query = "SELECT * FROM public_objectivecard WHERE idpublic_objectivecard IN (" + id[0] + "," + id[1] + "," + id[2] + ");";
		return selectCollectiveGoalCard(query);
	}
}
