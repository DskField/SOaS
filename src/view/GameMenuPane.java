package view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameMenuPane extends VBox {
	private Button btn_resume;
	private Button btn_rules;
	private Button btn_toMenu;
	private Button btn_exit;
	private Stage stage;

	public GameMenuPane() {
		// get the main stage
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
		setAlignment(Pos.CENTER);
		setSpacing(50);
		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));
		// set size
		setMinSize(stage.getWidth(), stage.getHeight());

		// Give buttons text
		btn_resume = new Button("Hervatten");
		btn_rules = new Button("Regels");
		btn_toMenu = new Button("Terug naar hoofd menu");
		btn_exit = new Button("Afsluiten");
		// give actions to buttons
		btn_resume.setOnAction(e -> resume());
		btn_rules.setOnAction(e -> showRules());
		btn_toMenu.setOnAction(e -> toMenu());
		btn_exit.setOnAction(e -> exit());
		// add button to pane
		getChildren().add(btn_resume);
		getChildren().add(btn_rules);
		getChildren().add(btn_toMenu);
		getChildren().add(btn_exit);
		// disable everything on gamescene
		Stage.getWindows().filtered(window -> window.isShowing()).get(0).getScene().getRoot().setDisable(true);

		// If escape key is pressed it registers it as if btn_resume is clicked
		btn_resume.setCancelButton(true);
	}

	// Resumes game, enables everything in the game en hides the gamemenu
	private void resume() {
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
		stage.getScene().getRoot().setDisable(false);
	}

//loads the rules and hides game menu
	private void showRules() {
		Popup rulePopup = new Popup();
		RulePane rules = new RulePane();
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(false);
		rulePopup.show(stage);
		rulePopup.centerOnScreen();
//		rulePopup.setX((stage.getWidth() - rulePopup.getWidth()) / 2);
//		rulePopup.setY((stage.getHeight() - rulePopup.getHeight()) / 2);
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}

	// exit to main menu by switching the scene to ClientScene and it hides gamemenu
	private void toMenu() {
		ClientScene clientScene = new ClientScene();
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
		stage.setScene(clientScene);

	}

	// exit game to windows
	private void exit() {
		Platform.exit();
	}
}
