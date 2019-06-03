package client;

import game.GameColor;

public class User {

	/* VARIABLES */
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int maxScore;
	private GameColor mostPlacedColor;
	private int mostPlacedValue;
	private int totalOpponents;
	private String username;

	/**
	 * Constructor used to create a User object
	 * 
	 * @param username - {@code String} containing the username
	 * @param gamesPlayed - {@code int} containing the total amount of games played
	 * @param maxScore - {@code int} containing the maximum score in the database
	 * @param mostPlacedColor - {@code GameColor} containing the most placed Color in the database
	 * @param mostPlacedValue - {@code int} containing the most placed eyes in the database
	 * @param wins - {@code int} containing the amount of won games
	 * @param loses - {@code int} containing the amount of lost games
	 * @param totalOpponents - {@code int} containing the total amount of unique opponents
	 */
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

	/* GETTERS AND SETTERS */
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