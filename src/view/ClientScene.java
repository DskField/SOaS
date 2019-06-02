package view;

import java.util.ArrayList;
import java.util.Map;

import client.Lobby;
import client.User;
import controllers.ClientController;
import game.Player;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ClientScene extends Scene {
	// variables
	private BorderPane rootPane;
	private ClientMenuPane clientmenupane;
	private ClientController clientcontroller;

	// variables for the 3 panes
	private LobbyListPane lobbyListPane;
	private UserListPane userListPane;
	private ChallengeListPane challengeListPane;

	// booleans for update
	private boolean isVisibleLobbyList;
	private boolean isVisibleChallengeList;
	private boolean isVisibleUserList;

	// Magic Numbers
	final private static Color backgroundColor = Color.ALICEBLUE;

	/**
	 * Constructor to create a ClientScene Object
	 * 
	 * @param controller
	 *            - Object containing the reference to ClientController
	 */
	public ClientScene(ClientController controller) {
		super(new BorderPane());
		isVisibleUserList = true;
		isVisibleLobbyList = false;
		isVisibleChallengeList = false;
		this.clientcontroller = controller;
		this.lobbyListPane = new LobbyListPane(this);
		this.userListPane = new UserListPane(this);
		this.challengeListPane = new ChallengeListPane(this);

		// initialize
		clientmenupane = new ClientMenuPane(this);
		rootPane = new BorderPane();
		rootPane.setLeft(clientmenupane);
		rootPane.setCenter(userListPane);

		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
	}

	/**
	 * check which pane is visible
	 */
	public boolean isShownLobbyList() {
		return isVisibleLobbyList;
	}

	public boolean isShownChallengeList() {
		return isVisibleChallengeList;
	}

	public boolean isShownUserList() {
		return isVisibleUserList;
	}

	/**
	 * end checks
	 */

	/**
	 * Handlers
	 */

	// Update the Client data
	public void updateClient() {
		clientcontroller.updateClient();
	}

	/**
	 * Method used to pass the variables to create a game
	 * 
	 * @param users
	 *            - List containing all the players
	 * @param useRandomPatternCards
	 *            - boolean containing if the user wants randompatterncards or not
	 * @return - return if the game was created succesfully
	 */
	public boolean createGame(ArrayList<String> users, boolean useRandomPatternCards) {
		return clientcontroller.createGame(users, useRandomPatternCards);
	}

	/**
	 * Handles when the users switches between panes
	 */
	public void handleUserListButton() {
		isVisibleUserList = true;
		isVisibleLobbyList = false;
		isVisibleChallengeList = false;
		clientcontroller.updateClient();
		userListPane.createLeft();
		rootPane.setCenter(userListPane);
	}

	public void handleLobbyListButton() {
		isVisibleUserList = false;
		isVisibleLobbyList = true;
		isVisibleChallengeList = false;
		clientcontroller.updateClient();
		if (lobbyListPane.getIDGame() != 0)
			lobbyListPane.createStats(lobbyListPane.getIDGame());
		lobbyListPane.createLeft();
		rootPane.setCenter(lobbyListPane);
	}

	public void handleChallengeListButton() {
		isVisibleUserList = false;
		isVisibleLobbyList = false;
		isVisibleChallengeList = true;
		clientcontroller.updateClient();
		challengeListPane.createLeft();
		rootPane.setCenter(challengeListPane);
	}
	/**
	 * end switches
	 */
	
	// handles logOut
	public void logOut() {
		clientcontroller.logOut();
	}

	// let's the user join a game
	public void joinGame(int idGame) {
		clientcontroller.joinGame(idGame);
	}

	// passes the response of the user on a challeneg
	public void handleReaction(boolean accepted, int idGame) {
		clientcontroller.handleReaction(accepted, idGame);
	}

	// Setters
	public void changeLobbyOrder(boolean orderASC) {
		clientcontroller.changeLobbyOrder(orderASC);
	}

	public void changeUserOrder(boolean orderASC) {
		clientcontroller.changeUserOrder(orderASC);
	}

	// getters
	public Lobby getLobby(int gameID) {
		return clientcontroller.getSpecificLobby(gameID);
	}

	public User getUser() {
		return clientcontroller.getUser();
	}

	public User getOpponent(String username) {
		return clientcontroller.getOpponent(username);
	}

	public ArrayList<Integer> getLobbies() {
		return clientcontroller.getLobbies();
	}

	public boolean isGameReady(int idGame) {
		return clientcontroller.isGameReady(idGame);
	}

	public ArrayList<Player> getPlayers(int gameID) {
		return clientcontroller.getPlayers(gameID);
	}

	public ArrayList<ArrayList<String>> getScore(int gameID, ArrayList<Player> player) {
		return clientcontroller.getScore(gameID, player);
	}

	public String getChallengerUsername(int idGame) {
		for (Map.Entry<String, String> entry : clientcontroller.getSpecificChallenge(idGame).getPlayers().entrySet()) {
			if (entry.getValue().equals("uitdager"))
				return entry.getKey();
		}
		System.err.println("ClientScene: geen speler met status 'uitdager' in challenge hashmap");
		return null;
	}

	public int getGameSize(int idGame) {
		return clientcontroller.getSpecificChallenge(idGame).getPlayers().size();
	}

	public String getUsername() {
		return clientcontroller.getUsername();
	}

	public ArrayList<Integer> getChallenges() {
		return clientcontroller.getChallenges();
	}

	public ArrayList<String> getUsers() {
		return clientcontroller.getAllUsernames();
	}

	public ArrayList<Integer> getPlayerLobbies() {
		return clientcontroller.getPlayerLobbies();
	}

	public ArrayList<ArrayList<String>> getScoreboard(int idGame) {
		return clientcontroller.getScoreboard(idGame);
	}

}