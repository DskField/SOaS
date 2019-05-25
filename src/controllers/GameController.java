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
import view.DiePane;
import view.GameScene;
import view.SpacePane;

public class GameController {
	// variables
	private Game game;
	private MainApplication mainApplication;
	private GameScene gameScene;
	private ChoiceScene choiceScene;
	private AnimationTimerExt timer;
	private PersistenceFacade persistencefacade;
	
	private int cheatMode;

	//TODO: write query for this
	private boolean dieNotPlaced = true;

	public GameController(MainApplication mainApplication, PersistenceFacade persistencefacade) {
		this.mainApplication = mainApplication;
		this.persistencefacade = persistencefacade;
		// Temporary call, when the game will be created the ClientController needs
		// to give the information to the GameController
		//		PersistenceFacade pf = new PersistenceFacade();
		//		ArrayList<User> users = new ArrayList<User>();
		//		users.add(new User("speler1", 0, 0, GameColor.RED, 0));
		//		users.add(new User("speler2", 0, 0, GameColor.RED, 0));
		//		users.add(new User("speler3", 0, 0, GameColor.RED, 0));
		//		users.add(new User("speler4", 0, 0, GameColor.RED, 0));
		//		pf.createGame(users);
		//		pf.setCardsGame(13);
		//		System.out.println("Created game");
		// 		joinGame(13, new User("speler1", 0, 0, GameColor.RED, 0));
		
		cheatMode = 0;
	}

	public void joinGame(int idGame, User clientUser) {
		game = new Game(idGame, clientUser, persistencefacade);
		//		game.persistenceFacade.setCardsGame(idGame);
		game.loadGame();

		// getClientPlayer().getGlassWindow().setPaterNull(null);
		if (getClientPlayer().getGlassWindow().getPatternCard() == null) {
			choiceScene = new ChoiceScene(this, getPatternChoices());
			mainApplication.setScene(choiceScene);
		}
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

	/**
	 * Get new dies for the round if its your turn
	 * 
	 * @return ArrayList<Die> - new dice
	 */

	// kevin stuff
	public ArrayList<PatternCard> getPatternChoices() {
		return game.patternChoices(game.getClientPlayer().getPlayerID());
	}

	// kevin stuff
	// FIXME spelling Pattern
	public void setClientPlayerPaternCard(int idPatternCard) {
		game.setClientPlayerPaternCard(idPatternCard);

		// gameScene = new GameScene(this);
		// mainApplication.setScene(gameScene);
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
	 * updates the view by using information out of the game model.
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
			if (game.getCurrentRound() <= 10) {
				update();
			} else {
				gameFinish();
				timer.stop();
			}
		}
	}

	public void update() {
		game.loadCurrentRound();
		game.updatePlayers();
		game.loadCurrentPlayer();
		gameScene.updateRoundTrack(game.getRoundTrack());
		gameScene.updateGlassWindow(game.updateGlassWindow());
		gameScene.updateTable(game.getTable());

		if (dieNotPlaced && checkMyTurn()) {
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

				return;
			}
		}
	}

	// kevin stuff

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
	 * @return boolean - true if its your turn
	 */
	private boolean checkMyTurn() {
		if (game.getCurrentPlayer().getPlayerID() == getClientPlayer().getPlayerID()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Checks if there are already dice on the window.
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
	 * @param space - The space you want to get all the diagonal dice from
	 * @return ArrayList<Die> - all diagonal dice from a certain space
	 */
	private ArrayList<Die> getDiagonalDice(SpaceGlass space) {
		SpaceGlass[][] spaces = getClientPlayer().getGlassWindow().getSpaces();
		ArrayList<Die> diagonal = new ArrayList<>();
		for (SpaceGlass[] spaceGlassRow : spaces) {
			for (SpaceGlass spaceGlass : spaceGlassRow) {
				boolean above = spaceGlass.getYCor() == space.getYCor() - 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1); // two diagonally above
				boolean below = spaceGlass.getYCor() == space.getYCor() + 1 && (spaceGlass.getXCor() == space.getXCor() - 1 || spaceGlass.getXCor() == space.getXCor() + 1);// two diagonally below
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
	 * @return ArrayList<Die> - all Orthogonal dice from a certain space
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
	 * @param sPattern - sPattern the space on the patterncard
	 * @param die - the die you want to place
	 * @return boolean - true if compatible, false if not
	 */
	private boolean checkCompatibility(SpacePattern sPattern, DiePane diePane) {
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
	private boolean checkSurrounding(DiePane diePane, SpaceGlass space) {
		boolean succes = true;
		ArrayList<Die> orthogonalDice = getOrthogonalDice(space);
		ArrayList<Die> diagonalDice = getDiagonalDice(space);

		for (Die die : orthogonalDice) {
			boolean sameColor = die.getDieColor().equals(diePane.getColor());// I seperated them and moved them out of the if for readability
			boolean sameValue = die.getDieValue() == diePane.getEyes();
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
	 * Puts all available places in an arraylist
	 * 
	 * @param die - the to be placed die
	 * @return ArrayList<SpaceGlass> - All available spaces
	 */
	private ArrayList<SpaceGlass> getAvailableSpaces(DiePane diePane) {
		ArrayList<SpaceGlass> available = new ArrayList<>();
		GlassWindow window = getClientPlayer().getGlassWindow();

		for (SpacePattern[] spacePatternRow : window.getPatternCard().getSpaces()) {
			for (SpacePattern space : spacePatternRow) {
				boolean compatible = checkCompatibility(space, diePane);
				boolean surrounding = checkSurrounding(diePane, window.getSpace(space.getXCor(), space.getYCor()));
				boolean empty = window.getSpace(space.getXCor(), space.getYCor()).getDie() == null;
				boolean edge = (space.getXCor() == 0 || space.getXCor() == 4 || space.getYCor() == 0 || space.getYCor() == 3);

				if ((checkFirstDie() && compatible && edge) || (empty && compatible && surrounding)) {
					available.add(window.getSpace(space.getXCor(), space.getYCor()));
				}
			}
		}
		return available;
	}

	public void selectDie(DiePane diePane) {
		if(cheatMode==1) {
			gameScene.selectDie(getAvailableSpaces(diePane));

		}else {
			System.out.println("not cheating");
		}
	}

	/**
	 * Finally place the die
	 * 
	 * @param Die - the to be placed die
	 * @param paceGlass newSpace- the space where its going to be placed
	 * @return boolean - true if succeeded
	 */
	public void placeDie(SpacePane spacePane) {
		DiePane diePane = gameScene.getSelectedDie();
		if (diePane != null) {
			ArrayList<SpaceGlass> available = getAvailableSpaces(diePane);
			for (SpaceGlass spaceGlass : available) {
				if (spaceGlass.getXCor() == spacePane.getX() && spaceGlass.getYCor() == spacePane.getY()) {
					game.placeDie(diePane.getNumber(), diePane.getColor(), spacePane.getX(), spacePane.getY());
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
		game.setFinalScore();
		gameScene.updateScore(game.getPlayers());
		for (Player player : game.getPlayers()) {
			if (maxScore < player.getScore()) {
				maxScore = player.getScore();
				winner = player;
			}
		}
		String winText = winner.getUsername() + " heeft het spel gewonnen met een score van:  " + maxScore;
		gameScene.gameFinish(winText);
	}
	public int cycleCheat() {
		switch (cheatMode) {
		case 0:
			 cheat();
			cheatMode++;
			break;
		case 1:
			advancedCheat();
			cheatMode++;
			break;
		case 2:
			cheatMode =0;
			break;
		default:
			System.err.println("GameController.cycle cheatmode: "+ cheatMode + "not recognized UwU, setting to 0");
			break; 
		}
		return cheatMode;
	}
	private void cheat() {
		
		
//		stage.hide();
//		Runtime rt = Runtime.getRuntime();
//		String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
//		try {
//			rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	private void advancedCheat() {
		
	}  
}
