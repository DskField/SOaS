package view;

import game.PatternCard;
import game.PatternCardGenerator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class GlassWindowPane extends BorderPane {
	private final int glassWindowWidth = 400;
	private final int glassWindowHeight = 600;
	private PatternPane patternField;
	
	private PatternCardGenerator yay;
	
	
	
	public GlassWindowPane(boolean small) {
		setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		setPrefSize(glassWindowWidth, glassWindowHeight);
		yay = new PatternCardGenerator();
		setPattern(yay.getCard(), small);
	}
	
	public void addContent(PatternCard pattern, Boolean small) {
		setPattern(pattern, small);
	}
	
	public void setSizeSmall() {
		setPrefSize(glassWindowWidth / 2, glassWindowHeight / 2);
	}
	
	private void setPattern(PatternCard pattern, Boolean small) {
		setBottom(patternField = new PatternPane(pattern, small));
	}
	
	
}
