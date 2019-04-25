package game;

public class SpaceGlass extends Space{

	private Die die;
	
	public SpaceGlass(int x, int y) {
		super(x, y);
		die = null;
	}

	public void setDie(Die die) {
		try {
			this.die = die;
		}
		catch(Exception e) {
			System.err.println("SpaceGlass: " + e.getMessage());
		}
	}
	
	public Die getDie() {
		return die;
	}
}
