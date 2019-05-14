package view;

import java.util.ArrayList;

import controllers.GameController;
import game.GameColor;
import game.Message;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameScene extends Scene {
	// constants
	private final int personalInfoSpacing = 10;
	private final int buttonWidth = 200;
	private final int buttonheigt = 50;
	private final int centerBoxPaddingTop = 0;
	private final int centerBoxPaddingRight = 100;
	private final int centerBoxPaddingBottom = 0;
	private final int centerBoxPaddingLeft = 100;
	private final int leftBoxSpacing = 10;
	private final int leftBoxPaddingTop = 0;
	private final int leftBoxPaddingRight = 0;
	private final int leftBoxPaddingBottom = 0;
	private final int leftBoxPaddingLeft = 10;
	private final int rightBoxSpacing = 10;
	private final int rightBoxPaddingTop = 0;
	private final int rightBoxPaddingRight = 10;
	private final int rightBoxPaddingBottom = 60;
	private final int rightBoxPaddingLeft = 0;
	// variables
	BorderPane rootPane;
	HBox cardBox;
	VBox personalInfo;
	VBox centerBox;
	VBox rightBox;
	VBox leftBox;
	VBox PublicCardsBox;
	CurrencyStonesPane currencyStonesPane;
	ChatPane chatPane;
	GlassWindowPane glassWindowPane1;
	GlassWindowPane glassWindowPane2;
	GlassWindowPane glassWindowPane3;
	GlassWindowPane glassWindowPane4;
	PersonalGoalCardPane personalGoalCardPane;
	RoundPane roundPane;
	GoalCardPane goalCardPane;
	ToolCardPane toolCardPane;
	DieOfferPane dieOfferPane;
	Button button;
	GameController gameController;
	Stage stage;

	/**
	 * Creates the GameScene
	 */
	public GameScene(GameController gameController) {
		super(new BorderPane());
		// initialize
		rootPane = new BorderPane();
		this.gameController = gameController;
		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));

		// creates and sets everything in the right place
		createCenter();
		createLeft();
		createRight();

		// Listener for escape key to open in game menu
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
					Popup gameMenuPopup = new Popup();
					GameMenuPane gameMenu = new GameMenuPane();
					gameMenuPopup.getContent().add(gameMenu);
					gameMenuPopup.setAutoHide(true);
					gameMenuPopup.show(stage);
					gameMenuPopup.setX(stage.getWidth() / 2);
					gameMenuPopup.setY(stage.getHeight() / 2);
				}
			}
		});

	}

	/**
	 * gives a list of messages to the ChatPane
	 * 
	 * @param messages ArrayList<Message> list of messages that will be added to the
	 *                 chat.
	 */
	public void updateChat(ArrayList<Message> messages) {
		chatPane.updateChat(messages);
	}

	/**
	 * Creates the center of the screen containing the following aspects:
	 * PersonalGoalCard, Currencystones, Roundtrack, PublicGoalCards, ToolCards,
	 * Dice offer and the necessary buttons.
	 */

	private void createCenter() {
		// initialize everything for personalInfo
		personalInfo = new VBox();
		currencyStonesPane = new CurrencyStonesPane(gameController);
		personalGoalCardPane = new PersonalGoalCardPane();

		// initialize everything for the cardBox
		cardBox = new HBox();
		PublicCardsBox = new VBox();
		goalCardPane = new GoalCardPane();
		toolCardPane = new ToolCardPane();

		// initialize everything for the center box
		centerBox = new VBox();
		centerBox.setMaxWidth(800);
		roundPane = new RoundPane(0, 0);
		dieOfferPane = new DieOfferPane();
		dieOfferPane.setMinWidth(800);
		button = new Button("Button");

		// handles everything regarding the button
		button.setPrefSize(buttonWidth, buttonheigt);

		// adds everything to personal info and handles makeup
		personalInfo.getChildren().addAll(personalGoalCardPane, currencyStonesPane);
		personalInfo.setAlignment(Pos.CENTER);
		personalInfo.setSpacing(10);

		// handles everything regarding the cardBox
		PublicCardsBox.getChildren().addAll(goalCardPane, toolCardPane);
		PublicCardsBox.setSpacing(10);
		cardBox.getChildren().addAll(personalInfo, PublicCardsBox);
		cardBox.setAlignment(Pos.CENTER_LEFT);
		cardBox.setPrefHeight(480);

		// adds everything to the centerBox and handles makeup
		centerBox.getChildren().addAll(roundPane, cardBox, dieOfferPane, button);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(personalInfoSpacing);
		centerBox.setPadding(
				new Insets(centerBoxPaddingTop, centerBoxPaddingRight, centerBoxPaddingBottom, centerBoxPaddingLeft));

		// adds the centerBox to the rootPane
		rootPane.setCenter(centerBox);
	}

	/**
	 * Creates the left column of the screen containing the following aspects:
	 * Glaswindow(large), Chat
	 */
	private void createLeft() {
		// Initialize everything for the leftBox
		leftBox = new VBox();
		glassWindowPane1 = new GlassWindowPane(GameColor.RED, gameController.getClientPlayer().getGlassWindow());
		chatPane = new ChatPane(gameController);

		// adds everything to the leftBox and handles makeup
		leftBox.getChildren().addAll(glassWindowPane1, chatPane);
		leftBox.setAlignment(Pos.BOTTOM_CENTER);
		leftBox.setSpacing(leftBoxSpacing);
		leftBox.setPadding(
				new Insets(leftBoxPaddingTop, leftBoxPaddingRight, leftBoxPaddingBottom, leftBoxPaddingLeft));

		// adds the leftBox to the rootPane
		rootPane.setLeft(leftBox);
	}

	/**
	 * Creates the right column on the screen containing the following aspects: 3
	 * Glaswindows(small)
	 */
	private void createRight() {
		// Initialize everything for the rightBox
		rightBox = new VBox();
		glassWindowPane2 = new GlassWindowPane(GameColor.GREEN, gameController.getClientPlayer().getGlassWindow());
		glassWindowPane3 = new GlassWindowPane(GameColor.BLUE, gameController.getClientPlayer().getGlassWindow());
		glassWindowPane4 = new GlassWindowPane(GameColor.YELLOW, gameController.getClientPlayer().getGlassWindow());

		// changes the glassWindow size to it's small size
		glassWindowPane2.toggleIsSmall();
		glassWindowPane3.toggleIsSmall();
		glassWindowPane4.toggleIsSmall();

		// adds everything to the rightBox and handles makeup
		rightBox.getChildren().addAll(glassWindowPane2, glassWindowPane3, glassWindowPane4);
		rightBox.setSpacing(rightBoxSpacing);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setPadding(
				new Insets(rightBoxPaddingTop, rightBoxPaddingRight, rightBoxPaddingBottom, rightBoxPaddingLeft));

		// adds the rightBox to the rootPane
		rootPane.setRight(rightBox);
	}
}
