package controllers;

import java.util.Timer;
import java.util.TimerTask;

import game.Game;
import game.Message;
import game.Player;
import view.GameScene;

public class GameController {
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;

	public GameController(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		gameScene = new GameScene(this);
		this.game = new Game(2);
		game.loadPlayers();
		mainApplication.setScene(gameScene);

		game.loadChat();

		Timer timer = new Timer();
		timer.schedule(new update(), 3000);
	}

	public Player getClientUser() {
		return game.getClientUser();
	}

	public void sendMessages(Message message) {
		gameScene.updateChat(game.sendMessage(message));
	}

	class update extends TimerTask {
		public void run() {
			gameScene.updateChat(game.updateChat());
		}
	}
}
