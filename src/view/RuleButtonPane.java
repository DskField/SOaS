package view;

import javafx.geometry.Pos;
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

	// instance variables
	private Button btn_GameProgress;
	private Button btn_PlaceDie;
	private Button btn_ToolCards;

	// constructor
	public RuleButtonPane(RuleDrawPane drawPane) {
		setPrefHeight(PANEHEIGHT);

		setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.7), null, null)));

		//setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;-fx-font: normal bold 25px 'serif';");

		btn_GameProgress = new Button("Spel verloop");
		btn_GameProgress.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, null, null)));
		btn_GameProgress.setFont(Font.font("serif", FontWeight.BOLD, 25));
		btn_GameProgress.setTextFill(Color.WHITE);
		btn_PlaceDie = new Button("Dobbelsteen plaatsen");
		btn_PlaceDie.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, null, null)));
		btn_PlaceDie.setFont(Font.font("serif", FontWeight.BOLD, 25));
		btn_PlaceDie.setTextFill(Color.WHITE);
		btn_ToolCards = new Button("Gereedschapskaarten");
		btn_ToolCards.setBackground(new Background(new BackgroundFill(Color.DARKSLATEBLUE, null, null)));
		btn_ToolCards.setFont(Font.font("serif", FontWeight.BOLD, 25));
		btn_ToolCards.setTextFill(Color.WHITE);

		// place the buttons
		setAlignment(Pos.CENTER);
		setSpacing(20);
		getChildren().addAll(btn_GameProgress, btn_PlaceDie, btn_ToolCards);
		// give the buttons actions
		btn_GameProgress.setOnMouseClicked(e -> drawPane.showProgression());
		btn_PlaceDie.setOnMouseClicked(e -> drawPane.showDiePlacing());
		btn_ToolCards.setOnMouseClicked(e -> drawPane.showToolCard());
	}
}
