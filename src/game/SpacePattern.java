package game;

public class SpacePattern extends Space{

	private GameColor patternColor;
	private int value;
	
	public SpacePattern(int x, int y, String color, int value) {
		super(x, y);
		this.value = value;
		
		try {
			patternColor = GameColor.valueOf(color);
		}
		catch(Exception e) {
			System.err.println("SpacePattern: Invalid color");
		}
	}

	
	public GameColor getPatternColor() {
		return patternColor;
	}
	
	
	public int getValue() {
		return value;
	}
}
