package controllers;

import java.util.Random;

import game.GameColor;
import game.PatternCard;
import game.SpacePattern;

public class PatternCardGenerator {
	private Random rng;


	public PatternCard getCard() {
		return generateCard();
	}

	private PatternCard generateCard() {
		SpacePattern[][] pattern = new SpacePattern[5][4];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				SpacePattern generatedPattern = null;

				//This will keep trying to add a new SpacePattern to the array until it doesn't have the same value or color as those above it/left of it
				while (generatedPattern == null) {
					generatedPattern = null;
					Boolean noY = false;

					SpacePattern tempPattern = generateSpacePattern(x + 1, y + 1);
					//Checks if the die above has the same value or color
					//If this is the case "generatedPattern" will become null, and the boolean "noY" will be true, preventing a check on the x axis if it isn't the first row
					if (y > 0) {
						if ((tempPattern.getColor().getColor().equals(pattern[x][y - 1].getColor().getColor()) && tempPattern.getColor() != GameColor.EMPTY)
								|| (tempPattern.getValue() == pattern[x][y - 1].getValue() && tempPattern.getValue() != 0)) {
							//System.out.println("NO Y");
							noY = true;
							generatedPattern = null;
						} else {
							generatedPattern = tempPattern;
						}
					} else {
						generatedPattern = tempPattern;
					}
					//Checks if the die to the left has the same value or color, but will not do so if "noY"
					if (x > 0) {
						if (((tempPattern.getColor().getColor().equals(pattern[x - 1][y].getColor().getColor()) && tempPattern.getColor() != GameColor.EMPTY)
								|| (tempPattern.getValue() == pattern[x - 1][y].getValue() && tempPattern.getValue() != 0)) || noY == true) {
							generatedPattern = null;
						} else {
							generatedPattern = tempPattern;
						}
					}

				}
				pattern[x][y] = generatedPattern;
			}
		}

		PatternCard generatedCard = new PatternCard(0, "Generated_Card", generateDifficulty(pattern));
		generatedCard.addPattern(pattern);
		return generatedCard;
	}

	private int generateDifficulty(SpacePattern[][] pattern) {
		int notEmpty = 0;
		for(SpacePattern[] pRow: pattern) {
			for(SpacePattern place: pRow) {
				if(!place.getColor().equals(GameColor.EMPTY)) {
					notEmpty +=1;
				}
			}
		}
		double difficulty = notEmpty / 3.33;

		int intDifficulty = (int)difficulty;
		
		return intDifficulty;
	}

	//Creates a new SpacePattern that is either blank or has a random value/color
	private SpacePattern generateSpacePattern(int x, int y) {
		rng = new Random();
		int random = rng.nextInt(7);
		SpacePattern generatedPattern = null;

		if (random == 0 || random == 3 || random == 6) {
			generatedPattern = new SpacePattern(x, y, GameColor.EMPTY, 0);
		} else {
			if (random == 1 || random == 4) {
				int value = rng.nextInt(6) + 1;
				generatedPattern = new SpacePattern(x, y, GameColor.GREY, value);
			} else {
				int value = rng.nextInt(5);
				GameColor[] colors = new GameColor[] { GameColor.RED, GameColor.YELLOW, GameColor.GREEN, GameColor.BLUE, GameColor.PURPLE };
				generatedPattern = new SpacePattern(x, y, colors[value], 0);
			}
		}
		return generatedPattern;
	}
}
