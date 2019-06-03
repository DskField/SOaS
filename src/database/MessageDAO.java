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

class MessageDAO {
	private Connection con;

	public MessageDAO(Connection connection) {
		con = connection;
	}
	
	/**
	 * 
	 * @param players - list of players who's messages will be fetched form the database
	 * @return returns an ArrayList of Message containing all the messages of the the specified players
	 * in order of time
	 */
	ArrayList<Message> getALLMessages(ArrayList<Player> players) {
		return selectMessage("SELECT * FROM chatline ORDER BY time ASC", players);
	}

	/**
	 * 
	 * @param players - list of players who's messages will be fetched form the database
	 * @param time - all messages later than this timestamp will be fetched from the database
	 * @return returns an ArrayList of Message containing all the messages of the the specified players
	 * in order of time
	 */
	ArrayList<Message> updateChat(ArrayList<Player> players, Timestamp time) {
		String s = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(time);
		return selectMessage("SELECT * FROM chatline WHERE time > '" + s + "' ORDER BY time ASC", players);
	}

	/**
	 * 
	 * @param message - The message that wil be inserted into the database.
	 */
	void sendMessage(Message message) {
		insertMessage(message);
	}


	/**
	 * runs a query on the database to get messages from the database for the specified Players.
	 * 
	 * @param query - An SQL query as string that will be run on the database
	 * @param players - list of players in the current game
	 * @return ArrayList<Message> list of messages
	 */
	private ArrayList<Message> selectMessage(String query, ArrayList<Player> players) {
		ArrayList<Message> results = new ArrayList<Message>();
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet dbResultSet = stmt.executeQuery();

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

			con.commit();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("MessageDAO (selectMessage) --> " + e.getMessage());

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
			System.err.println("MessageDAO (insertMessage #1) --> " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("MessageDAO (insertMessage #2) --> The rollback failed: Please check the Database!");
			}
		}
	}
}