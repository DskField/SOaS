package game;

import java.sql.ResultSet;
import java.util.Random;

public class PatternCard {

	private int patternCardId;
	private int difficulty;
	private SpacePattern pattern[][];
	private boolean standard;
	private Random rng;
	
	public PatternCard() {
		pattern = new SpacePattern[5][4];
		//TODO change based on Game class
		
	}
	
	public int getPatternCardId() {
		return patternCardId;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public Space getSpace(int x, int y) {
		Space temp = pattern[x][y];
		return temp;
	}
	
	public SpacePattern[][] getPattern(){
		return pattern;
	}
	
	private void createPattern(ResultSet fields) {
		//Subject to change based on Game class
		
		try {
			while(fields.next()) {
				int id = fields.getInt("patterncard_idpatterncard");
				int x = fields.getInt("position_x");
				int y = fields.getInt("position_y");
				String color = fields.getString("color");
				color.toUpperCase();
				int value = fields.getInt("value");
				pattern[x][y] = new SpacePattern(x, y, id, color, value);
//					Query: SELECT position_x, position_y, patterncard_idpatterncard, color, value FROM patterncardfield WHERE ...		
			}
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	private int getStandardPattern() {
		int random = (rng.nextInt(23) + 1);
		return random;
	}
	
	private void createRandomPattern(ResultSet fields) {
		//TODO stub
		
		for(int x = 0; x < 5; x++) {
			
			for(int y = 0; y < 4; y++) {
				
			}
		}
		
	}
	
	private void generatePattern() {
		int random = rng.nextInt(2);
	}
	
	private int generateValue() {
		int value = 0;
		return value;
	}
	
	private String generateColor() {
		String color = null;
		return color;
	}
}
