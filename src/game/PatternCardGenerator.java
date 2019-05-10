package game;

import java.util.Random;

public class PatternCardGenerator {
	
	private Random rng;
	
	public PatternCard getCard() {
		return generateCard();
	}
	
	private PatternCard generateCard() {
		SpacePattern[][] pattern = new SpacePattern[5][4];
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 4; y++) {
				SpacePattern generatedPattern = null;
				
				while(generatedPattern == null) {
					generatedPattern = null;
					Boolean noY = false;
					
					SpacePattern tempPattern = generateSpacePattern(x + 1, y + 1);
					if(y > 0) {
						if( (tempPattern.getPatternColor().getColor().equals(pattern[x][y - 1].getPatternColor().getColor()) && tempPattern.getPatternColor() != GameColor.EMPTY) || (tempPattern.getValue() == pattern[x][y - 1].getValue() && tempPattern.getValue() != 0)) {
							System.out.println("NO Y");
							noY = true;
							generatedPattern = null;
						}
						else {
							System.err.println("Neat");
							generatedPattern = tempPattern;
						}
					}
					else {
						System.err.println("Cool");
						generatedPattern = tempPattern;
					}
					if(x > 0) {
						if( ((tempPattern.getPatternColor().getColor().equals(pattern[x - 1][y].getPatternColor().getColor()) && tempPattern.getPatternColor() != GameColor.EMPTY) || (tempPattern.getValue() == pattern[x - 1][y].getValue() && tempPattern.getValue() != 0)) || noY == true ) {
							System.out.println("NO X");
							generatedPattern = null;
						}
						else {
							generatedPattern = tempPattern;
						}
					}
					
				}
				System.out.println("X: "+generatedPattern.getXCor() + " Y: " + generatedPattern.getYCor() + " Value: " + generatedPattern.getValue() + " Color: " + generatedPattern.getPatternColor());
				pattern[x][y] = generatedPattern;
			}
		}
		
		PatternCard generatedCard = new PatternCard(0, "Generated_Card", 0);
		generatedCard.addPattern(pattern);
		return generatedCard;
	}
	
	private int generateDifficulty(SpacePattern[][] pattern) {
		return 0;
		
	}
	
	private SpacePattern generateSpacePattern(int x, int y) {
		rng = new Random();
		int random = rng.nextInt(4);
		SpacePattern generatedPattern = null;
		
		if(random == 0 || random == 3) {
			generatedPattern = new SpacePattern(x, y, GameColor.EMPTY, 0);
			System.out.println("Empty");
		}
		else {
			if(random == 1) {
				int value = rng.nextInt(6) + 1;
				generatedPattern = new SpacePattern(x, y, GameColor.EMPTY, value);
				System.out.println("Number");
			}
			else {
				int value = rng.nextInt(5);
				GameColor[] colors = new GameColor[] {GameColor.RED, GameColor.YELLOW, GameColor.GREEN, GameColor.BLUE, GameColor.PURPLE};
				generatedPattern = new SpacePattern(x, y, colors[value], 0);
				System.out.println("Color");
			}
		}
		
		
		return generatedPattern;
	}
	
	private boolean checkPattern() {
		return true;
	}

	
}
