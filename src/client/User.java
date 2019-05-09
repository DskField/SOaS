package client;

import game.GameColor;

public class User {

	private String username;

	// User stats
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int maxScore;
	private GameColor mostPlacedColor;
	private int mostPlacedValue;
	private int totalOpponents;

	public User(String username, int gamesPlayed, int maxScore, GameColor mostPlacedColor, int mostPlacedValue) {
		this.username = username;
		this.gamesPlayed = gamesPlayed;
		this.maxScore = maxScore;
		this.mostPlacedColor = mostPlacedColor;
		this.mostPlacedValue = mostPlacedValue;
	}

	// SETTERS
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}

	public void setTotalOpponents(int totalOpponents) {
		this.totalOpponents = totalOpponents;
	}

	// GETTERS
	public String getUsername() {
		return username;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public GameColor getMostPlacedColor() {
		return mostPlacedColor;
	}

	public int getMostPlacedValue() {
		return mostPlacedValue;
	}

	public int getTotalOpponents() {
		return totalOpponents;
	}
}
