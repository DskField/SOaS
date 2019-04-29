package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PersonalGoalCardPane extends Pane {
	
	public PersonalGoalCardPane() {
		setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		setPrefSize(100, 100);
	}
}
