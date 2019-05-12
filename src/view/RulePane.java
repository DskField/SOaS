package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RulePane extends VBox {
	// instance variables
	private RuleDrawPane drawPane;
	private ButtonPane buttonPane;
	private Stage stage;

	// constructor
	public RulePane() {
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);

		setMinWidth(stage.getWidth());
		setMinHeight(stage.getHeight());

		setAlignment(Pos.CENTER);
		drawPane = new RuleDrawPane();
		buttonPane = new ButtonPane(drawPane);

		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));

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