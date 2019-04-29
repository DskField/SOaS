package client;

import game.GameColor;

public class User {

	private int userID;
	private String loginName;

	private int totalGames;
	private int totalWins;
	private int totalLosses;
	private int maxScore;
	private GameColor mostPlacedColor;
	private int mostPlacedValue;
	private int totalOpponents;

	public User(int id, String name) {

		this.userID = id;
		this.loginName = name;
	}

	// Getters and setters voor User
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String name) {
		this.loginName = name;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public int getTotalWins() {
		return totalWins;
	}

	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

	public int getTotalLosses() {
		return totalLosses;
	}

	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public GameColor getMostPlacedColor() {
		return mostPlacedColor;
	}

	public void setMostPlacedColor(GameColor mostPlacedColor) {
		this.mostPlacedColor = mostPlacedColor;
	}

	public int getMostPlacedValue() {
		return mostPlacedValue;
	}

	public void setMostPlacedValue(int mostPlacedValue) {
		this.mostPlacedValue = mostPlacedValue;
	}

	public int getTotalOpponents() {
		return totalOpponents;
	}

	public void setTotalOpponents(int totalOpponents) {
		this.totalOpponents = totalOpponents;
	}
}
