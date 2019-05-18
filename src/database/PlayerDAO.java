package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import game.GameColor;
import game.Player;

class PlayerDAO {
	private Connection con;

	public PlayerDAO(Connection connection) {
		con = connection;
	}
// kevin stuff
	private void insertPlayerPaterncard(int idPatternCard, int idPlayer) {
		try {
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE player SET patterncard_idpatterncard = " + idPatternCard + " WHERE idPlayer = " + idPlayer);
			stmt.executeUpdate();

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}

	}

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

	private void updatePlayer(Player oldPlayer, Player newPlayer) {
		try {
			PreparedStatement stmtOldPlayer = con
					.prepareStatement("UPDATE player SET seqnr = ?, isCurrentPlayer = FALSE WHERE idPlayer = ?;");
			stmtOldPlayer.setInt(1, oldPlayer.getSeqnr());
			stmtOldPlayer.setInt(2, oldPlayer.getPlayerID());
			PreparedStatement stmtNewPlayer = con
					.prepareStatement("UPDATE player SET isCurrentPlayer = TRUE WHERE idPlayer = ?;");
			stmtNewPlayer.setInt(1, newPlayer.getPlayerID());
			stmtOldPlayer.executeUpdate();
			stmtNewPlayer.executeUpdate();
			con.commit();
			stmtOldPlayer.close();
			stmtNewPlayer.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO " + e.getMessage());
		}
	}

	void updatePlayerTurn(Player oldPlayer, Player newPlayer) {
		updatePlayer(oldPlayer, newPlayer);
	}
	//kevin stuff
	void setPlayerPaternCard(int idPatternCard, int idPlayer) {
		System.out.println("5");
		insertPlayerPaterncard(idPatternCard, idPlayer);
	}

	ArrayList<Player> getAllPlayers() {
		return selectPlayer("SELECT * FROM player");
	}

	Player getCurrentPlayer(int idGame) {
		return selectPlayer("SELECT * FROM player WHERE isCurrentPlayer = TRUE AND game_idgame = " + idGame).get(0);
	}

	ArrayList<Player> getAllPlayersInGame(int idGame) {
		return selectPlayer("SELECT * FROM player WHERE game_idgame = " + idGame + " ORDER BY idplayer ASC");
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
