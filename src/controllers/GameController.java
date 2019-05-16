package controllers;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.User;
import game.CollectiveGoalCard;
import game.Game;
import game.GameColor;
import game.Message;
import game.Player;
import javafx.animation.AnimationTimer;
import view.GameScene;

public class GameController {
	//variables
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private AnimationTimerExt timer;

	public GameController(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		//TODO temporary call, when the game will be created the ClientController needs to give the information to the GameController
		joinGame(1, new User("speler1", 0, 0, GameColor.RED, 0));
	}

	public void joinGame(int idGame, User clientUser) {
		game = new Game(idGame, clientUser);
		game.loadGame();
		gameScene = new GameScene(this);
		mainApplication.setScene(gameScene);

		createTimer();
	}

	/**
	 * Uses the player text to make a new Message Object. sends the Message to model for processing and
	 * receives and Arraylist<Message> to update the chatPane with
	 * 
	 * @param text - the text that the player sends to the chat
	 */
	public void sendMessage(String text) {
		Message message = new Message(text, getClientPlayer(), new Timestamp(System.currentTimeMillis()));
		gameScene.updateChat(game.sendMessage(message));
	}

	public Player getClientPlayer() {
		return game.getClientPlayer();
	}

	public ArrayList<Player> getPlayers() {
		return game.getPlayers();
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

	/**
	 * updates the catPane by using information out of the game model.
	 */
	private void update() {
		gameScene.updateChat(game.updateChat());
	}
	
	public int getCollectiveGoalCard(int arrayNumber) {
		return game.getCollectiveGoalCards().get(arrayNumber).getCardID();
	}
	
	public int getToolCard(int arrayNumber) {
		return game.getToolCards().get(arrayNumber).getCardID();
	}
}
