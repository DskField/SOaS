package game;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	//variables
	private Player player;
	private String message;
	private Timestamp timestamp;

	/**
	 * Constructor used to create a Message object
	 * 
	 * @param message - String containing the text
	 * @param player - Player who typed the message
	 * @param timestamp - Timestamp from when the message was created
	 */
	public Message(String message, Player player, Timestamp timestamp) {
		this.message = message;
		this.player = player;
		this.timestamp = timestamp;
	}

	/**
	 * returns Timestamp as a string in the format used for the chat.
	 * 
	 * @return String chatTime
	 */
	public String getChatTime() {
		String s = new SimpleDateFormat("kk:mm:ss").format(timestamp);
		return s;
	}

	//getters & setters
	public String getMessage() {
		return message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public int getPlayerId() {
		return player.getPlayerID();
	}

	public String getUserName() {
		return player.getUsername();
	}

}
