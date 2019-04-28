package game;

public class SpaceGlass extends Space{

	private Die die;
	
	public SpaceGlass(int x, int y) {
		super(x, y);
		die = null;
	}
	

	public void setDie(Die die) {
		this.die = die;
	}
	
	
	public Die getDie() {
		return die;
	}
}
