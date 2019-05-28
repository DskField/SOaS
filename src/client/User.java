package client;

import game.GameColor;

public class User {

	// User stats
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int maxScore;
	private GameColor mostPlacedColor;
	private int mostPlacedValue;
	private int totalOpponents;
	private String username;

	public User(String username, int gamesPlayed, int maxScore, GameColor mostPlacedColor, int mostPlacedValue, int wins, int loses, int totalOpponents) {
		this.gamesPlayed = gamesPlayed;
		this.maxScore = maxScore;
		this.mostPlacedColor = mostPlacedColor;
		this.mostPlacedValue = mostPlacedValue;
		this.username = username;
		this.gamesWon = wins;
		this.gamesLost = loses;
		this.totalOpponents = totalOpponents;
	}

	// GETTERS
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

	public String getUsername() {
		return username;
	}
}