package view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameMenuPane extends VBox {
	private Button btn_resume;
	private Button btn_rules;
	private Button btn_toMenu;
	private Button btn_exit;
	private Stage stage;

	// magic numbers
	private int ruleheight = 800;
	private int rulewidth = 800;

	public GameMenuPane() {
		// get the main stage
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
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
		Stage.getWindows().filtered(window -> window.isShowing()).get(0).getScene().getRoot().setDisable(true);

		// eventlistener for escape key to close ingame menu and activate nodes on the
		// game scene again
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					resume();
				}
			}
		});
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
		rules.setMinSize(rulewidth, ruleheight);
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(false);
		rulePopup.show(stage);
		rulePopup.setX((stage.getWidth() - rulePopup.getWidth()) / 2);
		rulePopup.setY((stage.getHeight() - rulePopup.getHeight()) / 2);
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
