package game;

public abstract class Space {

	private int xCor;
	private int yCor;
	
	public Space(int x, int y) {
		xCor = x;
		yCor = y;
	}

	public int getXCor() {
		return xCor;
	}
	
	public int getYCor() {
		return yCor;
	}
}
