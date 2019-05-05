package game;

import java.util.ArrayList;
import java.util.Random;

import database.PersistenceFacade;

public class Game {
	private final int totalToolCards = 12;
	private final int amountToolCards = 3;
	private final int totalCollectiveGoalCards = 10;
	private final int amountCollectiveGoalCards = 3;
	private final int totalPatternCards = 24;
	private final int amountPatternCards = 4;

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

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 */

	// YOU
	private Player clientUser;

	public Player getClientUser() {
		return clientUser;
	}

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
	}

	public void updateDB() {
		// TODO give the whole game object?
	}

	/**
	 * This method loads all Dice from the DB to the Game.
	 * 
	 * @param dice
	 */
	public void loadDice() {
		ArrayList<Die> dbDice = new ArrayList<Die>();
		for (Die die : dbDice) {
			if (die.getRound() != 0) {
				roundTrack[die.getRound() - 1].addDie(die);
			} else {
				this.dice.add(die);
			}
		}
	}

	/**
	 * Loads the chat
	 * 
	 * @param chat - The chat with all messages
	 */
	public void loadChat() {

	}

	/**
	 * Loads all the used cards
	 * 
	 * @param toolCards           - The used ToolCard
	 * @param collectiveGoalCards - The used CollectiveGoalCard
	 */
	public void loadCards(ArrayList<ToolCard> toolCards, ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.toolCards = toolCards;
		this.collectiveGoalCards = collectiveGoalCards;
	}

	/**
	 * Load the GlassWindow with the right PatternCard
	 * 
	 * @param glassWindows - GlassWindow with the right PatternCard
	 */
	public void loadGlassWindow(GlassWindow[] glassWindows) {
		//TODO rewrite this function
		this.glassWindows = glassWindows;
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

	/**
	 * Updates the chat
	 */
	public void updateChat() {
		// TODO wait on Chat

	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public GlassWindow[] getGlassWindows() {
		return glassWindows;
	}

	public ArrayList<Player> getPlayers() {
		return players;
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
