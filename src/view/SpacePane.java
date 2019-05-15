package view;

import game.GameColor;
import javafx.scene.layout.Pane;

public class SpacePane extends Pane {
	private final int squareSize = 64;
	private double resizeScale = 2.2;

	private DiePane space;

	public SpacePane(int eyes, GameColor color) {
		space = new DiePane(eyes, color);
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
}
