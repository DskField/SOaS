package game;

import java.util.ArrayList;

public class ToolCard extends Card {

	private int seqnr;
	private ArrayList<CurrencyStone> usedCurrencyStones;

	public ToolCard(int cardID, String name, int seqnr, String cardDescription) {
		super(cardID, name, cardDescription);
		this.seqnr = seqnr;
	}

	public ArrayList<CurrencyStone> getUsedCurrencyStones() {
		return usedCurrencyStones;
	}

	// TODO: comment
	public void loadCurrencyStones(ArrayList<CurrencyStone> usedCurrencyStones) {
		this.usedCurrencyStones = usedCurrencyStones;
	}
	
	// GETTERS
	public int getSeqnr() {
		return seqnr;
	}
}
