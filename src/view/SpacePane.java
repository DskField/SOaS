package view;

import game.GameColor;
import javafx.scene.layout.Pane;

public class SpacePane extends Pane {
	private final int squareSize = 64;
	private double resizeScale = 2.2;

	private DiePane space;
	private int x;
	private int y;

	public SpacePane(int x, int y, int eyes, GameColor color) {
		this.x = x;
		this.y = y;
		space = new DiePane(-1, eyes, color);
		space.resize(squareSize);
		getChildren().add(space);
	}

	public SpacePane(int x, int y, DiePane diePane) {
		this.x = x;
		this.y = y;
		space = diePane;
		space.resize(squareSize);
		getChildren().add(space);
	}

	public void resize(boolean isSmall) {
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
}
