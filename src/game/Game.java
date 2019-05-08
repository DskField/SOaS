package game;

import java.util.ArrayList;
import java.util.Random;

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
	 * table is the list with die that are rolled but not placed or in the round track. That means they
	 * are the dice to choose out of this round
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
	private Player clientUser;

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 */

	public Game(int gameID) {
		this.gameID = gameID;

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

		//kevin stuff sets a client user for testing
		clientUser = new Player(5, 3, GameColor.RED, "Adri");
	}

	public void updateGame() {
		// TODO give the whole game object?
	}

	/**
	 * This method loads all Dice from the DB to the Game.
	 */
	public void loadDice() {
		dice = persistenceFacade.getGameDice(gameID);
		roundTrack = persistenceFacade.getRoundTrack(gameID);
	}

	/**
	 * Loads the chat
	 */
	public ArrayList<Message> loadChat() {
		ArrayList<Message> messages = persistenceFacade.getALLMessages(players);
		chat.addMessages(messages);
		return messages;
	}

	/**
	 * Load the GlassWindow with the right PatternCard
	 */
	public void loadGlassWindow() {
		//TODO write this function
	}

	/**
	 * Load the players from this game
	 */
	public void loadPlayers() {
		this.players = persistenceFacade.getAllPlayersInGame(gameID);
	}

	/**
	 * Pick random Tool and Goal cards
	 */
	public void shakePiles() {
		persistenceFacade.insertRandomGameToolCards(gameID);
		persistenceFacade.insertRandomSharedCollectiveGoalCards(gameID);
		loadCards();
	}

	/**
	 * Get the Tool and Goal cards form the DB
	 */
	public void loadCards() {
		toolCards = persistenceFacade.getGameToolCards(gameID);
		collectiveGoalCards = persistenceFacade.getSharedCollectiveGoalCards(gameID);
	}

	// TODO wait on PatternCard
	public void dealPatternCards() {
		// patternCards = getPatternCardsFromID(getRandomNotEqualInts(amountPatternCards, totalPatternCards));
	}

	/**
	 * Removes the die from the list with dice and places them on the list table. It also rolls the dice
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

	public void nextTurn() {
		// TODO just a stub
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

	public ArrayList<Message> updateChat() {
		ArrayList<Message> messages = persistenceFacade.updateChat(getPlayers(), chat.getLastTimestamp());
		chat.addMessages(messages);
		return messages;
	}

	public ArrayList<Message> sendMessage(Message message) {
		persistenceFacade.insertMessage(message);
		return updateChat();
	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public Player getClientUser() {
		return clientUser;
	}

	public GlassWindow[] getGlassWindows() {
		return glassWindows;
	}

	public ArrayList<Player> getPlayers() {
		return players;
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
