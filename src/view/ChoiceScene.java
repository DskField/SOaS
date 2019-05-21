package view;

import java.util.ArrayList;

import controllers.GameController;
import game.PatternCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ChoiceScene extends Scene {
	private BorderPane root;
	private HBox cards;
	private GameController gameController;


	public ChoiceScene(GameController gameController, ArrayList<PatternCard> patternCards) {
		super(new Pane());
		this.gameController = gameController;
		root = new BorderPane();
		cards = new HBox();
		cards.setAlignment(Pos.CENTER);
		cards.setSpacing(10);
		root.setCenter(cards);
		root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		createPatternCards(patternCards);
		setRoot(root);
	}
	
	private void createPatternCards(ArrayList<PatternCard> patternCards) {
		for(PatternCard patternCard : patternCards) {
			VBox vBox = new VBox();
			Label name = new Label(patternCard.getName());
			Label difficulity = new Label("" + patternCard.getDifficulty());
			FieldPane pc = new FieldPane(patternCard);
			pc.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for(PatternCard patternCard : gameController.getPatternChoices()) {
						if(patternCard.getPatternCardId() == pc.getPatternCardID()) {
							gameController.setClientPlayerPaternCard(patternCard.getPatternCardId());
						}
					}
					
				}
			});
			vBox.getChildren().addAll(name, pc, difficulity);
			vBox.setAlignment(Pos.CENTER);
			cards.getChildren().add(vBox);
			
		}
	}
	
}

