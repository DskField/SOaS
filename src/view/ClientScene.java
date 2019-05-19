package view;

import java.util.ArrayList;

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
		this.challengeListPane = new ChallengeListPane();
		
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
	
	// TODO FIND SOLUTION - TEMPORARY FIX
	
	public ArrayList<Player> getPlayers(int gameID) {
		return clientcontroller.getPlayers(gameID);
	}
	
	public int getScore(int gameID, Player player) {
		return clientcontroller.getScore(gameID, player);
	}

}
