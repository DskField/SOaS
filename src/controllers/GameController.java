package controllers;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.User;
import database.PersistenceFacade;
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
import view.GameScene;

public class GameController {
	/* VARIABLES */
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private ChoiceScene choiceScene;
	private AnimationTimerExt timer;
	private PersistenceFacade persistencefacade;
	private ClientController clientcontroller;

	private int cheatMode;

	private boolean dieNotPlaced;
	private boolean isFinished;

	public GameController(MainApplication mainApplication, PersistenceFacade persistencefacade, ClientController clientcontroller) {
		this.mainApplication = mainApplication;
		this.persistencefacade = persistencefacade;
		this.clientcontroller = clientcontroller;
		dieNotPlaced = true;
		isFinished = false;

		cheatMode = 0;
	}

	public void returnToClient() {
		timer.stop();
		gameScene = null;
		clientcontroller.returnToClient();
	}

	public void joinGame(int idGame, User clientUser) {
		game = new Game(idGame, clientUser, persistencefacade);
		// game.persistenceFacade.setCardsGame(idGame);
		game.loadGame();

		// getClientPlayer().getGlassWindow().setPaterNull(null);
		if (getClientPlayer().getGlassWindow().getPatternCard() == null) {
			choiceScene = new ChoiceScene(this, getPatternChoices());
			mainApplication.setScene(choiceScene);
		}
		createTimer();
	}

	/**
	 * Uses the player text to make a new {@code Message}. Sends the {@code Message} to the model for
	 * processing and receives an {@code Arraylist<Message>} to update the {@code ChatPane} with.
	 * 
	 * @param text - The text that the player sends to the {@code Chat}
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

	public ArrayList<PatternCard> getPatternChoices() {
		return game.patternChoices(game.getClientPlayer().getPlayerID());
	}

	public void setClientPlayerPatternCard(int idPatternCard) {
		game.setClientPlayerPaternCard(idPatternCard);
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
				check();
			}
		};

		timer.start();
	}

	/**
	 * Updates the view by using information out of the game model.
	 */
	public void check() {
		if (gameScene == null) {
			if (game.getPlayersWithoutPatternCards().isEmpty() && game.getPlayerWithPatternCardButWithoutCurrencyStones().isEmpty()) {
				game.loadGame();
				gameScene = new GameScene(this);
				mainApplication.setScene(gameScene);
				gameScene.updateTable(game.getTable());
				update();
			} else {
				if (getClientPlayer().getPlayerID() == getPlayers().get(0).getPlayerID()) {
					for (Player player : game.getPlayerWithPatternCardButWithoutCurrencyStones()) {
						PatternCard patternCard = game.getPlayerPatternCard(player.getPlayerID());
						game.updateCurrencyStone(player.getPlayerID(), patternCard.getDifficulty());
					}
				}
			}
		} else {
			if (!isFinished) {
				if (game.getCurrentRound() <= 10) {
					update();
				} else {
					if (game.getClientPlayer().getPlayerID() == game.getCurrentPlayer().getPlayerID()) {
						gameFinish();
					} else {
						if (game.checkScore()) {
							gameFinish();
						}
					}
				}
			} else {
				gameScene.updateChat(game.updateChat());
			}
		}
	}

	public void update() {
		game.loadCurrentRound();
		game.updatePlayers();
		game.loadCurrentPlayer();
		game.loadCurrencyStones();

		gameScene.updateCurrentPlayerBorder(game.getCurrentPlayer().getColor());
		gameScene.updateRoundTrack(game.getRoundTrack());
		gameScene.updateGlassWindow(game.updateGlassWindow());
		gameScene.updateTable(game.getTable());
		gameScene.updateToolCards(game.getToolCards());
		gameScene.updateCurrencyStone(game.getCurrencyStones());
		gameScene.updateCurrentPlayerLabel(game.getCurrentPlayer().getUsername());

		if (dieNotPlaced && checkMyTurn()) {
			gameScene.updateShakeButton(checkStartPlayer());

			gameScene.disableDieOfferPane(false);
		} else {
			gameScene.disableDieOfferPane(true);
		}

		gameScene.updateTurn(checkMyTurn());
		gameScene.updateChat(game.updateChat());
	}

	public void nextTurn() {
		dieNotPlaced = true;
		game.updatePlayers();
		int totalPlayers = game.getPlayers().size();
		int maxSeqnr = totalPlayers * 2;
		int nextSeqnr = 0;

		for (Player player : game.getPlayers()) {
			if (player.getPlayerID() == game.getCurrentPlayer().getPlayerID()) {
				switch (player.getSeqnr()) {
				case 1:
					game.setSeqnr(player, maxSeqnr);
					nextSeqnr = 2;
					break;
				case 2:
					game.setSeqnr(player, maxSeqnr - 1);
					nextSeqnr = 3;
					break;
				case 3:
					if (totalPlayers == 2) {
						game.setSeqnr(player, 1);
					} else {
						game.setSeqnr(player, maxSeqnr - 2);
					}
					nextSeqnr = 4;
					break;
				case 4:
					if (totalPlayers == 2) {
						game.setSeqnr(player, 2);
						nextSeqnr = 1;
						break;
					} else if (totalPlayers == 3) {
						game.setSeqnr(player, 2);
					} else if (totalPlayers == 4) {
						game.setSeqnr(player, maxSeqnr - 3);
					}
					nextSeqnr = 5;
					break;
				case 5:
					if (totalPlayers == 3) {
						game.setSeqnr(player, 1);
					} else if (totalPlayers == 4) {
						game.setSeqnr(player, 3);
					}
					nextSeqnr = 6;
					break;
				case 6:
					if (totalPlayers == 3) {
						game.setSeqnr(player, 3);
						nextSeqnr = 1;
						break;
					} else if (totalPlayers == 4) {
						game.setSeqnr(player, 2);
						nextSeqnr = 7;
						break;
					}
				case 7:
					game.setSeqnr(player, 1);
					nextSeqnr = 8;
					break;
				case 8:
					game.setSeqnr(player, 4);
					nextSeqnr = 1;
					break;
				}

				if (game.getCurrentRound() != 10) {
					for (Player player2 : game.getPlayers()) {
						if (player2.getSeqnr() == nextSeqnr) {
							game.setCurrentPlayer(player2);
						}
					}

					game.updatePlayerTurn(player);

					if (nextSeqnr == 1) {
						game.nextRound();
						gameScene.updateTable(game.getTable());
					}
				}

				// This return is very important, otherwise the next player will also be switched and you will softlock the game!
				return;
			}
		}
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
	 * @return boolean - True if its clientplayer's turn
	 */
	private boolean checkMyTurn() {
		if (game.getCurrentPlayer().getPlayerID() == getClientPlayer().getPlayerID()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if its the first die on the board
	 * 
	 * @return - True if it is the first die
	 */
	private boolean checkFirstDie() {
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
	 * @param space - The {@code Space} you want to get all the diagonal dice from
	 * @return ArrayList<Die> - All diagonal dice from a certain space
	 */
	private ArrayList<Die> getDiagonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> diagonal = new ArrayList<>();
		for (SpaceGlass[] spaceGlassRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlassRow) {
				// Two diagonally above
				boolean above = spaceGlass.getYCor() == space.getYCor() - 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1);
				// Two diagonally below
				boolean below = spaceGlass.getYCor() == space.getYCor() + 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1);

				if ((above || below) && spaceGlass.getDie() != null) {// also see if the is a die there
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
	 * @return ArrayList<Die> - All Orthogonal dice from a certain space
	 */
	private ArrayList<Die> getOrthogonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> surrounding = new ArrayList<>();
		for (SpaceGlass[] spaceGlassRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlassRow) {
				boolean horizontal = (spaceGlass.getYCor() == space.getYCor() && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1));
				boolean vertical = (spaceGlass.getXCor() == space.getXCor() && (spaceGlass.getYCor() == space.getYCor() - 1 || spaceGlass.getYCor() == space.getYCor() + 1));
				if ((vertical || horizontal) && spaceGlass.getDie() != null) { // also see if the is a die there
					surrounding.add(spaceGlass.getDie());
				}
			}
		}
		return surrounding;
	}

	/**
	 * Checks if die is compatible with that space
	 * 
	 * @param sPattern - The {@code Space} on the {@code PatternCard}
	 * @param die - The {@code Die} you want to place
	 * @return boolean - True if compatible, false if not
	 */
	private boolean checkCompatibility(SpacePattern sPattern, GameColor color, int eyes) {
		if (sPattern.getColor().equals(color) || sPattern.getValue() == eyes || (sPattern.getColor().equals(GameColor.EMPTY) && sPattern.getValue() == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks all surrounding spaces if the die can be placed there
	 * 
	 * @param newDie - The to be placed {@code Die}
	 * @param space - The {@code Space} where its going to be placed
	 * @return boolean - True if possible to place, false if not
	 */
	private boolean checkSurrounding(GameColor color, int eyes, SpaceGlass space) {
		boolean succes = true;
		ArrayList<Die> orthogonalDice = getOrthogonalDice(space);
		ArrayList<Die> diagonalDice = getDiagonalDice(space);

		for (Die die : orthogonalDice) {
			boolean sameColor = die.getDieColor().equals(color);
			boolean sameValue = die.getDieValue() == eyes;

			if (sameColor || sameValue) {
				succes = false;
				break;

			}
		}
		if (diagonalDice.isEmpty() && orthogonalDice.isEmpty()) {
			succes = false;
		}

		return succes;
	}

	/**
	 * Puts all available places in an {@code ArrayList}
	 * 
	 * @param diePane - The to be placed die
	 * @return ArrayList<SpaceGlass> - All available spaces
	 */
	private ArrayList<SpaceGlass> getAvailableSpaces(GameColor color, int eyes) {
		ArrayList<SpaceGlass> available = new ArrayList<>();
		GlassWindow window = getClientPlayer().getGlassWindow();

		for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
			for (SpacePattern space : spacePatternRow) {
				boolean compatible = checkCompatibility(space, color, eyes);
				boolean surrounding = checkSurrounding(color, eyes, window.getSpace(space.getXCor(), space.getYCor()));
				boolean empty = window.getSpace(space.getXCor(), space.getYCor()).getDie() == null;
				boolean edge = (space.getXCor() == 0 || space.getXCor() == 4 || space.getYCor() == 0 || space.getYCor() == 3);

				if ((checkFirstDie() && compatible && edge) || (empty && compatible && surrounding)) {
					available.add(window.getSpace(space.getXCor(), space.getYCor()));
				}
			}
		}
		return available;
	}

	public void selectDie(GameColor color, int eyes) {
		if (cheatMode == 0) {
			gameScene.selectDie(null);
		} else if (cheatMode == 1) {
			gameScene.selectDie(getAvailableSpaces(color, eyes));

		} else if (cheatMode == 2) {
			gameScene.selectDie(getBestPlaces(getAvailableSpaces(color, eyes), color, eyes));
		}
	}

	public void placeDie(int x, int y) {
		GameColor color = gameScene.getSelectedDieColor();
		int eyes = gameScene.getSelectedDieEyes();
		int id = gameScene.getSelectedDieId();
		if (color != null) {
			ArrayList<SpaceGlass> available = getAvailableSpaces(color, eyes);
			for (SpaceGlass spaceGlass : available) {
				if (spaceGlass.getXCor() == x && spaceGlass.getYCor() == y) {
					game.placeDie(id, color, x, y);
					gameScene.removeDieTable();
					gameScene.removeHighlight();
					gameScene.disableDieOfferPane(true);
					dieNotPlaced = false;
					update();
					break;
				}
			}
		}
	}

	private void gameFinish() {
		int maxScore = -99;
		Player winner = game.getPlayers().get(0);
		game.setGameFinal();
		gameScene.updateGlassWindow(game.getPlayers());
		for (Player player : game.getPlayers()) {
			if (maxScore < player.getScore()) {
				maxScore = player.getScore();
				winner = player;
			}
		}
		String winText = winner.getUsername() + " heeft het spel gewonnen met een score van:  " + maxScore;
		gameScene.gameFinish(winText);

		isFinished = true;
	}

	/**
	 * Cycles through {@code cheatmode}
	 */
	public void cycleCheat() {
		if (cheatMode == 2)
			cheatMode = 0;
		else
			cheatMode++;
	}

	/**
	 * @return cheatMode - 0 = no cheat, 1 = basic cheat, 2 = advanced cheat
	 */
	public int getCheatMode() {
		return cheatMode;
	}

	/**
	 * Returns best places based on whats next to the available spaces and the die you want to place if
	 * a space next to the availble space requires the same value or color it wont be added to best
	 * places
	 * 
	 * @param available - All available places
	 * @param newDie - The to be placed die
	 * @return ArrayList<SpaceGlass> - The best places to place the die
	 */
	private ArrayList<SpaceGlass> getBestPlaces(ArrayList<SpaceGlass> available, GameColor color, int eyes) {
		ArrayList<SpaceGlass> best = new ArrayList<>();
		PatternCard pC = getClientPlayer().getGlassWindow().getPatternCard();

		int minX = 0;
		int maxX = 4;
		int minY = 0;
		int maxY = 3;
		boolean rightSame = false;
		boolean leftSame = false;
		boolean aboveSame = false;
		boolean belowSame = false;

		for (SpaceGlass sG : available) {
			int currentX = sG.getXCor();
			int currentY = sG.getYCor();

			// Left
			if (currentX > minX) {
				leftSame = pC.getSpaceColor(sG.getXCor() - 1, sG.getYCor()).equals(color) || pC.getSpaceValue(sG.getXCor() - 1, sG.getYCor()) == eyes;
			}

			// Right
			if (currentX < maxX) {
				rightSame = pC.getSpaceColor(sG.getXCor() + 1, sG.getYCor()).equals(color) || pC.getSpaceValue(sG.getXCor() + 1, sG.getYCor()) == eyes;
			}

			// Above
			if (currentY > minY) {// ABOVE
				aboveSame = pC.getSpaceColor(sG.getXCor(), sG.getYCor() - 1).equals(color) || pC.getSpaceValue(sG.getXCor(), sG.getYCor() - 1) == eyes;
			}

			// Bellow
			if (currentY < maxY) {// BELOW
				belowSame = (pC.getSpaceColor(sG.getXCor(), sG.getYCor() + 1).equals(color) || pC.getSpaceValue(sG.getXCor(), sG.getYCor() + 1) == eyes);

			}

			if (!(leftSame || rightSame || aboveSame || belowSame)) {// if one of them is true its not ideal
				best.add(sG);
			}
		}

		return best;
	}

	/**
	 * Shakes the sack
	 */
	public void shakeSack() {
		game.shakeSack();

	}

	/**
	 * Used to check if you are allowed to throw the dice
	 * 
	 * @return - True if you are the start player
	 */
	public boolean checkStartPlayer() {
		if (getClientPlayer().getSeqnr() == 1 && getClientPlayer().equals(game.getCurrentPlayer()) && game.getTable().isEmpty() && game.getCurrentRound() <= 10) {
			return true;
		} else {
			return false;
		}
	}
}
