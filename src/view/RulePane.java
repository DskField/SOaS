package view;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class RulePane extends VBox {
	// instance variables
	private RuleDrawPane drawPane;
	private RuleButtonPane buttonPane;
	private Stage stage;

	// constructor
	RulePane() {
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);

		setMinWidth(stage.getWidth());
		setMinHeight(stage.getHeight());

		setAlignment(Pos.CENTER);
		drawPane = new RuleDrawPane();
		buttonPane = new RuleButtonPane(drawPane);

		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));

		// make the draw and buttonpane and add them to this panes
		getChildren().add(drawPane);
		getChildren().add(buttonPane);
	}
}