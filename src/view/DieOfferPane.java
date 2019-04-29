package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DieOfferPane extends HBox {
	
	public DieOfferPane() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setPrefSize(800, 50);
	}
}
