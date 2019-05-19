package view;

import game.GameColor;
import javafx.scene.effect.Glow;
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

	public SpacePane(int x, int y) {
		this.x = x;
		this.y = y;

		space = new DiePane(0, 0, GameColor.EMPTY);
	}

	public void loadPattern(int eyes, GameColor color) {
		space = new DiePane(-1, eyes, color);
		space.resize(squareSize);
		getChildren().add(space);
	}

	public void loadGlass(DiePane diePane) {
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

	public void highlight() {
		Glow glow = new Glow(3);
		space.setBackground(new Background(new BackgroundFill(Color.TURQUOISE, null, null)));
		setEffect(glow);

	}
}
