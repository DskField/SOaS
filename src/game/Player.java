package game;

public class Player {
	// All arrays are temporary, they can be changed in the future to ArrayLists or HashMaps
	private CurrencyStone[] currencyStones;
	private GlassWindow glassWindow;
	private PersonalGoalCard personalGoalCard;

	private int playerId;
	private int currentScore;
	private int position;

	private boolean turn;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public void chooseDie() {
		// TODO just a stub
	}

	public void layDie() {
		// TODO just a stub
	}

	public void loadCard() {
		// TODO just a stub
	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public GlassWindow getGlassWindow() {
		return glassWindow;
	}

	public void setGlassWindow(GlassWindow glassWindow) {
		this.glassWindow = glassWindow;
	}

	public CurrencyStone[] getCurrencyStones() {
		return currencyStones;
	}

	public void setCurrencyStones(CurrencyStone[] currencyStones) {
		this.currencyStones = currencyStones;
	}

	public PersonalGoalCard getPersonalGoalCard() {
		return personalGoalCard;
	}

	public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
		this.personalGoalCard = personalGoalCard;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}
