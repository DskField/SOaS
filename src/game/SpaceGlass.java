package game;

public class SpaceGlass extends Space {

	private Die die;

	public SpaceGlass(int x, int y) {
		super(x, y);
		die = null;
	}

	public int getDieValue() {
		return die.getDieValue();
	}

	public GameColor getDieColor() {
		return die.getDieColor();
	}

	public int getDieId() {
		return die.getDieId();
	}

	// GETTERS AND SETTERS
	public void setDie(Die die) {
		this.die = die;
	}

	public Die getDie() {
		return die;
	}
}
