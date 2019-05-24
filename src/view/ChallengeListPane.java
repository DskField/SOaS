package view;

import java.util.ArrayList;

import client.Challenge;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChallengeListPane extends BorderPane {

	private ClientScene clientscene;
	private ListView<ToggleButton> challengeList;
	private ToggleGroup togglegroup;
	private HandleButton handleButton;
	private ArrayList<Challenge> challenges;

	// Magic Numbers
	final private static int reactionButtonWidth = 180;
	final private static int reactionButtonHeight = 150;
	final private static int buttonBoxSpacing = 40;
	final private static int boxSpacing = 50;
	final private static int statsWidth = 400;
	final private static int statsHeight = 75;
	final private static Color statsBackgroundColor = Color.AQUAMARINE;
	final private static int labelSize = 30;

	public ChallengeListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		this.challengeList = new ListView<ToggleButton>();
		this.togglegroup = new ToggleGroup();
		this.handleButton = new HandleButton();

		createLeft();		
	}

	private void createLeft() {
		challengeList.getItems().clear();
		togglegroup.getToggles().clear();
		challenges = clientscene.getChallenges();
		this.setLeft(null);
		
		for (Challenge chal : challenges) {
			if (chal.getPlayers().get(clientscene.getUsername()).equals("uitgedaagde")) {
				ToggleButton togglebutton = new ToggleButton("Challenge " + chal.getGameID());
				togglebutton.setAlignment(Pos.CENTER);
				togglebutton.setOnMouseClicked(handleButton);
				challengeList.getItems().add(togglebutton);
				togglegroup.getToggles().add(togglebutton);
			}
		}

		this.setLeft(challengeList);
	}
	
	private void createCenter(int idGame) {
		Label challengeInfo = new Label("Uitdaging: Game " + idGame);
		challengeInfo.setMinSize(statsWidth, statsHeight);
		challengeInfo.setMaxSize(statsWidth, statsHeight);
		challengeInfo.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));
		challengeInfo.setAlignment(Pos.CENTER);
		challengeInfo.setFont(Font.font(labelSize));

		Label challengerPlayerInfo = new Label("Uitdager: " + clientscene.getChallengerUsername(idGame));
		challengerPlayerInfo.setMinSize(statsWidth, statsHeight);
		challengerPlayerInfo.setMaxSize(statsWidth, statsHeight);
		challengerPlayerInfo.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));
		challengerPlayerInfo.setAlignment(Pos.BASELINE_CENTER);
		challengerPlayerInfo.setFont(Font.font(labelSize));

		Label gameSizeLabel = new Label("Aantal spelers: " + clientscene.getGameSize(idGame));
		gameSizeLabel.setMinSize(statsWidth, statsHeight);
		gameSizeLabel.setMaxSize(statsWidth, statsHeight);
		gameSizeLabel.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));
		gameSizeLabel.setAlignment(Pos.CENTER);
		gameSizeLabel.setFont(Font.font(labelSize));

		Button acceptButton = new Button("Accepteren");
		acceptButton.setOnAction(e -> handleReactionButton(true, idGame));
		acceptButton.setMinSize(reactionButtonWidth, reactionButtonHeight);
		acceptButton.setMaxSize(reactionButtonWidth, reactionButtonHeight);

		Button declineButton = new Button("Weigeren");
		declineButton.setOnAction(e -> handleReactionButton(false, idGame));
		declineButton.setMinSize(reactionButtonWidth, reactionButtonHeight);
		declineButton.setMaxSize(reactionButtonWidth, reactionButtonHeight);

		VBox labelBox = new VBox();
		labelBox.getChildren().addAll(challengeInfo, challengerPlayerInfo, gameSizeLabel);
		labelBox.setAlignment(Pos.CENTER);

		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(acceptButton, declineButton);
		buttonBox.setSpacing(buttonBoxSpacing);
		buttonBox.setAlignment(Pos.CENTER);

		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(labelBox, buttonBox);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(boxSpacing);

		this.setCenter(centerBox);
	}
	
	private void handleReactionButton(boolean accepted, int idGame) {
		clientscene.handleReaction(accepted, idGame);
		this.setCenter(null);
		clientscene.updateClient();
		createLeft();
	}

	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] lobby = ((ToggleButton) e.getSource()).getText().split(" ");
			createCenter(Integer.parseInt(lobby[1]));
			// TODO TOM only update the variables and set errormessage visible to false
		}
	}
}
