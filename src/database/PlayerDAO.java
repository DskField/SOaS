package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import game.GameColor;
import game.Player;

class PlayerDAO extends BaseDAO {
	Connection con = super.getConnection();

	private ArrayList<Player> selectPlayer(String query) {
		ArrayList<Player> results = new ArrayList<Player>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				// Separated the variables on purpose for clarity
				int playerID = dbResultSet.getInt("idplayer");
				int seqnr = dbResultSet.getInt("seqnr");
				GameColor personalGoalCard = GameColor.getEnum(dbResultSet.getString("private_objectivecard_color"));
				String username = dbResultSet.getString("username");

				Player player = new Player(playerID, seqnr, personalGoalCard, username);
				results.add(player);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO " + e.getMessage());
		}
		return results;
	}

	private boolean isUsed(int idGame) {
		return selectPlayer("SELECT * FROM player WHERE game_idgame = " + idGame).size() != 0;
	}

	private ArrayList<String> getColors() {
		ArrayList<String> results = new ArrayList<String>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM color");
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				results.add(dbResultSet.getString("color"));
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (Colors) " + e.getMessage());
		}
		return results;
	}

	ArrayList<Player> getAllPlayers() {
		return selectPlayer("SELECT * FROM player");
	}

	ArrayList<Player> getAllPlayersInGame(int idGame) {
		return selectPlayer("SELECT * FROM player WHERE game_idgame = " + idGame);
	}

	void insertPlayer(int idGame, ArrayList<String> username) {
		if (!isUsed(idGame)) {
			ArrayList<String> colors = getColors();
			Collections.shuffle(colors);
			try {
				for (int i = 0; i < username.size(); i++) {
					String status = i == 0 ? "uitdager" : "uitgedaagde";

					PreparedStatement stmt = con
							.prepareStatement("INSERT INTO player VALUES (null,?,?,?,null,false,?,null,null)");
					stmt.setString(1, username.get(i));
					stmt.setInt(2, idGame);
					stmt.setString(3, status);
					stmt.setString(4, colors.get(i));
					stmt.executeUpdate();

					stmt.close();
				}
				con.commit();
			} catch (SQLException e) {
				System.err.println("PlayerDAO " + e.getMessage());

				try {
					con.rollback();
				} catch (SQLException e1) {
					System.err.println("The rollback failed: Please check the Database!");
				}
			}
		} else {

		}
	}
}
