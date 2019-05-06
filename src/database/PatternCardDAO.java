package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.PatternCard;

class PatternCardDAO extends BaseDAO {
	Connection con = super.getConnection();
	
	
	public ArrayList<PatternCard> getStandardPatternCards() {
		return selectPatternCard("SELECT * FROM patterncard WHERE standard IS TRUE");
	}
	
	
	public ArrayList<PatternCard> getGeneratedPatternCards() {
		return selectPatternCard("SELECT * FROM patterncard WHERE standard IS FALSE");
	}
	
	
	//Is used to obtain a player's chosen patterncard
	public ArrayList<PatternCard> getplayerPatternCard(int idPlayer) {
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard = (SELECT patterncard_idpatterncard FROM player WHERE idplayer = " + idPlayer + ")");
	}

	
	//Is used to obtain the options given to a player at the start of a game
	public ArrayList<PatternCard> getPlayerOptions(int idPlayer){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard IN (SELECT patterncard_idpatterncard FROM patterncardoption WHERE player_idplayer = " + idPlayer + ")");
	}
	
	
	public void addPatternCard(PatternCard patternCard) {
		insertPatternCard(patternCard);
	}
	
	
	public void addPatternCardOptions(int idPlayer, ArrayList<PatternCard> options) {
		insertPatternCardOptions(idPlayer, options);
	}
	
	
	//Is used to obtain a PatternCard Object from the database, altough it doesn't contain SpacePattern Objects yet
	private ArrayList<PatternCard> selectPatternCard(String query) {
		ArrayList<PatternCard> results = new ArrayList<PatternCard>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
				
			while(dbResultSet.next()) {
				int id = dbResultSet.getInt("idpatterncard");
				String name = dbResultSet.getString("name");
				int dif = dbResultSet.getInt("difficulty");

				PatternCard pattern = new PatternCard(id, name, dif);
				results.add(pattern);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PatternCardDAO: " + e.getMessage());
		}
		return results;
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
		}
		catch (SQLException e1) {
			System.err.println("PatternCardDAO " + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("PatternCardDAO: the rollback failed: Please check the Database!");
			}
		}
	}
	
	
	//Is used to insert patterncard IDs into the patterncardoption table based on the options given to a single player
	private void insertPatternCardOptions(int idPlayer, ArrayList<PatternCard> patternCards) {
		try {
			for(int i = 0; i < patternCards.size(); i++) {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncardoption VALUES (?," + idPlayer + ")");
				stmt.setInt(1, patternCards.get(i).getPatternCardId());
				stmt.executeUpdate();
				stmt.close();
			}
		con.commit();
		}
		catch (SQLException e1) {
			System.err.println("PatternCardDAO " + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("PatternCardDAO: the rollback failed: Please check the Database!");
			}
		}
	}
}
