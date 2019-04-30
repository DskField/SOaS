package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.GameColor;
import game.PatternCard;
import game.SpacePattern;

class SpacePatternDAO extends BaseDAO{
	Connection con = super.getConnection();
	
	
	public SpacePattern[][] getSpacePatterns(int i) {
		return selectSpacePattern("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard = " + Integer.toString(i));
	}
	
	
	public void addSpacePattern(int i, SpacePattern field) {
		insertSpacePattern(i,field);
	}
	
	
	public void addPattern(int i, SpacePattern[][] pattern) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 4; y++) {
				insertSpacePattern(i,pattern[x][y]);
			}
		}
	}
	
	
	public void addPattern(PatternCard patternCard) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 4; y++) {
				insertSpacePattern(patternCard.getPatternCardId(), patternCard.getSpace(x, y));
			}
		}
	}
	
	
	private SpacePattern[][] selectSpacePattern(String query) {
		SpacePattern[][] result = new SpacePattern[5][4];
		
		try {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while(dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				GameColor color = GameColor.getEnum(dbResultSet.getString("color"));
				int value = dbResultSet.getInt("value");
				result[x - 1][y - 1] = new SpacePattern(x, y, color, value);
			}
		}
		catch(Exception e) {
			System.out.println("SpacePatternDAO Select: " + e.getMessage());
		}
		return result;
	}
	
	
	private void insertSpacePattern(int i, SpacePattern field) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncardfield VALUES ("+ i +",?,?,?,?)");
			stmt.setInt(2, field.getXCor());
			stmt.setInt(3, field.getYCor());
			stmt.setString(4, GameColor.getDatabaseName(field.getPatternColor()));
			stmt.setInt(5, field.getValue());
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e1) {
			System.err.println("SpacePatternDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("SpacePatternDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}
}
