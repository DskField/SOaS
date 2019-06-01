package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class RulePane extends StackPane {
	private static final int WIDTH = 800, HEIGHT = 900;

	// instance variables
	private RuleDrawPane ruleDrawPane;
	private RuleButtonPane buttonPane;
	private Stage stage;

	// constructor
	public RulePane(double width, double height) {
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);

		setMinWidth(stage.getWidth());
		setMinHeight(stage.getHeight());

		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));

		ruleDrawPane = new RuleDrawPane();
		buttonPane = new RuleButtonPane(ruleDrawPane);

		VBox container = new VBox();
		container.getChildren().addAll(ruleDrawPane, buttonPane);
		container.setMaxSize(WIDTH, HEIGHT);
		getChildren().add(container);
	}
}