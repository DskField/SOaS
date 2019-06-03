package game;

public class PatternCard {
	/* VARIABLES */
	private int patternCardId;
	private String name;
	private int difficulty;
	private SpacePattern pattern[][];

	public PatternCard(int id, String name, int dif) {
		patternCardId = id;
		this.name = name;
		difficulty = dif;
		pattern = new SpacePattern[5][4];
	}

	/* GETTERS AND SETTERS */
	public void addPattern(SpacePattern[][] pattern) {
		this.pattern = pattern;
	}

	public int getPatternCardId() {
		return patternCardId;
	}

	public String getName() {
		return name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public SpacePattern getSpace(int x, int y) {
		return pattern[x][y];
	}

	public SpacePattern[][] getSpaces() {
		return pattern;
	}

	public GameColor getSpaceColor(int x, int y) {
		return pattern[x][y].getColor();
	}

	public int getSpaceValue(int x, int y) {
		return pattern[x][y].getValue();
	}

	public void setID(int id) {
		this.patternCardId = id;
	}
}
