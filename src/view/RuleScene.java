package view;

import javafx.scene.layout.VBox;

public class RuleScene extends VBox {
	// constants
	public static final int buttonPaneMinHeight = 50;
	// instance variables
	private RuleDrawPane drawPane;
	private ButtonPane buttonPane;

	// constructor
	public RuleScene() {
		drawPane = new RuleDrawPane();
		buttonPane = new ButtonPane(this, drawPane);

//		drawPane.prefWidthProperty().bind(widthProperty());
//		drawPane.prefHeightProperty().bind(heightProperty());
//
//		buttonPane.prefWidthProperty().bind(widthProperty());

		getChildren().add(drawPane);
		getChildren().add(buttonPane);
		drawPane.setOnMouseClicked(e -> close());

	}

	private void close() {

	}

}