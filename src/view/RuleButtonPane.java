package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class RuleButtonPane extends HBox {
	// constants
	private static final int PANEHEIGHT = 100;
	private static final int SPACING = 20;
	private static final int FONTSIZE = 25;
	private static final String FONT = "serif";
	private static final String BUTTONCOLOR = "#483D8B";
	private static final String PRESSEDBUTTON = "#241E45";
	// instance variables
	private Button btn_GameProgress;
	private Button btn_PlaceDie;
	private Button btn_ToolCards;
	
	private RuleDrawPane drawPane;

	// constructor
	public RuleButtonPane(RuleDrawPane drawPane) {
		this.drawPane = drawPane;
		setPrefHeight(PANEHEIGHT);

		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));

		btn_GameProgress = new Button("Spel verloop");
		//setting
		btn_PlaceDie = new Button("Dobbelsteen plaatsen");

		btn_ToolCards = new Button("Gereedschapskaarten");


		// place the buttons
		setAlignment(Pos.CENTER);
		setSpacing(SPACING);
		getChildren().addAll(btn_GameProgress, btn_PlaceDie, btn_ToolCards);
		
		//Set style for all nodes of type Button
		for(Node item: getChildren()) {
			if(item.getTypeSelector().toString().equals("Button")) {
				((Button) item).setBackground(new Background(new BackgroundFill(Color.web(BUTTONCOLOR), null, null)));
				((Button) item).setFont(Font.font(FONT, FontWeight.BOLD, FONTSIZE));
				((Button) item).setTextFill(Color.WHITE);
			}
		}
		// give the buttons actions
		btn_GameProgress.setOnMouseClicked(e -> handle_btn_GameProgress());
		btn_PlaceDie.setOnMouseClicked(e -> handle_btn_PlaceDie());
		btn_ToolCards.setOnMouseClicked(e -> handle_btn_ToolCards());
		

	}
	
	/**
	 * Shows gameprogression rules and highlights button
	 */
	private void handle_btn_GameProgress() {
		for(Node item: getChildren()) {
			if(item.getTypeSelector().toString().equals("Button")) {
				((Button) item).setBackground(new Background(new BackgroundFill(Color.web(BUTTONCOLOR), null, null)));
			}
		}
		btn_GameProgress.setBackground(new Background(new BackgroundFill(Color.web(PRESSEDBUTTON), null, null)));
		
		drawPane.showProgression();
	}
	/**
	 * Shows Dieplacing rules and highlights button
	 */
	private void handle_btn_PlaceDie() {
		for(Node item: getChildren()) {
			if(item.getTypeSelector().toString().equals("Button")) {
				((Button) item).setBackground(new Background(new BackgroundFill(Color.web(BUTTONCOLOR), null, null)));
			}
		}
		btn_PlaceDie.setBackground(new Background(new BackgroundFill(Color.web(PRESSEDBUTTON), null, null)));
		drawPane.showDiePlacing();
	}
	/**
	 * Shows Toolcard rules and highlights button
	 */
	private void handle_btn_ToolCards() {
		for(Node item: getChildren()) {
			if(item.getTypeSelector().toString().equals("Button")) {
				((Button) item).setBackground(new Background(new BackgroundFill(Color.web(BUTTONCOLOR), null, null)));

			}
		}
		btn_ToolCards.setBackground(new Background(new BackgroundFill(Color.web(PRESSEDBUTTON), null, null)));
		drawPane.showToolCard();
	}

}
