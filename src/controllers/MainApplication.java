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
//		 new GameController(this, new PersistenceFacade());
		new ClientController(this);
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
	
	
	//Some test code I use for testing cardgenerator
//	PatternCardGenerator gen = new PatternCardGenerator();
//	PatternCard patternCard = gen.getCard();
//	System.out.println(patternCard.getDifficulty());
//	for (SpacePattern[] row : patternCard.getSpaces()) {
//		for (SpacePattern spacePattern : row) {
//			System.out.println(spacePattern.getXCor() + " " + spacePattern.getYCor() +" value: "+ spacePattern.getValue() + " color: "+ spacePattern.getColor());
//		}		
//	}
		

}