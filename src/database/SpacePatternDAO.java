package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
			System.err.println("SpacePatternDAO (selectSpacePattern) --> " + e.getMessage());
		}
		return result;
	}

	//Inserts a pattern into the database
	void insertPattern(PatternCard patternCard) {
		try {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					PreparedStatement stmt = con.prepareStatement("INSERT INTO patterncardfield VALUES (" + patternCard.getPatternCardId() + ",?,?,?,?)");
					stmt.setInt(1, patternCard.getSpace(x, y).getXCor());
					stmt.setInt(2, patternCard.getSpace(x, y).getYCor());
					if( patternCard.getSpace(x, y).getColor().getDatabaseName().equals("")) {//Database wants null, this is to insert null if there is no color
						stmt.setNull(3, Types.VARCHAR);
					}else {
						stmt.setString(3, patternCard.getSpace(x, y).getColor().getDatabaseName());

					}
					if( patternCard.getSpace(x, y).getValue() ==0) {//Database wants null, this is to insert null if value == 0
						stmt.setNull(4,Types.INTEGER);
					}else {
						stmt.setInt(4,patternCard.getSpace(x, y).getValue());
					}
					stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
		} catch (SQLException e1) {
			System.err.println("SpacePatternDAO (insertPattern #1) --> " + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("SpacePatternDAO (insertPattern) --> the rollback failed: Please check the Database!");
			}
		}
	}
}
