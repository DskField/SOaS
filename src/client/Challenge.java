package client;

import java.util.HashMap;

public class Challenge {
	// variables
	private int gameID;
	private HashMap<String, String> players;

	/**
	 * Constructor used to create a Challenge object
	 * 
	 * @param gameID
	 *            - int containing the game_idgame in the database
	 * @param players
	 *            - Hashmap containing a list over usernames with their playstatus
	 */
	public Challenge(int gameID, HashMap<String, String> players) {
		this.gameID = gameID;
		this.players = players;
	}

	// GETTERS
	public int getGameID() {
		return gameID;
	}

	public HashMap<String, String> getPlayers() {
		return players;
	}
}