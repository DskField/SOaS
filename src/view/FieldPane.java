package view;

import java.util.ArrayList;

import game.GameColor;
import game.GlassWindow;
import game.PatternCard;
import game.SpaceGlass;
import game.SpacePattern;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class FieldPane extends FlowPane {

	private final int patternWidth = 360;
	private final int patternHeight = 280;
	private final static int squareGap = 4;

	private ArrayList<SpacePane> spaces;
	private SpacePane[][] patternSpaces;
	private SpacePane[][] glassSpaces;

	public FieldPane(GlassWindow glassWindow) {
		super(squareGap, squareGap);

		spaces = new ArrayList<SpacePane>();
		patternSpaces = new SpacePane[5][4];
		glassSpaces = new SpacePane[5][4];

		loadPatternCard(glassWindow.getPatternCard());
		loadGlassWindow(glassWindow);

		getChildren().addAll(spaces);

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		setPrefSize(patternWidth, patternHeight);
		setMaxSize(patternWidth, patternHeight);
	}

	private void loadPatternCard(PatternCard patternCard) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				SpacePattern space = patternCard.getSpace(x, y);
				if (space.getValue() > 0 && space.getColor() == GameColor.EMPTY) {
					patternSpaces[x][y] = new SpacePane(x, y, space.getValue(), GameColor.GREY);
				} else {
					patternSpaces[x][y] = new SpacePane(x, y, space.getValue(), space.getColor());
				}
			}
		}
	}

	public void loadGlassWindow(GlassWindow glassWindow) {
		SpaceGlass[][] spaceGlasses = glassWindow.getSpaces();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				SpaceGlass space = spaceGlasses[x][y];
				glassSpaces[x][y] = new SpacePane(x, y, new DiePane(space.getDieId(), space.getDieValue(), space.getDieColor()));
			}
		}

		updateSpaces();
	}

	private void updateSpaces() {
		spaces.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				if (glassSpaces[x][y].getColor() != GameColor.EMPTY) {
					spaces.add(glassSpaces[x][y]);
				} else {
					spaces.add(patternSpaces[x][y]);
				}
			}
		}
	}

	public void resize(boolean isSmall) {
		if (isSmall) {
			setPrefSize(patternWidth / 2, patternHeight / 2);
			setMaxSize(patternWidth / 2, patternHeight / 2);
		} else {
			setPrefSize(patternWidth, patternHeight);
			setMaxSize(patternWidth, patternHeight);
		}

		for (SpacePane space : spaces) {
			space.resize(isSmall);
		}
	}
}
