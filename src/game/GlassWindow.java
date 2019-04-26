package game;

public class GlassWindow {

	private int playerId;
	private int gameId;
	private SpaceGlass[][] spaces;
	
	
	//	temporary empty constructor as to not cause errors
	public GlassWindow() {
		
	}
	
	public GlassWindow(int playerId, int gameId, SpaceGlass[][] spaces) {
		try {
			this.playerId = playerId;
			this.gameId = gameId;
			this.spaces = spaces;
		}
		catch(Exception e) {
			System.err.println("GlassWindow: " + e.getMessage());
		}
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	
	public int getGameId() {
		return gameId;
	}
	
	public SpaceGlass getSpace(int x, int y) {
		SpaceGlass temp = spaces[x][y];
		return temp;
	}
	
	public SpaceGlass[][] getSpaces() {
		return spaces;
	}
}
