package controllers;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.User;
import game.Die;
import game.Game;
import game.GameColor;
import game.GlassWindow;
import game.Message;
import game.Player;
import game.SpaceGlass;
import game.SpacePattern;
import javafx.animation.AnimationTimer;
import view.GameScene;

public class GameController {
	// variables
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private AnimationTimerExt timer;

	public GameController(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		// TODO temporary call, when the game will be created the ClientController needs
		// to give the information to the GameController
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
	 * Uses the player text to make a new Message Object. sends the Message to model
	 * for processing and receives and Arraylist<Message> to update the chatPane
	 * with
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

	/**
	 * Checks if there are already dice on the window.
	 */
	public boolean checkFirstDie() {
		// checks if its the firstDie
		for (SpaceGlass[] spaceRow : getClientPlayer().getGlassWindow().getSpaces()) {
			for (SpaceGlass space : spaceRow) {
				if (space.getDie() != null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets all diagonal dice for a certain SpaceGlass
	 * 
	 * @param space - The space you want to get all the diagonal dice from
	 * @return ArrayList<Die> - all diagonal dice from a certain space
	 */
	public ArrayList<Die> getDiagonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> diagonal = new ArrayList<>();
		for (SpaceGlass[] spaceGlasRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlasRow) {
				if (((spaceGlass.getYCor() == space.getYCor() - 1
						&& (spaceGlass.getXCor() == space.getXCor() - 1 
						|| spaceGlass.getXCor() == space.getXCor() + 1))//first the the top left and top right
					|| (spaceGlass.getYCor() == space.getYCor() + 1 
						&& (spaceGlass.getXCor() == space.getXCor() - 1
						|| spaceGlass.getXCor() == space.getXCor() + 1)))//then the the bottom left and bottom right
					&& spaceGlass.getDie() != null) {//see if the is a die there
					diagonal.add(spaceGlass.getDie());
				}
			}
		}
		return diagonal;
	}

	/**
	 * Gets all Orthogonal dice for a certain SpaceGlass
	 * 
	 * @param space- Orthogonal from what space
	 * @return ArrayList<Die> - all Orthogonal dice from a certain space
	 */
	public ArrayList<Die> getOrthogonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> surrounding = new ArrayList<>();
		for (SpaceGlass[] spaceGlasRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlasRow) {
				if (((spaceGlass.getYCor() == space.getYCor()
						&& (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1)) //get dice horizontally neighboring that space
							|| (spaceGlass.getXCor() == space.getXCor()
						&& (spaceGlass.getYCor() == space.getYCor() - 1
							|| spaceGlass.getYCor() == space.getYCor() + 1))) //get dice vertically neighboring the space
						&& spaceGlass.getDie() != null) { //see if the is a die there
						surrounding.add(spaceGlass.getDie());
				}
			}
		}
		return surrounding;
	}

	/**
	 * Checks if die is compatible with that space
	 * 
	 * @param sPattern - sPattern the space on the patterncard
	 * @param  die - the die you want to place
	 * @return boolean - true if compatible, false if not
	 */
	public boolean checkCompatibility(SpacePattern sPattern, Die die) {
		if (sPattern.getColor().equals(die.getDieColor()) || sPattern.getValue() == die.getDieValue()
				|| sPattern.getColor().equals(GameColor.EMPTY)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks all surrounding spaces if the die can be placed there
	 * 
	 * @param newDie - the to be placed die
	 * @param space - the space where its going to be placed
	 * @return boolean - true if possible to place, false if not
	 */
	public boolean checkSurrounding(Die newDie, SpaceGlass space) {
		boolean succes = true;
		if (getDiagonalDice(space).isEmpty()) {//if getDiagonalDice(space) returns an empty ArrayList there is no diagonal die
			succes = false;
		}
		for (Die die : getOrthogonalDice(space)) {
			if (die.getDieColor().equals(newDie.getDieColor()) || die.getDieValue() == newDie.getDieValue()) {
				succes = false;
			}
		}
		return succes;
	}

	/**
	 * Puts all available places in an arraylist
	 * 
	 * @param die - the to be placed die
	 * @return ArrayList<SpaceGlass> - All available spaces
	 */
	public ArrayList<SpaceGlass> getAvailableSpaces(Die die) {
		ArrayList<SpaceGlass> available = new ArrayList<>();
		GlassWindow window  = getClientPlayer().getGlassWindow();
	
		if (checkFirstDie()) {
			for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
				for (SpacePattern space : spacePatternRow) {
					if ((checkCompatibility(space, die)&& (space.getXCor() == 1 || space.getXCor() == 5 || space.getYCor() == 1
									|| space.getYCor() == 4))) {
						available.add(window.getSpace(space.getXCor(), space.getYCor()));
					}
				}
			}
		} else {
			for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
				for (SpacePattern space : spacePatternRow) {
					if (checkCompatibility(space, die)
							&& checkSurrounding(die, window.getSpace(space.getXCor(), space.getYCor())) || window.getSpace(space.getXCor(), space.getYCor()).getDie() == null)  {
						
						available.add(window.getSpace(space.getXCor(), space.getYCor()));
					}
				}
			}
		}
		return available;
	}

	/**
	 * Finally place the die
	 * 
	 * @param Die       - the to be placed die
	 * @param paceGlass newSpace- the space where its going to be placed
	 * @return boolean - true if succeeded
	 */
	public boolean placeDie(Die die, SpaceGlass newSpace) {
		for (SpaceGlass space : getAvailableSpaces(die)) {
			if (space.equals(newSpace)) {
				return true;
			}
		}
		return false;
	}
}
