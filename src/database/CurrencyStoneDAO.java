package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.CollectiveGoalCard;
import game.CurrencyStone;

class CurrencyStoneDAO extends BaseDAO {
	private ArrayList<CurrencyStone> selectCurrencyStone(String query) {
		ArrayList<CurrencyStone> results = new ArrayList<CurrencyStone>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int stoneID = dbResultSet.getInt("idfavortoken");
				boolean isUsed = dbResultSet.getInt("gametoolcard") == 0 ? false : true;
				int playerID = dbResultSet.getInt("idplayer");
				CurrencyStone currencystone = new CurrencyStone(stoneID, isUsed, playerID);
				results.add(currencystone);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("CollectiveGoalCardDAO: " + e.getMessage());
		}
		return results;
	}
	
	public ArrayList<CurrencyStone> getCurrencyStones(int playerID) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE idplayer = " + Integer.toString(playerID));
	}
	
	public ArrayList<CurrencyStone> getCurrencyStonesOnCard(int gametoolcard) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE gametoolcard = " + Integer.toString(gametoolcard));
	}
}
