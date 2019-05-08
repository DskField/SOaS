package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import game.Message;
import game.Player;

class MessageDAO extends BaseDAO {
	Connection con = super.getConnection();

	private ArrayList<Message> selectMessage(String query, ArrayList<Player> players) {
		ArrayList<Message> results = new ArrayList<Message>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
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

		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());

		}
		return results;
	}

	private ArrayList<Message> selectMessage(String query, ArrayList<Player> players, Timestamp time) {
		ArrayList<Message> results = new ArrayList<Message>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setTimestamp(1, time);
			ResultSet dbResultSet = stmt.executeQuery();
			con.commit();
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

		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());

		}
		return results;
	}

	/**
	 * This method will take a Message object and insert it into the database table chatline
	 * 
	 * @param message - Message object
	 */
	private void insertMessage(Message message) {
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO chatline VALUES (?,?,?)");
			stmt.setInt(1, message.getPlayerId());
			stmt.setTimestamp(2, message.getTimestamp());
			stmt.setString(3, message.getMessage());
			stmt.executeUpdate();
			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("MessageDAO " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("The rollback failed: Please check the Database!");
			}
		}
	}

	/**
	 * 
	 * @param players - list of players who's messages will be fetched form the database
	 * @return returns an ArrayList of Message containing all the messages of the the specified players
	 * in order of time
	 */
	public ArrayList<Message> getALLMessages(ArrayList<Player> players) {
		return selectMessage("SELECT * FROM chatline ORDER BY time ASC", players);
	}

	/**
	 * 
	 * @param players - list of players who's messages will be fetched form the database
	 * @param time - all messages later than this timestamp will be fetched from the database
	 * @return returns an ArrayList of Message containing all the messages of the the specified players
	 * in order of time
	 */
	public ArrayList<Message> updateChat(ArrayList<Player> players, Timestamp time) {
		String s = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(time);
		return selectMessage("SELECT * FROM chatline WHERE time > '" + s + "' ORDER BY time ASC", players);
	}

	public void sendMessage(Message message) {
		insertMessage(message);
	}

}