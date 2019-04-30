package game;

public class GlassWindow {

	private SpaceGlass[][] spaces;
	private PatternCard patterncard;
	
	
	public GlassWindow(SpaceGlass[][] spaces) {
			this.spaces = spaces;
	}
	

	// GETTERS AND SETTERS
	public void loadPatternCard(PatternCard patterncard) {
		this.patterncard = patterncard;
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
	
}
