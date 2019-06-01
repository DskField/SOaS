package client;

import java.util.ArrayList;

import controllers.PatternCardGenerator;
import database.PersistenceFacade;
import game.CollectiveGoalCard;
import game.GameColor;
import game.GlassWindow;
import game.Player;

public class Client {
	/**
	 * variables for lobbylist
	 * 
	 * @param allLobbies
	 *            - List containing all the game_idgame of lobbies in the database
	 * @param allPlayerLobbies
	 *            - List containing all the game_idgame of lobbies the user is part of
	 * @param orderLobbyASC
	 *            - boolean containing how the List in the view has to be ordered
	 */
	private ArrayList<Integer> allLobbies;
	private ArrayList<Integer> allPlayerLobbies;
	private boolean orderLobbyASC;

	/**
	 * variables for userslist
	 * 
	 * @param allUsers
	 *            - List containing all the usersnames in the database
	 * @param orderUserASC
	 *            - boolean containing how the list in the view has to be ordered
	 */
	private ArrayList<String> allUsers;
	private boolean orderUserASC;

	/**
	 * variables for challengelist
	 * 
	 * @param challenges
	 *            - List containing all the game_idgame of unanswered challenges of the user
	 */
	private ArrayList<Integer> challenges;

	// variables
	private Lobby lobby;
	private Challenge challenge;
	private User user;
	private User opponent;
	private PersistenceFacade persistencefacade;

	/**
	 * Constructor used to create a Client object
	 * 
	 * @param persistencefacade
	 *            - Object containing all the methods to get data from the database
	 */
	public Client(PersistenceFacade persistencefacade) {
		orderLobbyASC = true;
		orderUserASC = true;
		this.persistencefacade = persistencefacade;
		
	}

	/**
	 * Insert all data after succesful login
	 * 
	 * @param username - String containing the username which is giving at login
	 */
	public void insertUserInClient(String username) {
		this.user = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username)
				: new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
		this.challenges = persistencefacade.getChallenges(username);
		this.allLobbies = persistencefacade.getAllLobbies(orderLobbyASC);
		this.allPlayerLobbies = persistencefacade.getAllPlayerLobbies(username);
		this.allUsers = persistencefacade.getAllUsername(orderUserASC);
	}
	
	/**
	 * Methods to update the data
	 */
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

	/**
	 * End update methods
	 */

	/**
	 * Method used to create a new game in the database
	 * 
	 * @param users
	 *            - List containing all the users for the new game
	 * @param useRandomPatternCards
	 *            - boolean containing whether to use random pattern cards or regular ones
	 * @return - If one of the players still has an open invite from the user, it will not create a new game and return false
	 */
	public boolean createGame(ArrayList<String> users, boolean useRandomPatternCards) {
		for (String u : users) {
			if (persistencefacade.hasOpenInvite(user.getUsername(), u))
				return false;
		}
		persistencefacade.createGame(users, useRandomPatternCards, new PatternCardGenerator());
		return true;
	}

	/**
	 * Method used to handle a reaction on an invite
	 * 
	 * @param accepted
	 *            - boolean containing if the player accepted or declined the invite
	 * @param idGame
	 *            - int containing the game_idgame that the player reacted to
	 */
	public void handleReaction(boolean accepted, int idGame) {
		persistencefacade.updatePlayerStatus(user.getUsername(), accepted, idGame);
	}

	// Setters
	public void changeLobbyOrder(boolean orderASC) {
		this.orderLobbyASC = orderASC;
	}

	public void changeUserOrder(boolean orderASC) {
		this.allUsers = persistencefacade.getAllUsername(orderASC);
	}

	// Getters which will update the data before returning
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
	
	public ArrayList<ArrayList<String>> getScoreboard(int idGame) {
		return persistencefacade.getScoreboard(idGame);
	}

	public ArrayList<Player> getPlayers(int idGame) {
		return persistencefacade.getAllPlayersInGame(idGame);
	}
	
	// Regular Getters
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

	public ArrayList<String> getAllUsernames() {
		return allUsers;
	}

	public boolean isGameReady(int idGame) {
		return persistencefacade.isGameReady(idGame);
	}
	
	public boolean loginCorrect(String username, String password) {
		return persistencefacade.loginCorrect(username, password);
	}
	
	public boolean insertCorrect(String username, String password) {
		return persistencefacade.insertCorrect(username, password);
	}
	
	public GlassWindow getPlayerGlasswindow(int idPlayer) {
		return persistencefacade.getGlassWindow(idPlayer);
	}
	
	public ArrayList<CollectiveGoalCard> getSharedCollectiveGoalCards(int gameID) {
		return persistencefacade.getSharedCollectiveGoalCards(gameID);
	}
}