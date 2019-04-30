package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.Die;
import game.GameColor;
import game.GlassWindow;
import game.SpaceGlass;


class SpaceGlassDAO extends BaseDAO{
	Connection con = super.getConnection();
	
	
	private SpaceGlass[][] selectSpaceGlass(String query) {
		SpaceGlass[][] result = new SpaceGlass[5][4];
		
		try {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while(dbResultSet.next()) {
				int x = dbResultSet.getInt("position_x");
				int y = dbResultSet.getInt("position_y");
				result[x - 1][y - 1] = new SpaceGlass(x, y);
			}
		}
		catch(Exception e) {
			System.out.println("SpaceGlassDAO Select: " + e.getMessage());
		}
		return result;
	}
	
	
	//Is used to add an incomplete playerFrameField
	private void insertEmptySpaceGlass(int i, SpaceGlass field) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO playerframefield VALUES ("+ i +",?,?)");
			stmt.setInt(2, field.getXCor());
			stmt.setInt(3, field.getYCor());
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e1) {
			System.err.println("SpaceGlassDAO InsertEmpty:" + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("SpaceGlassDAO InsertEmpty: the rollback failed: Please check the Database!");
			}
		}
	}
	
	
	//Is used to add a full field to playerframefield
	private void insertSpaceGlass(int idPlayer, SpaceGlass field, int gameId, Die die) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO playerframefield VALUES ("+ idPlayer +",?,?)");
			stmt.setInt(2, field.getXCor());
			stmt.setInt(3, field.getYCor());
			stmt.setInt(4, gameId);
			stmt.setInt(5, die.getDieId());
			stmt.setString(6, GameColor.getDatabaseName(die.getDieColor()));
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e1) {
			System.err.println("SpaceGlassDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("SpaceGlassDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}
	
	
	private void updateSpaceGlass(int idPlayer, SpaceGlass field, int gameId, Die die) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE playerframefield SET idgame = ?, dienumber = ?, diecolor = ? WHERE player_playerid = ? AND position_x = ? AND position_y = ? ");
			stmt.setInt(4, idPlayer);
			stmt.setInt(5, field.getXCor());
			stmt.setInt(6, field.getYCor());
			stmt.setInt(1, gameId);
			stmt.setInt(2, die.getDieId());
			stmt.setString(3, GameColor.getDatabaseName(die.getDieColor()));
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e1) {
			System.err.println("SpaceGlassDAO Insert:" + e1.getMessage());
			try {
				con.rollback();
			}
			catch(SQLException e2) {
				System.err.println("SpaceGlassDAO Insert: the rollback failed: Please check the Database!");
			}
		}
	}
	
	
	public GlassWindow getGlassWindow(int i) {
		return new GlassWindow(selectSpaceGlass("SELECT * FROM playerframefield WHERE player_idplayer =" + i));
	}
	
	
	public SpaceGlass[][] selectSpaces() {
		return selectSpaceGlass("");
	}
	
	
	public void addGlassWindow(int i,GlassWindow glassWindow) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 4; y++) {
				insertEmptySpaceGlass(i, glassWindow.getSpace(x, y));
			}
		}
	}
	
	
	public void addSpaceGlass(int i, SpaceGlass field) {
		insertEmptySpaceGlass(i, field);
	}
	
	public void updatePlayerField(int idPlayer, SpaceGlass field, int gameId, Die die) {
		updateSpaceGlass(idPlayer, field, gameId, die);
	}
	
	
	public void updatePlayerFields(int idPlayer, int gameId, GlassWindow glassWindow) {
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 4; y++) {
				updateSpaceGlass(idPlayer, glassWindow.getSpace(x, y), gameId, glassWindow.getSpace(x, y).getDie());
			}
		}
	}
}
