package client;

import game.GameColor;

public class User {

	// variables
	private int gamesPlayed;
	private int gamesWon;
	private int gamesLost;
	private int maxScore;
	private GameColor mostPlacedColor;
	private int mostPlacedValue;
	private int totalOpponents;
	private String username;

	/**
	 * Constrctor used to create a User object
	 * 
	 * @param username
	 *            - String containing the username
	 * @param gamesPlayed
	 *            - int containing the total amount of games played
	 * @param maxScore
	 *            - int containing the maximum score in the database
	 * @param mostPlacedColor
	 *            - GameColor containing the most placed Color in the database
	 * @param mostPlacedValue
	 *            - int containing the most placed eyes in the database
	 * @param wins
	 *            - int containing the amount of won games
	 * @param loses
	 *            - int containing the amount of lost games
	 * @param totalOpponents
	 *            - int containing the total amount of unique opponents
	 */
	public User(String username, int gamesPlayed, int maxScore, GameColor mostPlacedColor, int mostPlacedValue, int wins, int loses,
			int totalOpponents) {
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