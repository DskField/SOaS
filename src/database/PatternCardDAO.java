package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import game.PatternCard;

public class PatternCardDAO extends BaseDAO {

	private List<PatternCard> selectPatternCard(String query) {
		Connection con = super.getConnection();
			List<PatternCard> results = new ArrayList<PatternCard>();
			try {
				Statement stmt = con.createStatement();
				ResultSet dbResultSet = stmt.executeQuery(query);
				while(dbResultSet.next()) {
					int id = dbResultSet.getInt("idpatterncard");
					String name = dbResultSet.getString("name");
					int dif = dbResultSet.getInt("difficulty");
					boolean type = dbResultSet.getBoolean("standard");
					
					SpacePattern[][] patterns = selectSpacePattern(null, 0);
					
					PatternCard pattern = new PatternCard(id, name, dif, patterns, type);
					results.add(pattern);
				}
				stmt.close();
			} catch (SQLException e) {
				System.out.println("PatternCardDAO " + e.getMessage());
			}
			return results;
		}
	
	
	
	

	
	
	List<PatternCard> getAllPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard");
	}
	
	
	List<PatternCard> getStandardPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard <= 24");
	}
	
	
	List<PatternCard> getGeneratedPatternCards(){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard > 24");
	}
	
	
	List<PatternCard> getPatternCard(int i){
		return selectPatternCard("SELECT * FROM patterncard WHERE idpatterncard = " + Integer.toString(i));
	}
}
