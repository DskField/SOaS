package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.Die;
import game.GlassWindow;
import game.SpaceGlass;

class SpaceGlassDAO {
	private Connection con;

	public SpaceGlassDAO(Connection connection) {
		con = connection;
	}

	GlassWindow getGlassWindow(int idPlayer) {
		return selectSpaceGlass("SELECT * FROM playerframefield p LEFT JOIN gamedie d ON d.idgame = p.idgame AND d.dienumber = p.dienumber AND d.diecolor = p.diecolor WHERE player_idplayer = " + idPlayer);
	}

	void addGlassWindow(int idPlayer, GlassWindow glassWindow) {
		insertSpaceGlass(idPlayer, glassWindow);
	}

	void updatePlayerFields(int idPlayer, int gameId, GlassWindow glassWindow) {
		updateSpaceGlass(idPlayer, glassWindow, gameId);
	}

	//Is used to obtain a GlassWindow Object containing playerframefields from the database as SpaceGlass Objects
	private GlassWindow selectSpaceGlass(String query) {
		SpaceGlass[][] result = new SpaceGlass[5][4];
		GlassWindow playerFrame = new GlassWindow();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			while (dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				SpaceGlass spaceGlass = new SpaceGlass(x, y);

				int dienumber = dbResultSet.getInt("dienumber");
				int eyes = dbResultSet.getInt("eyes");
				int round = dbResultSet.getInt("round");
				String color = dbResultSet.getString("diecolor");
				Die die = new Die(dienumber, (color != null) ? color : "", round, eyes);
				spaceGlass.setDie(die);

				result[x - 1][y - 1] = spaceGlass;
			}
		} catch (SQLException e) {
			System.err.println("SpaceGlassDAO Select: " + e.getMessage());
		}
		playerFrame.loadSpaces(result);
		return playerFrame;
	}

	//Is used to add an incomplete playerFrameField
	private void insertSpaceGlass(int idPlayer, GlassWindow glassWindow) {
		try {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					PreparedStatement stmt = con.prepareStatement("INSERT INTO playerframefield VALUES (" + idPlayer + ",?,?,NULL,NULL,NULL)");
					stmt.setInt(2, glassWindow.getSpace(x, y).getXCor());
					stmt.setInt(3, glassWindow.getSpace(x, x).getYCor());
					stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
		} catch (SQLException e1) {
			System.err.println("SpaceGlassDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("SpaceGlassDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}

	//Is used to update all the playerFrameFields of a single player in the database
	private void updateSpaceGlass(int idPlayer, GlassWindow glassWindow, int gameId) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE playerframefield SET idgame = ?, dienumber = ?, diecolor = ? WHERE player_playerid = ? AND position_x = ? AND position_y = ? ");
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					stmt.setInt(1, gameId);
					stmt.setInt(2, glassWindow.getSpace(x, y).getDie().getDieId());
					stmt.setString(3, glassWindow.getSpace(x, y).getDie().getDieColor().getDatabaseName());
					stmt.setInt(4, idPlayer);
					stmt.setInt(5, glassWindow.getSpace(x, y).getXCor());
					stmt.setInt(6, glassWindow.getSpace(x, y).getYCor());
					stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
		} catch (SQLException e1) {
			System.err.println("SpaceGlassDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("SpaceGlassDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}
}
