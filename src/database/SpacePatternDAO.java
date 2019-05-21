package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.GameColor;
import game.PatternCard;
import game.SpacePattern;

class SpacePatternDAO {
	private Connection con;

	public SpacePatternDAO(Connection connection) {
		con = connection;
	}

	SpacePattern[][] getPattern(int idPatternCard) {
		return selectSpacePattern("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard = " + idPatternCard);
	}

	void addPattern(PatternCard patternCard) {
		insertPattern(patternCard);
	}

	//Is used to obtain a single PatternCard from the database
	private SpacePattern[][] selectSpacePattern(String query) {
		SpacePattern[][] result = new SpacePattern[5][4];

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			while (dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				GameColor color = GameColor.getEnum(dbResultSet.getString("color"));
				int value = dbResultSet.getInt("value");
				result[x - 1][y - 1] = new SpacePattern(x, y, color, value);
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("SpacePatternDAO Select: " + e.getMessage());
		}
		return result;
	}

	//Inserts a pattern into the database
	private void insertPattern(PatternCard patternCard) {
		try {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncardfield VALUES (" + patternCard.getPatternCardId() + ",?,?,?,?)");
					stmt.setInt(2, patternCard.getSpace(x, y).getXCor());
					stmt.setInt(3, patternCard.getSpace(x, y).getYCor());
					stmt.setString(4, patternCard.getSpace(x, y).getColor().getDatabaseName());
					stmt.setInt(5, patternCard.getSpace(x, y).getValue());
					stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
		} catch (SQLException e1) {
			System.err.println("SpacePatternDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("SpacePatternDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}
}
