package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GoalCardPane extends HBox {
	
	public GoalCardPane() {
		setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
		createCards();
		createCards();
		createCards();
		setAlignment(Pos.CENTER);
		setSpacing(10);
	}
	
	private void createCards() {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(240);
		rectangle.setWidth(160);
		rectangle.setFill(Color.GREEN);
		getChildren().add(rectangle);
	}
}
