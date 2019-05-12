package view;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ToolCardPane extends HBox {

	public ToolCardPane() {
		setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
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
