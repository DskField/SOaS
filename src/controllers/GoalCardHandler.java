package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import game.CollectiveGoalCard;
import game.Die;
import game.GameColor;
import game.GlassWindow;

public class GoalCardHandler {
	/* VARIALBES */
	private GlassWindow glasswindow;

	/**
	 * Method used to calculated the score for a specific {@code GlassWindow} according to the given
	 * collective {@code GoalCards}
	 * 
	 * @param glasswindow - The {@code GlassWindow} that needs to be checked
	 * @param collectiveGoalCards - {@code ArrayList} of {@code CollectiveGoalCards} in the games that
	 * have to be used to calculated the score
	 * @return - {@code int} containing the score for the {@code GlassWindow} gained according to the
	 * {@code CollectiveGoalCards}
	 */
	public int getGoalCardHandler(GlassWindow glasswindow, ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.glasswindow = glasswindow;
		int points = 0;
		// Database numbers and name
		// Depending on the cardID use the according method
		for (CollectiveGoalCard c : collectiveGoalCards) {
			switch (c.getCardID()) {
			case 1:
				points += handleShadesVariety() * c.getPoints();
				break;
			case 2:
				points += handleShades(3, 4) * c.getPoints();
				break;
			case 3:
				points += handleColumnShadeVariety() * c.getPoints();
				break;
			case 4:
				points += handleColumnColorVariety() * c.getPoints();
				break;
			case 5:
				points += handleShades(5, 6) * c.getPoints();
				break;
			case 6:
				points += handleColorVariety() * c.getPoints();
				break;
			case 7:
				points += handleRowColorVariety() * c.getPoints();
				break;
			case 8:
				points += handleColorDiagonals();
				break;
			case 9:
				points += handleShades(1, 2) * c.getPoints();
				break;
			case 10:
				points += handleRowShadeVariety() * c.getPoints();
				break;
			default:
				System.err.println("GoalCardHandler: none existing CollectiveGoalCard");
				break;
			}
		}
		return points;
	}

	/**
	 * Method that will check the eyes on every die and add it on the equivalent index
	 * 
	 * @return - {@code int} containing the lowest number in the List
	 */
	private int handleShadesVariety() {
		ArrayList<Integer> values = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				switch (die.getDieValue()) {
				case (1):
					values.set(0, values.get(0) + 1);
					break;
				case (2):
					values.set(1, values.get(1) + 1);
					break;
				case (3):
					values.set(2, values.get(2) + 1);
					break;
				case (4):
					values.set(3, values.get(3) + 1);
					break;
				case (5):
					values.set(4, values.get(4) + 1);
					break;
				case (6):
					values.set(5, values.get(5) + 1);
					break;
				default:
					break;
				}
			}
		}
		Collections.sort(values);
		return values.get(0);
	}

	/**
	 * Method that will check every column and check if there's a duplicate die with the same eyes
	 * 
	 * @return - {@code int} that contains the amount of columns that are full and don't contain
	 * duplicate eyes
	 */
	private int handleColumnShadeVariety() {
		ArrayList<Integer> values = new ArrayList<>();
		int column = 0;

		for (int x = 0; x < 5; x++) {
			values.clear();
			for (int y = 0; y < 4; y++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (values.contains(die.getDieValue())) {
					break;
				} else {
					values.add(die.getDieValue());
				}
			}
			if (values.size() == 4) {
				column++;
			}
		}
		return column;
	}

	/**
	 * Method that will check ever column and check if there's a duplicate die with the same color
	 * 
	 * @return - {@code int} that contains the amount of columns that are full and don't contain
	 * duplicate colors
	 */
	private int handleColumnColorVariety() {
		ArrayList<GameColor> colors = new ArrayList<>();
		int column = 0;

		for (int x = 0; x < 5; x++) {
			colors.clear();
			for (int y = 0; y < 4; y++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (colors.contains(die.getDieColor()))
					break;
				else
					colors.add(die.getDieColor());
			}
			if (colors.size() == 4)
				column++;
		}
		return column;
	}

	/**
	 * Method that will check 3 cards. It uses the 2 inputs to differentiate between the 3 cards.
	 * 
	 * @param dieA - {@code int} containing 1 specific eye that has to be checked
	 * @param dieB - {@code int} containing 1 specific eye that has to be checked
	 * @return - {@code int} that contains the lowest number of the two parameters
	 */
	private int handleShades(int dieA, int dieB) {
		int countA = 0;
		int countB = 0;

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (die.getDieValue() == dieA)
					countA++;
				else if (die.getDieValue() == dieB)
					countB++;
			}
		}

		// if dieThree is smaller return dieThree else dieFour
		return countA < countB ? countA : countB;
	}

	/**
	 * Method that will check the color on every die and add it on the equivalent index
	 * 
	 * @return - {@code int} containing the lowest number in the List
	 */
	private int handleColorVariety() {
		ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				switch (die.getDieColor()) {
				case RED:
					colors.set(0, colors.get(0) + 1);
					break;
				case YELLOW:
					colors.set(1, colors.get(1) + 1);
					break;
				case GREEN:
					colors.set(2, colors.get(2) + 1);
					break;
				case BLUE:
					colors.set(3, colors.get(3) + 1);
					break;
				case PURPLE:
					colors.set(4, colors.get(4) + 1);
					break;
				default:
					break;
				}
			}
		}

		Collections.sort(colors);
		return colors.get(0);
	}

	/**
	 * Method that will check ever row and check if there's a duplicate die with the same color
	 * 
	 * @return - {@code int} that contains the amount of rows that are full and don't contain duplicate
	 * colors
	 */
	private int handleRowColorVariety() {
		ArrayList<GameColor> colors = new ArrayList<>();
		int row = 0;

		for (int y = 0; y < 4; y++) {
			colors.clear();
			for (int x = 0; x < 5; x++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (colors.contains(die.getDieColor()))
					break;
				else
					colors.add(die.getDieColor());
			}
			if (colors.size() == 5)
				row++;
		}
		return row;
	}

	/**
	 * Method that will check if a die has another die placed diagonally with the same color every time
	 * there is a color on one of the sport on the top left, top right, bottom left or bottom right
	 * 
	 * @return - {@code int} which every time there is a color on one of the sport on the
	 * {@code topleft}, {@code topright}, {@code bottomleft} or {@code bottomright} add 1 to the
	 * {@code int}
	 */
	private int handleColorDiagonals() {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				// first get all diagonal surrounding colors IF the coordinates exist
				GameColor topleft = (x - 1 >= 0 && y - 1 >= 0) && glasswindow.getSpace(x - 1, y - 1).getDie() != null ? glasswindow.getSpace((x - 1), (y - 1)).getDieColor() : GameColor.EMPTY;
				GameColor topright = (x + 1 < 5 && y - 1 >= 0) && glasswindow.getSpace(x + 1, y - 1).getDie() != null ? glasswindow.getSpace((x + 1), (y - 1)).getDieColor() : GameColor.EMPTY;
				GameColor bottomleft = (x - 1 >= 0 && y + 1 < 4) && glasswindow.getSpace(x - 1, y + 1).getDie() != null ? glasswindow.getSpace((x - 1), (y + 1)).getDieColor() : GameColor.EMPTY;
				GameColor bottomright = (x + 1 < 5 && y + 1 < 4) && glasswindow.getSpace(x + 1, y + 1).getDie() != null ? glasswindow.getSpace((x + 1), (y + 1)).getDieColor() : GameColor.EMPTY;
				GameColor center = glasswindow.getSpace(x, y).getDie() != null ? glasswindow.getSpace(x, y).getDieColor() : GameColor.EMPTY;
				// check if center space is not empty
				if (center != GameColor.EMPTY) {
					// check if the center het a diagonal die with the color
					if (center == topleft || center == topright || center == bottomleft || center == bottomright)
						count++;
				}
			}
		}
		return count;
	}

	/**
	 * Method that will check every row and check if there's a duplicate die with the same eyes
	 * 
	 * @return - {@code int} that contains the amount of rows that are full and don't contain duplicate
	 * eyes
	 */
	private int handleRowShadeVariety() {
		ArrayList<Integer> values = new ArrayList<>();
		int row = 0;

		for (int y = 0; y < 4; y++) {
			values.clear();
			for (int x = 0; x < 5; x++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (values.contains(die.getDieValue()))
					break;
				else
					values.add(die.getDieValue());
			}

			if (values.size() == 5)
				row++;
		}
		return row;
	}
}
