package game;

public class SpaceGlass extends Space{

	private Die die;
	
	
	public SpaceGlass(int x, int y) {
		super(x, y);
		die = null;
	}
	
	
	// GETTERS AND SETTERS
	public void setDie(Die die) {
		this.die = die;
	}
	
	
	public Die getDie() {
		return die;
	}
}
