package client;

public class Lobby {

	private int gameID;
	private String gameState;
	
	// In het model staat er 'turn', ik heb er isMyTurn van gemaakt voor duidelijkheid
	private boolean isMyTurn;
	
	private int currentRound;
	private int lobbyResponse;
	private int lobbySize;
	private int finalScore;
	
	// In het model staat er 'won', ik heb er isMyVictory van gemaakt voor duidelijkheid
	// Checkt of de user gewonnen heeft
	private boolean isMyVictory;
	
	public Lobby(int id) {
		
		this.gameID = id;
	}
}
