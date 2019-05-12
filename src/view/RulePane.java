package view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulePane extends VBox {
	// constants
	public static final int buttonPaneMinHeight = 50;
	// instance variables
	private RuleDrawPane drawPane;
	private ButtonPane buttonPane;

	// constructor
	public RulePane() {
		drawPane = new RuleDrawPane();
		buttonPane = new ButtonPane(this, drawPane);
		// make the draw and buttonpane and add them to this panes
		getChildren().add(drawPane);
		getChildren().add(buttonPane);
		// escape event listener to enable nodes again
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					Stage.getWindows().filtered(window -> window.isShowing()).get(0).getScene().getRoot()
							.setDisable(false);
				}
			}
		});
	}
}