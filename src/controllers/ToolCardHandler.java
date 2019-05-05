package controllers;

import game.Die;
import game.GameColor;
import game.GlassWindow;

public class ToolCardHandler {

	private GlassWindow glassWindow;

	public void handleGrozingPliers(Die die, boolean answer) {

		int dieVal = die.getDieValue();

		GameColor dieCol = die.getDieColor();
		String dieColString = null;

		// Backwards engineering the string provided to set a GameColor within the
		// "getEnum" method
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
		
		// TODO: Check wether die is 1 or 6 so view doesn't get bothered with die values

		// Choose wether your picked die's value should increment, or decrement
		if (answer) {
			dieVal++;
			die = new Die(die.getDieId(), dieColString, die.getRound(), dieVal);
		}

		else if (!answer) {
			dieVal--;
			die = new Die(die.getDieId(), dieColString, die.getRound(), dieVal);
		}

		else {
			System.err.println("ToolCardHandler: something went wrong in Grozing Pliers!");
		}
	}
	
	public void handleEglomiseBrush() {
		
		
	}
}
