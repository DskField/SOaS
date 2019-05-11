package view;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PersonalGoalCardPane extends StackPane{
	
	public PersonalGoalCardPane() {
		setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		createCards();
		setPrefSize(240, 160);
	}
	
	private void createCards() {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(240);
		rectangle.setWidth(160);
		rectangle.setFill(Color.GREEN);
		getChildren().add(rectangle);
	}
}
