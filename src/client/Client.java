package client;

import java.util.ArrayList;

import database.PersistenceFacade;
import game.GameColor;

public class Client {

	private String username;

	private ArrayList<Integer> lobbiesGameID;
	
	private Lobby lobby;
	private Challenge challenge;
	private User user;
	private User opponent;
	private ArrayList<Integer> challenges;
	private PersistenceFacade persistencefacade;

	public Client(String username, PersistenceFacade persistencefacade) {
		this.username = username;
		this.persistencefacade = persistencefacade;
		this.user = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username) : new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
		this.challenges = persistencefacade.getChallenges(username);
		this.lobbiesGameID = persistencefacade.getAllLobbies(user.getUsername());
	}

	// Update
	public void updateChallenge() {
		this.challenges = persistencefacade.getChallenges(user.getUsername());	
	}

	public void updateLobby() {
		this.lobbiesGameID = persistencefacade.getAllLobbies(user.getUsername());
	}

	public void updateUser() {
		this.user = persistencefacade.getUser(user.getUsername()) != null ? persistencefacade.getUser(user.getUsername())
				: new User(user.getUsername(), 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
	}

	public User getUser() {
		return user;
	}
	
	public ArrayList<Integer> getAllLobbies() {
		return lobbiesGameID;
	}

	public ArrayList<Integer> getChallenges() {
		return challenges;
	}

	public String getUsername() {
		return username;
	}

	public User getOpponent(String username) {
		this.opponent = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username)
				: new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
//		opponent.setGamesWon(calcWon());
//		opponent.setGamesLost(calcLost());
//		opponent.setTotalOpponents(calcOpponents());
		return opponent;
	}

	public Lobby getLobby(int gameID) {
		this.lobby = persistencefacade.getLobby(gameID, user.getUsername());
		return lobby;
	}

	public Challenge getChallenge(int gameID) {
		this.challenge = persistencefacade.getChallenge(gameID);
		return challenge;
	}
}