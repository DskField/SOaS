package view;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameMenuPane extends VBox {
	private Button btn_resume;
	private Button btn_rules;
	private Button btn_toMenu;
	private Button btn_exit;
	private Stage stage;

	public GameMenuPane(Stage stage) {
		this.stage = stage;
		btn_resume = new Button("Hervatten");
		btn_rules = new Button("Regels");
		btn_toMenu = new Button("Terug naar hoofd menu");
		btn_exit = new Button("Afsluiten");
		btn_resume.setOnAction(e -> resume());
		btn_rules.setOnAction(e -> showRules());
		btn_toMenu.setOnAction(e -> toMenu());
		btn_exit.setOnAction(e -> exit());
	}

//TODO niet vergeten om klikken te disablen
	private void resume() {
		// TODO delete this pane
	}

	private void showRules() {
		Popup rulePopup = new Popup();
		RuleScene rules = new RuleScene();
		rules.setMinSize(800, 800);
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(false);
		rulePopup.show(stage.getScene().getWindow());
		rulePopup.setX((stage.getWidth() - rulePopup.getWidth()) / 2);
		rulePopup.setY((stage.getHeight() - rulePopup.getHeight()) / 2);
	}

	private void toMenu() {
		// TODO SCENE SWITCHER HAS TO BE MADE
	}

	private void exit() {
		Platform.exit();
	}
}
