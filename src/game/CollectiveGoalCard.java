package game;

public class CollectiveGoalCard extends Card {

	private int points;
	
	public CollectiveGoalCard(int cardID, String name, String cardDescription, int points) {
		super(cardID, name, cardDescription);
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
