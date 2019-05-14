package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import game.GameColor;
import game.PatternCard;
import game.SpacePattern;

class PatternCardDAO {
	private Connection con;

	public PatternCardDAO(Connection connection) {
		this.con = connection;
	}

	ArrayList<PatternCard> getStandardPatternCards() {
		return selectPatternCard("SELECT * FROM patterncard WHERE standard IS TRUE");
	}

	ArrayList<PatternCard> getGeneratedPatternCards() {
		return selectPatternCard("SELECT * FROM patterncard WHERE standard IS FALSE");
	}

	//Is used to obtain a player's chosen patterncard
	ArrayList<PatternCard> getplayerPatternCard(int idPlayer) {
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard = (SELECT patterncard_idpatterncard FROM player WHERE idplayer = " + idPlayer + ")");
	}

	//Is used to obtain the options given to a player at the start of a game
	ArrayList<PatternCard> getPlayerOptions(int idPlayer) {
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard IS IN (SELECT patterncard_idpatterncard FROM patterncardoption WHERE player_idplayer = " + idPlayer + ")");
	}

	void addPatternCard(PatternCard patternCard) {
		insertPatternCard(patternCard);
	}

	void insertPatternCardOptions(int idPlayer) {
		ArrayList<PatternCard> list = getStandardPatternCards();
		Collections.shuffle(list);

		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncardoption VALUES (?,?), (?,?), (?,?)");
			// Option 1
			stmt.setInt(1, list.get(0).getPatternCardId());
			stmt.setInt(2, idPlayer);

			// Option 2
			stmt.setInt(3, list.get(1).getPatternCardId());
			stmt.setInt(4, idPlayer);

			// Option 3
			stmt.setInt(5, list.get(2).getPatternCardId());
			stmt.setInt(6, idPlayer);

			// Option 4
			stmt.setInt(7, list.get(3).getPatternCardId());
			stmt.setInt(8, idPlayer);

			stmt.executeUpdate();
			stmt.close();
			con.commit();
		} catch (SQLException e1) {
			System.err.println("PatternCardDAO " + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("PatternCardDAO: the rollback failed: Please check the Database!");
			}
		}
	}

	//Is used to obtain a PatternCard Object from the database, altough it doesn't contain SpacePattern Objects yet
	private ArrayList<PatternCard> selectPatternCard(String query) {
		ArrayList<PatternCard> results = new ArrayList<PatternCard>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();

			while (dbResultSet.next()) {
				int id = dbResultSet.getInt("idpatterncard");
				String name = dbResultSet.getString("name");
				int dif = dbResultSet.getInt("difficulty");

				PatternCard pattern = new PatternCard(id, name, dif);
				pattern.addPattern(selectSpacePatterns(id));
				results.add(pattern);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PatternCardDAO --> selectPatternCard: " + e.getMessage());
		}
		return results;
	}

	private SpacePattern[][] selectSpacePatterns(int cardID) {
		SpacePattern[][] result = new SpacePattern[5][4];
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard = ?;");
			stmt.setInt(1, cardID);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();

			while (dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				GameColor color = GameColor.getEnum((dbResultSet.getString("color") == null) ? " " : dbResultSet.getString("color"));
				int value = dbResultSet.getInt("value");

				SpacePattern spacePattern = new SpacePattern(x - 1, y - 1, color, value);
				result[x - 1][y - 1] = spacePattern;
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PatternCardDAO --> selectSpacePatterns: " + e.getMessage());
		}
		return result;
	}

	//Is used to add a custom PatternCard Object to the database, but only to the patterncard table
	private void insertPatternCard(PatternCard patternCard) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncard VALUES (?,?,?,FALSE)");
			stmt.setInt(1, patternCard.getPatternCardId());
			stmt.setString(2, patternCard.getName());
			stmt.setInt(3, patternCard.getDifficulty());
			stmt.executeUpdate();
			con.commit();
			stmt.close();
		} catch (SQLException e1) {
			System.err.println("PatternCardDAO " + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("PatternCardDAO: the rollback failed: Please check the Database!");
			}
		}
	}
}
