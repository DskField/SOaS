package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import game.PatternCard;
import game.SpacePattern;

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
				con.close();
			} catch (SQLException e) {
				System.out.println("PatternCardDAO " + e.getMessage());
			}
			return results;
		}
	
	
	
	

	
	private SpacePattern[][] selectSpacePattern(String query, int id) {
		SpacePattern[][] pattern = new SpacePattern[5][4];
		//	Need to ask regarding dbConnection
		
		//leftovers from method previously written to get the pattern for a patterncard
		
		try {
//			while(fields.next()) {
//				int id = fields.getInt("patterncard_idpatterncard");
//				int x = fields.getInt("position_x");
//				int y = fields.getInt("position_y");
//				String color = fields.getString("color");
//				color.toUpperCase();
//				int value = fields.getInt("value");
//				pattern[x][y] = new SpacePattern(x, y, id, color, value);
////					Query: SELECT position_x, position_y, patterncard_idpatterncard, color, value FROM patterncardfield WHERE ...		
//			}
		}
		catch(Exception e) {
			System.out.println("PatternCardDAO: " + e.getMessage());
		}
		
		
		return pattern;
	}
	
}
