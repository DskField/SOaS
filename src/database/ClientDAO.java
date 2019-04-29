package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import game.Game;

@SuppressWarnings("unused")
public class ClientDAO extends BaseDAO {

	Connection con = super.getConnection();
	
	private List<Game> selectGame(String query) {
		List<Game> results = new ArrayList<Game>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				int gameID = dbResultSet.getInt("idgame");
				Game game = new Game(gameID);
				results.add(game);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO " + e.getMessage());
		}
		return results;
	}
}
