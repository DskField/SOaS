package game;

public class SpacePattern extends Space{

	private GameColor patternColor;
	private int value;
	
	
	public SpacePattern(int x, int y, GameColor color, int value) {
		super(x, y);
		this.value = value;
		patternColor = color;
	}

	
	// GETTERS AND SETTERS
	public GameColor getPatternColor() {
		return patternColor;
	}
	
	
	public int getValue() {
		return value;
	}
}
