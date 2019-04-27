package game;

public class CurrencyStone {

	private int stoneID;
	
	// duplicate information on purpose
	private int playerID;

	public CurrencyStone(int stoneID, int playerID) {
		this.stoneID = stoneID;
		this.playerID = playerID;
	}

	public int getStoneID() {
		return stoneID;
	}
	
	public int getPlayerID() {
		return playerID;
	}
}
