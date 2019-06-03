package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import client.Challenge;
import client.Client;
import client.Lobby;
import client.User;
import database.PersistenceFacade;
import game.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import view.ClientScene;
import view.LoginPane;

public class ClientController {
	/* VARIABLES */
	private Client client;
	private GameController gamecontroller;
	private MainApplication mainapplication;
	private PersistenceFacade persistencefacade;
	private ClientScene clientscene;

	private AnimationTimerExt timer;

	/**
	 * Constructor used to create a {@code ClientController}
	 * 
	 * @param mainapplication - used to change the scene in the {@code MainApplication}
	 */
	public ClientController(MainApplication mainapplication) {
		this.persistencefacade = new PersistenceFacade();
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication, persistencefacade, this);
		client = new Client(persistencefacade);
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	/**
	 * Method that can be called from {@code GameController} to swap back to the {@code ClientScene}
	 */
	public void returnToClient() {
		timer.start();
		mainapplication.setScene(clientscene);
	}

	/* GETTERS AND SETTERS */
	public ArrayList<Integer> getChallenges() {
		return client.getChallenges();
	}

	public ArrayList<Integer> getLobbies() {
		return client.getAllLobbies();
	}

	public ArrayList<Integer> getPlayerLobbies() {
		return client.getAllPlayerLobbies();
	}

	public Challenge getSpecificChallenge(int gameID) {
		return client.getChallenge(gameID);
	}

	public Lobby getSpecificLobby(int gameID) {
		return client.getLobby(gameID);
	}

	public void joinGame(int idGame) {
		timer.stop();
		gamecontroller.joinGame(idGame, client.getUser());
	}

	public void handleReaction(boolean accepted, int idGame) {
		client.handleReaction(accepted, idGame);
	}

	public User getUser() {
		return client.getUser();
	}

	public User getOpponent(String username) {
		return client.getOpponent(username);
	}

	public boolean createGame(ArrayList<String> users, boolean useRandomPatternCards) {
		return client.createGame(users, useRandomPatternCards);
	}

	public String getUsername() {
		return client.getUser().getUsername();
	}

	public ArrayList<Player> getPlayers(int idGame) {
		return client.getPlayers(idGame);
	}

	/**
	 * 
	 * @param gameID - int containing the gameID
	 * @param players - List of all the players in the game
	 * @return - return a two demensional array containing an array of the username and score for every
	 * index
	 */
	public ArrayList<ArrayList<String>> getScore(int gameID, ArrayList<Player> players) {
		ScoreHandler scorehandler = new ScoreHandler(client.getSharedCollectiveGoalCards(gameID));
		ArrayList<ArrayList<Integer>> comparator = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();

		// Change from String into Integer to be able to sort
		// Keep the relation between score and player by using numbers
		for (int i = 0; i < players.size(); i++) {
			players.get(i).loadGlassWindow(client.getPlayerGlasswindow(players.get(i).getPlayerID()));
			ArrayList<Integer> combo = new ArrayList<>();

			combo.add(i);
			combo.add(scorehandler.getScore(players.get(i), false));
			comparator.add(combo);
		}

		// Sorts list on the 2nd value of every ArrayList in ASC order
		Collections.sort(comparator, new Comparator<ArrayList<Integer>>() {
			@Override
			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
				return o1.get(1).compareTo(o2.get(1));
			}
		});
		// Reverse the list in DESC order
		Collections.reverse(comparator);

		// connect every score with the player
		for (int c = 0; c < players.size(); c++) {
			result.add(new ArrayList<String>(Arrays.asList(players.get(comparator.get(c).get(0)).getUsername(), String.valueOf(comparator.get(c).get(1)))));
		}
		return result;
	}

	public void logOut() {
		timer.stop();
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	public ArrayList<String> getAllUsernames() {
		return client.getAllUsernames();
	}

	public boolean isGameReady(int idGame) {
		return client.isGameReady(idGame);
	}

	public ArrayList<ArrayList<String>> getScoreboard(int idGame) {
		return client.getScoreboard(idGame);
	}

	public void changeLobbyOrder(boolean orderASC) {
		client.changeLobbyOrder(orderASC);
	}

	public void changeUserOrder(boolean orderASC) {
		client.changeUserOrder(orderASC);
	}

	// Update with Timer
	public void updateClient() {
		// 3 to 6 seconds
		if (clientscene.isShownChallengeList()) {
			client.updateChallenge();
		}
		if (clientscene.isShownLobbyList()) {
			client.updateLobby();
		}
		if (clientscene.isShownUserList()) {
			client.updateUser();
		}
	}

	public abstract class AnimationTimerExt extends AnimationTimer {
		private long sleepNs = 0;
		long prevTime = 0;

		public AnimationTimerExt(long sleepMs) {
			this.sleepNs = sleepMs * 1_000_000;
		}

		@Override
		public void handle(long now) {
			// some delay
			if ((now - prevTime) < sleepNs) {
				return;
			}
			prevTime = now;
			doAction();
		}

		public abstract void doAction();
	}

	/**
	 * Only after a successful login everything will be created
	 * 
	 * @param username - {@code String} containing the username input
	 * @param password - {@code String} containing the password input
	 * @return - {@code boolean} whether the login was successful or not
	 * 
	 */
	public boolean handleLogin(String username, String password) {
		if (!checkInformation(username, password)) {
			return false;
		}

		if (client.loginCorrect(username, password)) {
			client.insertUserInClient(username);
			this.clientscene = new ClientScene(this);
			mainapplication.setScene(clientscene);

			createTimer();
		}
		return client.loginCorrect(username, password);
	}

	/**
	 * Register the new user only if the username hasn't been used and if the username and password pass
	 * the criteria
	 * 
	 * @param username - {@code String} containing the username input
	 * @param password - {@code String} containing the password input
	 * @return - {@code boolean} whether the register was succesful or not
	 */
	public boolean handleRegister(String username, String password) {
		if (!checkInformation(username, password)) {
			return false;
		}

		return client.insertCorrect(username, password);
	}

	private boolean checkInformation(String username, String password) {
		if (username.length() < 3 || username.length() > 25 || password.length() < 3 || password.length() > 25) {
			return false;
		}
		if (!username.matches("[a-zA-Z0-9]+") && !password.matches("[a-zA-Z0-9]+")) {
			return false;
		}

		return true;
	}

	private void createTimer() {
		timer = new AnimationTimerExt(3000) {
			@Override
			public void doAction() {
				if (clientscene.isShownChallengeList()) {
					clientscene.handleChallengeListButton();
				}
				if (clientscene.isShownLobbyList()) {
					clientscene.handleLobbyListButton();
				}
				if (clientscene.isShownUserList()) {
					clientscene.handleUserListButton();
				}
			}
		};

		timer.start();
	}
}