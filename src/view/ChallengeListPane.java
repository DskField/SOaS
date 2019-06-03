package view;

import java.util.ArrayList;

import controllers.MainApplication;
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
	/* CONSTANTS */
	private final double reactionButtonWidth = 180 * MainApplication.width;
	private final double reactionButtonHeight = 150 * MainApplication.height;
	private final double buttonBoxSpacing = 40 * MainApplication.height;
	private final double boxSpacing = 50 * MainApplication.height;
	private final double statsWidth = 400 * MainApplication.width;
	private final double statsHeight = 75 * MainApplication.height;;
	private final Color statsBackgroundColor = Color.AQUAMARINE;
	private final double labelSize = 30 * MainApplication.height;;

	/* VARIABLES */
	private ClientScene clientscene;

	private ListView<ToggleButton> challengeList;
	private ToggleGroup togglegroup;
	private HandleButton handleButton;
	private ArrayList<Integer> challenges;

	/**
	 * Constructor used to create a {@code ChallengeListPane}
	 * 
	 * @param clientscene - Contains the reference to {@code ClientScene}
	 */
	public ChallengeListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		this.challengeList = new ListView<ToggleButton>();
		this.togglegroup = new ToggleGroup();
		this.handleButton = new HandleButton();

		createLeft();
	}

	/**
	 * Method used to create the List of Challenges
	 */
	public void createLeft() {
		challengeList.getItems().clear();
		togglegroup.getToggles().clear();
		challenges = clientscene.getChallenges();

		for (Integer chal : challenges) {
			ToggleButton togglebutton = new ToggleButton("Uitdaging " + chal);
			togglebutton.setAlignment(Pos.CENTER);
			togglebutton.setOnMouseClicked(handleButton);
			challengeList.getItems().add(togglebutton);
			togglegroup.getToggles().add(togglebutton);
		}

		this.setLeft(challengeList);
	}

	/**
	 * Method used to create the stats pane containing the challengee information
	 * 
	 * @param idGame - {@code int} containing the {@code idGame} for which you did not respond yet
	 */
	private void createCenter(int idGame) {
		Label challengeInfo = new Label("Uitdaging: Spel " + idGame);
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

	/**
	 * Method used to respond to the challenge based on the user input
	 * 
	 * @param accepted - {@code boolean} containing if the user accepted to declined
	 * @param idGame - {@code int} containing the {@code idgame} to which the {@code User} responded too
	 */
	private void handleReactionButton(boolean accepted, int idGame) {
		clientscene.handleReaction(accepted, idGame);
		this.setCenter(null);
		clientscene.updateClient();
		createLeft();
	}

	/**
	 * Method used to create the center when the {@code User} clicks on a challenge
	 */
	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] challenge = ((ToggleButton) e.getSource()).getText().split(" ");
			createCenter(Integer.parseInt(challenge[1]));
		}
	}
}
