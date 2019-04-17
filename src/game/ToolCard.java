package game;

import java.util.ArrayList;

public class ToolCard extends Card {

	private int cardNumber;
	private ArrayList<CurrencyStone> usedCurrencyStones;
	
	public ToolCard(String cardName, String cardDescription, int cardNumber) {
		super(cardName, cardDescription);
		this.cardNumber = cardNumber;
		usedCurrencyStones = new ArrayList<CurrencyStone>();
	}

	public int getCardNumber() {
		return cardNumber;
	}
	
	// you return the whole list, because you can get the size with it and
	// know which CurrencyStone is currently on the ToolCard
	public ArrayList<CurrencyStone> getUsedCurrencyStones() {
		return usedCurrencyStones;
	}
	
	// TODO: check logic of following method
	public void payToolCard(CurrencyStone currenctStone) {
		usedCurrencyStones.add(currenctStone);
	}
}
