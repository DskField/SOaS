package client;

import java.util.ArrayList;

public class Lobby {

	private int gameID;
	private enum gameState{
		
		CREATING,
		PLAYING,
		TERMINATED
	}

	// The model described this as "turn". I renamed it "isMyTurn" to prevent any
	// confusion
	private boolean isMyTurn;

	private int currentRound;
	private int lobbySize;
	private int finalScore;

	// In the model it says 'won', I named it isMyVictory instead to prevent
	// confusion
	// It holds the value determining wether the client user has won a certain game
	// in a lobby or not
	private boolean isMyVictory;

	// The users  in a lobby
	private ArrayList<User> userList;

	public Lobby(int id) {

		this.gameID = id;
		userList = new ArrayList<User>();
	}

	public void addUserToLobby(User user) {

		userList.add(user);
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public int getLobbySize() {
		return lobbySize;
	}

	public void setLobbySize(int lobbySize) {
		this.lobbySize = lobbySize;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	public boolean isMyVictory() {
		return isMyVictory;
	}

	public void setMyVictory(boolean isMyVictory) {
		this.isMyVictory = isMyVictory;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setPlayerList(ArrayList<User> userList) {
		this.userList = userList;
	}

}
