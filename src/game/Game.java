package game;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	// All arrays are temporary, they can be changed in the future to ArrayLists or HashMaps
	private PatternCard[] patternCards;
	private GlassWindow[] glassWindows;

	private ArrayList<Player> players;

	private CurrencyStone[] currencyStones;
	private ToolCard[] toolCards;
	private CollectiveGoalCard[] collectiveGoalCards;

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
	private int status;
	private int currentRound;

	/**
	 * Initialize the game
	 * 
	 * @param gameID - The id of the game
	 * @param status - The current status of the game
	 */
	public Game(int gameID, int status) {
		this.gameID = gameID;
		this.status = status;

		patternCards = new PatternCard[16];
		glassWindows = new GlassWindow[4];
		players = new ArrayList<Player>();
		currencyStones = new CurrencyStone[24];
		toolCards = new ToolCard[3];
		collectiveGoalCards = new CollectiveGoalCard[3];
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

	public void loadGame() {
		// TODO write method
	}

	/**
	 * This method loads all Dice from the DB to the Game.
	 * 
	 * @param dice
	 */
	private void loadDice(Die[] dice) {
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
	private void loadChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * Loads all the used cards
	 * 
	 * @param toolCards - The used ToolCard
	 * @param collectiveGoalCards - The used CollectiveGoalCard
	 */
	private void loadCards(ToolCard[] toolCards, CollectiveGoalCard[] collectiveGoalCards) {
		this.toolCards = toolCards;
		this.collectiveGoalCards = collectiveGoalCards;
	}

	/**
	 * Load the GlassWindow with the right PatternCard
	 * 
	 * @param glassWindows - GlassWindow with the right PatternCard
	 */
	private void loadGlassWindow(GlassWindow[] glassWindows) {
		this.glassWindows = glassWindows;
	}

	private void loadRoundTrack() {
		// TODO write method
	}

	public void shakePiles() {
		// TODO just a stub
	}

	public void dealPatternCards() {
		// TODO just a stub
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
	}

	public void nextTurn() {
		// TODO just a stub
	}

	public void nextRound() {
		// TODO just a stub
	}

	public void calculateScore() {
		// TODO wait on CardHandler
	}

	public void calculatePublicScore() {
		// TODO wait on CardHanler
	}

	public void calculateFinalScore() {
		// TODO wait on CardHandle
	}

	public void useToolCard() {
		// TODO wait on CardHandler
	}

	public void placeDie() {
		// TODO wait on GlassWindow
	}

	public void updateChat() {
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

	public CurrencyStone[] getCurrencyStones() {
		return currencyStones;
	}

	public ToolCard[] getToolCards() {
		return toolCards;
	}

	public CollectiveGoalCard[] getCollectiveGoalCards() {
		return collectiveGoalCards;
	}

	public ArrayList<Die> getDice() {
		return dice;
	}

	public Round[] getRoundTrack() {
		return roundTrack;
	}

	// TODO rewrite to get messages instead of chat
	public Chat getChat() {
		return chat;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getGameID() {
		return gameID;
	}

	public int getStatus() {
		return status;
	}
}
