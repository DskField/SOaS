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

		user.setGamesWon(calcWon());
		user.setGamesLost(calcLost());
		user.setTotalOpponents(calcOpponents());
	}

	// calculaters
	private int calcWon() {
		int won = 0;
		for (Lobby lob : lobbies) {
			if (lob.isWon())
				won++;
		}
		return won;
	}

	private int calcLost() {
		int lost = 0;
		for (Lobby lob : lobbies) {
			if (!lob.isWon() && lob.getGameState().equals("uitgespeeld"))
				lost++;
		}
		return lost;
	}

	private int calcOpponents() {
		int opponents = 0;
		for (Lobby lob : lobbies) {
			if (lob.getGameState().equals("uitgespeeld") || lob.getGameState().equals("aan de gang"))
				opponents += lob.getLobbyResponse() - 1;
		}
		return opponents;
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
			if (lob.getGameID() == gameID)
				return lob;
		}
		return null;
	}
}
