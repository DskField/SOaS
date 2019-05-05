package controllers;

import game.Game;
import game.Player;

public class GameController {
	Game game;

	public GameController() {
		this.game = new Game(69);
	}

	public Player getClientUser() {
		return game.getClientUser();
	}

}
