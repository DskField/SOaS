package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.Die;
import game.GameColor;
import game.Round;

class DieDAO {
	private Connection con;

	public DieDAO(Connection connection) {
		con = connection;
	}
	
	void insertDice(int idGame) {
		GameColor[] possibleColors = { GameColor.RED, GameColor.GREEN, GameColor.YELLOW, GameColor.PURPLE, GameColor.BLUE };

		try {
			for (GameColor color : possibleColors) {
				for (int i = 1; i <= 18; i++) {
					PreparedStatement stmt = con.prepareStatement("INSERT INTO gamedie VALUES (?, ?, ?, NULL, NULL, NULL, null);");
					stmt.setInt(1, idGame);
					stmt.setInt(2, i);
					stmt.setString(3, color.getDatabaseName());
					stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
		} catch (SQLException e) {
			System.err.println("DieDAO (insertDice) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("DieDAO (insertDice) --> The rollback failed: Please check the Database!");
			}
		}
	}

	ArrayList<Die> getGameDice(int gameID) {
		return selectDie("SELECT * FROM gameDie g LEFT JOIN playerframefield p ON g.idgame = p.idgame AND g.dienumber = p.dienumber AND g.diecolor = p.diecolor WHERE g.idgame = " + gameID
				+ " AND g.roundtrack IS NULL AND p.idgame IS NULL;");
	}

	Round[] getRoundTrack(int gameID) {
		return selectTrackDice("SELECT * FROM gameDie WHERE idgame = " + gameID + " AND roundtrack IS NOT NULL;");
	}

	ArrayList<Die> getTableDice(int gameID, int round) {
		return selectDiceWithEyes("SELECT * FROM playerframefield AS f  " + "RIGHT JOIN gamedie AS g " + " ON f.idgame = g.idgame AND f.dienumber = g.dienumber AND f.diecolor = g.diecolor "
				+ " WHERE f.idgame IS NULL AND f.dienumber IS NULL AND f.diecolor IS NULL AND " + "g.roundtrack IS NULL AND g.idgame = " + gameID + " AND g.round =" + round);
	}

	void updateDiceRoll(int gameID, ArrayList<Die> dice) {
		updateDice(gameID, dice);
	}

	void updateDiceRound(int gameID, int round, ArrayList<Die> dice) {
		updateRound(gameID, round, dice);
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
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("DieDAO (selectDie) --> " + e.getMessage());
		}
		return results;
	}

	private ArrayList<Die> selectDiceWithEyes(String query) {
		ArrayList<Die> results = new ArrayList<Die>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			while (dbResultSet.next()) {
				int number = dbResultSet.getInt("g.dienumber");
				String color = dbResultSet.getString("g.diecolor");
				int round = dbResultSet.getInt("g.round");
				int value = dbResultSet.getInt("g.eyes");
				Die die = new Die(number, color, round, value);
				results.add(die);
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("DieDAO (selectDiceWithEyes) --> " + e.getMessage());
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
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("DieDAO (selectTrackDice) --> " + e.getMessage());
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
			System.err.println("DieDAO (updateDice #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("DieDAO (updateDice #2) --> The rollback failed: Please check the Database!");
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
				stmt.close();
			}
			con.commit();
		} catch (SQLException e) {
			System.err.println("DieDAO (updateRound #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("DieDAO (updateRound #2) --> The rollback failed: Please check the Database!");
			}
		}
	}
}
