package view;

import game.GameColor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CurrencyStonePane extends Pane {
	/* CONSTANTS */
	private final int RADIUS = 15;
	private final int CENTERX = 18;
	private final int CENTERY = 18;

	/**
	 * Makes circle with a stroke to represent the stone.
	 * 
	 * @param color of the stone
	 */
	public CurrencyStonePane(GameColor color) {
		Shape stone = new Circle(CENTERX, CENTERY, RADIUS, color.getColor());
		stone.setStroke(Color.BLACK);
		getChildren().add(stone);
	}
}
