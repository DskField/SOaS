package game;

import java.util.ArrayList;

public class Player {
	private ArrayList<CurrencyStone> currencyStones;
	private GlassWindow glassWindow;
	private GameColor personalGoalCard;

	private int playerID;
	private String username;
	private int seqnr;
	private int score;

	public Player(int playerID, int seqnr, GameColor personalGoalCard, String username) {
		currencyStones = new ArrayList<>();
		this.personalGoalCard = personalGoalCard;
		this.playerID = playerID;
		this.seqnr = seqnr;
		this.username = username;
	}

	public void addCurrencyStone(CurrencyStone currencyStone) {
		this.currencyStones.add(currencyStone);
	}

	// GETTERS AND SETTERS
	public GameColor getColor() {
		return glassWindow.getColor();
	}

	public ArrayList<CurrencyStone> getCurrencyStones() {
		return currencyStones;
	}

	public GlassWindow getGlassWindow() {
		return glassWindow;
	}

	public void loadGlassWindow(GlassWindow glassWindow) {
		this.glassWindow = glassWindow;
	}

	public GameColor getPersonalGoalCard() {
		return personalGoalCard;
	}

	public int getPlayerID() {
		return playerID;
	}

	public int getSeqnr() {
		return seqnr;
	}

	public void setSeqnr(int seqnr) {
		this.seqnr = seqnr;
	}

	public String getUsername() {
		return username;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
	/**
	 * remove all the currencystones this player has
	 */
	public void clearCurrencyStones() {
		currencyStones.clear();
	}
}
