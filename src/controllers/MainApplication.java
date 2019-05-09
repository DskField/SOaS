package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import view.RuleScene;

public class MainApplication extends Application {
	private Stage stage;
	private GameController gameController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		gameController = new GameController(this);
		stage.setTitle("Sagrada");
		stage.setFullScreen(true);
		// Remove the exit hint
		stage.setFullScreenExitHint("");
		// This line is to disable the Esc key to make sure you can't exit full screen
		// stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.show();
		showRules();
	}

	public void setScene(Scene scene) {
		stage.setScene(scene);
	}

	public void showRules() {
		Popup rulePopup = new Popup();
		RuleScene rules = new RuleScene();
		rules.setMinSize(800, 800);
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(false);
		rulePopup.show(stage.getScene().getWindow());
		rulePopup.setX((stage.getWidth() - rulePopup.getWidth()) / 2);
		rulePopup.setY((stage.getHeight() - rulePopup.getHeight()) / 2);
	}

}