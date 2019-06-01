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

	// Is used to obtain a player's chosen patterncard
	PatternCard getplayerPatternCard(int idPlayer) {
		ArrayList<PatternCard> cards = selectPatternCard(
				"SELECT * FROM patterncard WHERE idpatterncard = (SELECT patterncard_idpatterncard FROM player WHERE idplayer = "
						+ idPlayer + ")");
		if (cards.isEmpty()) {
			return null;
		} else {
			return cards.get(0);
		}
	}

	// Is used to obtain the options given to a player at the start of a game
	ArrayList<PatternCard> getPlayerOptions(int idPlayer) {
		return selectPatternCard(
				"SELECT * FROM patterncard WHERE idpatterncard IN (SELECT patterncard_idpatterncard FROM patterncardoption WHERE player_idplayer = "
						+ idPlayer + ")");
	}

	/**
	 * add the pattencard option for players
	 * @param idPlayer the player for who this card is
	 * @param patternCards the already used patterncards or if you use generated patterncards the cards to be used in the game
	 * @param generatedCards true if using generated cards
	 */
	void insertPatternCardOptions(int idPlayer, ArrayList<PatternCard> patternCards, boolean generatedCards) {
		ArrayList<PatternCard>list = new ArrayList<>();
		if(generatedCards) {
			list = patternCards;
		}else {
			list = getStandardPatternCards();
			for (int i = 0; i < list.size(); i++) {// checks if that patterncard is already used and removes that one from 
				for (PatternCard patternCard : patternCards) {
					if (list.get(i).getPatternCardId() == patternCard.getPatternCardId()) {
						list.remove(i);
						i--;
					}
				}
			}

			Collections.shuffle(list);
		}


		try {
			PreparedStatement stmt = con
					.prepareStatement("INSERT INTO patterncardoption VALUES (?,?), (?,?), (?,?), (?,?)");
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
			System.err.println("PatternCardDAO (insertPatternCardOptions #1) --> " + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println(
						"PatternCardDAO (insertPatternCardOptions #2) --> the rollback failed: Please check the Database!");
			}
		}
	}

	// Is used to obtain a PatternCard Object from the database, altough it doesn't
	// contain SpacePattern Objects yet
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
			System.err.println("PatternCardDAO (selectPatternCard) --> " + e.getMessage());
		}
		return results;
	}

	private SpacePattern[][] selectSpacePatterns(int cardID) {
		SpacePattern[][] result = new SpacePattern[5][4];
		try {
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard = ?;");
			stmt.setInt(1, cardID);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();

			while (dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				GameColor color = GameColor
						.getEnum((dbResultSet.getString("color") == null) ? " " : dbResultSet.getString("color"));
				int value = dbResultSet.getInt("value");

				SpacePattern spacePattern = new SpacePattern(x - 1, y - 1, color, value);
				result[x - 1][y - 1] = spacePattern;
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PatternCardDAO (selectSpacePatterns) --> " + e.getMessage());
		}
		return result;
	}


	/**
	 * THis method adds the information to the database a generated patterncard to the database
	 * @param patternCard the generated patterncard
	 * @return the ID of the patterncard
	 */
	int insertPatternCard(PatternCard patternCard) {
		int last = 0;
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncard(name, difficulty, standard) VALUES (?,?,FALSE)");
			stmt.setString(1, patternCard.getName());
			stmt.setInt(2, patternCard.getDifficulty());
			stmt.executeUpdate();
			con.commit();
			stmt.close();
			PreparedStatement stmt2 = con.prepareStatement("SELECT LAST_INSERT_ID() last;");
			ResultSet dbResultSet = stmt2.executeQuery();
			
			while (dbResultSet.next()) {
				last = dbResultSet.getInt("last");
			}
			con.commit();
			stmt2.close();
		} catch (SQLException e1) {
			System.err.println("PatternCardDAO (insertPatternCard #1) --> " + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println(
						"PatternCardDAO (insertPatternCard #2) --> the rollback failed: Please check the Database!");
			}
		}
		return last;
	}
}
