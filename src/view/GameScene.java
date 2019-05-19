package view;

import java.util.ArrayList;

import controllers.GameController;
import game.Message;
import game.Player;
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
	private BorderPane rootPane;
	private HBox cardBox;
	private HBox goalCardsBox;
	private HBox toolCardBox;
	private VBox personalInfo;
	private VBox centerBox;
	private VBox rightBox;
	private VBox leftBox;
	private VBox PublicCardsBox;
	private CurrencyStonesPane currencyStonesPane;
	private ChatPane chatPane;
	private GlassWindowPane mainGlassWindow;
	private GlassWindowPane smallGlassWindow1;
	private GlassWindowPane smallGlassWindow2;
	private GlassWindowPane smallGlassWindow3;
	private GoalCardPane[] goalCardPanes;
	private ToolCardPane[] toolCardPanes;
	private PersonalGoalCardPane personalGoalCardPane;
	private RoundPane roundPane;
	private DieOfferPane dieOfferPane;
	private Button button;
	private GameController gameController;

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
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
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
	 * @param messages ArrayList<Message> list of messages that will be added to the chat.
	 */
	public void updateChat(ArrayList<Message> messages) {
		chatPane.updateChat(messages);
	}

	/**
	 * Creates the center of the screen containing the following aspects: PersonalGoalCard,
	 * Currencystones, Roundtrack, PublicGoalCards, ToolCards, Dice offer and the necessary buttons.
	 */

	private void createCenter() {
		// initialize everything for personalInfo
		personalInfo = new VBox();
		currencyStonesPane = new CurrencyStonesPane(gameController);
		personalGoalCardPane = new PersonalGoalCardPane();
		personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());

		// initialize everything for the cardBox
		cardBox = new HBox();
		PublicCardsBox = new VBox();
		goalCardsBox = new HBox();
		toolCardBox = new HBox();
		goalCardPanes = new GoalCardPane[3];
		toolCardPanes = new ToolCardPane[3];
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
		//adds goaldCards to the goalCardPanes array
		for (int i = 0; i < 3; i++) {
			goalCardPanes[i] = new GoalCardPane(i + 1);
		}
		//adds the goalCardPanes to the goalCardBox
		for (GoalCardPane goalCardPane : goalCardPanes) {
			goalCardsBox.getChildren().add(goalCardPane);
		}
		//adds toolCards to the toolCards Array
		for (int i = 0; i < 3; i++) {
			toolCardPanes[i] = new ToolCardPane(i + 1);
		}
		//adds the toolCardPanes to the toolCardBox
		for (ToolCardPane toolCardPane : toolCardPanes) {
			toolCardBox.getChildren().add(toolCardPane);
		}
		//handles the makeup of the various boxes
		goalCardsBox.setSpacing(10);
		goalCardsBox.setAlignment(Pos.CENTER);
		toolCardBox.setSpacing(10);
		goalCardsBox.setAlignment(Pos.CENTER);
		PublicCardsBox.getChildren().addAll(goalCardsBox, toolCardBox);
		PublicCardsBox.setSpacing(10);
		cardBox.getChildren().addAll(personalInfo, PublicCardsBox);
		cardBox.setAlignment(Pos.CENTER_LEFT);
		cardBox.setPrefHeight(480);

		// adds everything to the centerBox and handles makeup
		centerBox.getChildren().addAll(roundPane, cardBox, dieOfferPane, button);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(personalInfoSpacing);
		centerBox.setPadding(new Insets(centerBoxPaddingTop, centerBoxPaddingRight, centerBoxPaddingBottom, centerBoxPaddingLeft));

		// adds the centerBox to the rootPane
		rootPane.setCenter(centerBox);
	}

	/**
	 * Creates the left column of the screen containing the following aspects: Glaswindow(large), Chat
	 */
	private void createLeft() {
		// Initialize everything for the leftBox
		leftBox = new VBox();
		Player clientPlayer = gameController.getClientPlayer();
		mainGlassWindow = new GlassWindowPane(0, clientPlayer, this);
		chatPane = new ChatPane(gameController);

		// adds everything to the leftBox and handles makeup
		leftBox.getChildren().addAll(mainGlassWindow, chatPane);
		leftBox.setAlignment(Pos.BOTTOM_CENTER);
		leftBox.setSpacing(leftBoxSpacing);
		leftBox.setPadding(new Insets(leftBoxPaddingTop, leftBoxPaddingRight, leftBoxPaddingBottom, leftBoxPaddingLeft));

		// adds the leftBox to the rootPane
		rootPane.setLeft(leftBox);
	}

	/**
	 * Creates the right column on the screen containing the following aspects: 3 Glaswindows(small)
	 */
	private void createRight() {
		// Initialize everything for the rightBox
		rightBox = new VBox();
		ArrayList<Player> players = gameController.getPlayers();
		Player clientPlayer = gameController.getClientPlayer();
		for (Player player : players) {
			if (player.getPlayerID() != clientPlayer.getPlayerID()) {
				if (smallGlassWindow1 == null) {
					smallGlassWindow1 = new GlassWindowPane(1, player, this);
					smallGlassWindow1.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow1);
				} else if (smallGlassWindow2 == null) {
					smallGlassWindow2 = new GlassWindowPane(2, player, this);
					smallGlassWindow2.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow2);
				} else if (smallGlassWindow3 == null) {
					smallGlassWindow3 = new GlassWindowPane(3, player, this);
					smallGlassWindow3.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow3);
				}
			}
		}

		// adds everything to the rightBox and handles makeup
		rightBox.setSpacing(rightBoxSpacing);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setPadding(new Insets(rightBoxPaddingTop, rightBoxPaddingRight, rightBoxPaddingBottom, rightBoxPaddingLeft));

		// adds the rightBox to the rootPane
		rootPane.setRight(rightBox);
	}

	public void switchGlassWindows(int source) {
		leftBox.getChildren().remove(mainGlassWindow);
		GlassWindowPane temp = mainGlassWindow;
		switch (source) {
		case 1:
			rightBox.getChildren().remove(smallGlassWindow1);
			mainGlassWindow = smallGlassWindow1;
			smallGlassWindow1 = temp;
			mainGlassWindow.toggleIsSmall();
			smallGlassWindow1.toggleIsSmall();
			mainGlassWindow.setSwitchingNumber(0);
			smallGlassWindow1.setSwitchingNumber(1);
			rightBox.getChildren().add(0, smallGlassWindow1);
			break;
		case 2:
			rightBox.getChildren().remove(smallGlassWindow2);
			mainGlassWindow = smallGlassWindow2;
			smallGlassWindow2 = temp;
			mainGlassWindow.toggleIsSmall();
			smallGlassWindow2.toggleIsSmall();
			mainGlassWindow.setSwitchingNumber(0);
			smallGlassWindow2.setSwitchingNumber(2);
			rightBox.getChildren().add(1, smallGlassWindow2);
			break;
		case 3:
			rightBox.getChildren().remove(smallGlassWindow3);
			mainGlassWindow = smallGlassWindow3;
			smallGlassWindow3 = temp;
			mainGlassWindow.toggleIsSmall();
			smallGlassWindow3.toggleIsSmall();
			mainGlassWindow.setSwitchingNumber(0);
			smallGlassWindow3.setSwitchingNumber(3);
			rightBox.getChildren().add(2, smallGlassWindow3);
			break;
		}
		leftBox.getChildren().add(0, mainGlassWindow);

	}
}
