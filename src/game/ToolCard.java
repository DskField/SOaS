package game;

import java.util.ArrayList;

public class ToolCard extends Card {

	private int seqnr;
	private ArrayList<CurrencyStone> currencyStones;

	public ToolCard(int cardID, String name, int seqnr, String cardDescription) {
		super(cardID, name, cardDescription);
		this.seqnr = seqnr;
	}

	public ArrayList<CurrencyStone> getCurrencyStones() {
		return currencyStones;
	}

	// TODO: comment
	public void addCurrencyStone(CurrencyStone usedCurrencyStone) {
		currencyStones.add(usedCurrencyStone);
	}

	// GETTERS
	public int getSeqnr() {
		return seqnr;
	}
}
