package controllers;

import java.sql.Timestamp;

import game.Game;
import game.Message;
import game.Player;
import javafx.animation.AnimationTimer;
import view.GameScene;

public class GameController {
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private AnimationTimerExt timer;

	public GameController(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		gameScene = new GameScene(this);
		this.game = new Game(2);
		game.loadPlayers();
		mainApplication.setScene(gameScene);

		gameScene.updateChat(game.loadChat());

		createTimer();
	}

	public Player getClientUser() {
		return game.getClientUser();
	}

	public void sendMessages(String text) {
		Message message = new Message(text, getClientUser(), new Timestamp(System.currentTimeMillis()));
		gameScene.updateChat(game.sendMessage(message));
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
				update();
			}
		};

		timer.start();
	}

	private void update() {
		System.out.println("HEY");
		gameScene.updateChat(game.updateChat());
	}
}
