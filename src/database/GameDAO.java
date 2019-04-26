package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import game.Game;

class GameDAO extends BaseDAO {
	private List<Game> selectGame(String query) {
		List<Game> results = new ArrayList<Game>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				int gameID = dbResultSet.getInt("idgame");

				Game game = new Game(gameID);
				results.add(game);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("GameDAO " + e.getMessage());
		}
		return results;
	}
}
