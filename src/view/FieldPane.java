package view;

import java.util.ArrayList;

import controllers.GameController;
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
	private int patternCardID;

	private boolean isSmall;

	public FieldPane(PatternCard patternCard, GameController gameController) {
		super(squareGap, squareGap);
		this.patternCardID = patternCard.getPatternCardId();
		this.isSmall = false;

		spaces = new ArrayList<SpacePane>();
		patternSpaces = new SpacePane[5][4];
		glassSpaces = new SpacePane[5][4];
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				patternSpaces[x][y] = new SpacePane(x, y, gameController);
				glassSpaces[x][y] = new SpacePane(x, y, gameController);
			}
		}

		loadPatternCard(patternCard);

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
					patternSpaces[x][y].loadPattern(space.getValue(), GameColor.GREY);
				} else {
					patternSpaces[x][y].loadPattern(space.getValue(), space.getColor());
				}
			}
		}

		updateSpaces();
	}

	public void loadGlassWindow(GlassWindow glassWindow) {
		SpaceGlass[][] spaceGlasses = glassWindow.getSpaces();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				SpaceGlass space = spaceGlasses[x][y];
				if (space.getDieId() != 0) {
					glassSpaces[x][y].loadGlass(new DiePane(space.getDieId(), space.getDieValue(), space.getDieColor()));
				} else {
					glassSpaces[x][y].loadGlass(new DiePane(0, 0, GameColor.EMPTY));
				}
			}
		}

		resize(isSmall);
		updateSpaces();
	}

	private void updateSpaces() {
		spaces.clear();
		getChildren().clear();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				if (glassSpaces[x][y].getColor() != GameColor.EMPTY) {
					spaces.add(glassSpaces[x][y]);
				} else {
					spaces.add(patternSpaces[x][y]);
				}
			}
		}

		getChildren().addAll(spaces);
	}

	public void resize(boolean isSmall) {
		this.isSmall = isSmall;

		if (this.isSmall) {
			setPrefSize(patternWidth / 2, patternHeight / 2);
			setMaxSize(patternWidth / 2, patternHeight / 2);
		} else {
			setPrefSize(patternWidth, patternHeight);
			setMaxSize(patternWidth, patternHeight);
		}

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				glassSpaces[x][y].resize(this.isSmall);
				patternSpaces[x][y].resize(this.isSmall);
			}
		}
	}

	public void highlightSpaces(ArrayList<SpaceGlass> toHiglight) {
		for (SpacePane spacePane : spaces) {
			for (SpaceGlass spaceGlass : toHiglight) {
				if (spacePane.getX() == spaceGlass.getXCor() && spacePane.getY() == spaceGlass.getYCor()) {
					spacePane.highlight();
				}
			}
		}
	}

	public void removeHilightSpaces() {
		for (SpacePane spacePane : spaces) {
			spacePane.removeHighlight();
		}
	}

	public int getPatternCardID() {
		return patternCardID;
	}
}
