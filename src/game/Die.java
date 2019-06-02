package game;

import java.util.Random;

public class Die {
	/* VARIABLES */
	private int dieId;
	private int dieValue;
	/**
	 * If round = 0 it means that the {@code Die} is not rolled yet
	 */
	private int round;

	private GameColor dieColor;

	/**
	 * This constructor creates a new {@code Die} without a {@code round} and a {@code value}. Round and
	 * Value will be assigned after a {@code Die} is rolled.
	 * 
	 * @param dieId - The id of the {@code Die}
	 * @param color - The color of the {@code Die}
	 */
	public Die(int dieId, String color) {
		this.dieId = dieId;
		this.round = 0;

		if (dieId == 0 && color == null) {
			dieColor = GameColor.EMPTY;
		} else {
			dieColor = GameColor.getEnum(color);
		}
	}

	/**
	 * If a {@code Die} is already rolled you need to use this constructor with the right parameters to
	 * create the {@code Die}
	 * 
	 * @param dieId - The id of the {@code Die}
	 * @param color - The color of the {@code Die}
	 * @param round - The round the {@code Die} is used
	 * @param dieValue - The value of the {@code Die}
	 */
	public Die(int dieId, String color, int round, int dieValue) {
		this.dieId = dieId;
		dieColor = GameColor.getEnum(color);
		this.round = round;
		this.dieValue = dieValue;
	}

	/**
	 * 
	 * @param round - the {@code Round} the die gets rolled
	 * 
	 * This method gives a random value to a die and sets the round value
	 */
	public void roll(int round) {
		this.round = round;

		Random random = new Random();
		dieValue = random.nextInt(6) + 1;
	}

	/* GETTERS AND SETTERS */
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
