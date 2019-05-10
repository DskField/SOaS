package view;

import game.GameColor;
import game.PatternCard;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PatternPane extends FlowPane{

	private final int patternWidth = 270;// - 10
	private final int patternHeight = 220;// - 10
	private final int squareSize = 50;
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
		DiePane die = new DiePane(eyes, color);
		die.resize(squareSize);
		if(color.getColor() == Color.WHITE && eyes > 0) {
			die.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		}
		else {
			die.setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		}
		getChildren().add(die);
	}
	
	public void addSmallRectangle(int eyes, GameColor color) {
		DiePane die = new DiePane(eyes, color);
		die.resize(squareSize / 2.1);
		if(color.getColor() == Color.WHITE && eyes > 0) {
			die.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		}
		else {
			die.setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		}
		getChildren().add(die);
	}
}
