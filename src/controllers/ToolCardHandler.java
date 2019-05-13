package controllers;

import java.util.ArrayList;
import java.util.Random;

import game.Die;
import game.Game;
import game.GameColor;
import game.GlassWindow;
import game.SpaceGlass;
import game.SpacePattern;

public class ToolCardHandler {

	private GlassWindow glassWindow;
	private boolean canPlace;
	private String dieColString;
	private Random random;
	private Game game;

	// Handles the moving of a die
	// Does no checking whatsoever, the checks happen in the methods for each
	// respective tool card
	public void handleMoveDie(int oldX, int oldY, int newX, int newY, Die die) {

		SpaceGlass oldSpace = glassWindow.getSpace(oldX, oldY);
		SpaceGlass newSpace = glassWindow.getSpace(newX, newY);

		if (newSpace.getDie() == null) {
			newSpace.setDie(die);
			oldSpace.setDie(null);
		} else {
			System.out.println("ToolCardHandler: Cannot move die: selected space already has a die on it.");
		}
	}

	// Checks if selected die has the same color as the dice adjacent to it
	// Also checks if the new space has he same color as the selected die
	public void checkDieColorPerm(int xCor, int yCor, Die die) {

		canPlace = true;
		SpaceGlass glassSpace;
		Die spaceDie;
		ArrayList<Die> dieList = new ArrayList<Die>();

		// Compare chosen tile's color to die
		SpacePattern patternSpace = glassWindow.getPatternCard().getSpace(xCor, yCor);

		if (patternSpace.getPatternColor() == die.getDieColor()) {
			canPlace = false;
			return;
		}

		// Check all adjacent spaces
		glassSpace = glassWindow.getSpace(xCor + 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor - 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor, yCor + 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor, yCor - 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		// Compares the color enum of all dice in the list with the selected die
		for (Die nDie : dieList) {
			if (nDie.getDieColor() == die.getDieColor()) {
				canPlace = false;
				break;
			}
		}
	}

	// Checks if selected die has the same value as the dice adjacent to it
	public void checkDieValuePerm(int xCor, int yCor, Die die) {

		canPlace = true;
		SpaceGlass glassSpace;
		Die spaceDie;
		ArrayList<Die> dieList = new ArrayList<Die>();

		// Check all adjacent spaces
		glassSpace = glassWindow.getSpace(xCor + 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor - 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor, yCor + 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		glassSpace = glassWindow.getSpace(xCor, yCor - 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			dieList.add(spaceDie);
		}

		// Compares the die value of all dice in the list with the selected die
		for (Die nDie : dieList) {
			if (nDie.getDieValue() == die.getDieValue()) {
				canPlace = false;
				break;
			}
		}
	}

	// Checks which string should be used to determine a die colour
	public String translateColor(GameColor dieCol) {
		switch (dieCol) {
		case RED:
			dieColString = "rood";
		case YELLOW:
			dieColString = "geel";
		case GREEN:
			dieColString = "groen";
		case BLUE:
			dieColString = "blauw";
		case PURPLE:
			dieColString = "paars";
		default:
			System.err.println("ToolCardHandler: Die color value in GrozingPliers hasn't been recognized.");
		}

		return dieColString;
	}

	// Tool card that increases or decreases the value of a drafted die by 1
	public void handleGrozingPliers(Die die, boolean answer) {

		int dieVal = die.getDieValue();

		String dieColor = translateColor(die.getDieColor());

		// Backwards engineering the string provided to set a GameColor within the
		// "getEnum" method

		// TODO: Check wether die is 1 or 6 so view doesn't get bothered with die values

		// Choose wether your picked die's value should increment, or decrement
		if (answer) {
			dieVal++;
			die = new Die(die.getDieId(), dieColor, die.getRound(), dieVal);
		}

		else if (!answer) {
			dieVal--;
			die = new Die(die.getDieId(), dieColor, die.getRound(), dieVal);
		}

		else {
			System.err.println("ToolCardHandler: something went wrong in Grozing Pliers!");
		}
	}

	// Tool card that allows you to move a die to a space, regardless of shade
	public void handleEglomiseBrush(SpaceGlass currentSpace, SpaceGlass chosenSpace) {

		Die currDie = currentSpace.getDie();

		try {

			checkDieValuePerm(chosenSpace.getXCor(), chosenSpace.getYCor(), currDie);

			if (canPlace) {
				handleMoveDie(currentSpace.getXCor(), currentSpace.getYCor(), chosenSpace.getXCor(), chosenSpace.getYCor(), currDie);
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	// Tool card that allows you to move a die to a space, regardless of color
	public void handleCopperFoilBurnisher(SpaceGlass currentSpace, SpaceGlass chosenSpace) {

		Die currDie = currentSpace.getDie();

		try {

			checkDieColorPerm(chosenSpace.getXCor(), chosenSpace.getYCor(), currDie);

			if (canPlace) {
				handleMoveDie(currentSpace.getXCor(), currentSpace.getYCor(), chosenSpace.getXCor(), chosenSpace.getYCor(), currDie);
			}

		} catch (Exception e) {
			System.err.println(e);
		}

	}

	// Move exactly two dice
	public void handleLathekin(SpaceGlass curr1, SpaceGlass curr2, SpaceGlass new1, SpaceGlass new2) {

		Die die1 = curr1.getDie();
		Die die2 = curr2.getDie();

		try {

			checkDieColorPerm(new1.getXCor(), new1.getYCor(), die1);
			checkDieValuePerm(new1.getXCor(), new1.getYCor(), die1);

			if (canPlace) {
				handleMoveDie(curr1.getXCor(), curr1.getYCor(), new1.getXCor(), new1.getYCor(), die1);
			}

		} catch (Exception e) {
			System.out.println("ToolCardHandler: Lathekin method has encountered an error while placing die 1:");
			System.err.println(e);
		}

		try {

			checkDieColorPerm(new2.getXCor(), new2.getYCor(), die2);
			checkDieValuePerm(new2.getXCor(), new2.getYCor(), die2);

			if (canPlace) {
				handleMoveDie(curr2.getXCor(), curr2.getYCor(), new2.getXCor(), new2.getYCor(), die2);
			}

		} catch (Exception e) {
			System.out.println("ToolCardHandler: Lathekin method has encountered an error while placing die 2:");
			System.err.println(e);
		}
	}

	// Re-rolls the selected drafted die
	public void handleFluxBrush(Die die) {
		die.roll(die.getRound());
	}

	// Re-rolls every die left on the table
	// Can only be used on the player's second turn
	public void handleGlazingHammer(Game game) {

		// TODO: Notify Adri about lacking getter for 'table' inside of game.Game
		ArrayList<Die> table = new ArrayList<Die>(); // This will be a stub for the time being

		// Player needs a counter or boolean to check wether or not it's the
		// player's second turn
		// TODO: Notify group about these prefered changes

		for (Die die : table) {
			die.roll(die.getRound());
		}
	}

	public void handleRunningPliers(SpaceGlass space, Die die) {

		// TODO: Wait on Player for chooseDie()
		try {

			checkDieColorPerm(space.getXCor(), space.getYCor(), die);
			checkDieValuePerm(space.getXCor(), space.getYCor(), die);

			if (canPlace) {
				// TODO: Wait on Player.layDie()
			}

		} catch (Exception e) {
			System.out.println("ToolCardHandler: RunningPliers method has encountered an error while placing die 1:");
			System.err.println(e);
		}

		// TODO: Alert group about player turn statuses
	}

	// Allows the player to lay the chosen die on a spot that has no adjacent die
	public void handleCBS(int xCor, int yCor, Die die) {

		canPlace = true;
		SpaceGlass glassSpace;
		Die spaceDie;

		// Compare chosen tile's color to die
		SpacePattern patternSpace = glassWindow.getPatternCard().getSpace(xCor, yCor);

		if (patternSpace.getPatternColor() == die.getDieColor()) {
			canPlace = false;
			return;
		}

		// Check all adjacent spaces for dice
		glassSpace = glassWindow.getSpace(xCor + 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			canPlace = false;
			return;
		}

		glassSpace = glassWindow.getSpace(xCor - 1, yCor);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			canPlace = false;
			return;
		}

		glassSpace = glassWindow.getSpace(xCor, yCor + 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			canPlace = false;
			return;
		}

		glassSpace = glassWindow.getSpace(xCor, yCor - 1);
		spaceDie = glassSpace.getDie();
		if (spaceDie != null) {
			canPlace = false;
			return;
		}

		if (canPlace) {
			// TODO: Wait on Player.layDie()
		}

	}

	// Flips the die to its "opposite side"
	public void handleGrindingStone(Die die) {

		int dieVal = die.getDieValue();
		int newVal = 0;
		String dieCol = translateColor(die.getDieColor());

		switch (dieVal) {

		case 1:
			newVal = 6;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);

		case 2:
			newVal = 5;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);

		case 3:
			newVal = 4;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);

		case 4:
			newVal = 3;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);

		case 5:
			newVal = 2;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);

		case 6:
			newVal = 1;
			die = new Die(die.getDieId(), dieCol, die.getRound(), newVal);
		}
	}

	// Remove a die from the draft pile (table), 
	// and grab a new one from the sack (dice)

	// The die value is to be chosen by the player
	public void handleFluxRemover(Die die, int dieValue) {

		//		Die handDie;

		//		String dieCol = translateColor(handDie.getDieColor());

		ArrayList<Die> dice = game.getDice();
		ArrayList<Die> table = game.getTable(); // Stub for getTable

		dice.add(die);
		table.remove(die);

		int index = random.nextInt(dice.size());
		dice.get(index).roll(game.getCurrentRound()); // Stub for getCurrentRound
		//		handDie = dice.get(index);
		dice.remove(index);

		//		handDie = new Die(handDie.getDieId(), dieCol, die.getRound(), dieValue);

		// Player.layDie()
	}
}
