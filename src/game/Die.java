package game;

import java.util.Random;

public class Die {
	private int dieId;
	private int dieValue;
	private int round; // If round = 0 it means that the Die is not rolled yet

	private GameColor dieColor;

	/**
	 * This constructor creates a new Die without a round and a value. Round and Value will be asigned
	 * after a Die is rolled.
	 * 
	 * @param dieId
	 * @param color
	 */
	public Die(int dieId, String color) {
		this.dieId = dieId;
		this.round = 0;
		dieColor = GameColor.getEnum(color);
	}

	/**
	 * If a Die is already rolled you need to use this constructor with the right parameters to create.
	 * the Die
	 * 
	 * @param dieId - The id of the die
	 * @param color - The color of the die
	 * @param round - The round the die is used
	 * @param dieValue - The value of the die
	 */
	public Die(int dieId, String color, int round, int dieValue) {
		this.dieId = dieId;
		dieColor = GameColor.getEnum(color);
		this.round = round;
		this.dieValue = dieValue;
	}

	/**
	 * 
	 * @param round - the Round the die gets rolled
	 * 
	 * This method gives a random value to a die and sets the round value
	 */
	public void roll(int round) {
		this.round = round;

		Random random = new Random();
		dieValue = random.nextInt(6) + 1;
	}

	public void rollDieAgain(int value) {
		dieValue = value;
	}

	public void clearRound() {
		round = 0;
	}

	// GETTERS AND SETTERS
	public int getDieId() {
		return dieId;
	}

	public int getDieValue() {
		return dieValue;
	}

	public int getRound() {
		return round;
	}

	public GameColor getDieColor() {
		return dieColor;
	}
}
