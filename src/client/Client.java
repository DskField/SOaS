package client;

import java.util.ArrayList;

import database.PersistenceFacade;
import game.GameColor;

public class Client {

	private ArrayList<Integer> allLobbies;
	private ArrayList<Integer> allPlayerLobbies;
	private boolean orderLobbyASC;
	
	private ArrayList<String> allUsers;
	private boolean orderUserASC;
	
	private Lobby lobby;
	private Challenge challenge;
	private User user;
	private User opponent;
	private ArrayList<Integer> challenges;
	private PersistenceFacade persistencefacade;

	public Client(String username, PersistenceFacade persistencefacade) {
		orderLobbyASC = true;
		orderUserASC = true;
		this.persistencefacade = persistencefacade;
		this.user = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username) : new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
		this.challenges = persistencefacade.getChallenges(username);
		this.allLobbies = persistencefacade.getAllLobbies(orderLobbyASC);
		this.allPlayerLobbies = persistencefacade.getAllPlayerLobbies(username);
		this.allUsers = persistencefacade.getAllUsername(orderUserASC);
	}

	// Update
	public void updateChallenge() {
		this.challenges = persistencefacade.getChallenges(user.getUsername());	
	}

	public void updateLobby() {
		this.allLobbies = persistencefacade.getAllLobbies(orderLobbyASC);
		this.allPlayerLobbies = persistencefacade.getAllPlayerLobbies(user.getUsername());
	}

	public void updateUser() {
		this.user = persistencefacade.getUser(user.getUsername()) != null ? persistencefacade.getUser(user.getUsername())
				: new User(user.getUsername(), 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
	}

	public User getUser() {
		return user;
	}
	
	public ArrayList<Integer> getAllLobbies() {
		return allLobbies;
	}
	
	public ArrayList<Integer> getAllPlayerLobbies() {
		return allPlayerLobbies;
	}

	public ArrayList<Integer> getChallenges() {
		return challenges;
	}

	public User getOpponent(String username) {
		this.opponent = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username)
				: new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
		return opponent;
	}

	public Lobby getLobby(int gameID) {
		this.lobby = persistencefacade.getLobby(gameID);
		return lobby;
	}

	public Challenge getChallenge(int gameID) {
		this.challenge = persistencefacade.getChallenge(gameID);
		return challenge;
	}

	public void changeLobbyOrder(boolean orderASC) {
		this.orderLobbyASC = orderASC;
	}

	public ArrayList<String> getAllUsernames() {
		return allUsers;
	}

	public void changeUserOrder(boolean orderASC) {
		this.allUsers = persistencefacade.getAllUsername(orderASC);
	}
	
}