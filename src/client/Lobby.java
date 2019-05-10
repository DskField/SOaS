package client;

public class Lobby {

	private int gameID;
	private String gameState;
	private boolean isCurrentPlayer;
	private int currentRound;
	private int lobbyResponse;
	private int lobbySize;
	private int finalScore;
	private boolean won;

	public Lobby(int gameID, String gameState, boolean isCurrentPlayer, int lobbyResponse, int lobbySize,
			int finalScore, boolean won, int currentRound) {
		this.gameID = gameID;
		this.gameState = gameState;
		this.isCurrentPlayer = isCurrentPlayer;
		this.lobbyResponse = lobbyResponse;
		this.lobbySize = lobbySize;
		this.finalScore = finalScore;
		this.won = won;
		this.currentRound = currentRound;
	}

	// SETTERS
	public void setWon(boolean won) {
		this.won = won;
	}

	// GETTERS
	public int getGameID() {
		return gameID;
	}

	public String getGameState() {
		return gameState;
	}

	public boolean isCurrentPlayer() {
		return isCurrentPlayer;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public int getLobbyResponse() {
		return lobbyResponse;
	}

	public int getLobbySize() {
		return lobbySize;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public boolean isWon() {
		return won;
	}
}
