package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.ToolCard;

public class ToolCardDAO extends BaseDAO {
	private ArrayList<ToolCard> selectToolCards(String query) {
		ArrayList<ToolCard> results = new ArrayList<ToolCard>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				ToolCard toolCard = new ToolCard("Stub", dbResultSet.getString("description"), dbResultSet.getInt("idtoolcard"));
				results.add(toolCard);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ToolCardDAO: " + e.getMessage());
		}
		return results;
	}

	public ArrayList<ToolCard> getToolCards(int[] id) {
		String query = "SELECT * FROM toolcard WHERE idtoolcard IN (" + id[0] + "," + id[1] + "," + id[2] + ");";
		return selectToolCards(query);
	}
}
