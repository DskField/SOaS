package client;

import java.util.ArrayList;

import controllers.PatternCardGenerator;
import database.PersistenceFacade;
import game.CollectiveGoalCard;
import game.GameColor;
import game.GlassWindow;
import game.Player;

public class Client {
	/* VARIABLES */
	// LobbyList
	/**
	 * {@code ArrayList} containing all the game_idgame of lobbies in the database
	 */
	private ArrayList<Integer> allLobbies;
	/**
	 * {@code ArrayList} containing all the game_idgame of lobbies the user is part of
	 */
	private ArrayList<Integer> allPlayerLobbies;
	/**
	 * {@code boolean} containing how the List in the view has to be ordered
	 */
	private boolean orderLobbyASC;

	//UserList
	/**
	 * {@code ArrayList} containing all the usersnames in the database
	 */
	private ArrayList<String> allUsers;
	/**
	 * {@code boolean} containing how the list in the view has to be ordered
	 */
	private boolean orderUserASC;

	//ChallengeList
	/**
	 * {@code ArrayList} containing all the game_idgame of unanswered challenges of the user
	 */
	private ArrayList<Integer> challenges;

	private Lobby lobby;
	private Challenge challenge;
	private User user;
	private User opponent;
	private PersistenceFacade persistencefacade;

	/**
	 * Constructor used to create a {@code Client}
	 * 
	 * @param persistencefacade - Object containing all the methods to get data from the database
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
		this.user = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username) : new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
		this.challenges = persistencefacade.getChallenges(username);
		this.allLobbies = persistencefacade.getAllLobbies(orderLobbyASC);
		this.allPlayerLobbies = persistencefacade.getAllPlayerLobbies(username);
		this.allUsers = persistencefacade.getAllUsername(orderUserASC);
	}

	/**
	 * Method to update the challenges
	 */
	public void updateChallenge() {
		this.challenges = persistencefacade.getChallenges(user.getUsername());
	}

	/**
	 * Method to update the lobbies
	 */
	public void updateLobby() {
		this.allLobbies = persistencefacade.getAllLobbies(orderLobbyASC);
		this.allPlayerLobbies = persistencefacade.getAllPlayerLobbies(user.getUsername());
	}

	/**
	 * Method to update the user
	 */
	public void updateUser() {
		this.user = persistencefacade.getUser(user.getUsername()) != null ? persistencefacade.getUser(user.getUsername()) : new User(user.getUsername(), 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
	}

	/**
	 * Method used to create a new game in the database
	 * 
	 * @param users - {@code ArrayList} containing all the users for the new game
	 * @param useRandomPatternCards - {@code boolean} containing whether to use random patterncards or
	 * regular ones
	 * @return - If one of the players still has an open invite from the user, it will not create a new
	 * game and return false
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
	 * @param accepted - {@code boolean} containing if the player accepted or declined the invite
	 * @param idGame - {@code int} containing the game id that the player reacted to
	 */
	public void handleReaction(boolean accepted, int idGame) {
		persistencefacade.updatePlayerStatus(user.getUsername(), accepted, idGame);
	}

	/* GETTERS AND SETTERS */
	public void changeLobbyOrder(boolean orderASC) {
		this.orderLobbyASC = orderASC;
	}

	public void changeUserOrder(boolean orderASC) {
		this.allUsers = persistencefacade.getAllUsername(orderASC);
	}

	public User getOpponent(String username) {
		this.opponent = persistencefacade.getUser(username) != null ? persistencefacade.getUser(username) : new User(username, 0, 0, GameColor.EMPTY, 0, 0, 0, 0);
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