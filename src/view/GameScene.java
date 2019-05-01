package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameScene extends Scene {
	// constants
	private final int personalInfoSpacing = 10;
	private final int buttonWidth = 200;
	private final int buttonheigt = 50;
	private final int centerBoxPaddingTop = 0;
	private final int centerBoxPaddingRight = 200;
	private final int centerBoxPaddingBottom = 0;
	private final int centerBoxPaddingLeft = 200;
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
	HBox personalInfo;
	VBox centerBox;
	VBox rightBox;
	VBox leftBox;
	CurrencyStonePane currencyStonePane;
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

	/**
	 * Creates the GameScene
	 */
	public GameScene() {
		super(new BorderPane());
		// initialize
		rootPane = new BorderPane();

		// sets the rootPane and handles makeup
		setRoot(rootPane);
		rootPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));

		// creates and sets everything in the right place
		createCenter();
		createLeft();
		createRight();
	}

	/**
	 * Creates the center of the screen containing the following aspects: PersonalGoalCard,
	 * Currencystones, Roundtrack, PublicGoalCards, ToolCards, Dice offer and the necessary buttons.
	 */
	private void createCenter() {
		// initialize everything for personalInfo
		personalInfo = new HBox();
		currencyStonePane = new CurrencyStonePane();
		personalGoalCardPane = new PersonalGoalCardPane();

		// initialize everything for the center box
		centerBox = new VBox();
		centerBox.setMaxWidth(800);
		roundPane = new RoundPane(0, 0);
		goalCardPane = new GoalCardPane();
		goalCardPane.setMinWidth(800);
		toolCardPane = new ToolCardPane();
		toolCardPane.setMinWidth(800);
		dieOfferPane = new DieOfferPane();
		dieOfferPane.setMinWidth(800);
		button = new Button("Button");

		// handles everything regarding the button
		button.setPrefSize(buttonWidth, buttonheigt);

		// adds everything to personal info and handles makeup
		personalInfo.getChildren().addAll(currencyStonePane, personalGoalCardPane);
		personalInfo.setAlignment(Pos.CENTER);
		personalInfo.setSpacing(10);

		// adds everything to the centerBox and handles makeup
		centerBox.getChildren().addAll(personalInfo, roundPane, goalCardPane, toolCardPane, dieOfferPane, button);
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
		glassWindowPane1 = new GlassWindowPane();
		chatPane = new ChatPane();

		// adds everything to the leftBox and handles makeup
		leftBox.getChildren().addAll(glassWindowPane1, chatPane);
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
		glassWindowPane2 = new GlassWindowPane();
		glassWindowPane3 = new GlassWindowPane();
		glassWindowPane4 = new GlassWindowPane();

		// changes the glassWindow size to it's small size
		glassWindowPane2.setSizeSmall();
		glassWindowPane3.setSizeSmall();
		glassWindowPane4.setSizeSmall();

		// adds everything to the rightBox and handles makeup
		rightBox.getChildren().addAll(glassWindowPane2, glassWindowPane3, glassWindowPane4);
		rightBox.setSpacing(rightBoxSpacing);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setPadding(new Insets(rightBoxPaddingTop, rightBoxPaddingRight, rightBoxPaddingBottom, rightBoxPaddingLeft));

		// adds the rightBox to the rootPane
		rootPane.setRight(rightBox);
	}
}
