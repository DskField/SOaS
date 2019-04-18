package game;

import java.util.Random;

public class Die {
	private int dieId;
	private int dieValue;
	private int round;

	private GameColor dieColor;

	public Die(int dieId, String color) {
		// TODO Auto-generated constructor stub
	}

	public Die(int dieId, String color, int round, int dieValue) {
		this.dieId = dieId;
		this.dieValue = dieValue;

		try {
			dieColor = GameColor.valueOf(color);
		} catch (Exception e) {
			System.err.println("Invalid color");
		}
	}

	/**
	 * 
	 * @param round - the round the die gets rolled
	 * 
	 * This method gives a random value to a die and sets the round value
	 */
	public void roll(int round) {
		this.round = round;

		Random random = new Random();
		dieValue = random.nextInt(6) + 1;
	}

	// GETTERS AND SETTERS
	// TODO the current getters and setters are temporary, they will be changed in the future
	public int getDieId() {
		return dieId;
	}

	public void setDieId(int dieId) {
		this.dieId = dieId;
	}

	public int getDieValue() {
		return dieValue;
	}

	public void setDieValue(int dieValue) {
		this.dieValue = dieValue;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public GameColor getDieColor() {
		return dieColor;
	}

	public void setDieColor(GameColor dieColor) {
		this.dieColor = dieColor;
	}
}
