package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DieOfferPane extends HBox {
	private final int squareSize = 55;
	private int trackSize;
	public DieOfferPane() {
	
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setPrefSize(800, 50);
	}
	private void offering() {
		for (int i = 0; i < trackSize; i++) {
			Rectangle rectangle = new Rectangle(x + i * squareSize, y, squareSize, squareSize);
			rectangle.setStroke(Color.BLACK);
			rectangle.setFill(Color.ALICEBLUE);
			getChildren().add(rectangle);
		}
	}
}
