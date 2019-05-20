	package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainApplication extends Application {
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		new GameController(this);
		stage.setTitle("Sagrada");
		stage.setFullScreen(true);
		// Remove the exit hint
		stage.setFullScreenExitHint("");
		// This line is to disable the Esc key to make sure you can't exit full screen
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.show();
	}

	public void setScene(Scene scene) {
		stage.setScene(scene);
		stage.setFullScreen(true);
	}

}