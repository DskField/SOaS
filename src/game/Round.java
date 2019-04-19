package game;

public class Round {
	private Die[] dice;

	public Round() {
		dice = new Die[9];
	}

	/**
	 * This function will add the die to the round.
	 * 
	 * @param die - die you want to add to the round
	 */
	public void addDie(Die die) {
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] != null) {
				dice[i] = die;
			}
		}
	}

	/**
	 * This function will add the dice to the round.
	 * 
	 * @param dice - array with the dice that did not get used
	 */
	public void addDies(Die[] dice) {
		for (int i = 0; i < dice.length; i++) {
			for (int j = 0; i < this.dice.length; j++) {
				if (this.dice[j] != null) {
					this.dice[j] = dice[i];
				}
			}
		}
	}

	public Die[] getDies() {
		return dice;
	}
}
