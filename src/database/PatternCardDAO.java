package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.PatternCard;

class PatternCardDAO extends BaseDAO {
	Connection con = super.getConnection();
	
	
	public ArrayList<PatternCard> getAllPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard");
	}
	
	
	public ArrayList<PatternCard> getStandardPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard <= 24");
	}
	
	
	public ArrayList<PatternCard> getGeneratedPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard > 24");
	}
	
	
	public ArrayList<PatternCard> getPatternCard(int i){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard = " + Integer.toString(i));
	}
	
	public void addPatternCard(PatternCard patternCard) {
		insertPatternCard(patternCard);
	}
	
	
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
				boolean type = dbResultSet.getBoolean("standard");

				PatternCard pattern = new PatternCard(id, name, dif, type);
				results.add(pattern);
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("PatternCardDAO: " + e.getMessage());
		}
		return results;
	}
	
	
	private void insertPatternCard(PatternCard patternCard) {
		if(patternCard.getType() == false) {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncard VALUES (?,?,?,false)");
				stmt.setInt(1, patternCard.getPatternCardId());
				stmt.setString(2, patternCard.getName());
				stmt.setInt(3, patternCard.getDifficulty());
				stmt.executeUpdate();
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
		else {
			System.err.println("PatternCardDAO: This is not a generated patterncard!");
		}
	}
}
