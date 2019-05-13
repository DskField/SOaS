package view;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ClientScene extends Scene {

	private BorderPane rootPane;

	public ClientScene() {
		super(new BorderPane());
		// initialize
		rootPane = new BorderPane();

		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
	}

}
