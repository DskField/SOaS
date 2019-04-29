package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GlassWindowPane extends Pane {
	private final int glassWindowWidth = 200;
	private final int glassWindowHeight = 400;
	
	
	public GlassWindowPane() {
		setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		setPrefSize(glassWindowWidth, glassWindowHeight);
	}
	
	public void setSizeSmall() {
		setPrefSize(glassWindowWidth / 2, glassWindowHeight / 2);
	}
}
