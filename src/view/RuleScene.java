package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

class RuleScene extends Scene
{
	// constants
	public static final int buttonPaneMinHeight = 50;
	// instance variables
	private RuleDrawPane drawPane;
	private ButtonPane buttonPane;
	private BorderPane rootPane;

	// constructor
	public RuleScene() {
		super(new Pane(), 400, 400);
		drawPane = new RuleDrawPane();
		buttonPane = new ButtonPane(this, drawPane);

		drawPane.prefWidthProperty().bind(widthProperty());
		drawPane.prefHeightProperty().bind(heightProperty());

		buttonPane.prefWidthProperty().bind(widthProperty());

		createRoot();
	}

	private void createRoot() {
		rootPane = new BorderPane();
		rootPane.setCenter(drawPane);
		rootPane.setBottom(buttonPane);
		rootPane.setMinHeight(buttonPaneMinHeight);
		setRoot(rootPane);
	}

}