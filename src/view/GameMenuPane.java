package view;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameMenuPane extends VBox {
	private Button btn_resume;
	private Button btn_rules;
	private Button btn_toMenu;
	private Button btn_exit;

	public GameMenuPane() {
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
	}

	private void toMenu() {
		// TODO SCENE SWITCHER HAS TO BE MADE
	}

	private void exit() {
		Platform.exit();
	}
}
