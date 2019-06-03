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

	int getScore(int idPlayer) {
		int result = 0;
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT score FROM player WHERE idplayer = ?");
			stmt.setInt(1, idPlayer);
			ResultSet dbResultSet = stmt.executeQuery();

			dbResultSet.next();
			result = dbResultSet.getInt("score");

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (getScore) --> " + e.getMessage());
		}
		return result;
	}
	
	void updateScore(int score, int idPlayer) {
		try {

			PreparedStatement stmt = con.prepareStatement("Update player set score = ? WHERE idplayer = ?");
			stmt.setInt(1, score);
			stmt.setInt(2, idPlayer);

			stmt.executeUpdate();
			stmt.close();

			con.commit();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (updateScore) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}
	
	void updatePlayerTurn(Player oldPlayer, Player newPlayer) {
		updatePlayer(oldPlayer, newPlayer);
	}

	void setPlayerPaternCard(int idPatternCard, int idPlayer) {
		insertPlayerPaterncard(idPatternCard, idPlayer);
	}

	ArrayList<Player> getPlayerWithPatternCardButWithoutCurrencyStones(int idGame) {
		return selectPlayer(
				"SELECT * FROM player p WHERE game_idgame = " + idGame + " AND patterncard_idpatterncard IS NOT NULL AND (select idfavortoken from gamefavortoken g WHERE g.idplayer = p.idplayer LIMIT 1) IS NULL");
	}

	ArrayList<Player> getPlayersWithoutPatternCard(int idGame) {
		return selectPlayer("SELECT * FROM player WHERE game_idgame = " + idGame + " AND patterncard_idpatterncard IS NULL");
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

	void insertPlayers(int idGame, ArrayList<String> users) {
		ArrayList<String> colors = selectColors();
		Collections.shuffle(colors);
		try {
			for (int i = 0; i < users.size(); i++) {
				String status = i == 0 ? "uitdager" : "uitgedaagde";

				PreparedStatement stmt = con.prepareStatement("INSERT INTO player VALUES (null, ?, ?, ?, ?, ?, ?, null, null);");
				stmt.setString(1, users.get(i));
				stmt.setInt(2, idGame);
				stmt.setString(3, status);
				stmt.setInt(4, i + 1);
				stmt.setBoolean(5, (i == 0) ? true : false);
				stmt.setString(6, colors.get(i));

				stmt.executeUpdate();
				stmt.close();
			}
			con.commit();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (insertPlayer) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("PlayerDAO (insertPlayer) --> The rollback failed: Please check the Database!");
			}
		}
	}


	void updateStatusUitgespeeld(int idPlayer) {
		try {
			PreparedStatement stmt = con.prepareStatement("Update player set playstatus_playstatus = 'uitgespeeld' WHERE idplayer = ?");
			stmt.setInt(1, idPlayer);

			stmt.executeUpdate();
			stmt.close();

			con.commit();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (updateStatus) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}

	
	private void insertPlayerPaterncard(int idPatternCard, int idPlayer) {
		try {
			PreparedStatement stmt = con.prepareStatement("UPDATE player SET patterncard_idpatterncard = " + idPatternCard + " WHERE idPlayer = " + idPlayer);
			stmt.executeUpdate();

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (insertPlayerPatterncard #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("PlayerDAO (insertPlayerPatterncard #2) --> The rollback failed: Please check the Database!");
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

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (selectPlayer) --> " + e.getMessage());
		}
		return results;
	}

	private ArrayList<String> selectColors() {
		ArrayList<String> results = new ArrayList<String>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM color");
			ResultSet dbResultSet = stmt.executeQuery();

			while (dbResultSet.next()) {
				results.add(dbResultSet.getString("color"));
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (selectColors) --> " + e.getMessage());
		}
		return results;
	}

	private void updatePlayer(Player oldPlayer, Player newPlayer) {
		try {
			PreparedStatement stmtOldPlayer = con.prepareStatement("UPDATE player SET seqnr = ?, isCurrentPlayer = FALSE WHERE idPlayer = ?;");
			stmtOldPlayer.setInt(1, oldPlayer.getSeqnr());
			stmtOldPlayer.setInt(2, oldPlayer.getPlayerID());
			PreparedStatement stmtOldPlayer2 = con.prepareStatement("UPDATE player SET isCurrentPlayer = FALSE WHERE idPlayer = ?;");
			PreparedStatement stmtNewPlayer = con.prepareStatement("UPDATE player SET isCurrentPlayer = TRUE WHERE idPlayer = ?;");
			stmtOldPlayer2.setInt(1, oldPlayer.getPlayerID());
			stmtNewPlayer.setInt(1, newPlayer.getPlayerID());
			stmtOldPlayer.executeUpdate();
			stmtOldPlayer2.executeUpdate();
			stmtNewPlayer.executeUpdate();
			con.commit();
			stmtOldPlayer.close();
			stmtNewPlayer.close();
			stmtOldPlayer2.close();
		} catch (SQLException e) {
			System.err.println("PlayerDAO (updatePlayer #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e2) {
				System.err.println("DieDAO (updatePlayer #2) --> The rollback failed: Please check the Database!");
			}
		}
	}
}
