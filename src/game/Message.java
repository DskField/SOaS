package game;

import java.sql.Timestamp;

public class Message {
	//variables
	private Player player;
	private String message;
	public Timestamp timestamp;
	/**
	 * 
	 * @param message - the text message
	 * @param player - the player who typed the message
	 */
	public Message(String message, Player player, Timestamp timestamp) {
		this.message = message;
		this.player = player;
		this.timestamp = timestamp;
	}
	//getters & setters
	public String getMessage() {
		return message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public Player getPlayer() {
		return player;
	}
	

}
