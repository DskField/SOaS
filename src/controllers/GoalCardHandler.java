package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import game.CollectiveGoalCard;
import game.Die;
import game.GameColor;
import game.GlassWindow;

public class GoalCardHandler {

	private GlassWindow glasswindow;

	public int getGoalCardHandler(GlassWindow glasswindow, ArrayList<CollectiveGoalCard> collectiveGoalCards) {
		this.glasswindow = glasswindow;
		int points = 0;
		// database numbers + name
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

	private int handleShadesVariety() {
		ArrayList<Integer> values = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				switch (glasswindow.getSpace(x, y).getDie().getDieValue()) {
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

				if (values.contains(die.getDieValue()))
					break;
				else
					values.add(die.getDieValue());
			}
			if (values.size() == 4)
				column++;
		}
		return column;
	}

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

	// handles Halfdonkere, Donkere en Lichte Tinten
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

		System.out.println(countA + " - " + countB);
		// if dieThree is smaller return dieThree else dieFour
		return countA < countB ? countA : countB;
	}

	private int handleColorVariety() {
		ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
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
			if (colors.size() == 4)
				row++;
		}
		return row;
	}

	private int handleColorDiagonals() {
		int count = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				// first get all diagonal surrounding colors IF the coordinates exist
				GameColor topleft = (x - 1 >= 0 && y - 1 >= 0) && glasswindow.getSpace(x - 1, y - 1).getDie() != null
						? glasswindow.getSpace((x - 1), (y - 1)).getDieColor()
						: GameColor.EMPTY;
				GameColor topright = (x + 1 < 5 && y - 1 >= 0) && glasswindow.getSpace(x + 1, y - 1).getDie() != null
						? glasswindow.getSpace((x + 1), (y - 1)).getDieColor()
						: GameColor.EMPTY;
				GameColor bottomleft = (x - 1 >= 0 && y + 1 < 4) && glasswindow.getSpace(x - 1, y + 1).getDie() != null
						? glasswindow.getSpace((x - 1), (y + 1)).getDieColor()
						: GameColor.EMPTY;
				GameColor bottomright = (x + 1 < 5 && y + 1 < 4) && glasswindow.getSpace(x + 1, y + 1).getDie() != null
						? glasswindow.getSpace((x + 1), (y + 1)).getDieColor()
						: GameColor.EMPTY;
				GameColor center = glasswindow.getSpace(x, y).getDie() != null
						? glasswindow.getSpace(x, y).getDieColor()
						: GameColor.EMPTY;
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

	private int handleRowShadeVariety() {
		ArrayList<Integer> values = new ArrayList<>();
		int row = 0;

		for (int x = 0; x < 5; x++) {
			values.clear();
			for (int y = 0; y < 4; y++) {
				Die die = glasswindow.getSpace(x, y).getDie();
				if (die == null) {
					continue;
				}

				if (values.contains(die.getDieValue()))
					break;
				else
					values.add(die.getDieValue());
			}

			if (values.size() == 4)
				row++;
		}
		return row;
	}
}
