package controllers;

import client.Client;
import database.PersistenceFacade;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
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
		
		mainapplication.setScene(new Scene(new LoginPane(this)));
	}

	public boolean handleLogin(String username, String password) {
		if (persistencefacade.loginCorrect(username, password)) {
			client = new Client(username, persistencefacade);
			gamecontroller.joinGame(1, client.getUser());
//			createTimer();
		}
		
		return persistencefacade.loginCorrect(username, password);
	}
	
	public boolean handleRegister(String username, String password) {
		return persistencefacade.insertCorrect(username, password);
	}
	
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
