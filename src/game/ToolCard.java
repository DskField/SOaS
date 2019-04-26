package game;

import java.util.ArrayList;

public class ToolCard extends Card {

	private int seqnr;
	private ArrayList<CurrencyStone> usedCurrencyStones;

	public ToolCard(int cardID, String name, int seqnr, String cardDescription) {
		super(cardID, name, cardDescription);
		this.seqnr = seqnr;
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
	
	public void loadCurrencyStones(ArrayList<CurrencyStone> usedCurrencyStones) {
		this.usedCurrencyStones = usedCurrencyStones;
	}
	
	// GETTERS
	public int getSeqnr() {
		return seqnr;
	}
}
