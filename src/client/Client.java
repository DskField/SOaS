package client;

import java.util.ArrayList;

public class Client {

	private ArrayList<Lobby> lobbies;
	private User user;
	private ArrayList<Challenge> challenges;
	
	public Client(ArrayList<Lobby> lobbies, User user, ArrayList<Challenge> challenges) {
		this.lobbies = lobbies;
		this.user = user;
		this.challenges = challenges;
	}
	
	// GETTERS
	public ArrayList<Lobby> getLobbies() {
		return lobbies;
	}
	
	public User getUser() {
		return user;
	}
	
	public ArrayList<Challenge> getChallenges() {
		return challenges;
	}
	
	// TODO check if needed
	public Lobby getLobby(int gameID) {
		for (Lobby lob : lobbies) {
			if (lob.getGameID() == gameID) return lob;
		}
		return null;
	}
}
