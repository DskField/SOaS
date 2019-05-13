package view;

import game.GameColor;
import game.PatternCard;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PatternPane extends FlowPane{

	private final int patternWidth = 360;
	private final int patternHeight = 280;
	private final int squareSize = 64;
	private static int squareGap = 4;
	
	public PatternPane(PatternCard pattern, Boolean isSmall) {
		super(squareGap, squareGap);
		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		if(isSmall == true) {
			setPrefSize(patternWidth / 2, patternHeight / 2);
			setMaxSize(patternWidth / 2, patternHeight / 2);
		}
		else {
			setPrefSize(patternWidth, patternHeight);
			setMaxSize(patternWidth, patternHeight);
		}
		addPattern(pattern, isSmall);
	}
	
	public void addPattern(PatternCard pattern, Boolean small) {
		if(small == true) {
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 5; x++) {
					addSmallRectangle(pattern.getSpace(x, y).getValue(), pattern.getSpace(x, y).getPatternColor());
					
				}
			}
		}else {
			for(int y = 0; y < 4; y++) {
				for(int x = 0; x < 5; x++) {
					addRectangle(pattern.getSpace(x, y).getValue(), pattern.getSpace(x, y).getPatternColor());
					
				}
			}
		}
	}
	
	public void addRectangle(int eyes, GameColor color) {
		PatternSpacePane space = new PatternSpacePane(eyes, color);
		space.resize(squareSize);
		if(color.getColor() == Color.WHITE && eyes > 0) {
			space.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		}
		else {
			space.setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		}
		getChildren().add(space);
	}
	
	public void addSmallRectangle(int eyes, GameColor color) {
		PatternSpacePane space = new PatternSpacePane(eyes, color);
		space.resize(squareSize / 2.2);
		if(color.getColor() == Color.WHITE && eyes > 0) {
			space.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		}
		else {
			space.setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		}
		getChildren().add(space);
	}
	
	public void addDie(int position, int eyes, GameColor color) {
		((PatternSpacePane) getChildren().get(position)).setDie(eyes, color);
	}
}
