package game;

public class CurrencyStone {

	private int stoneID;

	private int playerID;
	private int cardID;

	public CurrencyStone(int stoneID, int playerID, int cardID) {
		this.stoneID = stoneID;
		this.playerID = playerID;
		this.cardID = cardID;
	}

	public int getStoneID() {
		return stoneID;
	}

	public int getCardID() {
		return cardID;
	}

	public int getPlayerID() {
		return playerID;
	}
}
