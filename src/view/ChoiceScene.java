package view;

import java.util.ArrayList;

import controllers.GameController;
import controllers.MainApplication;
import game.PatternCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	private final Font FONT = Font.font("arial", 25);
	private final double BUTTONWIDTH = 300 * MainApplication.width;
	private final double BUTTONHEIGHT = 100 * MainApplication.height;;
	private final Color DEFAULT_BUTTON_COLOR = Color.web("#483D8B");

	/* VARIABLES */
	private BorderPane root;
	private HBox patternCardBox;
	private GameController gameController;
	private VBox cardBox;
	private VBox buttons;

	public ChoiceScene(GameController gameController, ArrayList<PatternCard> patternCards) {
		super(new Pane());
		//initialize everything
		this.gameController = gameController;
		root = new BorderPane();
		cardBox = new VBox();
		buttons = new VBox();
		PersonalGoalCardPane personalGoalCardPane = new PersonalGoalCardPane();

		//handles makeup
		cardBox.setSpacing(generalSpacing);
		cardBox.setAlignment(Pos.CENTER);
		personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());
		patternCardBox = new HBox();
		patternCardBox.setAlignment(Pos.CENTER);
		patternCardBox.setSpacing(generalSpacing);
		root.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));

		Button menuButton = new Button("Terug naar het menu");
		menuButton.setOnAction(e -> gameController.returnToClient());
		menuButton.setBackground(new Background(new BackgroundFill(DEFAULT_BUTTON_COLOR, null, null)));
		menuButton.setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
		menuButton.setFont(FONT);
		menuButton.setTextFill(Color.WHITE);
		buttons.getChildren().add(menuButton);
		buttons.setAlignment(Pos.CENTER);

		cardBox.getChildren().addAll(patternCardBox, personalGoalCardPane);
		root.setCenter(cardBox);
		root.setBottom(buttons);
		createPatternCards(patternCards);
		setRoot(root);
	}

	/**
	 * This {@code ArrayList<PatternCard>} will be shown to the user to choose his {@code PatternCard}
	 * from.
	 * 
	 * @param patternCards - {@code ArrayList<PatterCard>} that will be added to the
	 * {@code ChoiceScene}.
	 */
	private void createPatternCards(ArrayList<PatternCard> patternCards) {
		for (PatternCard patternCard : patternCards) {
			//creates the card.
			VBox vBox = new VBox();
			Label name = new Label(patternCard.getName());
			Label difficulity = new Label("" + patternCard.getDifficulty());
			FieldPane pc = new FieldPane(patternCard, gameController);

			vBox.getChildren().addAll(name, pc, difficulity);
			vBox.setAlignment(Pos.CENTER);
			patternCardBox.getChildren().add(vBox);

			//disables the spaces so that they are not clickable.
			pc.disablesSpaces();

			//handle event when the PatterCard is clicked.
			pc.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					//handles visual feed back that the user has selected their PatternCard.
					pc.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
					Label selectedLabel = new Label("U heeft uw kaart geselecteerd. Wachten op andere spelers.");
					selectedLabel.setTextFill(Color.RED);
					selectedLabel.setFont(FONT);
					cardBox.getChildren().add(selectedLabel);

					//disables the the root pane to prevent the user from interacting any further with the pane.
					cardBox.setDisable(true);

					//gives the chosen PatternCard to the GameController for further handling.
					for (PatternCard patternCard : gameController.getPatternChoices()) {
						if (patternCard.getPatternCardId() == pc.getPatternCardID()) {
							gameController.setClientPlayerPatternCard(patternCard.getPatternCardId());
						}
					}
				}
			});
		}
	}
}
