package client;

import java.util.HashMap;

public class Challenge {

	private int gameID;
	// List of Usernames with their Playstatus
	private HashMap<String, String> players;
	
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
