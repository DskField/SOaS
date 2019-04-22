package game;

import java.util.ArrayList;

public class ToolCard extends Card {

	private ArrayList<CurrencyStone> usedCurrencyStones;

	public ToolCard(int cardID, int seqnr, String cardDescription) {
		super(cardID, seqnr, cardDescription);
		usedCurrencyStones = new ArrayList<CurrencyStone>();
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
