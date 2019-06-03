package game;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	/* VARIABLES */
	private Player player;
	private String message;
	private Timestamp timestamp;

	/**
	 * Constructor used to create a {@code Message}
	 * 
	 * @param message - {@code String} containing the text
	 * @param player - {@code Player} who typed the {@code Message}
	 * @param timestamp - {@code Timestamp} from when the {@code Message} was created
	 */
	public Message(String message, Player player, Timestamp timestamp) {
		this.message = message;
		this.player = player;
		this.timestamp = timestamp;
	}

	/**
	 * Returns {@code Timestamp} as a {@code String} in the format used for the chat.
	 * 
	 * @return - {@code String} chat time
	 */
	public String getChatTime() {
		String s = new SimpleDateFormat("kk:mm:ss").format(timestamp);
		return s;
	}

	/* GETTERS AND SETTERS */
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
