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
	private HBox buttonBox;
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
		if (smallGlassWindow1 != null && gameController.getClientPlayer().getColor() == smallGlassWindow1.getColor()) {
			switchGlassWindows(1);
		} else if (smallGlassWindow2 != null
				&& gameController.getClientPlayer().getColor() == smallGlassWindow2.getColor()) {
			switchGlassWindows(2);
		} else if (smallGlassWindow3 != null
				&& gameController.getClientPlayer().getColor() == smallGlassWindow3.getColor()) {
			switchGlassWindows(3);
		}
		removeHighlight();
		mainGlassWindow.highlightSpaces(available);
	}

	public void removeHighlight() {
		mainGlassWindow.removeHighlightSpaces();
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
	 * @param messages
	 *            ArrayList<Message> list of messages that will be added to the
	 *            chat.
	 */
	public void updateChat(ArrayList<Message> messages) {
		chatPane.updateChat(messages);
	}

	public void updateGlassWindow(ArrayList<Player> players) {
		for (Player player : players) {
			if (player.getColor() == mainGlassWindow.getColor()) {
				mainGlassWindow.updateScore(player.getScore());
				mainGlassWindow.updateGlassWindow(player.getGlassWindow());
			} else if (player.getColor() == smallGlassWindow1.getColor()) {
				smallGlassWindow1.updateScore(player.getScore());
				smallGlassWindow1.updateGlassWindow(player.getGlassWindow());
			} else if (player.getColor() == smallGlassWindow2.getColor()) {
				smallGlassWindow2.updateScore(player.getScore());
				smallGlassWindow2.updateGlassWindow(player.getGlassWindow());
			} else if (player.getColor() == smallGlassWindow3.getColor()) {
				smallGlassWindow3.updateScore(player.getScore());
				smallGlassWindow3.updateGlassWindow(player.getGlassWindow());
			}
		}
	}

	public void updateTable(ArrayList<Die> dice) {
		dieOfferPane.addDice(dice);
	}

	public void removeDieTable() {
		dieOfferPane.removeDie(getSelectedDie());
	}

	public void updateScore(ArrayList<Player> players) {
		for (Player player : players) {
			if (mainGlassWindow != null && mainGlassWindow.getColor() == player.getColor()) {
				mainGlassWindow.updateScore(player.getScore());
			} else if (smallGlassWindow1 != null && smallGlassWindow1.getColor() == player.getColor()) {
				smallGlassWindow1.updateScore(player.getScore());
			} else if (smallGlassWindow2 != null && smallGlassWindow2.getColor() == player.getColor()) {
				smallGlassWindow2.updateScore(player.getScore());
			} else if (smallGlassWindow3 != null && smallGlassWindow3.getColor() == player.getColor()) {
				smallGlassWindow3.updateScore(player.getScore());
			}
		}
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
		if (myTurn) {
			nextButton.setDisable(false);
		} else {
			nextButton.setDisable(true);
		}
	}
	public void updateCurrencyStone() {
		currencyStonesPane.showStones(gameController.getClientPlayer());
	}
		
	/**
	 * Creates the center of the screen containing the following aspects:
	 * PersonalGoalCard, Currencystones, Roundtrack, PublicGoalCards, ToolCards,
	 * Dice offer and the necessary buttons.
	 */

	private void createCenter() {
		// initialize everything for personalInfo
		personalInfo = new VBox();
		currencyStonesPane = new CurrencyStonesPane(gameController.getClientPlayer());
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
		nextButton.setDisable(false);
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

		// adds everything to the centerBox and handles makeup
		centerBox.getChildren().addAll(currentPlayerLabel, roundPane, cardBox, dieOfferPane, buttonBox);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(personalInfoSpacing);
		centerBox.setPadding(
				new Insets(centerBoxPaddingTop, centerBoxPaddingRight, centerBoxPaddingBottom, centerBoxPaddingLeft));

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
	 * Creates the left column of the screen containing the following aspects:
	 * Glaswindow(large), Chat
	 */
	private void createLeft() {
		// Initialize everything for the leftBox
		leftBox = new VBox();
		Player clientPlayer = gameController.getClientPlayer();
		mainGlassWindow = new GlassWindowPane(0, clientPlayer, this, gameController);
		chatPane = new ChatPane(gameController);

		// adds everything to the leftBox and handles makeup
		leftBox.getChildren().addAll(mainGlassWindow, chatPane);
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
		ArrayList<Player> players = gameController.getPlayers();
		Player clientPlayer = gameController.getClientPlayer();
		for (Player player : players) {
			if (player.getPlayerID() != clientPlayer.getPlayerID()) {
				if (smallGlassWindow1 == null) {
					smallGlassWindow1 = new GlassWindowPane(1, player, this, gameController);
					smallGlassWindow1.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow1);
				} else if (smallGlassWindow2 == null) {
					smallGlassWindow2 = new GlassWindowPane(2, player, this, gameController);
					smallGlassWindow2.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow2);
				} else if (smallGlassWindow3 == null) {
					smallGlassWindow3 = new GlassWindowPane(3, player, this, gameController);
					smallGlassWindow3.toggleIsSmall();
					rightBox.getChildren().add(smallGlassWindow3);
				}
			}
		}

		// adds everything to the rightBox and handles makeup
		rightBox.setSpacing(rightBoxSpacing);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setPadding(
				new Insets(rightBoxPaddingTop, rightBoxPaddingRight, rightBoxPaddingBottom, rightBoxPaddingLeft));

		// adds the rightBox to the rootPane
		rootPane.setRight(rightBox);
	}

	public void switchGlassWindows(int source) {
		mainGlassWindow.removeHighlightSpaces();
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

		if (mainGlassWindow.isClientPlayer()) {
			personalGoalCardPane.loadPersonalGoalCardImage(gameController.getClientPlayer().getPersonalGoalCard());
		} else {
			personalGoalCardPane.loadCardBack();
		}

		for (Player player : gameController.getPlayers()) {
			if (player.getColor() == mainGlassWindow.getColor()) {
				currencyStonesPane.showStones(player);
				break;
			}
		}
	}

	public void gameFinish(String winText) {
		Label winner = new Label(winText);
		rootPane.setCenter(winner);
		rootPane.setDisable(true);
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
		if (mainGlassWindow.getColor() == color) {
			mainGlassWindow.setActiveBorder();
			smallGlassWindow1.setInactiveBorder();
			smallGlassWindow2.setInactiveBorder();
			smallGlassWindow3.setInactiveBorder();
		} else if (smallGlassWindow1.getColor() == color) {
			smallGlassWindow1.setActiveBorder();
			mainGlassWindow.setInactiveBorder();
			smallGlassWindow2.setInactiveBorder();
			smallGlassWindow3.setInactiveBorder();
		} else if (smallGlassWindow2.getColor() == color) {
			smallGlassWindow2.setActiveBorder();
			mainGlassWindow.setInactiveBorder();
			smallGlassWindow1.setInactiveBorder();
			smallGlassWindow3.setInactiveBorder();
		} else if (smallGlassWindow3.getColor() == color) {
			smallGlassWindow3.setActiveBorder();
			mainGlassWindow.setInactiveBorder();
			smallGlassWindow1.setInactiveBorder();
			smallGlassWindow2.setInactiveBorder();
		}
	}
	
	public void updateCurrentPlayerLabel(String userName) {
		currentPlayerLabel.setText(userName + " is momenteel aan de beurt");
	}

	public void updateShakeButton(boolean checkStartPlayer) {
		if (checkStartPlayer) {
			System.out.println("GAMESCENE: " + checkStartPlayer);
			shakeButton.setDisable(false);
		}
	}
}
