package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
	private final int totalToolCards = 12;
	private final int amountToolCards = 3;
	private final int totalCollectiveGoalCards = 10;
	private final int amountCollectiveGoalCards = 3;
	private final int totalPatternCards = 24;
	private final int amountPatternCards = 4;

	// All arrays are temporary, they can be changed in the future to ArrayLists or HashMaps
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

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 * @param status - The current status of the game
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
	public void loadDice(Die[] dice) {
		for (int i = 0; i < dice.length; i++) {
			if (dice[i].getRound() != 0) {
				roundTrack[dice[i].getRound() - 1].addDie(dice[i]);
			} else {
				this.dice.add(dice[i]);
			}
		}
	}

	/**
	 * Loads the chat
	 * 
	 * @param chat - The chat with all messages
	 */
	public void loadChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * Loads all the used cards
	 * 
	 * @param toolCards - The used ToolCard
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
		this.glassWindows = glassWindows;
	}

	public void loadPlayers(ArrayList<Player> players) {
		this.players.addAll(players);
	}

	// TODO wait on Card
	public void shakePiles() {
		// toolCards = getToolCards(getRandomNotEqualInts(amountToolCards, totalToolCards));
		// collectiveGoalCards = getCollectiveGoalCards(getRandomNotEqualInts(amountCollectiveGoalCards, totalCollectiveGoalCards));
	}

	// TODO wait on PatternCard
	public void dealPatternCards() {
		// patternCards = getPatternCardsFromID(getRandomNotEqualInts(amountPatternCards, totalPatternCards));
	}

	/**
	 * This function gives you an array with random numbers chosen from a list with consecutively
	 * numbers based on you max
	 * 
	 * @param amount - the amount of numbers that will be generated, this needs to be less then the max
	 * @param max - the highest number that can be generated
	 * @return an array with the random generated numbers
	 */
	private int[] getRandomNotEqualInts(int amount, int max) {
		int[] ints = new int[amount];

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= max; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i = 0; i < amount; i++) {
			ints[i] = list.get(i);
		}

		return ints;
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

		// TODO send a update to the DB with the values of the die that just got added
	}

	public void nextTurn() {
		// TODO just a stub
	}

	public void nextRound() {
		if (!table.isEmpty()) {
			roundTrack[currentRound].addDice(table);
			table.clear();
			currentRound++;

			// TODO send the new version of the roundtrack to the DB
		}
	}

	public void calculateScore() {
		// TODO wait on CardHandler
	}

	public void calculatePublicScore() {
		// TODO wait on CardHanler and GlassWindow
	}

	public void calculateFinalScore() {
		// TODO wait on CardHandle and GlassWindow
	}

	public void useToolCard() {
		// TODO wait on CardHandler and GlassWindow
	}

	public void placeDie() {
		// TODO wait on GlassWindow
	}

	/**
	 * updates the chat
	 * 
	 * @param messages - new messages
	 */
	public void updateChat(ArrayList<Message> messages) {
		// TODO wait on Chat

	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public PatternCard[] getPatternCards() {
		return patternCards;
	}

	// TODO rewrite method
	public void setPatternCards(PatternCard[] patternCards) {
		this.patternCards = patternCards;
	}

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
