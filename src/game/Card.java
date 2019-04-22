package game;

public abstract class Card {

	private int cardID;
	private int seqnr;
	private String cardDescription;

	// constructor
	public Card(int cardID, int seqnr, String cardDescription) {
		this.cardID = cardID;
		this.cardDescription = cardDescription;
		this.seqnr = seqnr;
	}

	public int getCardName() {
		return cardID;
	}

	public String getCardDescription() {
		return cardDescription;
	}

	public int getSeqnr() {
		return seqnr;
	}
}
