package view;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

class RuleButtonPane extends BorderPane {
	// constants
	private static final int PANEHEIGHT = 50;
	private static final int PANEWIDTH = 800;

	// instance variables
	private Button btn_GameProgress;
	private Button btn_PlaceDie;
	private Button btn_ToolCards;

	// constructor
	RuleButtonPane(RuleDrawPane drawPane) {
		// set the size
		setPrefHeight(PANEHEIGHT);
		setMinHeight(PANEHEIGHT);
		setMaxHeight(PANEHEIGHT);
		setPrefWidth(PANEWIDTH);
		setMinWidth(PANEWIDTH);
		setMaxWidth(PANEWIDTH);
		// set background and make the button
		setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		btn_GameProgress = new Button("Spel verloop");
		btn_GameProgress.setStyle(
				"-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		btn_PlaceDie = new Button("Dobbelsteen plaatsen");
		btn_PlaceDie.setStyle(
				"-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		btn_ToolCards = new Button("Gereedschapskaarten");
		btn_ToolCards.setStyle(
				"-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");
		// place the buttons
		setLeft(btn_GameProgress);
		setCenter(btn_PlaceDie);
		setRight(btn_ToolCards);
		// give the buttons actions
		btn_GameProgress.setOnMouseClicked(e -> drawPane.showProgression());
		btn_PlaceDie.setOnMouseClicked(e -> drawPane.showDiePlacing());
		btn_ToolCards.setOnMouseClicked(e -> drawPane.showToolCard());
	}
}
