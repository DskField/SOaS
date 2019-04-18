package game;

public class PersonalGoalCard extends Card {
	
	public GameColor cardColor;

	public PersonalGoalCard(String cardName, String cardDescription, String cardColor) {
		super(cardName, cardDescription);
		
		// only update cardColor if it's in the enum
		try {
			this.cardColor = GameColor.valueOf(cardColor.toUpperCase());
		} catch (Exception e) {
			System.out.println("INVALID COLOR!");
			e.printStackTrace();
		}
	}
	
	public GameColor getCardColor() {
		return cardColor;
	}

}
