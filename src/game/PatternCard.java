package game;


public class PatternCard {

	private int patternCardId;
	private String name;
	private int difficulty;
	private SpacePattern pattern[][];
	private boolean standard;
	
	public PatternCard(int id, String name, int dif, boolean standard) {
		patternCardId = id;
		this.name = name;
		difficulty = dif;
		this.standard = standard;
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
	
	
	public SpacePattern[][] getPattern(){
		return pattern;
	}
	
	
	public boolean getType() {
		return standard;
	}
	
	public void loadPattern(SpacePattern[][] patterns) {
		pattern = patterns;
	}
}
