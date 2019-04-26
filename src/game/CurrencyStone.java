package game;

public class CurrencyStone {

	private int stoneID;
	private boolean isUsed;
	private int playerID;
	
	public CurrencyStone(int stoneID, boolean isUsed, int playerID) {
		this.stoneID = stoneID;
		this.isUsed = isUsed;
		this.playerID = playerID;
	}
	
	public int getStoneID() {
		return stoneID;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public int getPlayerID() {
		return playerID;
	}
}
