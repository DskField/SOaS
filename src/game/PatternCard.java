package game;


public class PatternCard {

	private int patternCardId;
	private String name;
	private int difficulty;
	private SpacePattern pattern[][];
	private boolean standard;
	
	public PatternCard(int id, String name, int dif, SpacePattern[][] patterns, boolean standard) {
		try {
			patternCardId = id;
			this.name = name;
			difficulty = dif;
			pattern = patterns;
			this.standard = standard;
		}
		catch(Exception e) {
			System.err.println("PatternCard: " + e.getMessage());
		}
	}
	

	public int getPatternCardId() {
		return patternCardId;
	}
	
	
	public String GetName() {
		if(name == null) {
			name = "";
		}
		return name;
	}
	
	
	public int getDifficulty() {
		return difficulty;
	}
	
	
	public SpacePattern getSpace(int x, int y) {
		SpacePattern temp = pattern[x][y];
		return temp;
	}
	
	
	public SpacePattern[][] getPattern(){
		return pattern;
	}
	
	
	public boolean getType() {
		return standard;
	}
}
