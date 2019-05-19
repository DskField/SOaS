package game;

public class GlassWindow {

	private SpaceGlass[][] spaces;
	private PatternCard patterncard;
	private GameColor color;

	public GlassWindow() {
		spaces = new SpaceGlass[5][4];
	}

	// GETTERS AND SETTERS
	//Is used to add a PatternCard Object to the GlassWindow
	public void loadPatternCard(PatternCard patterncard) {
		this.patterncard = patterncard;
	}

	//Is used to add SpaceGlass Objects to the GlassWindow
	public void loadSpaces(SpaceGlass[][] spaces) {
		this.spaces = spaces;
	}

	public PatternCard getPatternCard() {
		return patterncard;
	}

	public SpaceGlass getSpace(int x, int y) {
		return spaces[x][y];
	}

	public SpaceGlass[][] getSpaces() {
		return spaces;
	}

	public void setColor(GameColor color) {
		this.color = color;
	}

	public GameColor getColor() {
		return color;
	}
	
	public void setPaterNull(PatternCard patternCard) {
		this.patterncard = patternCard;
	}
}
