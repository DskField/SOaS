package game;

public class GlassWindow {

	private SpaceGlass[][] spaces;
	
	
	public GlassWindow(SpaceGlass[][] spaces) {
			this.spaces = spaces;
	}
	

	public SpaceGlass getSpace(int x, int y) {
		return spaces[x][y];
	}
	
	
	public SpaceGlass[][] getSpaces() {
		return spaces;
	}
	
	public void loadSpaces(SpaceGlass[][] spaces) {
		this.spaces = spaces;
	}
}
