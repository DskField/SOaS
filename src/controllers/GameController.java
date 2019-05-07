package controllers;

import java.util.ArrayList;

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
	}

	public Player getClientUser() {
		return game.getClientUser();
	}

	public void sendMessages(Message message) {
		System.out.println("2");
		gameScene.updateChat(game.sendMessage(message));
		

	}

}
