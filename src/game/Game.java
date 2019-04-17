package game;

public class Game {
	// All arrays are temporary, they can be changed in the future to ArrayLists or HashMaps
	private PatternCard[] patternCards;
	private GlassWindow[] glassWindows;
	private Player[] players;
	private CurrencyStone[] currencyStones;
	private ToolCard[] toolCards;
	private CollectiveGoalCard[] collectiveGoalCards;
	private Die[] dies;
	private Round[] roundTrack;

	private CardHandler cardHandler;
	private Chat chat;
	private Player currentPlayer;

	private int gameID;
	private int status;

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public void shakePiles() {
		// TODO just a stub
	}

	public void dealPatternCards() {
		// TODO just a stub
	}

	public void shakeSack() {
		// TODO just a stub
	}

	public void nextTurn() {
		// TODO just a stub
	}

	public void calculateScore() {
		// TODO just a stub
	}

	public void calculatePublicScore() {
		// TODO just a stub
	}

	public void calculateFinalScore() {
		// TODO just a stub
	}

	public void useToolCard() {
		// TODO just a stub
	}

	public void placeDie() {
		// TODO just a stub
	}

	public void updateGame() {
		// TODO just a stub
	}

	public void updateChat() {
		// TODO just a stub
	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public PatternCard[] getPatternCards() {
		return patternCards;
	}

	public void setPatternCards(PatternCard[] patternCards) {
		this.patternCards = patternCards;
	}

	public GlassWindow[] getGlassWindows() {
		return glassWindows;
	}

	public void setGlassWindows(GlassWindow[] glassWindows) {
		this.glassWindows = glassWindows;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public CurrencyStone[] getCurrencyStones() {
		return currencyStones;
	}

	public void setCurrencyStones(CurrencyStone[] currencyStones) {
		this.currencyStones = currencyStones;
	}

	public ToolCard[] getToolCards() {
		return toolCards;
	}

	public void setToolCards(ToolCard[] toolCards) {
		this.toolCards = toolCards;
	}

	public CollectiveGoalCard[] getCollectiveGoalCards() {
		return collectiveGoalCards;
	}

	public void setCollectiveGoalCards(CollectiveGoalCard[] collectiveGoalCards) {
		this.collectiveGoalCards = collectiveGoalCards;
	}

	public Die[] getDies() {
		return dies;
	}

	public void setDies(Die[] dies) {
		this.dies = dies;
	}

	public Round[] getRoundTrack() {
		return roundTrack;
	}

	public void setRoundTrack(Round[] roundTrack) {
		this.roundTrack = roundTrack;
	}

	public CardHandler getCardHandler() {
		return cardHandler;
	}

	public void setCardHandler(CardHandler cardHandler) {
		this.cardHandler = cardHandler;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
