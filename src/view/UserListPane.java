package view;

import java.util.ArrayList;

import client.Challenge;
import client.User;
import game.GameColor;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UserListPane extends BorderPane {

	private ClientScene clientscene;
	private ListView<HBox> userList;
	private ToggleGroup togglegroup;
	private HandleButton handlebutton;
	private ArrayList<String> users;
	private HandleRadioButton handleradiobutton;
	private ArrayList<CheckBox> inviteCheckBoxGroup;
	private Label errorMessage;
	private Button invitePlayers;

	// Magic Numbers
	final private static Color statsBackgroundColor = Color.AQUAMARINE;
	final private static int statsBoxWidth = 400;
	final private static int statsBoxHeight = 340;
	final private static int usernameLabelSize = 50;
	final private static int textSize = 25;
	final private static int inviteButtonSize = 40;
	final private static int errorMessageSize = 23;
	final private static Color errorMessageColor = Color.RED;

	public UserListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		this.userList = new ListView<HBox>();
		this.togglegroup = new ToggleGroup();
		this.handlebutton = new HandleButton();
		this.handleradiobutton = new HandleRadioButton();
		this.inviteCheckBoxGroup = new ArrayList<CheckBox>();

		createLeft();
		createCenter("");
	}

	public void createLeft() {
		userList.getItems().clear();
		togglegroup.getToggles().clear();
		users = clientscene.getUsers();

		// Remove yourself from selectable list
		users.remove(clientscene.getUsername());

		for (String username : users) {
			CheckBox inviteRadioButton = new CheckBox();
			inviteRadioButton.setOnMouseClicked(handleradiobutton);
			inviteRadioButton.setText(username);

			for (CheckBox box : inviteCheckBoxGroup) {
				if (box.getText().equals(inviteRadioButton.getText()))
					inviteRadioButton.setSelected(true);
			}

			HBox playerLine = new HBox();
			playerLine.getChildren().addAll(inviteRadioButton);
			playerLine.setOnMouseClicked(handlebutton);
			playerLine.setSpacing(10);

			userList.getItems().add(playerLine);
		}

		this.setLeft(userList);
	}

	public void createCenter(String opponentUsername) {
		VBox centerpane = new VBox();

		errorMessage = new Label();
		errorMessage.setFont(Font.font(errorMessageSize));
		errorMessage.setTextFill(errorMessageColor);
		errorMessage.setAlignment(Pos.CENTER);
		errorMessage.setVisible(false);

		invitePlayers = new Button("Spelers uitnodigen");
		invitePlayers.setMinWidth(statsBoxWidth);
		invitePlayers.setMaxWidth(statsBoxWidth);
		invitePlayers.setFont(Font.font(inviteButtonSize));
		invitePlayers.setOnAction(e -> handleInvitePlayers());
		invitePlayers.setDisable(true);

		VBox buttonAndLabel = new VBox();
		buttonAndLabel.getChildren().addAll(errorMessage, invitePlayers);
		buttonAndLabel.setAlignment(Pos.CENTER);

		centerpane.getChildren().addAll(createStatsPane(clientscene.getUser()), createStatsPane(clientscene.getOpponent(opponentUsername)),
				buttonAndLabel);
		centerpane.setAlignment(Pos.CENTER);
		centerpane.setSpacing(50);

		// at first when no opponent is selected hide stats
		if (opponentUsername.equals(""))
			centerpane.getChildren().get(1).setVisible(false);
		else
			centerpane.getChildren().get(1).setVisible(true);

		this.setCenter(centerpane);
	}

	private BorderPane createStatsPane(User user) {
		BorderPane bp = new BorderPane();
		bp.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));

		Label username = new Label(user.getUsername());
		username.setFont(Font.font(usernameLabelSize));

		Label gamesPlayed = new Label("Potjes gespeeld: " + Integer.toString(user.getGamesPlayed()));
		gamesPlayed.setFont(Font.font(textSize));

		Label gamesWon = new Label("Potjes gewonnen: " + Integer.toString(user.getGamesWon()));
		gamesWon.setFont(Font.font(textSize));

		Label gamesLost = new Label("Potjes verloren: " + Integer.toString(user.getGamesLost()));
		gamesLost.setFont(Font.font(textSize));

		Label maxScore = new Label("Hoogste score: " + Integer.toString(user.getMaxScore()));
		maxScore.setFont(Font.font(textSize));

		Label mostPlacedColor = new Label("Meest geplaatste kleur: " + user.getMostPlacedColor().getDatabaseName());
		mostPlacedColor.setFont(Font.font(textSize));

		Label mostPlacedValue = new Label("Meest geplaatste waarde: " + Integer.toString(user.getMostPlacedValue()));
		mostPlacedValue.setFont(Font.font(textSize));

		Label totalOpponents = new Label("Aantal tegenstanders: " + Integer.toString(user.getTotalOpponents()));
		totalOpponents.setFont(Font.font(textSize));

		VBox labelBox = new VBox();
		labelBox.getChildren().addAll(username, gamesPlayed, gamesWon, gamesLost, maxScore, mostPlacedColor, mostPlacedValue, totalOpponents);
		labelBox.setAlignment(Pos.CENTER);
		bp.setCenter(labelBox);
		bp.setMinSize(statsBoxWidth, statsBoxHeight);
		bp.setMaxSize(statsBoxWidth, statsBoxHeight);

		return bp;
	}

	private void handleInvitePlayers() {
		ArrayList<User> result = new ArrayList<>();
		errorMessage.setVisible(false);

		result.add(new User(clientscene.getUsername(), 0, 0, GameColor.EMPTY, 0));
		for (CheckBox box : inviteCheckBoxGroup) {
			for (Challenge chal : clientscene.getChallenges()) {
				if (chal.getPlayers().get(clientscene.getUsername()).equals("uitdager") && chal.getPlayers().containsKey(box.getText())
						&& chal.getPlayers().get(box.getText()).equals("uitgedaagde")) {
					errorMessage.setText("Je hebt " + box.getText() + " recent uitgenodigd");
					errorMessage.setVisible(true);
					return;
				}
			}
			result.add(new User(box.getText(), 0, 0, GameColor.EMPTY, 0));
		}
		clientscene.createGame(result);
		clientscene.updateClient();
	}

	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String user = ((Labeled) ((HBox) e.getSource()).getChildren().get(0)).getText();
			createCenter(user);
			// createCenter(Integer.parseInt(lobby[1]));
			// TODO TOM only update the variables and set errormessage visible to false
		}
	}

	private class HandleRadioButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			errorMessage.setVisible(false);
			// handle deselect
			if (!((CheckBox) e.getSource()).isSelected()) {
				for (int i = 0; i < inviteCheckBoxGroup.size(); i++) {
					if (inviteCheckBoxGroup.get(i).getText().equals(((CheckBox) e.getSource()).getText()))
						inviteCheckBoxGroup.remove(i);
				}
			} else {
				// you can only select 3
				if (inviteCheckBoxGroup.size() == 3) {
					inviteCheckBoxGroup.get(0).setSelected(false);
					inviteCheckBoxGroup.remove(0);
				}
				inviteCheckBoxGroup.add((CheckBox) e.getSource());
			}

			// when no opponents are selected, disable invite button
			if (inviteCheckBoxGroup.size() == 0)
				invitePlayers.setDisable(true);
			else
				invitePlayers.setDisable(false);
		}
	}
}
