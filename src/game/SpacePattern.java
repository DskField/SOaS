package game;

public class SpacePattern extends Space{

	private int patternId;
	private String color;
	private int value;
	
	public SpacePattern(int x, int y, int id, String color, int value) {
		super(x, y);
		patternId = id;
		this.color = color;
		this.value = value;
	}

	public int getPatternId(){
		return patternId;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}
}
