package game;

public class CollectiveGoalCard extends Card {

	private int seqnr;
	
	public CollectiveGoalCard(int cardID, int seqnr, String cardDescription) {
		super(cardID, cardDescription);
		this.seqnr = seqnr;
	}
	
	public int getValue() {
		return seqnr;
	}
}
