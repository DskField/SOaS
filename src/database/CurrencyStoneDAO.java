package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.CurrencyStone;

class CurrencyStoneDAO {
	private Connection con;

	public CurrencyStoneDAO(Connection connection) {
		con = connection;
	}

	public void insertCurrencyStones(int idGame) {
		try {
			for (int i = 0; i < 24; i++) {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO gamefavortoken VALUES (?, ?, null, null, null, null);");
				stmt.setInt(1, i);
				stmt.setInt(2, idGame);
				stmt.executeUpdate();
				stmt.close();
			}
			con.commit();
		} catch (SQLException e) {
			System.err.println("CurrencyStoneDAO (insertCurrencyStones) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}

	private ArrayList<CurrencyStone> selectCurrencyStone(String query) {
		ArrayList<CurrencyStone> results = new ArrayList<CurrencyStone>();

		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				int stoneID = dbResultSet.getInt("idfavortoken");
				int playerID = dbResultSet.getInt("idplayer");
				int toolcardID = dbResultSet.getInt("gametoolcard");

				CurrencyStone currencystone = new CurrencyStone(stoneID, playerID, toolcardID);
				results.add(currencystone);
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("CurrencyStoneDAO (selectCurrencyStone) --> " + e.getMessage());
		}
		return results;
	}

	private boolean isUsed(int stoneID, int idGame) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE idfavortoken = " + stoneID + " AND idgame = " + idGame + " AND gametoolcard IS NULL").size() == 0;
	}

	private int stonesLeft(int idGame, int idPlayer) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE (idplayer IS NULL OR idplayer = " + idPlayer + ") AND idgame = " + idGame).size();
	}

	ArrayList<CurrencyStone> getAllStonesInGame(int idGame) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE idgame = " + idGame);
	}

	ArrayList<CurrencyStone> getCurrencyStonesPlayer(int idGame, int playerID) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken WHERE idGame = " + idGame + " AND idplayer = " + playerID);
	}

	ArrayList<CurrencyStone> getCurrencyStonesOnCard(int idToolCard, int idGame) {
		return selectCurrencyStone("SELECT * FROM gamefavortoken gf JOIN gametoolcard gt ON gf.gametoolcard = gt.gametoolcard WHERE gt.idtoolcard = " + idToolCard + "  AND gf.idgame = " + idGame);
	}

	void updateGameFavorTokenUsed(int stoneID, int idGame, int gametoolcard) {
		if (!isUsed(stoneID, idGame)) {

			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE gamefavortoken SET gametoolcard = " + gametoolcard + " WHERE idfavortoken = " + stoneID + " AND idgame = " + idGame);
				stmt.executeUpdate();

				con.commit();
				stmt.close();
			} catch (SQLException e) {
				System.err.println("CurrencyStoneDAO (updateGameFavorTokenUsed #1) --> " + e.getMessage());
				try {
					con.rollback();
				} catch (SQLException e1) {
					System.err.println("CurrencyStoneDAO (updateGameFavorTokenUsed #2) --> The rollback failed: Please check the Database!");
				}
			}

		} else {
			System.err.println("CurrencyStoneDAO (updateGameFavorTokenUsed #3) --> trying to use a stone that is already used");
		}
	}

	void updateGivePlayerCurrencyStones(int idGame, int idPlayer, int ammount) {
		if (getCurrencyStonesPlayer(idGame, idPlayer).size() == 0) {

			System.out.println(stonesLeft(idGame, idPlayer));
			try {
				for (int i = (25 - stonesLeft(idGame, idPlayer)); i < ((25 - stonesLeft(idGame, idPlayer)) + ammount); i++) {
					PreparedStatement stmt = con.prepareStatement("UPDATE gamefavortoken SET idplayer = ? WHERE idfavortoken = ? AND idgame = ?");
					stmt.setInt(1, idPlayer);
					stmt.setInt(2, i);
					stmt.setInt(3, idGame);
					stmt.executeUpdate();
					stmt.close();
				}
				con.commit();
			} catch (SQLException e) {
				System.err.println("CurrencyStoneDAO (updateGivePlayerCurrencyStones #1) --> " + e.getMessage());
				try {
					con.rollback();
				} catch (SQLException e1) {
					System.err.println("CurrencyStoneDAO (updateGivePlayerCurrencyStones #2) -->The rollback has failed: Please check the Database!");
				}
			}
		} else {
			System.err.println("CurrencyStoneDAO (updateGivePlayerCurrencyStones #3) --> the player already has currencystones");
		}
	}
}
