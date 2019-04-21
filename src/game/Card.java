package game;

public abstract class Card {

	private int cardID;
	private String cardDescription;
	
	// constructor
	public Card(int cardID, String cardDescription) {
		this.cardID = cardID;
		this.cardDescription = cardDescription;
	}
	
	public int getCardName() {
		return cardID;
	}
	
	public String getCardDescription() {
		return cardDescription;
	}
}
