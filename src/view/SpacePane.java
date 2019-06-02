package view;

import controllers.GameController;
import game.GameColor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SpacePane extends Pane {
	/* CONSTANTS */
	private final int squareSize = 64;
	private final double resizeScale = 2.2;

	/* VARIABLESS */
	private DiePane space;
	private int x;
	private int y;

	private boolean isSmall;

	public SpacePane(int x, int y, GameController controller) {
		this.x = x;
		this.y = y;

		space = new DiePane(0, 0, GameColor.EMPTY);

		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SpacePane space = (SpacePane) event.getSource();
				controller.placeDie(space.getX(), space.getY());
			}
		});
	}

	public void loadPattern(int eyes, GameColor color) {
		space = new DiePane(-1, eyes, color);
		space.resize(squareSize);
		getChildren().add(space);
	}

	public void loadGlass(DiePane diePane) {
		space = diePane;
		resize(isSmall);
		getChildren().clear();
		getChildren().add(space);
	}

	public void resize(boolean isSmall) {
		this.isSmall = isSmall;

		if (isSmall) {
			space.resize(squareSize / resizeScale);
		} else {
			space.resize(squareSize);
		}
	}

	public GameColor getColor() {
		return space.getColor();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void highlight() {
		space.setBorder(new Border(new BorderStroke(Color.TURQUOISE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
	}

	public void removeHighlight() {
		space.setBorder(null);
	}
}
