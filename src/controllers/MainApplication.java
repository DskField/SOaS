package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainApplication extends Application {
	public static double width;
	public static double height;

	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		ClientController clientController = new ClientController(this);
		stage.setTitle("Sagrada");
		stage.setFullScreen(true);
		// Remove the exit hint
		stage.setFullScreenExitHint("");
		// This line is to disable the Esc key to make sure you can't exit full screen
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.show();
		stage.setOnCloseRequest(e -> clientController.closeApp());

		width = stage.getWidth() / 1920;
		height = stage.getHeight() / 1080;
	}

	public void setScene(Scene scene) {
		stage.setScene(scene);
		stage.setFullScreen(true);
	}
}