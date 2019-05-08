package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.Die;
import game.Round;

class DieDAO {
	private Connection con;

	public DieDAO(Connection connection) {
		con = connection;
	}

	/**
	 * Load dice
	 * 
	 * @param query - The query you made to return the dice
	 * @return An ArrayList with Die
	 */
	private ArrayList<Die> selectDie(String query) {
		ArrayList<Die> results = new ArrayList<Die>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				int number = dbResultSet.getInt("dienumber");
				String color = dbResultSet.getString("diecolor");
				Die die = new Die(number, color);
				results.add(die);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("DieDAO " + e.getMessage());
		}
		return results;
	}

	/**
	 * Load the round track form the db
	 * 
	 * @param query - The query you made to return the round track
	 * @return An Array with the type Round filled with Die, in the correct format for the roundTrack
	 */
	private Round[] selectTrackDice(String query) {
		Round[] result = new Round[10];

		for (int i = 0; i < result.length; i++) {
			result[i] = new Round();
		}

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				int number = dbResultSet.getInt("dienumber");
				String color = dbResultSet.getString("diecolor");
				int eyes = dbResultSet.getInt("eyes");
				int round = dbResultSet.getInt("round");
				int roundTrack = dbResultSet.getInt("roundtrack");
				result[roundTrack - 1].addDie(new Die(number, color, round, eyes));
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("DieDAO " + e.getMessage());
		}
		return result;
	}

	/**
	 * Update dice that got rolled this round
	 * 
	 * @param gameID - The id of the game
	 * @param dice - The dice that got rolled in this round
	 */
	private void updateDice(int gameID, ArrayList<Die> dice) {
		try {
			for (Die die : dice) {
				PreparedStatement stmt = con.prepareStatement("UPDATE gameDie SET eyes = ?, round = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?;");
				stmt.setInt(1, die.getDieValue());
				stmt.setInt(2, die.getRound());
				stmt.setInt(3, gameID);
				stmt.setInt(4, die.getDieId());
				stmt.setString(5, die.getDieColor().getDatabaseName());
				stmt.executeUpdate();
				con.commit();
				stmt.close();
			}
		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}

	/**
	 * Update the dice that didn't got picked this round
	 * 
	 * @param gameID - The game ID
	 * @param round - The ID of the currentRound
	 * @param dice - The dice that didn't got picked
	 */
	private void updateRound(int gameID, int round, ArrayList<Die> dice) {
		try {
			for (Die die : dice) {
				PreparedStatement stmt = con.prepareStatement("UPDATE gameDie SET eyes = ?, round = ?, roundtrack = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?;");
				stmt.setInt(1, die.getDieValue());
				stmt.setInt(2, die.getRound());
				stmt.setInt(3, round);
				stmt.setInt(4, gameID);
				stmt.setInt(5, die.getDieId());
				stmt.setString(6, die.getDieColor().getDatabaseName());
				stmt.executeUpdate();
				con.commit();
				stmt.close();
			}
		} catch (SQLException e) {
			System.err.println("The rollback failed: Please check the Database!");
		}
	}

	private void insertDice() {

	}

	ArrayList<Die> getGameDice(int gameID) {
		//TODO Tweak to let this return no round dice and no placed dice
		return selectDie("Select * from gameDie WHERE idgame = " + gameID);
	}

	Round[] getRoundTrack(int gameID) {
		return selectTrackDice("SELECT * FROM gameDie WHERE idgame = " + gameID + " AND roundtrack IS NOT NULL;");
	}

	void updateDiceRoll(int gameID, ArrayList<Die> dice) {
		updateDice(gameID, dice);
	}

	void updateDiceRound(int gameID, int round, ArrayList<Die> dice) {
		updateRound(gameID, round, dice);
	}
}
