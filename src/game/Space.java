package game;

public abstract class Space {

  private int spaceId;
	private int xCor;
	private int yCor;
	
	public Space(int id, int x, int y) {
		spaceId = id;
		xCor = x;
		yCor = y;
	}
	
	public int getSpaceId() {
		return spaceId;
	}
	
	public int getXCor() {
		return xCor;
	}
	
	public int getYCor() {
		return yCor;
	}
}
