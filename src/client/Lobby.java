package client;

public class Lobby {
	/* VARIABLES */
	private int gameID;
	private String gameState;
	private boolean isCurrentPlayer;
	private int currentRound;
	private int lobbyResponse;
	private int lobbySize;
	private int finalScore;

	/**
	 * Constructor used to create a Lobby object
	 * 
	 * @param gameID - {@code int} containing the game_idgame in the database
	 * @param gameState - {@code String} containing the state the game is currently in ("wachtende",
	 * "aan de gang", "uitgespeeld", "afgebroken")
	 * @param isCurrentPlayer - {@code boolean} containing if the user has the turn to place a die
	 * @param lobbyResponse - {@code int} containing how many players have respond to the challenge
	 * @param lobbySize - {@code int} containing te lobby size of all the players who are part of the
	 * game
	 * @param finalScore - {@code int} containing the score pushed to the database when the game is
	 * finished
	 * @param currentRound - {@code int} containing the current round of the game that it's in
	 */
	public Lobby(int gameID, String gameState, boolean isCurrentPlayer, int lobbyResponse, int lobbySize, int finalScore, int currentRound) {
		this.gameID = gameID;
		this.gameState = gameState;
		this.isCurrentPlayer = isCurrentPlayer;
		this.lobbyResponse = lobbyResponse;
		this.lobbySize = lobbySize;
		this.finalScore = finalScore;
		this.currentRound = currentRound;
	}

	/* GETEERS AND SETTERS */
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
}