package game;

import javafx.scene.paint.Color;

public enum GameColor {
	RED(Color.RED), YELLOW(Color.YELLOW), GREEN(Color.GREEN), BLUE(Color.BLUE), PURPLE(Color.PURPLE), EMPTY(Color.WHITE), GREY(Color.GREY);

	private Color color;

	GameColor(Color color) {
		this.color = color;
	}

	/**
	 * @return - The JavaFX {@code Color} that is connected to the {@code GameColor}
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Change the dutch input into the enum variable
	 * 
	 * @param key - Dutch input
	 * @return - Enum variable
	 */
	public static GameColor getEnum(String key) {
		switch (key.toLowerCase()) {
		case "rood":
			return RED;
		case "geel":
			return YELLOW;
		case "groen":
			return GREEN;
		case "blauw":
			return BLUE;
		case "paars":
			return PURPLE;
		default:
			return EMPTY;
		}
	}

	/**
	 * Change the enum variable into the Dutch output
	 * 
	 * @return - Dutch output
	 */
	public String getDatabaseName() {
		switch (this) {
		case RED:
			return "rood";
		case YELLOW:
			return "geel";
		case GREEN:
			return "groen";
		case BLUE:
			return "blauw";
		case PURPLE:
			return "paars";
		default:
			return "";
		}
	}
}
