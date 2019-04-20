package game;

import java.sql.Timestamp;

public class Message {
	//variables
	private Player player;
	private String message;
	private Timestamp timestamp;
	/**
	 * 
	 * @param message - the text message
	 * @param player - the player who typed the message
	 */
	public Message(String message, Player player) {
		this.message = message;
		this.player = player;
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public String getMessage() {
		return message;
	}
	/**
	 * 
	 * @return returns the timestamp as a string in the following format hh:mm
	 */
	public String getTimestamp() {
		return String.format("%1$TH:%1$TM", timestamp );
	}
	
	public Player getPlayer() {
		return player;
	}
	

}
