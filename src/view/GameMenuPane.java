package view;

import controllers.GameController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * the menu when you press escape
 *
 */
class GameMenuPane extends VBox {

	private static final int BUTTONWIDTH = 300; 
	private static final int BUTTONHEIGHT = 100;
	private static final int SPACING = 50;
	private static final int FONTSIZE = 25;
	private static final String FONT = "serif";
	private static final String DEFAULT_BUTTON_COLOR = "#483D8B";

	private Button btn_resume;
	private Button btn_rules;
	private Button btn_Cheat;
	private Button btn_toMenu;
	private Button btn_exit;
	private Stage stage;

	private GameController gC;

	public GameMenuPane(GameController gC) {
		this.gC = gC;
		// get the main stage
		this.stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
		// Do some Styling
		setAlignment(Pos.CENTER);
		setSpacing(SPACING);
		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));
		// set size
		setMinSize(stage.getWidth(), stage.getHeight());
		showButtons();
	}

	/**
	 * Shows all them buttons, adds a nice style to them, gives them text and a goal in life
	 */
	private void showButtons() {

		
		// Give buttons text and style
		btn_resume = new Button("Hervatten");
		
		btn_rules = new Button("Regels");

		switch (gC.getCheatMode()) {
		case 0:
			btn_Cheat = new Button("Cheat mode: Off");
			break;
		case 1:
			btn_Cheat = new Button("Cheat mode: On");
			break;
		case 2:
			btn_Cheat = new Button("Cheat mode: Advanced");
		default:
			break;
		}
		
		btn_toMenu = new Button("Terug naar hoofd menu");
		
		btn_exit = new Button("Afsluiten");

		
		// give actions to buttons
		btn_resume.setOnAction(e -> resume());
		btn_rules.setOnAction(e -> showRules());
		btn_Cheat.setOnAction(e -> cycleCheatFunction());
		btn_toMenu.setOnAction(e -> toMenu());
		btn_exit.setOnAction(e -> exit());
		// add button to pane
		getChildren().addAll(btn_resume, btn_rules, btn_Cheat, btn_toMenu, btn_exit);
		
		//Sets default style thats the same for every button like size, and text stuff
		for(Node item: getChildren()) {
			if(item.getTypeSelector().toString().equals("Button")) {
				((Button) item).setBackground(new Background(new BackgroundFill(Color.web(DEFAULT_BUTTON_COLOR), null, null)));
				((Button) item).setMinSize(BUTTONWIDTH, BUTTONHEIGHT);
				((Button) item).setFont(Font.font(FONT, FontWeight.BOLD, FONTSIZE));
				((Button) item).setTextFill(Color.WHITE);
			}
		}
		//Sets differentiating colors
		btn_resume.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		btn_exit.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));


		// If escape key is pressed it registers it as if btn_resume is clicked
		btn_resume.setCancelButton(true);
	}

	/**
	 * Resumes game, enables everything in the game en hides the gamemenu
	 */
	private void resume() {
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}

	/**
	 * loads the rules and hides game menu
	 */
	private void showRules() {
		Popup rulePopup = new Popup();
		RulePane rules = new RulePane(stage.getWidth(), stage.getHeight());
		rulePopup.getContent().add(rules);
		rulePopup.setAutoHide(true);
		rulePopup.show(stage);
		rulePopup.setX(stage.getWidth() / 2);
		rulePopup.setY(stage.getHeight() / 2);
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}

	/**
	 * the button to cycle throught cheat functions, also sets the text on the button
	 */
	private void cycleCheatFunction() {
		gC.cycleCheat();
		switch (gC.getCheatMode()) {
		case 0:
			btn_Cheat.setText("Cheat mode: Off");
			break;
		case 1:
			btn_Cheat.setText("Cheat mode: On");
			break;
		case 2:
			btn_Cheat.setText("Cheat mode: Advanced");
			break;
		default:
			break;
		}
	}

	/**
	 * Go back to client scene
	 */
	private void toMenu() {
		gC.returnToClient();
		Stage.getWindows().filtered(window -> window.isShowing()).get(1).hide();
	}


	/**
	 *  exit game to desktop
	 */
	private void exit() {
		Platform.exit();
	}
}
