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
	
	public User(String username, int gamesPlayed, int gamesWon, int gamesLost, int maxScore, GameColor mostPlacedColor, int mostPlacedValue, int totalOpponents) {
		this.username = username;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.maxScore = maxScore;
		this.mostPlacedColor = mostPlacedColor;
		this.mostPlacedValue = mostPlacedValue;
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
