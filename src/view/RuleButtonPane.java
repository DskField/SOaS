package view;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

class ButtonPane extends BorderPane {
	// constants
	private final int paneheight = 50;

	// instance variables
	private Button btn_GameProgress;
	private Button btn_PlaceDie;
	private Button btn_ToolCards;

	// constructor
	public ButtonPane(RuleScene myscene, RuleDrawPane drawPane) {
		setPrefHeight(paneheight);
		setMinHeight(paneheight);
		setMaxHeight(paneheight);

		setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
		btn_GameProgress = new Button("Spel verloop");
		btn_PlaceDie = new Button("Dobbelsteen plaatsen");
		btn_ToolCards = new Button("Gereedschapskaarten");

		setLeft(btn_GameProgress);
		setCenter(btn_PlaceDie);
		setRight(btn_ToolCards);

		btn_GameProgress.setOnMouseClicked(e -> drawPane.showProgression());
		btn_PlaceDie.setOnMouseClicked(e -> drawPane.showDiePlacing());
		btn_ToolCards.setOnMouseClicked(e -> drawPane.showToolCard());
	}
}
