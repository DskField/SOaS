package controllers;

import javafx.application.Application;
import javafx.stage.Stage;
import view.GameScene;

public class MainApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		GameScene gameScene = new GameScene();
		stage.setTitle("Sagrada");
		stage.setScene(gameScene);
		stage.setFullScreen(true);
		stage.show();
	}

}
