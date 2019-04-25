package game;

public abstract class Card {

	private int cardID;
	private String name;
	private String cardDescription;

	// constructor
	public Card(int cardID, String name, String cardDescription) {
		this.cardID = cardID;
		this.name = name;
		this.cardDescription = cardDescription;
	}

	public int getCardName() {
		return cardID;
	}

	public String getCardDescription() {
		return cardDescription;
	}

	public String getName() {
		return name;
	}
}
