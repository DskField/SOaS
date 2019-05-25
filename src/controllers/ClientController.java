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

	private boolean checkUpdateClient = false;

	private Client client;
	private GameController gamecontroller;
	private MainApplication mainapplication;
	private PersistenceFacade persistencefacade;
	private ClientScene clientscene;

	private AnimationTimerExt timer;

	public ClientController(MainApplication mainapplication) {
		this.persistencefacade = new PersistenceFacade();
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication, persistencefacade);
		// handleLogin("speler1", "speler1");
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	// Only after a succesfull login everything will be created
	public boolean handleLogin(String username, String password) {
		if (persistencefacade.loginCorrect(username, password)) {
			client = new Client(username, persistencefacade);
			this.clientscene = new ClientScene(this);
			mainapplication.setScene(clientscene);

			// TODO TOM fix timer/update
			//createTimer();
		}

		return persistencefacade.loginCorrect(username, password);
	}

	public boolean handleRegister(String username, String password) {
		return persistencefacade.insertCorrect(username, password);
	}

	// Getters
	public ArrayList<Challenge> getChallenges() {
		return client.getChallenges();
	}

	public ArrayList<Lobby> getLobbies() {
		return client.getLobbies();
	}

	public Challenge getSpecificChallenge(int gameID) {
		return client.getChallenge(gameID);
	}

	public Lobby getSpecificLobby(int gameID) {
		return client.getLobby(gameID);
	}

	public void joinGame(int idGame) {
		gamecontroller.joinGame(idGame, client.getUser());
	}

	public void handleReaction(boolean accepted, int idGame) {
		persistencefacade.updatePlayerStatus(client.getUser().getUsername(), accepted, idGame);
	}

	public User getUser() {
		return client.getUser();
	}

	public User getOpponent(String username) {
		return client.getOpponent(username);
	}

	public void createGame(ArrayList<User> users) {
		persistencefacade.createGame(users);
	}

	// use getUser method
	public String getUsername() {
		return client.getUser().getUsername();
	}

	// TODO TOM MOVE FACADE - TEMPORARY FIX
	public ArrayList<Player> getPlayers(int gameID) {
		return persistencefacade.getAllPlayersInGame(gameID);
	}

	// TODO TOM MOVE FACADE - TEMPORARY FIX
	public ArrayList<ArrayList<String>> getScore(int gameID, ArrayList<Player> players) {
		ScoreHandler scorehandler = new ScoreHandler(persistencefacade.getSharedCollectiveGoalCards(gameID));
		ArrayList<String> playerToInt = new ArrayList<>();
		ArrayList<ArrayList<Integer>> comparator = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).loadGlassWindow(persistencefacade.getGlassWindow(players.get(i).getPlayerID()));
			ArrayList<Integer> combo = new ArrayList<>();
			playerToInt.add(players.get(i).getUsername());

			combo.add(i);
			combo.add(scorehandler.getScore(players.get(i), false));
			comparator.add(combo);
		}

		Collections.sort(comparator, new Comparator<ArrayList<Integer>>() {
			@Override
			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
				return o1.get(1).compareTo(o2.get(1));
			}
		});
		Collections.reverse(comparator);

		for (int c = 0; c < players.size(); c++) {
			result.add(new ArrayList<String>(
					Arrays.asList(players.get(comparator.get(c).get(0)).getUsername(), String.valueOf(comparator.get(c).get(1)))));
		}
		return result;
	}

	public void logOut() {
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	// Updat with Timer
	public void updateClient() {
		// 3 to 6 seconds
		if (clientscene.isShownChallengeList()) {
			client.updateChallenge();
			clientscene.handleChallengeListButton();
		}
		
		if (clientscene.isShownLobbyList()) {
			client.updateLobby();
			clientscene.handleLobbyListButton();
		}
		
		if (clientscene.isShownUserList()) {
			client.updateUser();
			clientscene.handleUserListButton();
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

	private void createTimer() {
		timer = new AnimationTimerExt(3000) {
			@Override
			public void doAction() {
				updateClient();
			}
		};

		timer.start();
	}

	public ArrayList<String> getAllUsernames() {
		return persistencefacade.getAllUsername();
	}
}