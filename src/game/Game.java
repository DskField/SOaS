package game;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import client.User;
import controllers.ScoreHandler;
import database.PersistenceFacade;

public class Game {
	private GlassWindow[] glassWindows;

	private ArrayList<Player> players;

	private ArrayList<CurrencyStone> currencyStones;
	private ArrayList<ToolCard> toolCards;
	private ArrayList<CollectiveGoalCard> collectiveGoalCards;

	/**
	 * dice will only contain Die that did not get rolled
	 */
	private ArrayList<Die> dice;
	private Round[] roundTrack;

	private ScoreHandler scoreHandler;

	/**
	 * table is the list with die that are rolled but not placed or in the round
	 * track. That means they are the dice to choose out of this round
	 */
	private ArrayList<Die> table;

	private Chat chat;
	private Player currentPlayer;

	private Random random;

	private int gameID;
	private int currentRound;

	public PersistenceFacade persistenceFacade;

	// YOU
	private Player clientPlayer;
	private User clientUser;

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 */

	public Game(int gameID, User clientUser, PersistenceFacade persistencefacade) {
		this.gameID = gameID;
		this.clientUser = clientUser;

		glassWindows = new GlassWindow[4];
		players = new ArrayList<Player>();
		currencyStones = new ArrayList<CurrencyStone>();
		dice = new ArrayList<Die>();
		roundTrack = new Round[10];
		currentRound = 0;

		table = new ArrayList<Die>();

		chat = new Chat();

		random = new Random();

		persistenceFacade = persistencefacade;

		for (int i = 0; i < roundTrack.length; i++) {
			roundTrack[i] = new Round();
		}

	}

	public void loadGame() {
		loadPlayers();
		loadCards();
		loadGlassWindow();
		loadCurrencyStones();
		loadCurrentPlayer();
		loadCurrentRound();
		loadDice();
		scoreHandler = new ScoreHandler(collectiveGoalCards);
	}

	public void loadCurrentRound() {
		currentRound = persistenceFacade.getCurrentRound(gameID);
	}

	/**
	 * This method loads all Dice from the DB to the Game.
	 */
	private void loadDice() {
		dice = persistenceFacade.getGameDice(gameID);
		roundTrack = persistenceFacade.getRoundTrack(gameID);
		table = persistenceFacade.getTableDice(gameID, currentRound);
		if (currentRound <= 10) {
			if (table.isEmpty() && roundTrack[currentRound - 1].getDice().isEmpty()
					&& currentPlayer.getPlayerID() == clientPlayer.getPlayerID()) {
			}
		}
	}

	/**
	 * Load the players from this game
	 */
	private void loadPlayers() {
		players = persistenceFacade.getAllPlayersInGame(gameID);
		for (Player player : players) {
			if (player.getUsername().equals(clientUser.getUsername())) {
				clientPlayer = player;
				break;
			}
		}
	}

	/**
	 * Get the Tool and Goal cards form the DB
	 */
	private void loadCards() {
		toolCards = persistenceFacade.getGameToolCards(gameID);
		collectiveGoalCards = persistenceFacade.getSharedCollectiveGoalCards(gameID);
	}

	/**
	 * Load the GlassWindow with the right PatternCard
	 */
	private void loadGlassWindow() {
		final GameColor colors[] = { GameColor.RED, GameColor.GREEN, GameColor.BLUE, GameColor.PURPLE };
		int num = 1;
		for (Player player : players) {
			player.loadGlassWindow(persistenceFacade.getGlassWindow(player.getPlayerID()));
			PatternCard card = persistenceFacade.getplayerPatternCard(player.getPlayerID());
			if (card != null) {
				player.getGlassWindow().loadPatternCard(card);
			} else {
				player.getGlassWindow().loadPatternCard(null);
			}

			if (player.getPlayerID() == clientPlayer.getPlayerID()) {
				player.getGlassWindow().setColor(colors[0]);
			} else {
				player.getGlassWindow().setColor(colors[num++]);
			}
		}

	}
	/*
	 * used for loading and updating currencystones
	 */
	public void loadCurrencyStones() {
		currencyStones = persistenceFacade.getAllStonesInGame(gameID);
		for (Player player : players) {
			player.clearCurrencyStones();

			for (CurrencyStone cs : currencyStones) {

				if (cs.getPlayerID() == player.getPlayerID()) {
					player.addCurrencyStone(cs);
				}

			}

		}
		for (ToolCard toolCard : toolCards) {
			for (CurrencyStone currencyStone : persistenceFacade.getCurrencyStonesOnCard(toolCard.getCardID(),
					gameID)) {
				toolCard.addCurrencyStone(currencyStone);
			}

		}

	}

	public void loadCurrentPlayer() {
		int id = persistenceFacade.getCurrentPlayer(gameID).getPlayerID();
		for (Player player : players) {
			if (player.getPlayerID() == id) {
				currentPlayer = player;
			}
		}
	}

	// kevin stuff
	public void setFinalScore() {
		for (Player player : players) {
			int score = scoreHandler.getScore(player, true);
			player.setScore(score);
			if(clientPlayer.getSeqnr() == getPlayers().size()) {
				persistenceFacade.updateScore(scoreHandler.getScore(player, false), player.getPlayerID());
			}
		}
	}

	public void updateCurrencyStone(int idPlayer, int ammount) {
		persistenceFacade.updateGivePlayerCurrencyStones(gameID, idPlayer, ammount);
	}

	public ArrayList<Player> getPlayersWithoutPatternCards() {
		return persistenceFacade.getPlayersWithoutPatternCard(gameID);
	}

	public ArrayList<Player> getPlayerWithPatternCardButWithoutCurrencyStones() {
		return persistenceFacade.getPlayerWithPatternCardButWithoutCurrencyStones(gameID);
	}

	// kevin stuff
	public void updateClientPlayerScore() {
		persistenceFacade.updateScore(scoreHandler.getScore(clientPlayer, false), clientPlayer.getPlayerID());
	}

	public void setClientPlayerPaternCard(int idPatternCard) {

		persistenceFacade.setPlayerPaternCard(idPatternCard, clientPlayer.getPlayerID());
		clientPlayer.getGlassWindow()
				.loadPatternCard(persistenceFacade.getplayerPatternCard(clientPlayer.getPlayerID()));
	}

	public PatternCard getPlayerPatternCard(int idPlayer) {
		return persistenceFacade.getplayerPatternCard(idPlayer);
	}

	public ArrayList<PatternCard> patternChoices(int idPlayer) {
		return persistenceFacade.getPlayerOptions(idPlayer);
	}
	// end kevin stuff

	/**
	 * Removes the die from the list with dice and places them on the list table. It
	 * also rolls the dice
	 */
	public void shakeSack() {
		updateDice();

		int numDice = players.size() * 2 + 1;
		for (int i = 0; i < numDice; i++) {
			int index = random.nextInt(dice.size());
			dice.get(index).roll(currentRound);
			table.add(dice.get(index));
			dice.remove(index);
			persistenceFacade.updateDiceRoll(gameID, table);
		}
	}

	public void updateDice() {
		dice = persistenceFacade.getGameDice(gameID);
	}

	/**
	 * Pick random Tool and Goal cards
	 */
	public void shakePiles() {
		persistenceFacade.insertRandomGameToolCards(gameID);
		persistenceFacade.insertRandomSharedCollectiveGoalCards(gameID);
		loadCards();
	}

	public void updatePlayers() {
		ArrayList<Player> tempPlayers = persistenceFacade.getAllPlayersInGame(gameID);

		for (Player temp : tempPlayers) {
			for (Player player : players) {
				if (temp.getPlayerID() == player.getPlayerID()) {
					player.setSeqnr(temp.getSeqnr());
				}
			}
		}
	}

	public void setSeqnr(Player player, int newSeqnr) {
		for (Player P : players) {
			if (P.getPlayerID() == player.getPlayerID()) {
				P.setSeqnr(newSeqnr);
			}
		}
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public void updatePlayerTurn(Player oldPlayer) {
		persistenceFacade.updatePlayerTurn(oldPlayer, currentPlayer, gameID);
	}

	public void nextRound() {
		if (!table.isEmpty()) {
			roundTrack[currentRound - 1].addDice(table);
			persistenceFacade.updateDiceRound(gameID, currentRound, table);
			table.clear();
			currentRound++;
		}
	}

	/**
	 * gets new Messages from the database and adds it to the chat.
	 * 
	 * @return ArrayList<Messages> list of Messages that need to be added to the
	 *         ChatPane
	 */
	public ArrayList<Message> updateChat() {
		ArrayList<Message> messages = persistenceFacade.updateChat(players, chat.getLastTimestamp());
		chat.addMessages(messages);
		return messages;
	}

	public ArrayList<Player> updateGlassWindow() {
		for (Player player : players) {
			if (player.equals(clientPlayer)) {
				player.setScore(scoreHandler.getScore(player, true));
			} else {
				player.setScore(scoreHandler.getScore(player, false));
			}

			player.getGlassWindow().loadSpaces(persistenceFacade.getGlassWindow(player.getPlayerID()).getSpaces());
		}

		return players;
	}

	public void placeDie(int id, GameColor color, int x, int y) {
		for (Die die : table) {
			if (die.getDieId() == id && die.getDieColor() == color) {
				for (Player player : players) {
					if (player.getPlayerID() == currentPlayer.getPlayerID()) {
						player.getGlassWindow().placeDie(x, y, die);
						persistenceFacade.updateSpaceGlass(player.getPlayerID(), player.getGlassWindow(), gameID);
						break;
					}
				}

				table.remove(table.indexOf(die));
				break;
			}
		}
	}

	/**
	 * Checks if the new Message has the same primary key as the message before it.
	 * If this is the case the method wil return an ArrayList<Message> containing an
	 * error message. Otherwise the message will be send to the database for
	 * insertion. After insertion this method will call upon the updateChat function
	 * to update the chat from the database.
	 * 
	 * @param message - the Message that needs to be send to the database
	 * @return ArrayList<Message> list of messages that need to be added to the
	 *         ChatPane
	 */
	public ArrayList<Message> sendMessage(Message message) {
		if (message.getChatTime().equals(chat.getLastChatTime())) {
			Message error = new Message("please don't spam you can only send 1 message a second", getClientPlayer(),
					new Timestamp(System.currentTimeMillis()));
			ArrayList<Message> messages = new ArrayList<Message>();
			messages.add(error);
			return messages;
		} else {
			persistenceFacade.insertMessage(message);
			return updateChat();
		}
	}

	// GETTERS AND SETTERS
	public Player getClientPlayer() {
		return clientPlayer;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public GlassWindow[] getGlassWindows() {
		return glassWindows;
	}

	public ArrayList<Die> getTable() {
		table = persistenceFacade.getTableDice(gameID, currentRound);
		return table;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public ArrayList<CurrencyStone> getCurrencyStones() {
		return currencyStones;
	}

	public ArrayList<ToolCard> getToolCards() {
		return toolCards;
	}

	public ArrayList<CollectiveGoalCard> getCollectiveGoalCards() {
		return collectiveGoalCards;
	}

	public ArrayList<Die> getDice() {
		return dice;
	}

	public Round[] getRoundTrack() {
		roundTrack = persistenceFacade.getRoundTrack(gameID);
		return roundTrack;
	}

	public Chat getChat() {
		return chat;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getGameID() {
		return gameID;
	}
}
