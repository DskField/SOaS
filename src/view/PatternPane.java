package view;

import java.util.ArrayList;

import game.GameColor;
import game.GlassWindow;
import game.PatternCard;
import game.SpaceGlass;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PatternPane extends FlowPane {

	private final int patternWidth = 360;
	private final int patternHeight = 280;
	private final int squareSize = 64;
	private final static int squareGap = 4;

	private ArrayList<DiePane> spaces;
	private ArrayList<DiePane> dice;

	public PatternPane(PatternCard pattern) {
		super(squareGap, squareGap);
		spaces = new ArrayList<DiePane>();

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setPrefSize(patternWidth, patternHeight);
		setMaxSize(patternWidth, patternHeight);

		addPattern(pattern);
	}

	public void loadGlassWindow(GlassWindow glassWindow) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				SpaceGlass space = glassWindow.getSpace(x, y);
				if (space != null) {
					dice.add(new DiePane(space.getDie().getDieValue(), space.getDie().getDieColor()));
				} else {
					dice.add(null);
				}
			}
		}
	}

	public void addPattern(PatternCard pattern) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				addRectangle(pattern.getSpaceValue(x, y), pattern.getSpaceColor(x, y));
			}
		}
	}

	public void addRectangle(int eyes, GameColor color) {
		DiePane space = new DiePane(eyes, color);
		space.resize(squareSize);
		if (color == GameColor.EMPTY && eyes > 0) {
			space.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		} else {
			space.setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		}
		spaces.add(space);
		getChildren().add(space);
	}

	public void resize(boolean isSmall) {
		if (isSmall) {
			setPrefSize(patternWidth / 2, patternHeight / 2);
			setMaxSize(patternWidth / 2, patternHeight / 2);
			for (DiePane space : spaces) {
				space.resize(squareSize / 2.2);
			}
		} else {
			setPrefSize(patternWidth, patternHeight);
			setMaxSize(patternWidth, patternHeight);
			for (DiePane space : spaces) {
				space.resize(squareSize);
			}
		}
	}
}
