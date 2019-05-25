package client;

import java.util.ArrayList;

import database.PersistenceFacade;
import game.GameColor;

public class Client {

	private String username;

	private ArrayList<Lobby> lobbies;
	private User user;
	private User opponent;
	private ArrayList<Challenge> challenges;
	private PersistenceFacade persistencefacade;

	public Client(String username, PersistenceFacade persistencefacade) {
		this.username = username;
		this.persistencefacade = persistencefacade;
		this.user = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username) : new User(username, 0, 0, GameColor.EMPTY, 0);
		this.lobbies = persistencefacade.getLobbies(username);
		this.challenges = persistencefacade.getChallenges(username);

		user.setGamesWon(calcWon());
		user.setGamesLost(calcLost());
		user.setTotalOpponents(calcOpponents());
	}

	// Update
	public void updateClient() {
		this.challenges = persistencefacade.getChallenges(username);
		this.lobbies = persistencefacade.getLobbies(username);
		this.user = persistencefacade.getUser(username);
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

	public String getUsername() {
		return username;
	}

	public User getOpponent(String username) {
		this.opponent = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username)
				: new User(username, 0, 0, GameColor.EMPTY, 0);
		opponent.setGamesWon(calcWon());
		opponent.setGamesLost(calcLost());
		opponent.setTotalOpponents(calcOpponents());
		return opponent;
	}

	public Lobby getLobby(int gameID) {
		for (Lobby lob : lobbies) {
			if (lob.getGameID() == gameID)
				return lob;
		}
		System.err.println("Client: specific lobby not found");
		return null;
	}

	public Challenge getChallenge(int gameID) {
		for (Challenge chal : challenges) {
			if (chal.getGameID() == gameID)
				return chal;
		}
		return null;
	}
}