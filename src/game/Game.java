package game;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import client.User;
import database.PersistenceFacade;

public class Game {

	// All arrays are temporary, they can be changed in the future to ArrayLists or
	// HashMaps
	private PatternCard[] patternCards;
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

	/**
	 * table is the list with die that are rolled but not placed or in the round
	 * track. That means they are the dice to choose out of this round
	 */
	private ArrayList<Die> table;

	private CardHandler cardHandler;
	private Chat chat;
	private Player currentPlayer;

	private Random random;

	private int gameID;
	private int currentRound;

	private PersistenceFacade persistenceFacade;

	// YOU
	private Player clientPlayer;
	private User clientUser;

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 */

	public Game(int gameID, User clientUser) {
		this.gameID = gameID;
		this.clientUser = clientUser;

		glassWindows = new GlassWindow[4];
		players = new ArrayList<Player>();
		currencyStones = new ArrayList<CurrencyStone>();
		dice = new ArrayList<Die>();
		roundTrack = new Round[10];
		currentRound = 1;

		table = new ArrayList<Die>();

		cardHandler = new CardHandler();
		chat = new Chat();

		random = new Random();

		persistenceFacade = new PersistenceFacade();

		for (int i = 0; i < roundTrack.length; i++) {
			roundTrack[i] = new Round();
		}
	}

	public void loadGame() {
		loadDice();
		loadPlayers();
		loadCards();
		loadGlassWindow();
		loadCurrencyStones();
	}

	/**
	 * This method loads all Dice from the DB to the Game.
	 */
	private void loadDice() {
		dice = persistenceFacade.getGameDice(gameID);
		roundTrack = persistenceFacade.getRoundTrack(gameID);
	}

	/**
	 * Load the players from this game
	 */
	private void loadPlayers() {
		players = persistenceFacade.getAllPlayersInGame(gameID);
		currentPlayer = persistenceFacade.getCurrentPlayer(gameID);
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
		int num = 0;
		for (Player player : players) {
			player.loadGlassWindow(persistenceFacade.getGlassWindow(player.getPlayerID()));
			player.getGlassWindow().loadPatternCard(persistenceFacade.getplayerPatternCard(player.getPlayerID()).get(0));
			player.getGlassWindow().setColor(colors[num++]);
		}

	}

	private void loadCurrencyStones() {
		currencyStones = persistenceFacade.getAllStonesInGame(gameID);

		for (CurrencyStone cs : currencyStones) {
			for (Player player : players) {
				if (cs.getPlayerID() == player.getPlayerID()) {
					player.addCurrencyStone(cs);
				}
			}
			for (ToolCard toolCard : toolCards) {
				if (cs.getCardID() == toolCard.getCardID()) {
					toolCard.addCurrencyStone(cs);
				}
			}
		}
	}

	public void dealPatternCards() {
		for (Player player : players) {
			//			persistenceFacade.insertPatternCardOptions(player.getPlayerID());
		}
	}

	/**
	 * Removes the die from the list with dice and places them on the list table. It
	 * also rolls the dice
	 */
	public void shakeSack() {
		int numDice = players.size() * 2 + 1;
		for (int i = 0; i < numDice; i++) {
			int index = random.nextInt(dice.size());
			dice.get(index).roll(currentRound);
			table.add(dice.get(index));
			dice.remove(index);
		}

		persistenceFacade.updateDiceRoll(gameID, table);
	}

	/**
	 * Pick random Tool and Goal cards
	 */
	public void shakePiles() {
		persistenceFacade.insertRandomGameToolCards(gameID);
		persistenceFacade.insertRandomSharedCollectiveGoalCards(gameID);
		loadCards();
	}

	public void nextTurn() {
		int totalPlayers = players.size();
		int maxSeqnr = totalPlayers * 2;
		int nextSeqnr = 0;

		switch (currentPlayer.getSeqnr()) {
		case 1:
			currentPlayer.setSeqnr(maxSeqnr);
			nextSeqnr = 2;
			break;
		case 2:
			currentPlayer.setSeqnr(maxSeqnr - 1);
			nextSeqnr = 3;
			break;
		case 3:
			if (totalPlayers == 2) {
				currentPlayer.setSeqnr(1);
			} else {
				currentPlayer.setSeqnr(maxSeqnr - 2);
			}
			nextSeqnr = 4;
			break;
		case 4:
			if (totalPlayers == 2) {
				currentPlayer.setSeqnr(2);
				nextSeqnr = 1;
				break;
			} else if (totalPlayers == 3) {
				currentPlayer.setSeqnr(2);
			} else if (totalPlayers == 4) {
				currentPlayer.setSeqnr(maxSeqnr - 3);
			}
			nextSeqnr = 5;
			break;
		case 5:
			if (totalPlayers == 3) {
				currentPlayer.setSeqnr(1);
			} else if (totalPlayers == 4) {
				currentPlayer.setSeqnr(3);
			}
			nextSeqnr = 6;
			break;
		case 6:
			if (totalPlayers == 3) {
				currentPlayer.setSeqnr(3);
				nextSeqnr = 1;
				break;
			} else if (totalPlayers == 4) {
				currentPlayer.setSeqnr(2);
				nextSeqnr = 7;
				break;
			}
		case 7:
			currentPlayer.setSeqnr(1);
			nextSeqnr = 8;
			break;
		case 8:
			currentPlayer.setSeqnr(4);
			nextSeqnr = 1;
			break;
		}

		Player oldPlayer = currentPlayer;

		for (Player player : players) {
			if (player.getSeqnr() == nextSeqnr) {
				currentPlayer = player;
			}
		}

		persistenceFacade.updatePlayerTurn(oldPlayer, currentPlayer);

		if (nextSeqnr == 1) {
			nextRound();
		}
	}

	public void nextRound() {
		if (!table.isEmpty()) {
			roundTrack[currentRound].addDice(table);
			persistenceFacade.updateDiceRound(gameID, currentRound, table);
			table.clear();
			currentRound++;
		}
	}

	public void useToolCard() {
		// TODO wait on CardHandler
	}

	public void placeDie() {
		// TODO wait on GlassWindow
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
	// TODO the current getters and setters are temporary, they will be changed in
	// the future
	public Player getClientPlayer() {
		return clientPlayer;
	}

	public GlassWindow[] getGlassWindows() {
		return glassWindows;
	}

	public ArrayList<Die> getTable() {
		return table;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	// TODO rewrite method
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
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
