package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ToolCardPane extends HBox {

	public ToolCardPane() {
		setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
		setPrefSize(800, 200);
	}
}
