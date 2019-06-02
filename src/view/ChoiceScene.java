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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChoiceScene extends Scene {
	/* CONSTANTS */
	private final int generalSpacing = 10;
	private final Font font = Font.font("arial", 25);
	
	/* VARIABLES */
	private BorderPane root;
	private HBox patternCardBox;
	private GameController gameController;
	private VBox cardBox;

	public ChoiceScene(GameController gameController, ArrayList<PatternCard> patternCards) {
		super(new Pane());
		this.gameController = gameController;
		root = new BorderPane();
		cardBox = new VBox();
		cardBox.setSpacing(generalSpacing);
		cardBox.setAlignment(Pos.CENTER);
		PersonalGoalCardPane personalGoalCardPane = new PersonalGoalCardPane();
		personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());
		patternCardBox = new HBox();
		patternCardBox.setAlignment(Pos.CENTER);
		patternCardBox.setSpacing(generalSpacing);
		cardBox.getChildren().addAll(patternCardBox, personalGoalCardPane);
		root.setCenter(cardBox);
		root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		createPatternCards(patternCards);
		setRoot(root);
	}

	private void createPatternCards(ArrayList<PatternCard> patternCards) {
		for (PatternCard patternCard : patternCards) {
			VBox vBox = new VBox();
			Label name = new Label(patternCard.getName());
			Label difficulity = new Label("" + patternCard.getDifficulty());
			FieldPane pc = new FieldPane(patternCard, gameController);
			pc.disablesSpaces();
			pc.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					pc.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
					Label selectedLabel = new Label("u heeft uw kaart geselecteerd. wachten op andere spelers.");
					selectedLabel.setTextFill(Color.RED);
					selectedLabel.setFont(font);
					cardBox.getChildren().add(selectedLabel);
					root.setDisable(true);
					for (PatternCard patternCard : gameController.getPatternChoices()) {
						if (patternCard.getPatternCardId() == pc.getPatternCardID()) {
							gameController.setClientPlayerPatternCard(patternCard.getPatternCardId());
						}
					}
				}
			});
			vBox.getChildren().addAll(name, pc, difficulity);
			vBox.setAlignment(Pos.CENTER);
			patternCardBox.getChildren().add(vBox);
		}
	}
}
