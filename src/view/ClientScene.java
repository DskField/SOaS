package view;

import java.util.ArrayList;
import java.util.Map;

import client.Lobby;
import controllers.ClientController;
import game.Player;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ClientScene extends Scene {

	private BorderPane rootPane;
	private ClientMenuPane clientmenupane;
	private ClientController clientcontroller;

	private LobbyListPane lobbyListPane;
	private UserListPane userListPane;
	private ChallengeListPane challengeListPane;

	// Magic Numbers
	final private static Color backgroundColor = Color.ALICEBLUE;

	public ClientScene(ClientController controller) {
		super(new BorderPane());
		this.clientcontroller = controller;
		this.lobbyListPane = new LobbyListPane(clientcontroller.getLobbies(), this);
		this.userListPane = new UserListPane();
		this.challengeListPane = new ChallengeListPane(clientcontroller.getChallenges(), this);

		// initialize
		clientmenupane = new ClientMenuPane(this);
		rootPane = new BorderPane();
		rootPane.setLeft(clientmenupane);
		rootPane.setCenter(lobbyListPane);

		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
	}

	public Lobby getLobby(int gameID) {
		return clientcontroller.getSpecificLobby(gameID);
	}

	public void handleUserListButton() {
		rootPane.setCenter(userListPane);
	}

	public void handleLobbyListButton() {
		rootPane.setCenter(lobbyListPane);
	}

	public void handleChallengeListButton() {
		rootPane.setCenter(challengeListPane);
	}

	public void joinGame(int idGame) {
		clientcontroller.joinGame(idGame);
	}

	public void handleReaction(boolean accepted, int idGame) {
		clientcontroller.handleReaction(accepted, idGame);
	}

	// TODO TOM FIND SOLUTION - TEMPORARY FIX
	public ArrayList<Player> getPlayers(int gameID) {
		return clientcontroller.getPlayers(gameID);
	}

	public ArrayList<ArrayList<String>> getScore(int gameID, ArrayList<Player> player) {
		return clientcontroller.getScore(gameID, player);
	}

	public String getChallengerUsername(int idGame) {
		for (Map.Entry<String, String> entry : clientcontroller.getChallenges().get(idGame).getPlayers().entrySet()) {
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

}