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

	private Client client;
	private GameController gamecontroller;
	private MainApplication mainapplication;
	private PersistenceFacade persistencefacade;
	private ClientScene clientscene;

	private AnimationTimerExt timer;

	public ClientController(MainApplication mainapplication) {
		this.persistencefacade = new PersistenceFacade();
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication, persistencefacade, this);
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	// Only after a succesfull login everything will be created
	public boolean handleLogin(String username, String password) {
		if (persistencefacade.loginCorrect(username, password)) {
			client = new Client(username, persistencefacade);
			this.clientscene = new ClientScene(this);
			mainapplication.setScene(clientscene);

			createTimer();
		}
		// TODO does this also have to be in client
		return persistencefacade.loginCorrect(username, password);
	}

	public void returnToClient() {
		timer.start();
		mainapplication.setScene(clientscene);
	}

	public boolean handleRegister(String username, String password) {
		if (username.length() < 3 || username.length() > 25 || password.length() < 3 || password.length() > 25) {
			return false;
		}
		if (!username.matches("[a-zA-Z0-9]*") && !password.matches("[a-zA-Z0-9]*")) {
			return false;
		}
		// TODO does this also have to be in client
		return persistencefacade.insertCorrect(username, password);
	}

	// Getters
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

	public ArrayList<ArrayList<String>> getScore(int gameID, ArrayList<Player> players) {
		// TODO should be part of game controller?
		ScoreHandler scorehandler = new ScoreHandler(persistencefacade.getSharedCollectiveGoalCards(gameID));
		ArrayList<ArrayList<Integer>> comparator = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		// Change from String into Integer to be able to sort
		// Keep the relation between score and player by using numbers
		for (int i = 0; i < players.size(); i++) {
			// TODO should be part of gamecontroller?
			players.get(i).loadGlassWindow(persistencefacade.getGlassWindow(players.get(i).getPlayerID()));
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
			result.add(new ArrayList<String>(
					Arrays.asList(players.get(comparator.get(c).get(0)).getUsername(), String.valueOf(comparator.get(c).get(1)))));
		}
		return result;
	}

	public void logOut() {
		timer.stop();
		mainapplication.setScene(new Scene(new LoginPane(this)));
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

	public ArrayList<String> getAllUsernames() {
		return client.getAllUsernames();
	}

	// TODO possible move to gamecontroller
	public boolean isGameReady(int idGame) {
		// Check if game has toolcards
		if (persistencefacade.getGameToolCards(idGame).size() == 0) {
			System.err.println("no toolcards");
			return false;
		}
		
		// Check if game has public goalcards
		if (persistencefacade.getSharedCollectiveGoalCards(idGame).size() == 0) {
			System.err.println("no goalcards");
			return false;
		}
		
		// Check if all players have patterncard options
		for (Player p : persistencefacade.getAllPlayersInGame(idGame)) {
			if (persistencefacade.getPlayerOptions(p.getPlayerID()).size() == 0) {
				System.err.println("no patterncard options player " + p.getPlayerID());
				return false;
			}
		}
		
		return true;
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
}