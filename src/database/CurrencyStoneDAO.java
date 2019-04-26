package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.CurrencyStone;

class CurrencyStoneDAO extends BaseDAO {
	Connection con = super.getConnection();

	private ArrayList<CurrencyStone> selectCurrencyStone(String query) {
		ArrayList<CurrencyStone> results = new ArrayList<CurrencyStone>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
			stmt.close();
			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int stoneID = dbResultSet.getInt("idfavortoken");
				boolean isUsed = dbResultSet.getInt("gametoolcard") == 0 ? false : true;
				int playerID = dbResultSet.getInt("idplayer");
				CurrencyStone currencystone = new CurrencyStone(stoneID, isUsed, playerID);
				results.add(currencystone);
			}

		} catch (SQLException e) {
			System.err.println("CurrencyStoneDAO " + e.getMessage());
			try {
				con.rollback();

			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");

			}

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
