package controllers;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.User;
import game.CollectiveGoalCard;
import game.Die;
import game.Game;
import game.GameColor;
import game.GlassWindow;
import game.Message;
import game.PatternCard;
import game.Player;
import game.SpaceGlass;
import game.SpacePattern;
import javafx.animation.AnimationTimer;
import view.ChoiceScene;
import view.DiePane;
import view.GameScene;

public class GameController {
	// variables
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private ChoiceScene choiceScene;
	private AnimationTimerExt timer;
	private ArrayList<Die> diesInCurrentRound;

	public GameController(MainApplication mainApplication) {
		this.mainApplication = mainApplication;
		// TODO temporary call, when the game will be created the ClientController needs
		// to give the information to the GameController
		joinGame(1, new User("speler2", 0, 0, GameColor.RED, 0));
	}

	public void joinGame(int idGame, User clientUser) {
		game = new Game(idGame, clientUser);
		game.loadGame();
		//		getClientPlayer().getGlassWindow().setPaterNull(null);
		if (getClientPlayer().getGlassWindow().getPatternCard() == null) {
			game.dealPatternCards();
			choiceScene = new ChoiceScene(this, getPatternChoices());
			mainApplication.setScene(choiceScene);
		} else {
			gameScene = new GameScene(this);
			mainApplication.setScene(gameScene);
			createTimer();
		}
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

	public Game getGame() {
		return game;
	}

	/**
	 * Get new dies for the round if its your turn
	 * 
	 * @return ArrayList<Die> - new dice
	 */
	public ArrayList<Die> getDiceOffering() {
		game.shakeSack();
		return game.getTable();
	}

	public int getInitialDieAmount() {
		return getPlayers().size() * 2 + 1;
	}

	//kevin stuff
	public ArrayList<PatternCard> getPatternChoices() {
		return game.patternChoices(game.getClientPlayer().getPlayerID());
	}

	//kevin stuff
	public void setClientPlayerPaternCard(int idPatternCard) {
		game.setClientPlayerPaternCard(idPatternCard);
		game.loadGame();
		gameScene = new GameScene(this);
		mainApplication.setScene(gameScene);
		createTimer();
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
		gameScene.updateScore(game.updateScore());
	}

	//kevin stuff
	public void updateCurrencyStones(int ammount) {
		game.updateCurrencyStone(game.getGameID(), game.getClientPlayer().getPlayerID(), ammount);
	}

	public int getCollectiveGoalCard(int arrayNumber) {
		return game.getCollectiveGoalCards().get(arrayNumber).getCardID();
	}

	public ArrayList<CollectiveGoalCard> getCollectiveGoalCards() {
		return game.getCollectiveGoalCards();
	}

	public int getToolCard(int arrayNumber) {
		return game.getToolCards().get(arrayNumber).getCardID();
	}

	/**
	 * Checks if there are already dice on the window.
	 */
	boolean checkFirstDie() {
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
	ArrayList<Die> getDiagonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> diagonal = new ArrayList<>();
		for (SpaceGlass[] spaceGlassRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlassRow) {
				if (((spaceGlass.getYCor() == space.getYCor() - 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1))//first the the top left and top right
						|| (spaceGlass.getYCor() == space.getYCor() + 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1)))//then the the bottom left and bottom right
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
	ArrayList<Die> getOrthogonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> surrounding = new ArrayList<>();
		for (SpaceGlass[] spaceGlassRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlassRow) {
				if (((spaceGlass.getYCor() == space.getYCor() && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1)) //get dice horizontally neighboring that space
						|| (spaceGlass.getXCor() == space.getXCor() && (spaceGlass.getYCor() == space.getYCor() - 1 || spaceGlass.getYCor() == space.getYCor() + 1))) //get dice vertically neighboring the space
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
	 * @param die - the die you want to place
	 * @return boolean - true if compatible, false if not
	 */
	boolean checkCompatibility(SpacePattern sPattern, DiePane diePane) {
		if (sPattern.getColor().equals(diePane.getColor()) || sPattern.getValue() == diePane.getEyes() || (sPattern.getColor().equals(GameColor.EMPTY) && sPattern.getValue() == 0)) {
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
	boolean checkSurrounding(DiePane diePane, SpaceGlass space) {
		boolean succes = true;
		ArrayList<Die> orthogonalDice = getOrthogonalDice(space);
		ArrayList<Die> diagonalDice = getDiagonalDice(space);

		for (Die die : orthogonalDice) {
			boolean sameColor = die.getDieColor().equals(diePane.getColor());//I seperated them and moved them out of the if for readability
			boolean sameValue = die.getDieValue() == diePane.getEyes();
			if (sameColor || sameValue) {
				succes = false;
				break;
			}
		}

		if (diagonalDice.isEmpty() && orthogonalDice.isEmpty()) {//If there is no die diagonal and orthogonal is same color or value succes is false
			succes = false;
		}

		return succes;
	}

	/**
	 * Puts all available places in an arraylist
	 * 
	 * @param die - the to be placed die
	 * @return ArrayList<SpaceGlass> - All available spaces
	 */
	public ArrayList<SpaceGlass> getAvailableSpaces(DiePane diePane) {
		ArrayList<SpaceGlass> available = new ArrayList<>();
		GlassWindow window = getClientPlayer().getGlassWindow();

		if (checkFirstDie()) {
			for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
				for (SpacePattern space : spacePatternRow) {
					if ((checkCompatibility(space, diePane) && (space.getXCor() == 0 || space.getXCor() == 4 || space.getYCor() == 0 || space.getYCor() == 3))) {
						available.add(window.getSpace(space.getXCor(), space.getYCor()));
					}
				}
			}
		} else {
			for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
				for (SpacePattern space : spacePatternRow) {
					if (window.getSpace(space.getXCor(), space.getYCor()).getDie() == null) {
						if (checkCompatibility(space, diePane)) {
							if (checkSurrounding(diePane, window.getSpace(space.getXCor(), space.getYCor()))) {
								available.add(window.getSpace(space.getXCor(), space.getYCor()));
							}
						}
					}
				}
			}
		}
		return available;
	}

	/**
	 * Finally place the die
	 * 
	 * @param Die - the to be placed die
	 * @param paceGlass newSpace- the space where its going to be placed
	 * @return boolean - true if succeeded
	 */
	public boolean placeDie(Die die, SpaceGlass newSpace) {
		//		for (SpaceGlass space : getAvailableSpaces(die)) {
		//			if (space.equals(newSpace)) {
		//				return true;
		//			}
		//		}
		return false;
	}
}
