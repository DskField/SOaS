package view;

import game.GameColor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CurrencyStonePane extends Pane {
	final int RADIUS = 15;
	final int CENTERX = 18;
	final int CENTERY = 18;

	// Might change it so that it gets a currency stone
	CurrencyStonePane(GameColor color) {
		Shape stone = new Circle(CENTERX, CENTERY, RADIUS, color.getColor());
		getChildren().add(stone);
	}
}
