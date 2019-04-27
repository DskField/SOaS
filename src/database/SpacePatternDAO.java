package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import game.SpacePattern;

public class SpacePatternDAO extends BaseDAO{
	
	private SpacePattern[][] selectSpacePattern(String query) {
		SpacePattern[][] result = new SpacePattern[5][4];
		
		
		try (Connection con = super.getConnection()){
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while(dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				String color = dbResultSet.getString("color");
				color.toUpperCase();
				int value = dbResultSet.getInt("value");
				result[x][y] = new SpacePattern(x, y, color, value);
			}
		}
		catch(Exception e) {
			System.out.println("SpacePatternDAO: " + e.getMessage());
		}
		return result;
	}
	
	
	public SpacePattern[][] getSpacePatterns(int i) {
		return selectSpacePattern("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard = " + Integer.toString(i));
	}
}
