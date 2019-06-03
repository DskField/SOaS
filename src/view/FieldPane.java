package view;

import java.util.ArrayList;

import controllers.GameController;
import controllers.MainApplication;
import game.GameColor;
import game.GlassWindow;
import game.PatternCard;
import game.SpaceGlass;
import game.SpacePattern;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class FieldPane extends GridPane {
	/* CONSTANTS */
	private final double patternWidth = 360 * MainApplication.width;
	private final double offset = 4;
	private final double borderWidth = 10;

	/* VARIABLES */
	private SpacePane[][] patternSpaces;
	private SpacePane[][] glassSpaces;
	private int patternCardID;

	public FieldPane(PatternCard patternCard, GameController gameController) {
		this.patternCardID = patternCard.getPatternCardId();

		patternSpaces = new SpacePane[5][4];
		glassSpaces = new SpacePane[5][4];

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				patternSpaces[x][y] = new SpacePane(x, y, (patternWidth / 5) - borderWidth, gameController);
				glassSpaces[x][y] = new SpacePane(x, y, (patternWidth / 5) - borderWidth, gameController);
			}
		}

		loadPatternCard(patternCard);

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		setHgap(offset);
		setVgap(offset);

		setPadding(new Insets(borderWidth));
	}

	/**
	 * Disables the {@code SpacePane}s to prevent any further interaction.
	 */
	public void disablesSpaces() {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				patternSpaces[x][y].setDisable(true);
				glassSpaces[x][y].setDisable(true);
				;
			}
		}
	}

	/**
	 * Takes a given {@code GlasWindow} and takes over its attributes to visualize it
	 * 
	 * @param glassWindow - {@code GlasWindow} that needs to be visualized
	 */
	public void loadGlassWindow(GlassWindow glassWindow) {
		SpaceGlass[][] spaceGlasses = glassWindow.getSpaces();
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				SpaceGlass space = spaceGlasses[x][y];
				if (space.getDieId() != 0) {
					glassSpaces[x][y].loadGlass(new DiePane(space.getDieId(), space.getDieValue(), space.getDieColor()));
				} else {
					glassSpaces[x][y].loadGlass(new DiePane(0, 0, GameColor.EMPTY));
				}
			}
		}

		updateSpaces();
	}

	/**
	 * Gives isSmall to the {@code SpacePane}s to resize it.
	 * 
	 * @param isSmall - {@code boolean}
	 */
	public void resize(boolean isSmall) {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				glassSpaces[x][y].resize(isSmall);
				patternSpaces[x][y].resize(isSmall);
			}
		}
	}

	/**
	 * Highlights the spaces from the cheatfunction
	 * 
	 * @param toHiglight - {@code ArrayList<SpaceGlass>}
	 */
	public void highlightSpaces(ArrayList<SpaceGlass> toHiglight) {
		if (toHiglight == null) {
			return;
		}
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				for (SpaceGlass highlight : toHiglight) {
					if (x == highlight.getXCor() && y == highlight.getYCor()) {
						glassSpaces[x][y].highlight();
					}
				}
			}
		}
	}

	/**
	 * Removes the highlight from the cheat function
	 */
	public void removeHilightSpaces() {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				glassSpaces[x][y].removeHighlight();
			}
		}
	}

	/**
	 * Takes a given {@code PatternCard} and takes over its attributes to visualize the card
	 * 
	 * @param patternCard {@code PatternCard} that need to be visualized
	 */
	private void loadPatternCard(PatternCard patternCard) {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
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

	/**
	 * Updates all the {@code SpacePane}
	 */
	private void updateSpaces() {
		//		spaces.clear();
		getChildren().clear();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 5; x++) {
				if (glassSpaces[x][y].getColor() != GameColor.EMPTY) {
					add(glassSpaces[x][y], x, y);
				} else {
					add(patternSpaces[x][y], x, y);
				}
			}
		}
	}

	//getter
	public int getPatternCardID() {
		return patternCardID;
	}
}
