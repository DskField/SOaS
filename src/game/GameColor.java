package game;

import javafx.scene.paint.Color;

public enum GameColor {
	RED(Color.RED), YELLOW(Color.YELLOW), GREEN(Color.GREEN), BLUE(Color.BLUE), PURPLE(Color.PURPLE), EMPTY(Color.WHITE);

	private Color color;

	GameColor(Color color) {
		this.color = color;
	}

	// getter for the Color connected to the String
	public Color getColor() {
		return this.color;
	}
	
	// changing dutch input into an enum
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

	// tijdelijke commentaar voor uitleg van enum voor degene die het nog niet
	// kennen
	// elke keer als je één van de kleuren nodig hebt voor bijvoorbeeld dobbelstenen
	// verwijs naar de enum. Hierdoor kan je later veel makkelijker de kleuren
	// veranderen
	// zonder alles langs te lopen. Ook hoef je niet moeilijk te doen met een string
	// omzetten in een kleur.

	// gebruik:

	// als je alleen de String wilt
	// GameColor.RED; -> dit geeft de kleur RED puur als string
	//
	// als je de kleur wilt hebben
	// GameColor.RED.getColor(); -> dit geeft de kleur Color.RED

	// wanneer je een kleur krijgt uit de database zet die om naar full caps
	// vervolgens kan je met een try catch (je weet het maar nooit) die string
	// omzetten in de bijpassende kleur
}
