package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class AccountDAO {
	private Connection con;

	public AccountDAO(Connection con) {
		this.con = con;
	}

	private String[] selectUsername(String username) {
		String[] result = new String[2];

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM account WHERE username = ?");
			stmt.setString(1, username);
			ResultSet dbResultSet = stmt.executeQuery();
			if (dbResultSet.next()) {
				result[0] = dbResultSet.getString("username");
				result[1] = dbResultSet.getString("password");
			} else {
				result[0] = "";
				result[1] = "";
			}

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("AccountDAO (selectUsername) --> " + e.getMessage());
		}
		return result;
	}

	private boolean insertAccount(String username, String password) {

		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO account VALUES(?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.executeUpdate();
			con.commit();
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.err.println("AccountDAO (insertAccount) --> " + e.getMessage());
			return false;
		}
	}

	ArrayList<String> selectAllUsername(boolean orderASC) {
		ArrayList<String> result = new ArrayList<>();

		ArrayList<String> allUsernames = new ArrayList<String>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM account");
			ResultSet dbResultSet = stmt.executeQuery();
			while (dbResultSet.next()) {
				allUsernames.add(dbResultSet.getString("username"));
			}
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("AccountDAO (selectAllUsername) --> " + e.getMessage());
		}

		ArrayList<ArrayList<Integer>> comparator = new ArrayList<>();

		// Change from String into Integer to be able to sort
		// Keep the relation between score and player by using numbers
		for (int i = 0; i < allUsernames.size(); i++) {
			// get all finished games
			try {
				PreparedStatement stmtFinishedGames = con
						.prepareStatement("SELECT game_idgame FROM player WHERE username = ? AND playstatus_playstatus = \"uitgespeeld\"");
				stmtFinishedGames.setString(1, allUsernames.get(i));
				ResultSet dbResultSetFinishedGames = stmtFinishedGames.executeQuery();

				int countWins = 0;
				while (dbResultSetFinishedGames.next()) {
					// Wins and Loses
					PreparedStatement stmtWinsAndLoses = con
							.prepareStatement("SELECT username, playstatus_playstatus FROM player WHERE game_idgame = ? ORDER BY score DESC LIMIT 1");
					stmtWinsAndLoses.setInt(1, dbResultSetFinishedGames.getInt("game_idgame"));
					ResultSet dbResultSetWinsAndLoses = stmtWinsAndLoses.executeQuery();
					dbResultSetWinsAndLoses.next();
					if (dbResultSetWinsAndLoses.getString("username").equals(allUsernames.get(i)))
						countWins++;
					stmtWinsAndLoses.close();
				}
				stmtFinishedGames.close();

				comparator.add(new ArrayList<>(Arrays.asList(i, countWins)));
			} catch (SQLException e) {
				System.err.print("AccountDAO: " + e.getMessage());
			}
		}

		// Sorts list on the 2nd value of every ArrayList in ASC order
		Collections.sort(comparator, new Comparator<ArrayList<Integer>>() {
			@Override
			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
				return o1.get(1).compareTo(o2.get(1));
			}
		});

		// connect every score with the player
		for (int c = 0; c < allUsernames.size(); c++) {
			result.add(allUsernames.get(comparator.get(c).get(0)));
		}
		
		// Sort list according to orderASC
		if (orderASC)
			Collections.reverse(result);

		return result;
	}

	ArrayList<String> getAllUsernames(boolean orderASC) {
		return selectAllUsername(orderASC);
	}

	boolean loginCorrect(String username, String password) {
		String[] dbResult = selectUsername(username);
		return username.equals(dbResult[0]) && password.equals(dbResult[1]);
	}

	boolean insertCorrect(String username, String password) {
		return selectUsername(username)[0].equals(username) ? false : insertAccount(username, password);
	}
}
