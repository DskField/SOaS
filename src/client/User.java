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

	public User(String name) {

		this.loginName = name;
	}
	
	public void calcLostGames() {	
		this.totalLosses = this.totalGames - this.totalWins; 
	}
	
	public void loadStats() {
		
		// Grab stats from queries in UserDAO
		// TODO Waiting on ClientController
	}

	// Getters and setters
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
}
