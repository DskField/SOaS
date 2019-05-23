package view;

import controllers.GameController;
import game.GameColor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SpacePane extends Pane {
	private final int squareSize = 64;
	private double resizeScale = 2.2;

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
				controller.placeDie((SpacePane) event.getSource());
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
		space.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, null, null)));
	}

	public void removeHighlight() {
		space.setBackground(new Background(new BackgroundFill(space.getColor().getColor(), null, null)));
	}
}
