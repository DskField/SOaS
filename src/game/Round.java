package game;

import java.util.ArrayList;

public class Round {
	private ArrayList<Die> dice;

	public Round() {
		dice = new ArrayList<Die>();
	}

	/**
	 * This function will add the die to the round.
	 * 
	 * @param die - {@code Die} you want to add to the {@code Round}
	 */
	public void addDie(Die die) {
		dice.add(die);
	}

	/**
	 * This function will add the dice to the round.
	 * 
	 * @param dice - {@code ArrayList} with the dice that did not get used
	 */
	public void addDice(ArrayList<Die> dice) {
		this.dice.addAll(dice);
	}

	public ArrayList<Die> getDice() {
		return dice;
	}
}
