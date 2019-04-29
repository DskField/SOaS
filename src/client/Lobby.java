package client;

import java.util.ArrayList;

import game.Player;

public class Lobby {

	private int gameID;
	private String gameState;

	// The model described this as "turn". I renamed it "isMyTurn" to prevent any
	// confusion
	private boolean isMyTurn;

	private int currentRound;
	private int lobbyResponse;
	private int lobbySize;
	private int finalScore;

	// In the model it says 'won', I named it isMyVictory instead to prevent
	// confusion
	// It holds the value determining wether the client user has won a certain game
	// in a lobby or not
	private boolean isMyVictory;

	// The players in a lobby
	private ArrayList<Player> playerList;

	public Lobby(int id) {

		this.gameID = id;
		playerList = new ArrayList<Player>();
	}

	public void addPlayerToLobby(Player player) {

		playerList.add(player);
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
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

	public int getLobbyResponse() {
		return lobbyResponse;
	}

	public void setLobbyResponse(int lobbyResponse) {
		this.lobbyResponse = lobbyResponse;
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

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

}
