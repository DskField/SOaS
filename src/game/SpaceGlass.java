package game;

public class SpaceGlass extends Space {

	private Die die;

	public SpaceGlass(int x, int y) {
		super(x, y);
		die = null;
	}

	//TODO maybe useless?
	public int getDieValue() {
		return die.getDieValue();
	}

	public GameColor getDieColor() {
		return die.getDieColor();
	}

	public int getDieId() {
		if (die != null) {
			return die.getDieId();
		} else {
			return 0;
		}
	}

	// GETTERS AND SETTERS
	public void setDie(Die die) {
		this.die = die;
	}

	public Die getDie() {
		return die;
	}
}
