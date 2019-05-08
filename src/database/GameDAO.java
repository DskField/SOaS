package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import game.Game;

class GameDAO {
	private Connection con;

	public GameDAO(Connection connection) {
		con = connection;
	}

	private List<Game> selectGame(String query) {
		List<Game> results = new ArrayList<Game>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				int gameID = dbResultSet.getInt("idgame");
				//				Game game = new Game(gameID, new User("test"));
				//				results.add(game);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO " + e.getMessage());
		}
		return results;
	}
}