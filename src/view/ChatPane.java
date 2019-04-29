package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChatPane extends Pane{
	
	public ChatPane() {
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setPrefSize(200, 200);
	}
}
