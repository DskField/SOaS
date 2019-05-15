package client;

import java.util.ArrayList;

import database.PersistenceFacade;

public class Client {

	private String username;

	private ArrayList<Lobby> lobbies;
	private User user;
	private User opponent;
	private ArrayList<Challenge> challenges;
	private PersistenceFacade persistencefacade;

	public Client(String username) {
		this.username = username;
		this.persistencefacade = new PersistenceFacade();
		this.lobbies = persistencefacade.getLobbies(username);
		this.user = persistencefacade.getUser(username);
		this.challenges = persistencefacade.getChallenges(username);

		user.setGamesWon(calcWon());
		user.setGamesLost(calcLost());
		user.setTotalOpponents(calcOpponents());
	}

	// Update
	public void updateClient() {
		if (persistencefacade.updateChallenge(username, challenges))
			this.challenges = persistencefacade.getChallenges(username);
		if (persistencefacade.updateUser(username, user))
			this.user = persistencefacade.getUser(username);
		if (persistencefacade.updateLobby(username, lobbies))
			this.lobbies = persistencefacade.getLobbies(username);
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

	public User getOpponent() {
		return opponent;
	}

	public ArrayList<Challenge> getChallenges() {
		return challenges;
	}

	public String getUsername() {
		return username;
	}

	public User getOpponent(String username) {
		this.opponent = persistencefacade.getUser(username);
		opponent.setGamesWon(calcWon());
		opponent.setGamesLost(calcLost());
		opponent.setTotalOpponents(calcOpponents());
		return opponent;
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
