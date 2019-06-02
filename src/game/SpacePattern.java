package game;

public class SpacePattern extends Space {
	/* VARIABLES */
	private GameColor color;
	private int value;

	public SpacePattern(int x, int y, GameColor color, int value) {
		super(x, y);
		this.value = value;
		this.color = color;
	}

	/* GETTERS AND SETTERS */
	public GameColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}
}
