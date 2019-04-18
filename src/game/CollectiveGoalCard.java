package game;

public class CollectiveGoalCard extends Card {

	private int value;
	
	public CollectiveGoalCard(String cardName, String cardDescription, int value) {
		super(cardName, cardDescription);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
