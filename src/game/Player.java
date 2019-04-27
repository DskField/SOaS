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
	
	private boolean isCurrentPlayer;

	public Player(int playerID, int seqnr, GameColor personalGoalCard, String username) {
		currencyStones = new ArrayList<>();
		glassWindow = new GlassWindow();
		this.personalGoalCard = personalGoalCard;
		this.playerID = playerID;
		this.seqnr = seqnr;
		this.username = username;
	}

	public void chooseDie() {
		// TODO waiting for Sioux
	}

	public void layDie() {
		// TODO waiting for Sioux
	}

	public void loadCard() {
		// TODO waiting for Sioux
	}
	
	// GETTERS AND SETTERS
	public ArrayList<CurrencyStone> getCurrencyStones() {
		return currencyStones;
	}
	
	public void setCurrencyStones(ArrayList<CurrencyStone> currencyStones) {
		this.currencyStones = currencyStones;
	}
	
	public GlassWindow getGlassWindow() {
		return glassWindow;
	}
	
	public GameColor getPersonalGoalCard() {
		return personalGoalCard;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public int getPosition() {
		return seqnr;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}
	
	public void setCurrentPlayer(boolean isCurrentPlayer) {
		this.isCurrentPlayer = isCurrentPlayer;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
