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

class GameMenuPane extends VBox {

	private static final int BUTTONWIDTH = 300;
	private static final int BUTTONHEIGHT = 100;
	private static final int SPACING = 50;

	private Button btn_resume;
	private Button btn_rules;
	private Button btn_Cheat;
	private Button btn_toMenu;
	private Button btn_exit;
	private Stage stage;

	GameMenuPane() {

		// get the main stage
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
		// Do some Styling
		setAlignment(Pos.CENTER);
		setSpacing(SPACING);
		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));
		// set size
		setMinSize(stage.getWidth(), stage.getHeight());
		showButtons();
	}
	private void showButtons() {
		// Give buttons text and style
		btn_resume = new Button("Hervatten");
		btn_resume.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		btn_resume.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font: normal bold 25px 'serif'; ");
		btn_rules = new Button("Regels");
		btn_rules.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		btn_rules.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		btn_Cheat = new Button("Cheat mode: ");
		btn_Cheat.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		btn_Cheat.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		btn_toMenu = new Button("Terug naar hoofd menu");
		btn_toMenu.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		btn_toMenu.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		btn_exit = new Button("Afsluiten");
		btn_exit.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		btn_exit.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		// give actions to buttons
		btn_resume.setOnAction(e -> resume());
		btn_rules.setOnAction(e -> showRules());
		btn_toMenu.setOnAction(e -> toMenu());
		btn_exit.setOnAction(e -> exit());
		// add button to pane
		getChildren().addAll(btn_resume, btn_rules,btn_Cheat, btn_toMenu, btn_exit);

		// If escape key is pressed it registers it as if btn_resume is clicked
		btn_resume.setCancelButton(true);
	}

	// Resumes game, enables everything in the game en hides the gamemenu
	private void resume() {
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}

	//loads the rules and hides game menu
	private void showRules() {
		Popup rulePopup = new Popup();
		RulePane rules = new RulePane();
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(true);
		rulePopup.show(stage);
		rulePopup.setX(stage.getWidth() / 2);
		rulePopup.setY(stage.getHeight() / 2);
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}

	//TODO Back to client
	private void toMenu() {
		//		ClientScene clientScene = new ClientScene();
		//		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
		//		stage.setScene(clientScene);

	}

	// exit game to windows
	private void exit() {
		Platform.exit();
	}
}
