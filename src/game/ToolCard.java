package game;

import java.util.ArrayList;

public class ToolCard extends Card {
	/* VARIABLES */
	private int seqnr;
	private ArrayList<CurrencyStone> currencyStones;

	public ToolCard(int cardID, String name, int seqnr, String cardDescription) {
		super(cardID, name, cardDescription);
		currencyStones = new ArrayList<>();
		this.seqnr = seqnr;
	}

	public ArrayList<CurrencyStone> getCurrencyStones() {
		return currencyStones;
	}

	public void addCurrencyStone(CurrencyStone usedCurrencyStone) {
		currencyStones.add(usedCurrencyStone);
	}

	/* GETTERS AND SETTERS */
	public int getSeqnr() {
		return seqnr;
	}
}
