package controllers;

import java.util.ArrayList;

import client.Challenge;
import client.Client;
import client.Lobby;
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

	private AnimationTimerExt timer;

	public ClientController(MainApplication mainapplication) {
		this.persistencefacade = new PersistenceFacade();
		this.mainapplication = mainapplication;
		this.gamecontroller = new GameController(mainapplication);
		handleLogin("speler1", "speler1");
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	// Only after a succesfull login everything will be created
	public boolean handleLogin(String username, String password) {
		if (persistencefacade.loginCorrect(username, password)) {
			client = new Client(username, persistencefacade);
			mainapplication.setScene(new ClientScene(this));
			// TODO has to be deleted later on
			//			gamecontroller.joinGame(1, client.getUser());

			// TODO fix timer/update
			//			createTimer();
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

	public Lobby getSpecificLobby(int gameID) {
		return client.getLobby(gameID);
	}

	public void joinGame(int idGame) {
		gamecontroller.joinGame(idGame, client.getUser());
	}

	// TODO FIND SOLUTION - TEMPORARY FIX
	public ArrayList<Player> getPlayers(int gameID) {
		return persistencefacade.getAllPlayersInGame(gameID);
	}

	// TODO FIND SOLUTION - TEMPORARY FIX
	public int getScore(int gameID, Player player) {
		ScoreHandler scorehandler = new ScoreHandler(persistencefacade.getSharedCollectiveGoalCards(gameID));
		// TODO FIX SCOREHANDLER - getwindow
		//		return scorehandler.getScore(player, false);
		return 100;
	}

	// Updat with Timer
	private void updateClient() {
		// 3 to 6 seconds
		while (checkUpdateClient) {
			client.updateClient();
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
				client.updateClient();
			}
		};

		timer.start();
	}
}