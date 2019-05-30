package view;

import java.util.ArrayList;

import controllers.GameController;
import game.CurrencyStone;
import game.Die;
import game.GameColor;
import game.Message;
import game.Player;
import game.Round;
import game.SpaceGlass;
import game.ToolCard;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GameScene extends Scene {
	// constants
	private static final int MAIN = 0;

	private static final int personalInfoSpacing = 10;

	private static final int buttonWidth = 200;
	private static final int buttonheigt = 50;

	private static final int centerBoxPaddingTop = 0;
	private static final int centerBoxPaddingRight = 100;
	private static final int centerBoxPaddingBottom = 0;
	private static final int centerBoxPaddingLeft = 100;

	private static final int leftBoxSpacing = 10;
	private static final int leftBoxPaddingTop = 0;
	private static final int leftBoxPaddingRight = 0;
	private static final int leftBoxPaddingBottom = 0;
	private static final int leftBoxPaddingLeft = 10;

	private static final int rightBoxSpacing = 10;
	private static final int rightBoxPaddingTop = 0;
	private static final int rightBoxPaddingRight = 10;
	private static final int rightBoxPaddingBottom = 60;
	private static final int rightBoxPaddingLeft = 0;

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
	private HBox buttonBox;
	private CurrencyStonesPane currencyStonesPane;
	private ChatPane chatPane;

	private ArrayList<GlassWindowPane> glassWindowPanes;

	private GoalCardPane[] goalCardPanes;
	private ToolCardPane[] toolCardPanes;
	private PersonalGoalCardPane personalGoalCardPane;
	private RoundPane roundPane;
	private DieOfferPane dieOfferPane;
	private Button shakeButton;
	private Button nextButton;
	private GameController gameController;
	public Stage stage;
	private Label currentPlayerLabel;

	/**
	 * Creates the GameScene
	 */
	public GameScene(GameController gameController) {
		super(new BorderPane());
		// initialize
		rootPane = new BorderPane();
		this.gameController = gameController;
		glassWindowPanes = new ArrayList<GlassWindowPane>();

		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));

		stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);

		// creates and sets everything in the right place
		createCenter();
		createLeft();
		createRight();

		// Listener for escape key to open in game menu
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ESCAPE) {
					Popup gameMenuPopup = new Popup();
					GameMenuPane gameMenu = new GameMenuPane(gameController);
					gameMenuPopup.getContent().add(gameMenu);
					gameMenuPopup.setAutoHide(true);
					gameMenuPopup.show(stage);
					gameMenuPopup.setX(stage.getWidth() / 2);
					gameMenuPopup.setY(stage.getHeight() / 2);
				}
			}
		});
	}

	public void disableDieOfferPane(boolean b) {
		dieOfferPane.setDisable(b);
	}

	public void selectDie(ArrayList<SpaceGlass> available) {
		for (GlassWindowPane glassWindowPane : glassWindowPanes) {
			if (glassWindowPane.getColor() == gameController.getClientPlayer().getColor()) {
				switchGlassWindows(glassWindowPane.getSwitchingNumber());
			}
		}

		removeHighlight();
		glassWindowPanes.get(MAIN).highlightSpaces(available);
	}

	public void removeHighlight() {
		glassWindowPanes.get(MAIN).removeHighlightSpaces();
	}

	public DiePane getSelectedDie() {
		try {
			return (DiePane) focusOwnerProperty().get();
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * gives a list of messages to the ChatPane
	 * 
	 * @param messages ArrayList<Message> list of messages that will be added to the chat.
	 */
	public void updateChat(ArrayList<Message> messages) {
		chatPane.updateChat(messages);
	}

	public void updateGlassWindow(ArrayList<Player> players) {
		for (Player player : players) {
			for (GlassWindowPane glassWindowPane : glassWindowPanes) {
				if (player.getColor() == glassWindowPane.getColor()) {
					glassWindowPane.updateScore(player.getScore());
					glassWindowPane.updateGlassWindow(player.getGlassWindow());
				}
			}
		}
	}

	public void updateTable(ArrayList<Die> dice) {
		dieOfferPane.addDice(dice);
	}

	public void removeDieTable() {
		dieOfferPane.removeDie(getSelectedDie());
	}

	public void updateRoundTrack(Round[] rounds) {
		roundPane.getChildren().clear();
		for (int i = 0; i < rounds.length; i++) {
			roundPane.clear(i + 1);
			for (Die die : rounds[i].getDice()) {
				DiePane diePane = new DiePane(die.getDieId(), die.getDieValue(), die.getDieColor());
				roundPane.addDie(i + 1, diePane);
			}
		}
		roundPane.update();
	}

	public void disableOffer(boolean b) {
		
		dieOfferPane.setDisable(b);
	}

	public void updateTurn(boolean myTurn) {
		if (myTurn && !dieOfferPane.getChildren().isEmpty()) {
			nextButton.setDisable(false);
		} else {
			nextButton.setDisable(true);
		}
	}

	public void updateCurrencyStone() {
		currencyStonesPane.showStones(gameController.getClientPlayer());
	}

	/**
	 * Creates the center of the screen containing the following aspects: PersonalGoalCard,
	 * Currencystones, Roundtrack, PublicGoalCards, ToolCards, Dice offer and the necessary buttons.
	 */

	private void createCenter() {
		// initialize everything for personalInfo
		personalInfo = new VBox();
		currencyStonesPane = new CurrencyStonesPane();
		personalGoalCardPane = new PersonalGoalCardPane();
		personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());
		
		// initialize everything for the cardBox
		cardBox = new HBox();
		PublicCardsBox = new VBox();
		goalCardsBox = new HBox();
		toolCardBox = new HBox();
		goalCardPanes = new GoalCardPane[3];
		toolCardPanes = new ToolCardPane[3];
		buttonBox = new HBox();
		// initialize everything for the center box
		centerBox = new VBox();
		centerBox.setMaxWidth(800);
		roundPane = new RoundPane(0, 0);
		dieOfferPane = new DieOfferPane(gameController);

		shakeButton = new Button("Schudden");
		nextButton = new Button("Beurt klaar");

		// handles everything regarding the button
		shakeButton.setPrefSize(buttonWidth, buttonheigt);
		nextButton.setPrefSize(buttonWidth, buttonheigt);
		dieOfferPane.setDisable(false);
		shakeButton.setDisable(true);
		nextButton.setDisable(true);
		shakeButton.setOnAction(e -> handleShakeButton());
		nextButton.setOnAction(e -> handleNextButton());
		// adds everything to personal info and handles makeup
		personalInfo.getChildren().addAll(personalGoalCardPane, currencyStonesPane);
		personalInfo.setAlignment(Pos.CENTER);
		personalInfo.setSpacing(10);

		// handles everything regarding the cardBox
		// adds goaldCards to the goalCardPanes array
		for (int i = 0; i < 3; i++) {
			goalCardPanes[i] = new GoalCardPane(gameController.getCollectiveGoalCard(i));
		}

		// adds the goalCardPanes to the goalCardBox
		for (GoalCardPane goalCardPane : goalCardPanes) {
			goalCardsBox.getChildren().add(goalCardPane);
		}
		// adds toolCards to the toolCards Array
		for (int i = 0; i < 3; i++) {
			toolCardPanes[i] = new ToolCardPane(gameController.getToolCard(i));
		}
		// adds the toolCardPanes to the toolCardBox
		for (ToolCardPane toolCardPane : toolCardPanes) {
			toolCardBox.getChildren().add(toolCardPane);
		}

		// handles the makeup of the various boxes
		goalCardsBox.setSpacing(10);
		goalCardsBox.setAlignment(Pos.CENTER);
		toolCardBox.setSpacing(10);
		goalCardsBox.setAlignment(Pos.CENTER);
		PublicCardsBox.getChildren().addAll(goalCardsBox, toolCardBox);
		PublicCardsBox.setSpacing(10);
		buttonBox.getChildren().addAll(shakeButton, nextButton);
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER);
		cardBox.getChildren().addAll(personalInfo, PublicCardsBox);
		cardBox.setSpacing(10);
		cardBox.setAlignment(Pos.CENTER_LEFT);
		cardBox.setPrefHeight(480);

		// adds current Player label
		currentPlayerLabel = new Label();
		currentPlayerLabel.setTextFill(Color.RED);
		currentPlayerLabel.setFont(new Font("Arial", 20));

		// adds everything to the centerBox and handles makeup
		centerBox.getChildren().addAll(currentPlayerLabel, roundPane, cardBox, dieOfferPane, buttonBox);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(personalInfoSpacing);
		centerBox.setPadding(new Insets(centerBoxPaddingTop, centerBoxPaddingRight, centerBoxPaddingBottom, centerBoxPaddingLeft));

		// adds the centerBox to the rootPane
		rootPane.setCenter(centerBox);
	}

	private void handleShakeButton() {
		shakeButton.setDisable(true);
		gameController.shakeSack();

	}

	// Handles button
	private void handleNextButton() {
		nextButton.setDisable(true);
		gameController.nextTurn();
	}

	/**
	 * Creates the left column of the screen containing the following aspects: Glaswindow(large), Chat
	 */
	private void createLeft() {
		// Initialize everything for the leftBox
		leftBox = new VBox();
		Player clientPlayer = gameController.getClientPlayer();
		glassWindowPanes.add(new GlassWindowPane(0, clientPlayer, this, gameController));
		chatPane = new ChatPane(gameController);

		// adds everything to the leftBox and handles makeup
		leftBox.getChildren().addAll(glassWindowPanes.get(0), chatPane);
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
		int counter = 1;
		for (Player player : players) {
			if (player.getPlayerID() != clientPlayer.getPlayerID()) {
				System.out.println(counter);
				glassWindowPanes.add(counter, new GlassWindowPane(counter, player, this, gameController));
				glassWindowPanes.get(counter).toggleIsSmall();
				counter++;
			}
		}

		for (GlassWindowPane glassWindowPane : glassWindowPanes) {
			if (glassWindowPanes.indexOf(glassWindowPane) != MAIN) {
				rightBox.getChildren().add(glassWindowPane);
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
		removeHighlight();
		GlassWindowPane temp = glassWindowPanes.get(MAIN);

		leftBox.getChildren().remove(glassWindowPanes.get(MAIN));
		rightBox.getChildren().remove(glassWindowPanes.get(source));

		glassWindowPanes.set(MAIN, glassWindowPanes.get(source));
		glassWindowPanes.set(source, temp);
		glassWindowPanes.get(MAIN).toggleIsSmall();
		glassWindowPanes.get(source).toggleIsSmall();
		glassWindowPanes.get(MAIN).setSwitchingNumber(MAIN);
		glassWindowPanes.get(source).setSwitchingNumber(source);

		rightBox.getChildren().add((source - 1 >= 0) ? source - 1 : 0, glassWindowPanes.get(source));
		leftBox.getChildren().add(MAIN, glassWindowPanes.get(MAIN));

		if (glassWindowPanes.get(MAIN).isClientPlayer()) {
			personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());
		} else {
			personalGoalCardPane.loadCardBack();
		}

		for (Player player : gameController.getPlayers()) {
			if (player.getColor() == glassWindowPanes.get(MAIN).getColor()) {
				currencyStonesPane.showStones(player);
				break;
			}
		}
	}

	public void gameFinish(String winText) {
		rootPane.setDisable(true);

		Label winner = new Label(winText);
		rootPane.setCenter(winner);
	}

	public void updateToolCards(ArrayList<ToolCard> toolCards) {
		for (ToolCardPane toolCardPane : toolCardPanes) {
			for (ToolCard toolCard : toolCards) {// for toolcard make an arralist with currencyStonepaness
				ArrayList<CurrencyStonePane> stonePanes = new ArrayList<>();
				stonePanes.clear();
				Player thisPlayer = null;
				if (!toolCard.getCurrencyStones().isEmpty()) {//if toolcard has currencystones
					for (CurrencyStone stone : toolCard.getCurrencyStones()) {//for every stone that card has make a StonePane and add to ArrayList of stonepanes				
						for (Player player : gameController.getPlayers()) {//Check whose currencystone it is						
							if (stone.getPlayerID() == player.getPlayerID()) {//if playerid's match use that player to get the color
								thisPlayer = player;
							}
						}
						CurrencyStonePane stonePane = new CurrencyStonePane(thisPlayer.getColor());
						stonePanes.add(stonePane);
					}
					if (toolCard.getSeqnr() == toolCardPane.getSeqNr()) {
						toolCardPane.updateStones(stonePanes);
					}
				}
			}
		}
	}

	public void updateCurrentPlayerBorder(GameColor color) {
		for (GlassWindowPane glassWindowPane : glassWindowPanes) {
			if (glassWindowPane.getColor() == color) {
				for (GlassWindowPane glassWindowPane1 : glassWindowPanes) {
					glassWindowPane1.setInactiveBorder();
				}
				glassWindowPane.setActiveBorder();
			}
		}
	}

	public void updateCurrentPlayerLabel(String userName) {
		currentPlayerLabel.setText(userName + " is momenteel aan de beurt");
	}

	public void updateShakeButton(boolean checkStartPlayer) {
		if (checkStartPlayer) {
			shakeButton.setDisable(false);
		}
		
	}
}
