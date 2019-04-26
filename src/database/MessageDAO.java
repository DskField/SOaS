package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import game.Message;
import game.Player;

class MessageDAO extends BaseDAO {

	private ArrayList<Message> selectMessage(String query, ArrayList<Player> players) {
		ArrayList<Message> results = new ArrayList<Message>();
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);
			while (dbResultSet.next()) {
				String text = dbResultSet.getString("message");
				int playerId = dbResultSet.getInt("player_idplayer");
				Timestamp timestamp = dbResultSet.getTimestamp("time");
				for (Player player : players) {
					if (player.getPlayerID() == playerId) {
						Message message = new Message(text, player, timestamp);
						results.add(message);
					}
				}
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());
		}
		return results;
	}

	/**
	 * This method will take a Message object and insert it into the database table
	 * chatline
	 * 
	 * @param message
	 *            - Message object
	 */
	private void insertMessage(Message message) {
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			String text = message.getMessage();
			int playerId = message.getPlayer().getPlayerID();
			Timestamp time = message.getTimestamp();
			stmt.executeUpdate("INSERT INTO chatline VALUES ( '" + playerId + "', '" + time + "', '" + text + "' )");
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param players
	 *            - list of players who's messages will be fetched form the database
	 * @return returns an ArrayList of Message containing all the messages of the
	 *         the specified players in order of time
	 */
	public ArrayList<Message> getALLMessages(ArrayList<Player> players) {
		return selectMessage("SELECT * FROM chatline ORDER BY time DESC", players);
	}

	/**
	 * 
	 * @param players
	 *            - list of players who's messages will be fetched form the database
	 * @param time
	 *            - all messages later than this timestamp will be fetched from the
	 *            database
	 * @return returns an ArrayList of Message containing all the messages of the
	 *         the specified players in order of time
	 */
	public ArrayList<Message> updateChat(ArrayList<Player> players, Timestamp time) {
		return selectMessage("SELECT * FROM chatline WHERE time > " + time + "ORDER BY time DESC", players);
	}
	
	public void sendMessage(Message message) {
		insertMessage(message);
	}

}