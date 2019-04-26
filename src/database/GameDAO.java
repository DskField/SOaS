package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import game.Game;

class GameDAO extends BaseDAO {
	Connection con = super.getConnection();

	private List<Game> selectGame(String query) {
		List<Game> results = new ArrayList<Game>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			stmt.close();
			while (dbResultSet.next()) {
				int gameID = dbResultSet.getInt("idgame");
				Game game = new Game(gameID);
				results.add(game);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO " + e.getMessage());
			try {
				con.rollback();

			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");

			}

		}
		return results;
	}
}