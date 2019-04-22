package game;

import java.util.ArrayList;

public class Player {
	private ArrayList<CurrencyStone> currencyStones;
	private GlassWindow glassWindow;
	private GameColor personalGoalCard;

	private int playerID;
	private int seqnr;

	private boolean turn;

	public Player(int playerID, int seqnr, GameColor personalGoalCard) {
		currencyStones = new ArrayList<>();
		glassWindow = new GlassWindow();
		this.personalGoalCard = personalGoalCard;
		this.playerID = playerID;
		this.seqnr = seqnr;
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
	public ArrayList<CurrencyStone> getCurrencyStone() {
		return currencyStones;
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
	
	public boolean isTurn() {
		return turn;
	}
	
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
}
